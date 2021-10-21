package com.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author ymm
 * @create 2020--12--15:17
 */
public class MethodTest {
    public static void main(String []args){
        test2();
    }

    //获取实例方法
    public static void test1(){
        //获取当前类的实例
        Class clazz =Person.class;
        //获取当前类及其父类所有的public方法构成一个数组
        Method[] methods =clazz.getMethods();
        for(Method me : methods)
        {
            System.out.println(me);
        }
        System.out.println();

        //获取当前类的申明的成员变量方法，不包含构造函数（没有父类）
        Method[] methods1 =clazz.getDeclaredMethods();
        for(Method me : methods1)
        {
            System.out.println(me);
        }

    }

    //获取构造器方法
    public static void test2(){
        Class clazz = Person.class;    //公共语句，获得类，getxxx方法
        //获取当前类的声明为public的构造器方法
        Constructor []cons = clazz.getConstructors();
        for(Constructor c:cons){
            System.out.println(c);
        }
        System.out.println();

        //获取当前类的所有构造函数，包括私有的构造器
        Constructor []constructors =clazz.getDeclaredConstructors();
        for(Constructor c:constructors){
            System.out.println(c);
        }
    }



}
