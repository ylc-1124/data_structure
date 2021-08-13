package com.ylc.model;

public class Person {
    private String name;
    private int age;
    private float height;

    public Person(String name, int age, float height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        //比较内存地址
        if (this == o) {
            return true;
        }
     /*   if (o == null || o.getClass() != getClass()) {
            return false;
        }*/
        if (o == null || !(o instanceof Person)) {
            return false;
        }
        //比较成员变量
        Person p = ((Person) o);
        return p.name == null ? name == null : p.name.equals(name)
                && p.age == age
                && p.height == height;
    }

    @Override
    public int hashCode() {

        int hashCode = Integer.hashCode(age);
        hashCode = hashCode * 31 + Float.hashCode(height);
        hashCode = hashCode * 31 + (name != null ? name.hashCode() : 0);

        return hashCode;
    }
}
