package com.yuanwhy.simple.sharding.spring.parser;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

/**
 * Created by hongyuan.wang on 06/08/2017.
 */
public class ShardingRuleParser {

    public static void doParseForShardingRule(Element element, BeanDefinitionBuilder builder) {
        String fieldNameForDb = element.getAttribute("fieldNameForDb");
        builder.addPropertyValue("fieldNameForDb", fieldNameForDb);

        String fieldNameForTable = element.getAttribute("fieldNameForTable");
        builder.addPropertyValue("fieldNameForTable", fieldNameForTable);
    }
}
