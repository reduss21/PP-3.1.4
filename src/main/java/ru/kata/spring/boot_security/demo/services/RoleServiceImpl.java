package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
   private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<String> getRoleList () {
        List<Role> roles = roleRepository.findAll();
        List<String> listRoles = new ArrayList<>();
        for(Role role: roles) {
            listRoles.add(role.getRole());

        }
        return listRoles;
    }
}
