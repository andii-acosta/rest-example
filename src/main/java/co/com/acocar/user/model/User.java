package co.com.acocar.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Table(name="AC_USER")
@Entity
public class User {
	
	@Id
	@Column(name = "ID",nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME",nullable = false)
	private String name;
	
	@Column(name = "LAST_NAME",nullable = false)
	private String last_name;
	
	@Column(name = "AGE",nullable = false)
	private Long age;
	
	@Column(name = "PASSWORD",nullable = false)
	private String password;
	
	@Column(name = "CITY",nullable = true)
	private String city;
	
	@Column(name = "STATUS",nullable = false, length = 1)
	private String status;
	
	@PrePersist
	public void onPersist() {
		status = "A";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", last_name=" + last_name + ", age=" + age + ", password="
				+ password + ", city=" + city + ", status=" + status + "]";
	}
}
