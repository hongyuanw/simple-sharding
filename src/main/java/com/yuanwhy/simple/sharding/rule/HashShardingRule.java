package com.yuanwhy.simple.sharding.rule;

/**
 * Created by yuanwhy on 17/3/28.
 */
public class HashShardingRule extends AbstractShardingRule{

    public static String SEPARATOR = "_";

    private int dbCount;

    private int tableCount;

    public HashShardingRule() {
    }

    public HashShardingRule(int dbCount, int tableCount) {
        this.dbCount = dbCount;
        this.tableCount = tableCount;
    }

    public int getDbCount() {
        return dbCount;
    }

    public void setDbCount(int dbCount) {
        this.dbCount = dbCount;
    }

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    @Override
    public String getDbSuffix(Object fieldValueForDb) {

        return SEPARATOR + hash(fieldValueForDb.hashCode(), dbCount);

    }
    @Override
    public String getTableSuffix(Object fieldForTableValue) {

        return SEPARATOR + hash(fieldForTableValue.hashCode(), tableCount);

    }


    private String hash(int hashCode, int count){

        int result = hashCode % count;

        int formatSize =  String.valueOf(count-1).length();

        return String.format("%0"+ formatSize + "d", result);
    }
}
