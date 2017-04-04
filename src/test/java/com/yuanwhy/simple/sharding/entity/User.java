package com.yuanwhy.simple.sharding.entity;

/**
 * Created by yuanwhy on 17/4/3.
 */
public class User {

    public enum Role{

        BUYER(0),
        SELLER(1);

        private int id;

        Role(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }


    private int id;

    private String name;

    private int age;

    private int role;

    public User() {
    }

    public User(int id, String name, int age, int role) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getRole() {
        return role;
    }
}
