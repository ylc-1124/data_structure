package com.ylc;

import java.util.Objects;

public class Person {
    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println(name+"死了");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //内存地址相同，肯定相同
        if (o == null || getClass() != o.getClass()) return false;//传入对象为null或者类型不相同，则不相同
        Person person = (Person) o;//到这就是类型相同，但是内存地址不同的情况
        return Objects.equals(name, person.name) && Objects.equals(age, person.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
