module com.example.bachprog {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires layout;
    requires kernel;
    requires java.desktop;
    requires io;

    opens com.example.bachprog to javafx.fxml;
    exports com.example.bachprog;
}
