package com.example.demo.repository;

import com.example.demo.domain.Role;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

// Spring JDBC 이용해서 Database 프로그래밍
// @Repository는 @Component이므로 컨테이너가 관리하는 Bean
@Repository
public class RoleDao {
    private final NamedParameterJdbcTemplate jdbcTemplate; // 필드 final 선언하면 반드시 생성자에서 초기화 해야함
    private SimpleJdbcInsertOperations insertActions;// insert 쉽게 하도록 도와주는 인퍼에이스
    // 생성자에 파라미터 넣어주면 스프링부트가 자동으로 주입 - DI,생성자 주입
    public RoleDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource); // DataSource 넣어줘야 함
        insertActions = new SimpleJdbcInsert(dataSource).withTableName("role");

    }

    // Role 테이블에 한건 저장
    public boolean addRole(Role role){
//        String sql = "INSERT INTO role(role_id, name) VALUES(?, ?)";
//        int result = jdbcTemplate.update(sql, role.getRoleId(), role.getName()); // update 메소드 insert, update, delete SQL문 실행시 사용
//        return result == 1;

        // role 프로퍼티 roleId, name
        // INSERT INTO role(role_id, name) VALUES(:roleId, :name)
        // 위 같은 SQL을 SimpleJdbcInsert가 내부적으로 만듦
        // Role 클래스 프로퍼티 이름과 컬러명 규칙이 맞아야함 ex> roleId 프로퍼티 = role_id 컬럼명
        SqlParameterSource params = new BeanPropertySqlParameterSource(role); // role 객체 필드명과 테이블 컬럼명이 같아야 함
        int result = insertActions.execute(params);
        return result == 1;
    }

    public boolean deleteRole(int roleId) {
        String sql = "DELETE FROM role WHERE role_id =:roleId";
        SqlParameterSource params = new MapSqlParameterSource("roleId", roleId);
        int result = jdbcTemplate.update(sql, params);
        return result == 1;
    }

    public Role getRole(int roleId) {
        try {
            String sql = "SELECT role_id, name FROM role WHERE role_id =:roleId"; // role_id PK, 1건 or 0건 데이터 조회
            // queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)  1건 또는 0건 읽는 메소드
            SqlParameterSource params = new MapSqlParameterSource("roleId", roleId);
            RowMapper<Role> roleRowMapper = BeanPropertyRowMapper.newInstance(Role.class);
            return jdbcTemplate.queryForObject(sql, params, roleRowMapper);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Role> getRoles(){
        try {
            String sql = "SELECT role_id, name FROM role ORDER BY role_id DESC";
            // query 여러건 결과 구할 때 사용하는 메소드
            RowMapper<Role> roleRowMapper = BeanPropertyRowMapper.newInstance(Role.class);
            return jdbcTemplate.query(sql, roleRowMapper);
        } catch (Exception e) {
            return null;
        }
    }
}
