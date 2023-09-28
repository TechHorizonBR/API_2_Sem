module com.tgsync.tgsync {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tgsync.tgsync to javafx.fxml;
    exports com.tgsync.tgsync;
}