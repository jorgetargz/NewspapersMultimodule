package dao.hibernate_tests;

import dao.hibernate_impl.LoginDaoImpl;
import dao.hibernate_impl.ReadersDaoImpl;
import dao.hibernate_model.Login;
import dao.hibernate_model.Reader;
import dao.utils.JPAUtil;

import java.time.LocalDate;

public class TestReaders {

    public static void main(String[] args) {
        System.out.println("Testing Hibernate readers");

        System.out.println("READERS DAO");
        ReadersDaoImpl readersDaoImpl = new ReadersDaoImpl(new JPAUtil());
        Reader reader = new Reader("Pepe", LocalDate.of(1990, 1, 1));

        System.out.println("GET ALL");
        readersDaoImpl.getAll().forEach(System.out::println);

//        System.out.println("SAVE");
//        System.out.println(readersDaoImpl.save(reader));

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
        Login login = new Login("Pepe", "1234", "mega@server.com", new Reader("Jacobino", LocalDate.of(1990, 1, 1)));

        System.out.println("SAVE");
        System.out.println(loginDaoImpl.save(login));

        System.out.println("UPDATE");
        login.getReader().setName("Pepito");
        System.out.println(readersDaoImpl.update(login.getReader()));

        System.out.println("GET ALL");
        readersDaoImpl.getAll().forEach(System.out::println);

        System.out.println("GET");
        System.out.println(loginDaoImpl.get(login.getUsername()));

        System.out.println("DELETE");
        loginDaoImpl.delete(login);

        System.out.println("GET ALL");
        readersDaoImpl.getAll().forEach(System.out::println);

    }
}

