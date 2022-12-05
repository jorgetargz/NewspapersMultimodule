module ClientJavaFX {
    requires Utils;

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    requires retrofit2;
    requires retrofit2.converter.gson;
    requires retrofit2.adapter.rxjava3;
    requires io.reactivex.rxjava3;

    requires lombok;
    requires io.vavr;
    requires org.apache.logging.log4j;

    requires jakarta.inject;
    requires jakarta.annotation;
    requires jakarta.cdi;
    requires java.sql;
    requires gson;
    requires okhttp3;

    exports dao;
    exports dao.di;
    exports dao.impl;
    exports dao.newspapers_api;
    exports dao.common;
    exports dao.newspapers_api.di;
    exports dao.newspapers_api.config;
    exports domain.services;
    exports domain.services.impl;

    exports gui.main.common;
    exports gui.screens.welcome;
    exports gui.screens.readers.readers_list;
    exports gui.screens.readers.readers_add;
    exports gui.screens.readers.readers_update;
    exports gui.screens.readers.readers_delete;
    exports gui.screens.newspapers.newspapers_list;
    exports gui.screens.newspapers.newspapers_add;
    exports gui.screens.newspapers.newspapers_update;
    exports gui.screens.newspapers.newspapers_delete;

    opens config;
    opens dao.common;

    opens gui.main;
    opens gui.main.common;
    opens gui.screens.common;
    opens gui.screens.main;
    opens gui.screens.login;
    opens gui.screens.welcome;
    opens gui.screens.readers.readers_list;
    opens gui.screens.readers.readers_add;
    opens gui.screens.readers.readers_update;
    opens gui.screens.readers.readers_delete;
    opens gui.screens.newspapers.newspapers_list;
    opens gui.screens.newspapers.newspapers_add;
    opens gui.screens.newspapers.newspapers_update;
    opens gui.screens.newspapers.newspapers_delete;

}