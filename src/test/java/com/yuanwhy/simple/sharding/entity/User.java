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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (getAge() != user.getAge()) return false;
        if (getRole() != user.getRole()) return false;
        return getName().equals(user.getName());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getAge();
        result = 31 * result + getRole();
        return result;
    }
}
