package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserDao;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {return userDao.findAll();}

    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();

        for (Role role : user.getRoles()) {
            if (role.getId() !=null) {
            roles.add(roleService.findById(role.getId()));}
        }
        user.setRoles(roles);

        userDao.save(user);
        System.out.println(user.getRoles());
    }

    @Transactional
    @Override
    public void update(User user) {

        User userBD = userDao.findById(user.getId());
        userBD.setName(user.getName());
        userBD.setEmail(user.getEmail());

        userBD.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();

        for (Role role : user.getRoles()) {
            if (role.getId() !=null) {
                roles.add(roleService.findById(role.getId()));}
        }
        userBD.setRoles(roles);

    }

    @Transactional
    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}