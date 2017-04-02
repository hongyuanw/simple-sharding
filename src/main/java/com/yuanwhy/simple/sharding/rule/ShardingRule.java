package com.yuanwhy.simple.sharding.rule;

/**
 * Created by yuanwhy on 17/3/28.
 */
public interface ShardingRule {

    String getFieldNameForDb();

    String getFieldNameForTable();

    String getDbSuffix(Object fieldValueForDb);

    String getTableSuffix(Object fieldValueForTable);

}
