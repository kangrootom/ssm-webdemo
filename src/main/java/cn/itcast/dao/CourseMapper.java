package cn.itcast.dao;


import java.util.List;

import cn.itcast.domain.Course;
@MybatisAnnotation
public interface CourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
    
    List<Course> findAll();
    
    List<Course> selectAll();
    
    Course selectID(String name);
    
    String selectByPrimaryKey2(Integer id);
}