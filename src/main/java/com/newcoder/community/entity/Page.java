package com.newcoder.community.entity;


/**
 * @author xiuxiaoran
 * @date 2022/4/21 9:44
 * 分页的相关信息
 */
public class Page {
    // 当前传入的页码
    private int current = 1;
    //每一页显示的数据
    private int limit =10;
    //数据总数， 用来计算总的页数
    private int rows;
    //查询路径（复用分页的链接）暂时没懂
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current>=1) this.current = current;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit>=1 && limit<=100) this.limit = limit;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows>=0) this.rows = rows;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    /*
       计算出当前页的起始行是多少
       current*limit - limit   ，总共显示了100条数据，-每页的有限数据就是起始数据
     */
    public int getOffset(){
        return (current-1)*limit;
    }
    /*
          获取总的页数，便于计算页码边界
     */
    public int getTotal(){
        if(rows % limit==0) return rows/limit;
        return rows/limit+1;
    }

    /*
       获取要展示的起始页码，比如现在就是current=8
       那我们一般显示页码的6 7 8 9 10 这几个页码
       那么显示的起始页码就是6 ，显示的结束页码就是10
     */
    //这边的方法可以驼峰式命名，那边前端就可以调用page.from这种格式调用了
    public int getFrom(){
        int from = current-2;
        //return from <1 ? 1:from;   //这样就可以使用from或者to属性了嘛？奇怪
        return Math.max(1,from);
    }

    public int getTo(){
        int to = current+2;
        int total = getTotal();
        //return to > total ? total:to;
        return Math.min(to,total);  //这样写没什么问题，那么我要是在里面改写成函数调用呢
    }
}
