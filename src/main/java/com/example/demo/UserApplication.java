package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class UserApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    UserDao userDao;

    @Override
    public void run(String... args) throws Exception {
// 1건 조회
            User user = userDao.getUser(2);
            System.out.println(user.getName() + "" + user.getEmail());
// 전체 조회
            List<User> list = userDao.getUsers();
            for (User data: list) {
                System.out.println(data.getName() + "/" + data.getPassword() + "/" + data.getEmail());
            }

// 생성
            User oneUser = new User();
        oneUser.setName("자바");
        oneUser.setEmail("java@spring.co.kr");
        oneUser.setPassword("boot123");
        oneUser.setRegdate(new Date());

            userDao.addUser(user);
// 수정
            Boolean flag1  = userDao.updateUser("야호", 1);
            System.out.println(flag1);
// 삭제
        Boolean flag2 = userDao.deleteUser(2);
        System.out.println(flag2);

    }
}
