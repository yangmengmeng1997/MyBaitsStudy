package org.example;

import org.apache.ibatis.session.SqlSession;
import org.example.dao.StudentDao;
import org.example.enity.Student;
import org.example.utils.MyBaitsUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestINsert {

    @Test
    public void Testif() {
        SqlSession sqlSession = MyBaitsUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);
        Student stu = new Student();
        stu.setName("xiu");
        stu.setAge(18);
        List<Student> students = dao.selectStudentIf(stu);
        for (Student s : students) {
            System.out.println(s);
        }
    }

    @Test
    public void TestWhere() {
        SqlSession sqlSession = MyBaitsUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);
        Student stu = new Student();
        //stu.setName("xiu");   //名字为空时where不会出错，自动过滤连接词，增强if
        stu.setAge(18);
        List<Student> students = dao.selectStudentWhere(stu);
        for (Student s : students) {
            System.out.println(s);
        }
    }

    @Test
    public void TestFor() {
        SqlSession sqlSession = MyBaitsUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);
        List<Integer> list=new ArrayList<>();
        list.add(1002);
        list.add(1003);
        list.add(1004);
        List<Student> students=dao.selectStudentForEach(list);
        for(Student s:students){
            System.out.println(s);
        }
    }

    @Test
    public void TestForObjext() {
        SqlSession sqlSession = MyBaitsUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);
        List<Student> list=new ArrayList<>();
        Student s1=new Student(1002,"xiao","222",19);
        Student s2=new Student(1003,"ran","333",20);
        list.add(s1);
        list.add(s2);
        List<Student> students=dao.selectStudentForEachObject(list);
        for(Student s:students){
            System.out.println(s);
        }
    }
}
