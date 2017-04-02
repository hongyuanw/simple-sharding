package com.yuanwhy.simple.sharding.rule;

/**
 * Created by yuanwhy on 17/4/2.
 */
public abstract class AbstractShardingRule implements ShardingRule {

    private String fieldForDbName;

    private String fieldForTableName;


    public String getFieldNameForDb() {
        return fieldForDbName;
    }

    public void setFieldForDbName(String fieldForDbName) {
        this.fieldForDbName = fieldForDbName;
    }

    public String getFieldNameForTable() {
        return fieldForTableName;
    }

    public void setFieldForTableName(String fieldForTableName) {
        this.fieldForTableName = fieldForTableName;
    }

}
