package com.yuanwhy.simple.sharding.spring.parser;

import com.yuanwhy.simple.sharding.rule.HashShardingRule;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;


/**
 * Created by hongyuan.wang on 06/08/2017.
 */
public class HashShardingRuleBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return HashShardingRule.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {

        ShardingRuleParser.doParseForShardingRule(element, builder);

        String dbCount = element.getAttribute("dbCount");
        builder.addPropertyValue("dbCount", Integer.valueOf(dbCount));

        String tableCount = element.getAttribute("tableCount");
        builder.addPropertyValue("tableCount", Integer.valueOf(tableCount));

    }



}
