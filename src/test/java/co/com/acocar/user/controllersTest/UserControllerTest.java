package co.com.acocar.user.controllersTest;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.acocar.user.dao.UserDao;
import co.com.acocar.user.model.User;

public class UserControllerTest {
	
	@Autowired
	UserDao userDao;
	
	/*@Test
	void listUsers() {
		
		int total = 0;
		List<User> list = null;
		list = userDao.getUsers();
		System.out.println(list.size());
		//Assertions.assertNotEquals(list.size(), total);
	}*/

}
