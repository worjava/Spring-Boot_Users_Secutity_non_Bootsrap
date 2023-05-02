package com.UsersMVC.users.services;

import com.UsersMVC.users.models.Role;
import com.UsersMVC.users.models.User;
import com.UsersMVC.users.repositories.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository peopleRepository;

    public UserServiceImpl(UserRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public List<User> index() {
        return peopleRepository.index();
    }

    @Override
    public User show(int id) {
        return peopleRepository.show(id);
    }

    @Override
    @Transactional
    public void save(@Valid User person) {
        peopleRepository.save(person);
    }

    @Override
    @Transactional
    public void update(int id, User person) {
        peopleRepository.update(id, person);
    }

    @Override
    @Transactional
    public void delete(int id) {
        peopleRepository.delete(id);
    }

    @Override
    public User findByUsername(String name) {
        return peopleRepository.findByUsername(name);
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<User> user = Optional.ofNullable(peopleRepository.findByUsername(username));
        if (user == null) {
            throw new UsernameNotFoundException(String.format("user name not find " + username));
        }
        return user.get();
    };



    private Collection<? extends GrantedAuthority> authoritiesAllRoles(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

