package dao.hibernate_model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(exclude = { "reader"})
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

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_reader", nullable = false)
    private Reader reader;

    public Login(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
