module ServerRest {
    requires Utils;
    requires jakarta.jakartaee.web.api;
    requires lombok;
    requires org.apache.logging.log4j;
    requires com.zaxxer.hikari;
    requires java.sql;
    requires spring.tx;
    requires spring.jdbc;
    requires jakarta.mail;
}