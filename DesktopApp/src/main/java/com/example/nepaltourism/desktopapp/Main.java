package com.example.nepaltourism.desktopapp;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        ChangeScene changeScene = new ChangeScene();
        changeScene.switchScene(primaryStage, "login.fxml", "Nepal Tourism - Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

