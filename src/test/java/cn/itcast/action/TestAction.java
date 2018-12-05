package cn.itcast.action;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.domain.User;

public class TestAction {

	@Test
	public void getBean(){
		ApplicationContext  ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
		UserAction userAction = (UserAction) ac.getBean("userAction");
		System.out.println(userAction);
	}
	
	@Test
	public void getAction(){
		ApplicationContext  ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
		UserAction userAction = (UserAction) ac.getBean("userAction");
		User user = userAction.getUser(1);
		System.out.println(user.getName()+user.getId());
	}
}
