package org.example.dao;

import org.example.enity.Student;

import java.util.List;

public interface StudentDao {
    //动态sql，传入对象方便切换
    List<Student> selectStudentIf(Student student);

    List<Student> selectStudentWhere(Student student);

    //这里是传入相关的整形参数，传入的是一个值
    List<Student> selectStudentForEach(List<Integer> idlist);

    //这里传入的是对象
    List<Student> selectStudentForEachObject(List<Student> idlist);

}


