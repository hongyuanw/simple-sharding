# Simple-Sharding

[![Build Status](https://travis-ci.org/yuanwhy/simple-sharding.svg?branch=master)](https://travis-ci.org/yuanwhy/simple-sharding)
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

    <simple-sharding:hashShardingRule id="hashShardingRule" dbCount="2" tableCount="2" fieldNameForDb="role"
                                      fieldNameForTable="id"/>

    <simple-sharding:logicDataSource id="dataSource"
                                     name="passport"
                                     shardingRule="hashShardingRule">
        <simple-sharding:physicalDataSource name="passport_0" jdbcUrl="jdbc:mysql://127.0.0.1:3306/passport_0"
                                            user="root" password=""/>
        <simple-sharding:physicalDataSource name="passport_1" jdbcUrl="jdbc:mysql://127.0.0.1:3306/passport_0"
                                            user="root" password=""/>
    </simple-sharding:logicDataSource>

</beans>

```

#### 5. Feel free to use JDBC API or ORM framework (e.g. MyBatis ) to execute your SQL

## Document
[Simple-Sharding 中文](http://www.jianshu.com/p/9784a3d4c7a8)

[References](http://yuanwhy.com/tags/%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8/)

## Todo
  - [x] Support Spring custom namespace
  - [ ] Finish other methods

## License
Apache License, Version 2.0
