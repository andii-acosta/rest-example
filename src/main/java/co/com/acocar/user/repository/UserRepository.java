package co.com.acocar.user.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.com.acocar.user.model.User;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, String>{

}
