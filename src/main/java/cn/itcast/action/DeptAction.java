package cn.itcast.action;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.service.DeptService;
import cn.itcast.util.Result;

@Controller
@RequestMapping("/dept")
public class DeptAction {

	@Resource
	private DeptService deptServiceImpl;
	
	@RequestMapping(value="/depts",method=RequestMethod.GET)
	@ResponseBody
	public Result selectAll(){
		return deptServiceImpl.selectAll();
	}
}
