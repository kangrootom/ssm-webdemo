package cn.itcast.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.dao.CourseMapper;
import cn.itcast.domain.Course;
@Service
public class CourseServiceImpl implements CourseService {
	@Resource
	CourseMapper courseMapper;
	
	@Override
	public List<Course> selectAll() {
		// TODO Auto-generated method stub
		return courseMapper.selectAll();
	}

	@Override
	public Course selectID(String name) {
		// TODO Auto-generated method stub
		return courseMapper.selectID(name);
	}

	@Override
	public String selectByPrimaryKey2(Integer id) {
		// TODO Auto-generated method stub
		return courseMapper.selectByPrimaryKey2(id);
	}

}
