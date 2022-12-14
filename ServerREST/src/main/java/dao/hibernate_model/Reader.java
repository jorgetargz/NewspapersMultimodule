package dao.hibernate_model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "reader")

@NamedQuery(name = "HQL_GET_ALL_READERS",
        query = "from Reader")

public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_reader")
    private String name;

    @Column(name = "birth_reader")
    private LocalDate dateOfBirth;

    @Transient
    private Login login;

    public Reader(String nameInput, LocalDate birthdayInput, Login loginInput) {
        this.name = nameInput;
        this.dateOfBirth = birthdayInput;
        this.login = loginInput;
    }

    public Reader(int id, String nameInput, LocalDate birthdayInput) {
        this.id = id;
        this.name = nameInput;
        this.dateOfBirth = birthdayInput;
    }

    //USED IN HIBERNATE TEST ONLY
    public Reader(String nameInput, LocalDate birthdayInput) {
        this.name = nameInput;
        this.dateOfBirth = birthdayInput;
    }

    public void changePassword(String password) {
        if (this.login != null && login.getPassword() != null) {
            this.login.setPassword(password);
        }
    }
}
