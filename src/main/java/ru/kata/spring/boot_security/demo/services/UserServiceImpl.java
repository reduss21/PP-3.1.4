package ru.kata.spring.boot_security.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService,UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,@Lazy PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    public List<User> findAll () {
        return userRepository.findAll();
    }


    public User findUserByName(String name) {
        Optional<User> foundUser = userRepository.findUserByName(name);
        return foundUser.orElse(null);
    }


    @Transactional
    public void saveNewUser(User user, String role) {
        String[] roleTypes = role.split(",");
        Set<Role> roles = Stream.of(roleTypes).map(roleRepository::findRoleByRole).map(Optional::get).collect(Collectors.toSet());
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    @Transactional
    public void updateUser(User userUpdate, String roleUpdate) {
        User foundUser = userRepository.findUserById(userUpdate.getId()).get();
        Set<Role> roles;
        if (roleUpdate == null) {
            roles = foundUser.getRoles();
            userUpdate.setRoles(roles);
        } else {
            String[] roleTypes = roleUpdate.split(",");
            roles = Stream.of(roleTypes).map(roleRepository::findRoleByRole).map(Optional::get).collect(Collectors.toSet());
            userUpdate.setRoles(roles);
        }
        userUpdate.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        userRepository.save(userUpdate);
    }


    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userFound = userRepository.findUserByName(username);
        if(userFound.isEmpty()){
            throw new UsernameNotFoundException(String.format("User '%s' not found!", username));
        }
        return new org.springframework.security.core.userdetails.User(userFound.get().getName(), userFound.get().getPassword(), userFound.get().getAuthorities());
    }

    public User getUser(String username) {
        return userRepository.findUserByName(username).get();
    }
}
