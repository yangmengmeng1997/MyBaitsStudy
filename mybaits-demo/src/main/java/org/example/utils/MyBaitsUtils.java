package org.example.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBaitsUtils {
    //创建factory。因为只需要有一个，所以只要在初始化块中进行
    static SqlSessionFactory factory=null;
    static {
        String config="mybaits.xml";
        try {
            InputStream in= Resources.getResourceAsStream(config);
            factory=new SqlSessionFactoryBuilder().build(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSqlSession(){
        SqlSession sqlSession=null;
        if(factory!=null){
            sqlSession=factory.openSession();
        }
        return sqlSession;
    }
}
