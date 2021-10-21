package com.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ymm
 * @create 2020--12--15:49
 * 反射机制调用运行类的属性，方法，构造器
 */
public class Reflectiontest {
    public static void main(String []args) throws IllegalAccessException, InstantiationException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        //testMethod();
        testConstructor();
        //tsetFiled();
    }
    //这种方法不适用
    public static void tsetFiled() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Class clazz=Person.class;

        //创建运行时的实例对象；
        Person p=(Person) clazz.newInstance();
        //获取相对应的属性,getField获取属性只能为public，所以不适用
        Field age=clazz.getField("age");
        //clazz set方法设定对应的值,为P设置值,都是设置的object的
        age.set(p,10);

        //得到对应的值,转换为值
        int page=(int)age.get(p);
        System.out.println(page);
    }

    //这种可以操作类中的实例变量
    public static  void testfiled1() throws IllegalAccessException, InstantiationException, NoSuchFieldException {
          Class clazz = Person.class;
          Person p =(Person) clazz.newInstance();

          //获取非public属性,但是私有的还是访问不了
          Field name = p.getClass().getDeclaredField("name");
          name.set(p,"China");
          System.out.println(name.get(p));
          Field add = clazz.getDeclaredField("address");
          //将其设置为true才可以修改这个私有变量
          add.setAccessible(true);
          add.set(p,"yangmengmeng");   //设置
          System.out.println(add.get(p));  //获取

    }

    //这种可以操作类中的方法（非静态）,invoke传入的就是必须是一个实例对象
    public static void testMethod() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class clazz = Person.class;
        Person p =(Person) clazz.newInstance();

        //1、获取指定的方法名字，参数1是方法名字，参数2是形参类型的列表类型；是int.class不是Integer.class
        Method show =clazz.getDeclaredMethod("show",String.class,String.class,int.class);
        //2、这里定义的方法一定要设置权限
        show.setAccessible(true);
        //3、invoke调用执行方法，参数1是实例对象调用者，参数2传实参
        //并且invoke函数返回值就是对应类里面的参数的返回值
        show.invoke(p,"xiu","yang",12);

        //调用静态方法传入的可以是类（或者实例化的对象均可以）
        System.out.println("********************调用静态方法***********************************");
        Method stashow= clazz.getDeclaredMethod("showDec");
        stashow.setAccessible(true);
        stashow.invoke(clazz);

    }

    //调用指定构造器
    public static void testConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class clazz = Person.class;

        //1、指定参数构造，参数就是构造器形参
        Constructor cons =clazz.getDeclaredConstructor(int.class,String.class);
        //2、指定权限为true
        cons.setAccessible(true);
        //3、调用构造器,相当于实例化变量,这种构造器具有一定的泛化性
        Person per =(Person) cons.newInstance(12,"xiu");
        System.out.println(per);
    }




}
