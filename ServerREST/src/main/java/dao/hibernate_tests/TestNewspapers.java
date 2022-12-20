package dao.hibernate_tests;

import dao.hibernate_impl.NewspaperDaoImpl;
import dao.hibernate_model.Article;
import dao.hibernate_model.Articletype;
import dao.hibernate_model.Newspaper;
import dao.utils.JPAUtil;

import java.time.LocalDate;
import java.util.Set;

public class TestNewspapers {

    public static void main(String[] args) {
        System.out.println("Testing Hibernate newspapers");


        System.out.println("NEWSPAERS DAO");
        NewspaperDaoImpl newspaperDaoImpl = new NewspaperDaoImpl(new JPAUtil());

        System.out.println("GET ALL");
        newspaperDaoImpl.getAll().forEach(System.out::println);

        System.out.println("SAVE");
        Newspaper newspaper = new Newspaper("El País", LocalDate.of(2021, 1, 1));
        newspaperDaoImpl.save(newspaper);
        System.out.println(newspaper);

        System.out.println("GET");
        System.out.println(newspaperDaoImpl.get(newspaper.getId()));

        System.out.println("GET ALL");
        newspaperDaoImpl.getAll().forEach(System.out::println);

        System.out.println("UPDATE");
        newspaper.setArticles(Set.of(new Article("Articulo 1", new Articletype(1, "Tipo 1"), newspaper)));
        newspaperDaoImpl.update(newspaper);

        System.out.println("GET ALL");
        newspaperDaoImpl.getAll().forEach(System.out::println);

        System.out.println("DELETE");
        newspaperDaoImpl.delete(newspaper);

        System.out.println("GET ALL");
        newspaperDaoImpl.getAll().forEach(System.out::println);

    }

}
