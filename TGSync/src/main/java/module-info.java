module com.tgsync.tgsync {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.opencsv;


    exports com.tgsync.tgsync;
    opens Model.DTO to javafx.base;
    exports com.tgsync.tgsync.util;
    opens com.tgsync.tgsync.util;
    opens com.tgsync.tgsync;
}