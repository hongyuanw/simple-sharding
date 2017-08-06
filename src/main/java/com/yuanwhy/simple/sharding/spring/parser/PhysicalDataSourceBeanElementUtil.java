package com.yuanwhy.simple.sharding.spring.parser;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.w3c.dom.Element;

import java.beans.PropertyVetoException;

/**
 * Created by hongyuan.wang on 06/08/2017.
 */
public class PhysicalDataSourceBeanElementUtil {


    public static ComboPooledDataSource parse(Element element) {

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

        try {
            comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        String name = element.getAttribute("name");
        comboPooledDataSource.setDataSourceName(name);


        String jdbcUrl = element.getAttribute("jdbcUrl");
        comboPooledDataSource.setJdbcUrl(jdbcUrl);

        String user = element.getAttribute("user");
        comboPooledDataSource.setUser(user);

        String password = element.getAttribute("password");
        comboPooledDataSource.setPassword(password);

        return comboPooledDataSource;

    }
}
