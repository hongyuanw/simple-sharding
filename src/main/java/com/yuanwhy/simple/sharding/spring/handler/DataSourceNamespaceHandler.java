package com.yuanwhy.simple.sharding.spring.handler;

import com.yuanwhy.simple.sharding.spring.parser.HashShardingRuleBeanDefinitionParser;
import com.yuanwhy.simple.sharding.spring.parser.LogicDataSourceBeanDefinitionParser;
import com.yuanwhy.simple.sharding.spring.parser.PhysicalDataSourceBeanElementUtil;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by yuanwhy on 05/08/2017.
 */
public class DataSourceNamespaceHandler extends NamespaceHandlerSupport {


    @Override
    public void init() {
        registerBeanDefinitionParser("logicDataSource", new LogicDataSourceBeanDefinitionParser());
        registerBeanDefinitionParser("hashShardingRule", new HashShardingRuleBeanDefinitionParser());
    }


}
