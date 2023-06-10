package com.integrador.ms_users.service;

import com.integrador.ms_users.models.User;

import java.util.List;

public interface UserService {

    List<User> getAll();
    String getNombreById(String id);
}
