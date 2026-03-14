package ru.kata.spring.boot_security.demo.repository;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
    void save (User user);
    void update (User user);
    void delete (Long id);
    User findById(Long id);
    User findByEmail(String email);
}
