package org.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.dao.StudentDao;
import org.example.enity.student;
import org.example.utils.MyBaitsUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyTest {
    public static void main(String[] args) throws IOException {
        SqlSession sqlSession= MyBaitsUtils.getSqlSession();
        //创建接口对应的实现类
        StudentDao dao=sqlSession.getMapper(StudentDao.class);
        List<student> studentList=dao.queryStudents();
        for (student student : studentList) {
            System.out.println(student);
        }
        sqlSession.close();
    }
}
