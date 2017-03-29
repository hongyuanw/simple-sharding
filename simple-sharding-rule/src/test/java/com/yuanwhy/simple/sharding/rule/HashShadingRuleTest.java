package com.yuanwhy.simple.sharding.rule;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by yuanwhy on 17/3/29.
 */
public class HashShadingRuleTest {

    @Test
    public void testHash(){

        HashShardingRule hashShardingRule = new HashShardingRule(10, 90);

        String dbSuffix = hashShardingRule.getDbSuffix(8);
        String tableSuffix = hashShardingRule.getTableSuffix(3);

        Assert.assertTrue(dbSuffix.equals("_8"));
        Assert.assertTrue(tableSuffix.equals("_03"));

    }

}
