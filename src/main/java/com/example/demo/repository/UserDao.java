package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

@Repository
public class UserDao
{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsertOperations insertAction;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertAction = new SimpleJdbcInsert(dataSource).withTableName("user");
    }

    public Boolean addUser(User user){
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        int result = insertAction.execute(params);
        return result == 1;
    }

    public Boolean deleteUser(int userId){
        String sql = "DELETE FROM user WHERE user_id =:userId";
        SqlParameterSource params = new MapSqlParameterSource("userId", userId);
        int result = jdbcTemplate.update(sql, params);
        return result == 1;
    }

    public Boolean updateUser(String name, int userId){
        String sql = "UPDATE user SET name=:name WHERE user_id =:userId";
        SqlParameterSource params = new MapSqlParameterSource("userId", userId).addValue("name", name);
        int result = jdbcTemplate.update(sql, params);
        return result == 1;
    }

    public User getUser(int userId){
        String sql = "SELECT user_id, name, email, password FROM user WHERE user_id =:userId";
        SqlParameterSource params = new MapSqlParameterSource("userId", userId);
        RowMapper<User> rowUserMapper = BeanPropertyRowMapper.newInstance(User.class);
        return jdbcTemplate.queryForObject(sql,params, rowUserMapper);
    }

    public List<User> getUsers(){
        String sql = "SELECT user_id, name, email, password FROM user ORDER BY user_id DESC";
        RowMapper<User> rowUserMapper = BeanPropertyRowMapper.newInstance(User.class);
        return jdbcTemplate.query(sql, rowUserMapper);
    }
}
