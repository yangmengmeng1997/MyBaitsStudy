package org.example;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.dao.ProvinceDao;
import org.example.dao.StudentDao;
import org.example.enity.Province;
import org.example.enity.student;
import org.example.utils.MyBaitsUtils;
import org.example.vo.QueryParam;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestINsert {

    @Test
    public void testqueryStudent() throws IOException {
        //创建sqlsession对话。
        SqlSession sqlSession= MyBaitsUtils.getSqlSession();
        //创建dao层，获得实现类对象getMapper，直接创建对象
        StudentDao dao=sqlSession.getMapper(StudentDao.class);
        List<student> students=dao.queryStudents();
        for(student s:students){
            System.out.println(s);
        }
        sqlSession.close();
    }


    @Test
    public void testinsert() throws IOException {
        //创建sqlsession对话。
        SqlSession sqlSession= MyBaitsUtils.getSqlSession();
        //创建dao层，获得实现类对象getMapper，直接创建对象
        StudentDao dao=sqlSession.getMapper(StudentDao.class);
        student stu=dao.queryStudentById(1002);
        System.out.println(stu);
        sqlSession.close();
    }
    @Test
    public void queryMultiParam() throws IOException {
        //创建sqlsession对话。
        SqlSession sqlSession= MyBaitsUtils.getSqlSession();
        //创建dao层，获得实现类对象getMapper，直接创建对象
        StudentDao dao=sqlSession.getMapper(StudentDao.class);
        List<student> stu=dao.queryMultiParam("xiu",18);
        for(student s:stu){
            System.out.println(s);
        }
        sqlSession.close();
    }

    //将对象进行参数封装，更加友善
    @Test
    public void CountStudent() throws IOException {
        //创建sqlsession对话。
        SqlSession sqlSession= MyBaitsUtils.getSqlSession();
        //创建dao层，获得实现类对象getMapper，直接创建对象
        StudentDao dao=sqlSession.getMapper(StudentDao.class);
        int count=dao.countStudent();
        System.out.println(count);
        sqlSession.close();
    }

    //测试查询一个数据
    @Test
    public void queryObject() throws IOException {
        //创建sqlsession对话。
        SqlSession sqlSession= MyBaitsUtils.getSqlSession();
        //创建dao层，获得实现类对象getMapper，直接创建对象
        StudentDao dao=sqlSession.getMapper(StudentDao.class);
        QueryParam queryParam=new QueryParam("xiu",18);
        List<student> stu=dao.queryObject(queryParam);
        for(student s:stu){
            System.out.println(s);
        }
        sqlSession.close();
    }

    //别名查询
    @Test
    public void queryStudentByid() throws IOException {
        //创建sqlsession对话。
        SqlSession sqlSession= MyBaitsUtils.getSqlSession();
        //创建dao层，获得实现类对象getMapper，直接创建对象
        StudentDao dao=sqlSession.getMapper(StudentDao.class);
        student stu=dao.queryStudentById(1002);
        System.out.println(stu);
        sqlSession.close();
    }

    @Test
    public void queryProvince(){
        SqlSession sqlSession=MyBaitsUtils.getSqlSession();
        ProvinceDao dao=sqlSession.getMapper(ProvinceDao.class);
        List<Province> list=dao.queryProvince();
        for(Province p:list){
            System.out.println(p);
        }
        sqlSession.close();
    }

    @Test
    public void queryStudentForMap(){
        SqlSession sqlSession=MyBaitsUtils.getSqlSession();
        StudentDao dao=sqlSession.getMapper(StudentDao.class);
        Map<Object,Object> mymap =dao.selctMapById(1003);
        System.out.println(mymap);
        sqlSession.close();
    }

}
