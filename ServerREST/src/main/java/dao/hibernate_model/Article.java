package dao.hibernate_model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name_article")
    private String nameArticle;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_type")
    private Articletype idType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_newspaper")
    @ToString.Exclude
    private Newspaper idNewspaper;

    @OneToMany(mappedBy = "idArticle")
    @ToString.Exclude
    private Set<Readarticle> readarticles = new LinkedHashSet<>();

    public Article(String nameArticle, Articletype idType) {
        this.nameArticle = nameArticle;
        this.idType = idType;
    }

    public Article(String name, Articletype articletype, Newspaper newspaper) {
        this.nameArticle = name;
        this.idType = articletype;
        this.idNewspaper = newspaper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Article article = (Article) o;
        return id != null && Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}