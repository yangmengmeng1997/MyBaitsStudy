package com.generic;

/**
 * 测试自定义泛型Order
 * @author ymm
 * @create 2020--12--20:29
 */
public class Testorder {
    public static void main(String[] args){

        //Order order =new Order();
        //默认不指定的泛型是object类型，所以还是可以混搭，不适合，静态方法不可以用类的泛型
//        order.setOrder(123);
//        order.setOrder("ABC");
        Order<String> order =new Order<>();
        order.setOrder("123");
        //order.setOrder(456);  自定义类型报错，只能加string ，这样比较好





    }


}
