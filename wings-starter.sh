#!/bin/bash
cat <<'EOF'
#################################################
# 使用`ln -s`把此脚本软连接到`执行目录/workdir`，
# 其同名`env`如（wings-starter.env）会被自动载入。
# `BOOT_CNF|BOOT_ARG|JAVA_ARG`内变量可被延时求值，
# 使用`'`为延时求值，使用`"`为立即求值。
#################################################
EOF

################ modify the following params ################
USER_RUN="$USER" # 用来启动程序的用户。
PORT_RUN=''      # 默认端口，空时
ARGS_RUN="start" # 默认参数。空时使用$1
BOOT_JAR="$2"    # 主程序。通过env覆盖
BOOT_OUT=''      # 控制台日志，默认 $BOOT_JAR.out
BOOT_LOG=''      # 程序日志，需要外部指定
BOOT_PID=''      # 主程序pid，默认 $BOOT_JAR.pid
BOOT_CNF=''      # 外部配置。通过env覆盖
BOOT_ARG=''      # 启动参数。通过env覆盖
JAVA_ARG='
-server
-Duser.timezone=Asia/Shanghai
-Djava.awt.headless=true
-Dfile.encoding=UTF-8

-Xms6G
-Xmx6G
-XX:MetaspaceSize=128m
-XX:+UseG1GC
-XX:MaxGCPauseMillis=200
-XX:ParallelGCThreads=8
-XX:ConcGCThreads=8
-XX:InitiatingHeapOccupancyPercent=70

-XX:HeapDumpPath=${BOOT_JAR}.heap
-Xloggc:${BOOT_JAR}.gc
-XX:+PrintGC
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
'

#-XX:+UseConcMarkSweepGC
#-XX:NewSize=256m
#-XX:MaxNewSize=256m
#-XX:+CMSClassUnloadingEnabled
#-XX:MaxTenuringThreshold=5
#-XX:+HeapDumpOnOutOfMemoryError
#-XX:-OmitStackTraceInFastThrow

################ NO NEED to modify the following ################

################ script dir ################
this_file="$0"
cd $(dirname $this_file) || exit
echo -e "\e[0;32mINFO: ==== work dir ==== \e[m"
pwd

# load env
thie_envf=$(basename "$this_file" | sed -r 's/\.\w+/.env/g')
if [[ -f "$thie_envf" ]]; then
    echo -e "\e[0;32mINFO: load env file, $thie_envf ==== \e[m"
    source "$thie_envf"
fi

# calc env
BOOT_CNF=$(eval "echo \"$BOOT_CNF\"")
BOOT_ARG=$(eval "echo \"$BOOT_ARG\"")
JAVA_ARG=$(eval "echo \"$JAVA_ARG\"")

# check user
if [[ "$USER_RUN" != "$USER" ]]; then
    echo -e "\e[0;31mERROR: need user $USER_RUN to run\e[m"
    exit
fi

# check java
echo -e "\e[0;32mINFO: ==== java version ==== \e[m"

if ! java -version; then
    echo -e "\e[0;31mERROR: can not found 'java' in the $PATH\e[m"
    exit
fi

# check jar
if [[ ! -f "$BOOT_JAR" ]]; then
    BOOT_JAR=$(find . -type f -name "$BOOT_JAR" | head -n 1)
fi
if [[ ! -f "$BOOT_JAR" ]]; then
    echo -e "\e[0;31mERROR: can not found jar file, $BOOT_JAR\e[m"
    exit
fi

# check out
JAR_NAME=$(basename "$BOOT_JAR")
if [[ "$BOOT_OUT" == "" ]]; then
    BOOT_OUT=${JAR_NAME}.out
fi

# check pid
if [[ "$BOOT_PID" == "" ]]; then
    BOOT_PID=${JAR_NAME}.pid
fi

# check ps
#count=$(ps -ef -u $USER_RUN | grep -E "java.+$BOOT_JAR " | grep -v grep | wc -l)
count=$(pgrep -fc -u "$USER_RUN"  "jar +$BOOT_JAR ")

# check arg
if [[ "$1" != "" ]]; then
    ARGS_RUN="$1"
fi
case "$ARGS_RUN" in
    start)
        if [[ ${count} == 0 ]]; then
            if [ "$BOOT_CNF" != "" ];then
                BOOT_ARG="--spring.config.location=$BOOT_CNF $BOOT_ARG"
            fi
            if [ "$PORT_RUN" != "" ];then
                BOOT_ARG="--server.port=$PORT_RUN $BOOT_ARG"
            fi
            echo -e "\e[0;32mINFO: boot-jar=$BOOT_JAR \e[m"
            echo -e "\e[0;32mINFO: boot-pid=$BOOT_PID \e[m"
            echo -e "\e[0;32mINFO: boot-log=$BOOT_LOG \e[m"
            echo -e "\e[0;32mINFO: boot-out=$BOOT_OUT \e[m"
            echo -e "\e[0;32mINFO: boot-arg=$BOOT_ARG \e[m"
            echo -e "\e[0;32mINFO: java-arg=$JAVA_ARG \e[m"

            if [[ -f "${BOOT_OUT}" ]];then
                echo -e "\e[0;33mNOTE: backup old output \e[m"
                mv "${BOOT_OUT}" "${BOOT_OUT}.$(date '+%y%m%d-%H%M%S')"
            fi

            nohup java ${JAVA_ARG} -jar ${BOOT_JAR} ${BOOT_ARG} > ${BOOT_OUT} 2>&1 &
            echo $! > "$BOOT_PID"
            sleep 2
        else
            echo -e "\e[0;31mERROR: already $count running of $JAR_NAME\e[m"
        fi
        echo -e "\e[0;33mNOTE: current process about $JAR_NAME \e[m"
        pgrep -af "$JAR_NAME"

        if [[ -f "$BOOT_LOG" ]];then
            echo -e "\e[0;33mNOTE: tail 20 lines of log-file= $BOOT_LOG \e[m"
            tail -n 20 "$BOOT_LOG"
        fi
        echo -e "\e[0;33mNOTE: tail current output, Ctrl-C to skip \e[m"
        tail -n 50 -f "$BOOT_OUT"
        ;;

    stop)
        if [[ ${count} == 0 ]]; then
            echo -e "\e[0;33mNOTE: not found running $JAR_NAME\e[m"
        else
            echo -e "\e[0;33mNOTE: current process about $JAR_NAME \e[m"
            pgrep -af "$JAR_NAME"
            timeout=60
            pid=$(cat "$BOOT_PID")
            cpid=$(pgrep -f "$JAR_NAME")
            if [[ "$pid" != "$cpid" ]]; then
                echo -e "\e[0;31mWARN: pid not match, proc-pid=$cpid, file-pid=$pid\e[m"
                echo -e "\e[0;31mWARN: press <y> to kill $cpid, ohters to kill $pid\e[m"
                read -r yon
                if [[ "$yon" == "y" ]]; then
                  pid=$cpid
                fi
            fi
            echo -e "\e[0;33mNOTE: killing boot.pid=$pid of $JAR_NAME\e[m"
            kill "$pid"

            icon=''
            for (( i = 0; i < timeout; i++)); do
                for((j=0;j< 10;j++));do
                    printf "[%ds][%-60s]\r" "$i" "$icon"
                    if [[ ${#icon} -ge 60 ]]; then
                        icon=''
                    else
                        icon='#'${icon}
                    fi
                    sleep 0.1
                done
                if [[ $(pgrep -fc -u "$USER_RUN"  "jar +$BOOT_JAR ") == 0 ]]; then
                    echo -e "\e[0;33mNOTE: successfully stop in $i seconds, pid=$pid of $JAR_NAME\e[m"
                    exit
                fi
            done
            echo -e "\e[0;31mWARN: stopping timeout[${timeout}s], pid=$pid\e[m"
            echo -e "\e[0;31mWARN: need manually check the ${JAR_NAME}\e[m"
            pgrep -af "$JAR_NAME"
            echo -e "\e[0;33mNOTE: <ENTER> to 'kill -9 $pid', <Ctrl-C> to exit\e[m"
            read -r
            kill -9 "$pid"
        fi
        ;;

    status)
        if [[ ${count} == 0 ]]; then
            echo -e "\e[0;33mNOTE: not found running $JAR_NAME\e[m"
        else
            echo -e "\e[0;33mNOTE: last 20 lines of output=$BOOT_OUT\e[m"
            tail -n 20 "$BOOT_OUT"
            if [[ -f "$BOOT_LOG" ]];then
                echo -e "\e[0;33mNOTE: tail 20 lines of log-file= $BOOT_LOG \e[m"
                tail -n 20 "$BOOT_LOG"
            fi
            pid=$(cat "$BOOT_PID")
            echo -e "\e[0;33mNOTE: boot.pid=$pid \e[m"
            echo -e "\e[0;33mNOTE: current process aoubt $JAR_NAME \e[m"
            pgrep -af "$JAR_NAME"
            cpid=$(pgrep -f "$JAR_NAME")
            if [[ "$pid" != "$cpid" ]]; then
                echo -e "\e[0;31mWARN: pid not match, proc-pid=$cpid, file-pid=$pid\e[m"
            fi
        fi
        ;;

    *)
        echo -e '\e[0;31mERROR: use start|stop|status\e[m'
        echo -e '\e[0;31meg ./wings-starter.sh start\e[m'
esac
