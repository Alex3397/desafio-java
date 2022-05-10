package com.accenture.desafio.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private @Id String id;
    private String name;
    private String email;
    private String password;
    private @OneToMany List<Phone> phones;
    private String created;
    private String modified;
    private String last_login;
    private String token;
}
