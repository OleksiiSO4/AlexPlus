package com.example.bachprog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static java.util.Objects.*;

public class LogInMechanicWindow
{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label alertLabel;
    @FXML
    private TextField mechanicNameLabel;
    @FXML
    private PasswordField mechanicPasswordLabel;
    @FXML
    private Button mechanicLoginButton;

    @FXML
    private CheckBox showThePasswordButton;

    @FXML
    private TextField unhiddenPassword;

    @FXML
    private Button infoButton;

    @FXML
    private void handleInfoButtonClick(ActionEvent event) {
        try {
            Info.openInfoWindow("Info.fxml", "CarHelper - Info");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showThePassword(ActionEvent event)
    {
        if(showThePasswordButton.isSelected())
        {
            unhiddenPassword.setText(mechanicPasswordLabel.getText());
            unhiddenPassword.setVisible(true);
            mechanicPasswordLabel.setVisible(false);
            return;
        }
        mechanicPasswordLabel.setText(unhiddenPassword.getText());
        mechanicPasswordLabel.setVisible(true);
        unhiddenPassword.setVisible(false);
    }

    public void submit(ActionEvent event)
    {
        try
        {

            System.out.println("Mechanic name: "+ mechanicNameLabel.getText());
            System.out.println("Password: " + mechanicPasswordLabel.getText());

            if (DataBase.mechanicLogInDataBaseCheckUp((mechanicNameLabel.getText()),(mechanicPasswordLabel.getText())))
            {
                // successfully proceeded
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("You've entered the correct user name or password");
                alert.showAndWait();

                mechanicNameLabel.clear();
                mechanicPasswordLabel.clear();
                unhiddenPassword.clear();
            }
            else
            {
                mechanicNameLabel.clear();
                mechanicPasswordLabel.clear();
                unhiddenPassword.clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("You've entered the wrong user name or password");
                alert.showAndWait();

            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public void OpenLogInMechanicWindow(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(requireNonNull(getClass().getResource("LogInMechanicWindow.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        Image icon = new Image("Logo.png");
        stage.getIcons().add(icon);

        stage.setTitle("CarHelper - Mechanic Mode");
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }

    public void OpenClientWindow(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(requireNonNull(getClass().getResource("LogInClientWindow.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        Image icon = new Image("Logo.png");
        stage.getIcons().add(icon);

        stage.setTitle("CarHelper - Client Mode");
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }

    public void OpenMainWindow(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        Image icon = new Image("Logo.png");
        stage.getIcons().add(icon);

        stage.setTitle("CarHelper");
        stage.setResizable(false);


        stage.setScene(scene);
        stage.show();
    }

    public static void openWindow(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(LogInMechanicWindow.class.getResource("/com/example/bachprog/" + fxmlFile));
        Parent root = loader.load();

        LogInMechanicWindow controller = loader.getController();

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


