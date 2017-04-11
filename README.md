# Simple-Sharding

[![Build Status](https://travis-ci.org/yuanwhy/simple-sharding.svg?branch=master)](https://travis-ci.org/yuanwhy/simple-sharding)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)


一款简单易用的分库分表中间件, 基于JDBC API开发, 应用只需替换DataSource并设置相应参数即可快速获得分库分表能力

## Features
 * 支持单库事务
 * 分库分表
 * 支持重写规则

## Quick Start

1. `git clone https://github.com/yuanwhy/simple-sharding.git`
   下载代码后执行test/create_schema.sql脚本建立测试库
2. 执行`mvn clean install` 编译并安装到本地仓库
3. 引用maven依赖
   ```xml
   <dependency>
      <groupId>com.yuanwhy</groupId>
      <artifactId>simple-sharding</artifactId>
      <version>0.0.1-SNAPSHOT</version>
   </dependency>
   ```
4. 设置DataSource以及物理连接池(以test/datasource.xml为例,逻辑数据源为dataSource, 逻辑库为passport, 有两个物理分库passport_0、passport_1, 分库分表规则是HashShardingRule, 即通过分库分表字段hash到具体的物理节点, 分库字段为role, 分表字段为id)
   ```xml
   <beans xmlns="http://www.springframework.org/schema/beans"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="
      http://www.springframework.org/schema/beans
      http://www.springframework.org/schema/beans/spring-beans.xsd">

   <bean id="shardingRule" class="com.yuanwhy.simple.sharding.rule.HashShardingRule">
      <property name="fieldNameForDb" value="role"/>
      <property name="fieldNameForTable" value="id"/>
      <property name="dbCount" value="2"/>
      <property name="tableCount" value="2"/>
   </bean>

   <bean id="physicalDataSource0" class="com.mchange.v2.c3p0.ComboPooledDataSource">
      <property name="driverClass" value="com.mysql.jdbc.Driver"/>
      <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/passport_0"/>
      <property name="user" value="root"/>
      <property name="password" value=""/>
   </bean>

   <bean id="physicalDataSource1" class="com.mchange.v2.c3p0.ComboPooledDataSource">
      <property name="driverClass" value="com.mysql.jdbc.Driver"/>
      <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/passport_1"/>
      <property name="user" value="root"/>
      <property name="password" value=""/>
   </bean>

   <bean id="dataSource" class="com.yuanwhy.simple.sharding.jdbc.LogicDataSource">
      <property name="logicDatabase" value="passport"/>
      <property name="shardingRule" ref="shardingRule"/>
      <property name="physicalDataSourceMap">
          <map>
              <entry key="passport_0" value-ref="physicalDataSource0"/>
              <entry key="passport_1" value-ref="physicalDataSource1"/>
          </map>
      </property>
   </bean>

   </beans>

   ```
5. 使用JDBC API或者ORM框架执行SQL即可

## License
Apache License, Version 2.0




