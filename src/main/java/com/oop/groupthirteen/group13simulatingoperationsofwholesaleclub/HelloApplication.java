package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneSwitcher.stage= stage;
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("Loginfxml.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("wholesaleClub");
        stage.setScene(scene);



        stage.show();
    }

    public static <String> void main(String[] args) {
        launch();
    }
}