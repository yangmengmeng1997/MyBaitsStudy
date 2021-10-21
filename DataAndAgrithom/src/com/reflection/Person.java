package com.reflection;

/**
 * @author ymm
 * @create 2020--12--15:13
 */
public class Person {
    public int age;
    String name;
    private String address;

    public Person(){

    }
    public Person(int age,String name){
        this.age=age;
        this.name=name;
    }

    private Person(int age,String name,String address){
        this.age=age;
        this.name=name;
        this.address=address;
    }




    public String getName(){
        return this.name;
    }
    public int getAge()
    {
        return this.age;
    }

    private void show(String name,String address,int age){
        System.out.println("and name is"+name+" address is"+address+" "+String.valueOf(age));
    }

    public static void showDec()
    {
        System.out.println("Hello world");
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + String.valueOf(age) +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
