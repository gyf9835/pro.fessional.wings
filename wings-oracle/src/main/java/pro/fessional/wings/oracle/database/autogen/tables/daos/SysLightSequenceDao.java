/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.oracle.database.autogen.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pro.fessional.wings.oracle.database.autogen.tables.SysLightSequence;
import pro.fessional.wings.oracle.database.autogen.tables.records.SysLightSequenceRecord;
import pro.fessional.wings.oracle.service.lightid.LightIdAware;


/**
 * The table <code>wings.SYS_LIGHT_SEQUENCE</code>.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9",
        "schema version:2019051201"
    },
    date = "2019-06-03T13:01:45.550Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class SysLightSequenceDao extends DAOImpl<SysLightSequenceRecord, pro.fessional.wings.oracle.database.autogen.tables.pojos.SysLightSequence, Record2<String, Integer>> implements LightIdAware {

    /**
     * Create a new SysLightSequenceDao without any configuration
     */
    public SysLightSequenceDao() {
        super(SysLightSequence.SYS_LIGHT_SEQUENCE, pro.fessional.wings.oracle.database.autogen.tables.pojos.SysLightSequence.class);
    }

    /**
     * Create a new SysLightSequenceDao with an attached configuration
     */
    @Autowired
    public SysLightSequenceDao(Configuration configuration) {
        super(SysLightSequence.SYS_LIGHT_SEQUENCE, pro.fessional.wings.oracle.database.autogen.tables.pojos.SysLightSequence.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Record2<String, Integer> getId(pro.fessional.wings.oracle.database.autogen.tables.pojos.SysLightSequence object) {
        return compositeKeyRecord(object.getSeqName(), object.getBlockId());
    }

    /**
     * Fetch records that have <code>SEQ_NAME IN (values)</code>
     */
    public List<pro.fessional.wings.oracle.database.autogen.tables.pojos.SysLightSequence> fetchBySeqName(String... values) {
        return fetch(SysLightSequence.SYS_LIGHT_SEQUENCE.SEQ_NAME, values);
    }

    /**
     * Fetch records that have <code>BLOCK_ID IN (values)</code>
     */
    public List<pro.fessional.wings.oracle.database.autogen.tables.pojos.SysLightSequence> fetchByBlockId(Integer... values) {
        return fetch(SysLightSequence.SYS_LIGHT_SEQUENCE.BLOCK_ID, values);
    }

    /**
     * Fetch records that have <code>NEXT_VAL IN (values)</code>
     */
    public List<pro.fessional.wings.oracle.database.autogen.tables.pojos.SysLightSequence> fetchByNextVal(Long... values) {
        return fetch(SysLightSequence.SYS_LIGHT_SEQUENCE.NEXT_VAL, values);
    }

    /**
     * Fetch records that have <code>STEP_VAL IN (values)</code>
     */
    public List<pro.fessional.wings.oracle.database.autogen.tables.pojos.SysLightSequence> fetchByStepVal(Integer... values) {
        return fetch(SysLightSequence.SYS_LIGHT_SEQUENCE.STEP_VAL, values);
    }

    /**
     * Fetch records that have <code>COMMENTS IN (values)</code>
     */
    public List<pro.fessional.wings.oracle.database.autogen.tables.pojos.SysLightSequence> fetchByComments(String... values) {
        return fetch(SysLightSequence.SYS_LIGHT_SEQUENCE.COMMENTS, values);
    }
}
