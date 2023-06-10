package com.integrador.ms_users.repository;

import com.integrador.ms_users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query( value = "select * from USER_ENTITY where REALM_ID='4072228b-2586-4df3-97e9-443da84cee1e' and ENABLED=1",
            nativeQuery = true)
    List<User> getUsers();

    @Query("select concat(u.FIRST_NAME,' ', u.LAST_NAME) as nombre from User u where u.ID = ?1")
    String getNombrebyId(String id);
}
