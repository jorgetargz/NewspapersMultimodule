package dao.hibernate_model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "login")

public class Login {

    @Id
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "mail")
    private String email;

    @Column(name = "id_reader")
    private int idReader;

    public Login(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
