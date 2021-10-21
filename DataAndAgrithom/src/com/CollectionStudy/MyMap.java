package com.CollectionStudy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyMap {
    //IDEA工具默认简化了显示界面，所以要打开
    //在debug dataviwe中将Enabel Alternative view 勾选结束
    public static void main(String[] args) {
        Map map=new HashMap<>();
        map.put(1,"xiu");
        map.put(2,"ymm");
        map.put(1,"tihuan");
        map.put(3,"xiu");
        //map遍历 取出封装好的entry
//        for(Object obj:map.entrySet()){
//            //这里取出来的就是父类接口，还需要转型
//            Map.Entry entry=(Map.Entry) obj;  //转型之后可以看到有很多专用的方法了
//            System.out.println(entry.getKey()+" "+entry.getValue());
//        }
        //2.取出key对应的value,增强for循环
//        for(Object key:map.keySet()){
//            System.out.println(key+" "+map.get(key));
//        }
        //3.迭代器遍历集合
        Set set=map.keySet();
        Iterator it=set.iterator();
        while (it.hasNext()){
            Object key=it.next();
            System.out.println(key+" "+map.get(key));
        }
    }
}
