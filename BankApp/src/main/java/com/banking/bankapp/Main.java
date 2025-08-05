package com.banking.bankapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/banking/bankapp/view/login.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Rural Bank of Nepal - Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false); // Login window typically fixed size
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
