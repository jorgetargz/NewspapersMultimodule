package dao.hibernate_model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor

@Entity
@Table(name = "newspaper")

@NamedQuery(name = "HQL_GET_ALL_NEWSPAPERS",
        query = "from Newspaper")

public class Newspaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_newspaper")
    private String nameNewspaper;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    public Newspaper(String name, LocalDate releaseDate) {
        this.nameNewspaper = name;
        this.releaseDate = releaseDate;
    }

//    @Override
//    public String toString() {
//        return this.nameNewspaper;
//    }
}
