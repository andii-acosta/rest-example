package co.com.acocar.user.dao;

import java.util.List;

import co.com.acocar.user.dto.AuthCredentialsDto;
import co.com.acocar.user.model.User;

public interface UserDao {

	List<User> getUsers();
	
	User getUSerById(Long id);
	
	void deleteUser(Long id);

	User createUSer(User u);

	User updateUser(User u);

	User validUser(AuthCredentialsDto auth);
}
