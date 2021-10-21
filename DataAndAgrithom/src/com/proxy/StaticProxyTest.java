package com.proxy;

/**
 * 静态代理，动态代理先不看（有点复杂），需要前面的设计模式做铺垫
 * 代理类和被代理类在编译期间被处理
 * @author ymm
 * @create 2020--12--19:17
 */

//抽象接口
interface Clothfactory{
    void produceCloth();
}

//代理类
class ProxyClothFactory implements Clothfactory{

    private Clothfactory factory;   //被代理类实例化
    public ProxyClothFactory(Clothfactory factory){
        this.factory=factory;
    }
    @Override
    public void produceCloth() {
        System.out.println("代理工厂初始化工作");
        factory.produceCloth();  //?????
        System.out.println("处理后续收尾工作");
    }
}

//被代理类
class NikeClothFactory implements Clothfactory{

    @Override
    public void produceCloth() {
        System.out.println("Nike正在生产运作");
    }
}

//主要测试类
public class StaticProxyTest {
    public static  void main(String[] args){
        //创建被代理类对象
        Clothfactory nike =new NikeClothFactory();
        //创建代理对象
        Clothfactory proxyClothFactory=new ProxyClothFactory(nike);

        //调用方法
        proxyClothFactory.produceCloth();

    }


}
