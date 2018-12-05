package cn.itcast.service;

import java.util.List;

import cn.itcast.domain.Course;

public interface CourseService {
	
	List<Course> selectAll();
	
	Course selectID(String name);
	
	String selectByPrimaryKey2(Integer id);
}
