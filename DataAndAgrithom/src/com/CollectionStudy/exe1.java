package com.CollectionStudy;

import java.util.HashMap;

public class exe1 {
    public static void main(String[] args) {
        HashMap map=new HashMap();
        Emploee e1=new Emploee(1,"xiu",1890000);
        Emploee e2=new Emploee(2,"xiu",189000);
        Emploee e3=new Emploee(3,"xiu",1800);
        map.put(e1.id,e1) ;
        map.put(e2.id,e2) ;
        map.put(e3.id,e3) ;
        System.out.println(map);
    }
}
class Emploee{
    int id;
    String name;
    int salary;

    public Emploee() {
    }
    public Emploee(int id, String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
}