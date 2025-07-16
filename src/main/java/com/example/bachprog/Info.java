package com.example.bachprog;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Info
{
    public static void openInfoWindow(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(Info.class.getResource("/com/example/bachprog/" + fxmlFile));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        Image icon = new Image("Logo.png");
        stage.getIcons().add(icon);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}
