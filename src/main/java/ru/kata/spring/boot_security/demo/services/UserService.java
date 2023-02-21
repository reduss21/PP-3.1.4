package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface UserService {
    public List<User> findAll ();
    public User findUserByName(String name);

    public void saveNewUser(User user, String role);

    public void updateUser(User userUpdate, String roleUpdate);

    public void delete(int id);

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    public User getUser(String username);

}
