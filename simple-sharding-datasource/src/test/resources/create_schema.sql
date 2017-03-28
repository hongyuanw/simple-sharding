DROP database passport_0;
DROP database passport_1;

CREATE SCHEMA passport_0;
CREATE TABLE passport_0.user_0(id INT , name VARCHAR(10) , age INT , role int);
CREATE TABLE passport_0.user_1(id INT , name VARCHAR(10) , age INT , role int);

CREATE SCHEMA passport_1;
CREATE TABLE passport_1.user_0(id INT , name VARCHAR(10) , age INT , role int);
CREATE TABLE passport_1.user_1(id INT , name VARCHAR(10) , age INT , role int);

INSERT INTO passport_0.user_0 VALUES (10, "john", 20, 0);
INSERT INTO passport_0.user_1 VALUES (11, "tom", 20, 0);

INSERT INTO passport_1.user_0 VALUES (22, "bob", 50, 1);
INSERT INTO passport_1.user_1 VALUES (25, "jack", 40, 1);
