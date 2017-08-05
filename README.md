# Simple-Sharding


[![Build Status](https://travis-ci.org/yuanwhy/simple-sharding.svg?branch=master)](https://travis-ci.org/yuanwhy/simple-sharding)
[![Join the chat at https://gitter.im/yuanwhy/simple-sharding](https://badges.gitter.im/yuanwhy/simple-sharding.svg)](https://gitter.im/yuanwhy/simple-sharding?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)


A simple database shard middleware, based on JDBC API. Applications can scale database by using new DataSource with simple-sharding. This project is not finished, and feel free to study.


## Features
 * Transaction in single database shard
 * Sharding
 * Rewriting rules

## Quick Start

#### 1. Get source code

```
git clone https://github.com/yuanwhy/simple-sharding.git
```
and then execute `test/create_schema.sql` to init data.

#### 2. Install simple-sharding to you local repository by

```
mvn clean install
```

#### 3. Add artifact dependency

```xml
<dependency>
  <groupId>com.yuanwhy</groupId>
  <artifactId>simple-sharding</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```
####  4. Set logic datasource and physic datasources

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
   
#### 5. Feel free to use JDBC API or ORM framework (e.g. MyBatis ) to execute your SQL

## Document
[Simple-Sharding 中文](http://www.jianshu.com/p/9784a3d4c7a8)

[References](http://yuanwhy.com/tags/%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8/)

## Todo
  - [ ] Support Spring custom namespace
  - [ ] Finish other methods

## License
Apache License, Version 2.0
