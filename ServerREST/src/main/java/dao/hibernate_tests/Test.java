package dao.hibernate_tests;

import dao.hibernate_impl.LoginDaoImpl;
import dao.hibernate_impl.NewspaperDaoImpl;
import dao.hibernate_impl.ReadersDaoImpl;
import dao.hibernate_model.Login;
import dao.hibernate_model.Newspaper;
import dao.hibernate_model.Reader;
import dao.utils.JPAUtil;

import java.time.LocalDate;

public class Test {

    public static void main(String[] args) {
        System.out.println("Testing Hibernate implementations");


        System.out.println("NEWSPAERS DAO");
        NewspaperDaoImpl newspaperDaoImpl = new NewspaperDaoImpl(new JPAUtil());
        Newspaper newspaper = new Newspaper("El País", LocalDate.of(2021, 1, 1));

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


        System.out.println("READERS DAO");
        ReadersDaoImpl readersDaoImpl = new ReadersDaoImpl(new JPAUtil());
        Reader reader = new Reader("Pepe", LocalDate.of(1990, 1, 1));

        System.out.println("GET ALL");
        readersDaoImpl.getAll().forEach(System.out::println);

        System.out.println("SAVE");
        System.out.println(readersDaoImpl.save(reader));

        System.out.println("GET ALL");
        readersDaoImpl.getAll().forEach(System.out::println);

        System.out.println("UPDATE");
        reader.setName("Pepito");
        System.out.println(readersDaoImpl.update(reader));

        System.out.println("GET ALL");
        readersDaoImpl.getAll().forEach(System.out::println);

        System.out.println("DELETE");
        readersDaoImpl.delete(reader);

        System.out.println("GET ALL");
        readersDaoImpl.getAll().forEach(System.out::println);


        System.out.println("LOGIN DAO");
        LoginDaoImpl loginDaoImpl = new LoginDaoImpl(new JPAUtil());
        Login login = new Login("jacobino", "1234", "mega@server.com", new Reader("Jacobino", LocalDate.of(1990, 1, 1)));

        System.out.println("SAVE");
        System.out.println(loginDaoImpl.save(login));

        System.out.println("GET");
        System.out.println(loginDaoImpl.get(login.getUsername()));

        System.out.println("DELETE");
        loginDaoImpl.delete(login);

    }
}
