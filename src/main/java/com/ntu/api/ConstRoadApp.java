package com.ntu.api;

import com.ntu.api.domain.Lists;
import com.ntu.api.domain.listCreate.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ConstRoadApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Constant Road 1.2.5");
        primaryStage.setResizable(false);
        Parent main = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/ntu/api/fx/model/main.fxml")));
        Main.start();
        primaryStage.setScene(new Scene(main));
        primaryStage.show();
    }
}
