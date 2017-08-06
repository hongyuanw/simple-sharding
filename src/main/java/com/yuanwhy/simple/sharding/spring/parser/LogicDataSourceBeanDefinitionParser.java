package com.yuanwhy.simple.sharding.spring.parser;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yuanwhy.simple.sharding.jdbc.LogicDataSource;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.CollectionUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuanwhy on 06/08/2017.
 */
public class LogicDataSourceBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return LogicDataSource.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {

        String name = element.getAttribute("name");
        builder.addPropertyValue("name", name);


        String shardingRule = element.getAttribute("shardingRule");

        builder.addPropertyReference("shardingRule", shardingRule);


        List<Element> physicalElements = DomUtils.getChildElementsByTagName(element, "physicalDataSource");

        if (CollectionUtils.isEmpty(physicalElements)) {
            throw new RuntimeException("physical databases must be set !");
        }

        Map<String, DataSource> physicalDataSourceMap = new HashMap<>();

        PhysicalDataSourceBeanElementUtil physicalDataSourceBeanDefinitionParser = new PhysicalDataSourceBeanElementUtil();
        for (Element physicalElement : physicalElements) {

            ComboPooledDataSource comboPooledDataSource = PhysicalDataSourceBeanElementUtil.parse(physicalElement);

            physicalDataSourceMap.put(comboPooledDataSource.getDataSourceName(), comboPooledDataSource);

        }

        builder.addPropertyValue("physicalDataSourceMap", physicalDataSourceMap);
    }
}
