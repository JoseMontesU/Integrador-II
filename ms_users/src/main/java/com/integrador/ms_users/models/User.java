package com.integrador.ms_users.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_ENTITY")
public class User {

    @Id
    private String ID;
    private String EMAIL;
    private boolean ENABLED;
    private String FIRST_NAME;
    private String LAST_NAME;
    private String USERNAME ;

}
