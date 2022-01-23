package co.com.acocar.user.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import co.com.acocar.user.dto.AuthCredentialsDto;
import co.com.acocar.user.model.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	
	@PersistenceContext
	EntityManager entitimanager;


	@Override
	public List<User> getUsers() {
		String q = "FROM User";
		return entitimanager.createQuery(q).getResultList();
	}

	@Override
	public User getUSerById(Long id) {
		return entitimanager.find(User.class, id);
	}

	@Override
	public void deleteUser(Long id) {
		User user = entitimanager.find(User.class, id);
		entitimanager.remove(user);
	}

	@Override
	public User createUSer(User u) {
		User user = entitimanager.merge(u);
		return user;
	}

	@Override
	public User updateUser(User u) {
		User user = entitimanager.find(User.class, u.getId());
		user = entitimanager.merge(u);
		return user;
	}

	@Override
	public User validUser(AuthCredentialsDto auth) {
		String q = "FROM User WHERE name = :name";
		
		List<User> list = entitimanager.createQuery(q)
		.setParameter("name",auth.getName())
		.getResultList();
		
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		return argon2.verify(list.isEmpty() ? "" : list.get(0).getPassword(), auth.getPassword()) ? list.get(0) : null;
	}

}
