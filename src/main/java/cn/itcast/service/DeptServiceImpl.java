package cn.itcast.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.dao.DeptMapper;
import cn.itcast.dao.EmpMapper;
import cn.itcast.domain.Dept;
import cn.itcast.util.Result;

@Service
public class DeptServiceImpl implements DeptService{

	@Resource
	private DeptMapper deptMapper;
	@Resource
	private EmpMapper empMapper;
	@Override
	public Result selectAll() {
		int code = 0 ;
		Result result = new Result();
		try {
			List<Dept> depts = deptMapper.selectAll();
			code =1;
			result.success(code, depts);
		} catch (Exception e) {
			e.printStackTrace();
			result.fail(code);
		}
		return result;
	}

	
}
