package com.example.bachprog;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class MainWindow{

    @FXML
    private Button clientButton;
    @FXML
    private Button dispatchButton;

    @FXML
    private Button InfoButton;

    @FXML
    private Button accountantButton;

    @FXML
    private Button mechanicButton;

    @FXML
    private void openLogInClientWindow(ActionEvent event)
    {
        try {
            LogInClientWindow.openWindow("LogInClientWindow.fxml", "Client Window");

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void openInfoWindow(ActionEvent event)
    {
        try {
            Info.openInfoWindow("Info.fxml", "Info");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void openLogInDispatchWindow(ActionEvent event)
    {
        try
        {
            LogInDispatchWindow.openWindow("LogInDispatchWindow.fxml", "Dispatch Window");

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        }catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void openLogInMechanicWindow(ActionEvent event)
    {
        try
        {
            LogInMechanicWindow.openWindow("LogInMechanicWindow.fxml", "Mechanic Window");

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }
    @FXML
    private void openLogInAccountantWindow(ActionEvent event)
    {
        //accountant
    }

}