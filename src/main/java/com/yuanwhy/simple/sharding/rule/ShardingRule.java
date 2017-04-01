package com.yuanwhy.simple.sharding.rule;

/**
 * Created by yuanwhy on 17/3/28.
 */
public interface ShardingRule {

    String getDbSuffix(Object fieldForDb);

    String getTableSuffix(Object fieldForTable);

}
