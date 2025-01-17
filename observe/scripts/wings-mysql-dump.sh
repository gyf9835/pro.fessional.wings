#!/bin/bash
THIS_VERSION=2022-02-14

cat << EOF
#################################################
# Version $THIS_VERSION # test on Mac and Lin
# 通过 mysqldump 生成 'db-ts' 开头的以下文件，
- {db-ts}-main.sql 主表
- {db-ts}-logs.sql log表
- {db-ts}-tbl.log dump的表及结果信息
- {db-ts}-tip.txt scp及restore手册

# Usage $0 db [cnf] [opt]
- db - 需要dump的database，必填
- cnf - 配置文件，参考'--defaults-extra-file'
- opt - dump参数，如 '--no-data'
# option 详细参考client和mysqldump段
- https://dev.mysql.com/doc/refman/8.0/en/option-files.html
#################################################
EOF

database=$1
extracnf=$2
dumpopts=${*:3}
if [[ "$database" == "" ]]; then
  echo -e "\033[0;31mWARN: need param-1=database to dump\033[m"
  echo "./wings-mysql-dump.sh database wings-mysql-client.cnf --no-data"
  echo "defaults-extra-file example"
cat << 'EOF'
[client]
protocol=tcp
host=127.0.0.1
port=3306
user=trydofor
password=moilioncircle

[mysqldump]
#column-statistics=0
max-allowed-packet=64M
net-buffer-length=64k
set-gtid-purged=OFF
single-transaction
EOF
  exit
fi

confopts=""
if [[ -f "$extracnf" ]]; then
  echo -e "\033[0;33mNOTE: defaults-extra-file \033[m"
  grep -E "^(host|port|user)" "$extracnf"
  confopts=--defaults-extra-file=$extracnf
else
  echo -e "\033[0;31mNOTE: use mysql default(my.cnf), something like\033[m"
fi

###
dump_head="${database}_$(date '+%y%m%d%H%M%S')"
dump_main_file="$dump_head-main.sql"
dump_logs_file="$dump_head-logs.sql"
dump_tbl_file="$dump_head-tbl.log"
dump_tip_file="$dump_head.tip"
dump_tar_file="$dump_head.tgz"
dump_md5_file="$dump_head.md5"

unalias mysql >/dev/null 2>&1
unalias mysqldump >/dev/null 2>&1

# shellcheck disable=SC2086
if ! mysql $confopts -D "$database" -N -e "show tables" > "$dump_tbl_file"; then
  echo -e "\033[37;41;1mERROR: failed to show tables of $database \033[0m"
  rm -rf "$dump_tbl_file"
  exit
fi

logs_cnt=$(grep -cE '\$|__' "$dump_tbl_file")
if [[ $logs_cnt == 0 ]]; then
  echo "no logs tables to dump"
  echo "-- no logs tables to dump" > "$dump_logs_file"
else
  echo -e "\033[0;33mNOTE: dump logs tables without data, count=$logs_cnt\033[m"

  # shellcheck disable=SC2046,SC2086
  if mysqldump $confopts $dumpopts --no-data \
  "$database" $(grep -E '\$|__' "$dump_tbl_file") > "$dump_logs_file"; then
    echo "successfully dump logs"
  else
    echo -e "\033[37;41;1mERROR: failed to dump logs \033[0m"
    exit
  fi
fi

main_cnt=$(grep -cvE '\$|__' "$dump_tbl_file")
if [[ $main_cnt == 0 ]]; then
  echo "no main tables to dump"
  echo "-- no main tables to dump" > "$dump_main_file"
else
  echo -e "\033[0;33mNOTE: dump main tables with data, count=$main_cnt\033[m"
  # shellcheck disable=SC2046,SC2086
  if mysqldump $confopts $dumpopts \
  "$database" $(grep -vE '\$|__' "$dump_tbl_file") > "$dump_main_file"; then
    echo "successfully dump main"
  else
    echo -e "\033[37;41;1mERROR: failed to dump main \033[0m"
    exit
  fi
fi

echo -e "\033[0;33mNOTE: dump file $dump_head\033[m"
echo >> "$dump_tbl_file"

# shellcheck disable=SC2010
ls -lsh |grep "$dump_head" | tee -a "$dump_tbl_file"

echo -e "\033[0;33mNOTE: tips for zip, scp, restore \033[m"
tee -a "$dump_tip_file" << EOF
md5sum -c $dump_md5_file

tar -tzf $dump_tar_file
tar -xzf $dump_tar_file
tar -xzf $dump_tar_file $dump_tip_file

scp ${dump_head}.* trydofor@moilioncircle:/data/mysql-dump/

unalias mysql
newdb="$dump_head"

# with progress
cat $dump_logs_file $dump_main_file \\
| pv -Ipert \\
| sed -E 's/DEFINER=[^*]+/DEFINER=CURRENT_USER/g' \\
| mysql $confopts \\
--init-command="CREATE DATABASE IF NOT EXISTS \$newdb; use \$newdb;"

# nohup
nohup \\
cat $dump_logs_file $dump_main_file \\
| sed -E 's/DEFINER=[^*]+/DEFINER=CURRENT_USER/g' \\
| mysql $confopts \\
--init-command="CREATE DATABASE IF NOT EXISTS \$newdb; use \$newdb;" \\
&
EOF

echo -e "\033[0;33mNOTE: tar files into $dump_tar_file \033[m"
tar -czf "$dump_tar_file" "$dump_tip_file" "$dump_tbl_file" "$dump_logs_file" "$dump_main_file" \
&& md5sum "$dump_tar_file" | tee "$dump_md5_file" \
&& rm -f "$dump_tbl_file" "$dump_logs_file" "$dump_main_file"

