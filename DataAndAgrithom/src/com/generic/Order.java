package com.generic;

/**
 * @author ymm
 * @create 2020--12--20:22
 */
public class Order<T> {
    //泛型T，在这里可以使用T的类型
    String name;
    T order;    //T类型变量
    public Order(){};
    public Order(String name,T order){
        this.name=name;
        this.order=order;
    }
    public T getT(){
        return order;
    }
    public void setOrder(T order){   //设置类型变量
        this.order=order;
    }

    @Override
    public String toString() {
        return "Order{" +
                "name='" + name + '\'' +
                ", order=" + order +
                '}';
    }
}
