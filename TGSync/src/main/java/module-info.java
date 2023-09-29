module com.tgsync.tgsync {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.tgsync.tgsync to javafx.fxml;
    exports com.tgsync.tgsync;
}