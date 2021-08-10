package com.ylc;

public class Person implements Comparable<Person> {
    private int age;
    private String name;

    public Person(int age,String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int compareTo(Person o) {
        return this.age - o.age;
    }

    @Override
    public String toString() {
        return age + "_" + name;
    }
}
