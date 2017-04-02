package com.yuanwhy.simple.sharding.rule;

/**
 * Created by yuanwhy on 17/4/2.
 */
public abstract class AbstractShardingRule implements ShardingRule {

    private String fieldNameForDb;

    private String fieldNameForTable;


    public String getFieldNameForDb() {
        return fieldNameForDb;
    }

    public void setFieldNameForDb(String fieldForDbName) {
        this.fieldNameForDb = fieldForDbName;
    }

    public String getFieldNameForTable() {
        return fieldNameForTable;
    }

    public void setFieldNameForTable(String fieldForTableName) {
        this.fieldNameForTable = fieldForTableName;
    }

}
