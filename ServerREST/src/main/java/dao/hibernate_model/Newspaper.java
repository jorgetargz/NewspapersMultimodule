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
@Table(name = "newspaper")

@NamedQuery(name = "HQL_GET_ALL_NEWSPAPERS",
        query = "from Newspaper")

public class Newspaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_newspaper")
    private String nameNewspaper;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "idNewspaper", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Article> articles = new LinkedHashSet<>();

    public Newspaper(String name, LocalDate releaseDate) {
        this.nameNewspaper = name;
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Newspaper newspaper = (Newspaper) o;
        return id != null && Objects.equals(id, newspaper.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
