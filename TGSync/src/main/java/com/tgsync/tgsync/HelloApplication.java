package com.tgsync.tgsync;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Scene mainScene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        mainScene = new Scene(fxmlLoader.load(), 1200, 800);

        stage.setTitle("TGSync");
        stage.setScene(mainScene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo-tgsync.png")));

        stage.show();
    }

    public static Scene getMainScene(){
        return mainScene;
    }
    public static void main(String[] args) {
        launch();
    }
}