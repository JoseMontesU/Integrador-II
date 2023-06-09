package com.integrador.ms_users.service;

import com.integrador.ms_users.models.User;
import com.integrador.ms_users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> getAll() {
        return userRepository.getUsers();
    }
}
