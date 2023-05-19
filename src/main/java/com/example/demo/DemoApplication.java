package com.example.demo;

import com.example.demo.domain.Role;
import com.example.demo.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.util.List;

// @Componenent 붙어있는 객체는 스프링 컨테이너가 관리하는 객체, Bean이 된다
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	// main 메소드는 Spring이 관리하지 않음
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);}

	// DataSource Bean(Spring이 관리하는 객체)을 주입 - Autowired
	@Autowired
	DataSource dataSource;

	@Autowired
	RoleDao roleDao;

	// Spring의 시작 메소드
	@Override
	public void run(String... args) throws Exception {
//		Role role = new Role();
//		role.setRoleId(3);
//		role.setName("test");
//		roleDao.addRole(role);

//		boolean flag = roleDao.deleteRole(3);
//		System.out.println(flag);

//		Role role = roleDao.getRole(1);
//		System.out.println(role.getName());

//		List<Role> list = roleDao.getRoles();
//		for (Role role: list) System.out.println(role.getRoleId()+ ":"+role.getName());
	}
}
