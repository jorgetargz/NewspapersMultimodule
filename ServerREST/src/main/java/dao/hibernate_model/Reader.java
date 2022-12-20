package dao.hibernate_model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Entity
@Table(name = "reader")

@NamedQuery(name = "HQL_GET_ALL_READERS",
        query = "from Reader")

public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_reader")
    private String name;

    @Column(name = "birth_reader")
    private LocalDate dateOfBirth;

    @OneToOne (mappedBy="reader", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Login login;

    @OneToMany(mappedBy = "idReader")
    @ToString.Exclude
    private Set<Readarticle> readarticles = new LinkedHashSet<>();


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reader reader = (Reader) o;
        return id != null && Objects.equals(id, reader.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
