package org.example.dao;

import org.apache.ibatis.annotations.Param;
import org.example.enity.student;
import org.example.vo.QueryParam;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public interface StudentDao {
    public List<student> queryStudents();

    //根据id查询,传入参数
    public student queryStudentById(Integer id);

    //自定义多参数注解 @Param("myname") 括号中的形参名要和想xml一起对应
    public List<student> queryMultiParam(@Param("myname") String name, @Param("myage") Integer age);

    //自定义多参数封装成参数进行传递  paramName是类中的属性名，还是需要一一对应
    public List<student> queryObject(QueryParam param);

    //返回值表示添加了几行数据
    public int insertStudent(student stu);

    //查询一行一列数据
    public int countStudent();

    //返回结果为MAP
    public Map<Object,Object> selctMapById(Integer id);
}
