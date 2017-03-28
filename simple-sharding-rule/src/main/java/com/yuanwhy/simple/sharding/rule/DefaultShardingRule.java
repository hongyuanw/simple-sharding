package com.yuanwhy.simple.sharding.rule;

/**
 * Created by yuanwhy on 17/3/28.
 */
public class DefaultShardingRule implements ShardingRule{

    public static String SEPARATOR = "_";

    @Override
    public String getDbSuffix(Object fieldForDb) {

        Integer intField = (Integer) fieldForDb;

        return SEPARATOR + intField % 2; // TODO: 17/3/29 动态获取分库个数
    }

    @Override
    public String getTableSuffix(Object fieldForTable) {
        Integer intField = (Integer) fieldForTable;

        return SEPARATOR + intField % 2;
    }
}
