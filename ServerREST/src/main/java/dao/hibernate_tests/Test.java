package dao.hibernate_tests;

import dao.hibernate_impl.NewspaperDaoImpl;
import dao.hibernate_impl.ReadersDaoImpl;
import dao.hibernate_model.Newspaper;
import dao.hibernate_model.Reader;
import dao.utils.JPAUtil;

import java.time.LocalDate;

public class Test {

    public static void main(String[] args) {
        System.out.println("Testing Hibernate implementations");
        NewspaperDaoImpl newspaperDaoImpl = new NewspaperDaoImpl(new JPAUtil());

        Newspaper newspaper = new Newspaper("El País", LocalDate.of(2021, 1, 1));

        System.out.println("NEWSPAERS DAO");
        System.out.println("GET ALL");
        newspaperDaoImpl.getAll().forEach(System.out::println);

        System.out.println("SAVE");
        System.out.println(newspaperDaoImpl.save(newspaper));

        System.out.println("GET");
        System.out.println(newspaperDaoImpl.get(newspaper.getId()));

        System.out.println("GET ALL");
        newspaperDaoImpl.getAll().forEach(System.out::println);

        System.out.println("DELETE");
        newspaperDaoImpl.delete(newspaper);

        System.out.println("GET ALL");
        newspaperDaoImpl.getAll().forEach(System.out::println);

        Reader reader = new Reader("Jacobino", LocalDate.of(1990, 1, 1));

        ReadersDaoImpl readersDaoImpl = new ReadersDaoImpl(new JPAUtil());

        System.out.println("READERS DAO");
        System.out.println("GET ALL");
        readersDaoImpl.getAll().forEach(System.out::println);

        System.out.println("SAVE");
        System.out.println(readersDaoImpl.save(reader));

        System.out.println("GET");
        System.out.println(readersDaoImpl.get(reader.getId()));

        System.out.println("GET ALL");
        readersDaoImpl.getAll().forEach(System.out::println);

        System.out.println("DELETE");
        readersDaoImpl.delete(reader);

        System.out.println("GET ALL");
        readersDaoImpl.getAll().forEach(System.out::println);


    }
}
