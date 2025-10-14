package com.medac.trello.api.model;

import com.medac.trello.api.view.UserView;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "usuario")
public class User {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_usuario")
    private long id;

    @Column(name = "nombre")
    private String name;
    @Column(name = "nombre_usuario")
    private String userName;
    private String email;
    private String password;

    protected User() {}

    public User(String name, String userName, String email, String password) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
