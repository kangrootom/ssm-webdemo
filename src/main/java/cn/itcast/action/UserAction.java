package cn.itcast.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;

@Controller
@RequestMapping("/user")
public class UserAction {

	@Resource
	private UserService userServiceImpl;
	
	@RequestMapping("/getOne")
	@ResponseBody
	public User getUser(Integer id){
		System.out.println("getUser().................");
		User user = userServiceImpl.selectByPrimaryKey(id);
		System.out.println(user);
		return user;
	}
	@RequestMapping("/test")
	public String test(){
		System.out.println("test()...");
		return "testAction";
	}
}
