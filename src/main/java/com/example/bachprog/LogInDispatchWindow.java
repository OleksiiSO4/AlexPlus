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

public class LogInDispatchWindow
{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label alertLabel;
    @FXML
    private TextField dispatcherNameLabel;
    @FXML
    private PasswordField dispatcherPasswordLabel;
    @FXML
    private Button dispatcherLoginButton;

    @FXML
    private CheckBox showThePasswordButton;

    @FXML
    private TextField unhiddenPassword;

    @FXML
    void showThePassword(ActionEvent event)
    {
        if(showThePasswordButton.isSelected())
        {
            unhiddenPassword.setText(dispatcherPasswordLabel.getText());
            unhiddenPassword.setVisible(true);
            dispatcherPasswordLabel.setVisible(false);
            return;
        }
        dispatcherPasswordLabel.setText(unhiddenPassword.getText());
        dispatcherPasswordLabel.setVisible(true);
        unhiddenPassword.setVisible(false);
    }

    public void OpenLogInDispatchWindow(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("LogInDispatchWindow.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void submit(ActionEvent event)
    {
        try
        {
            System.out.println("Dispatch name: "+ dispatcherNameLabel.getText());
            System.out.println("Password: " + dispatcherPasswordLabel.getText());
            if (DataBase.dispatcherLogInDataBaseCheckUp((dispatcherNameLabel.getText()),(dispatcherPasswordLabel.getText())))
            {
                // successfully proceeded
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("You've entered the correct user name or password");
                alert.showAndWait();

                dispatcherNameLabel.clear();
                dispatcherPasswordLabel.clear();
                unhiddenPassword.clear();

                Stage currentStage = (Stage) dispatcherNameLabel.getScene().getWindow();
                currentStage.close();

                DispatchWindow.openWindow("DispatchWindow.fxml", "CarHelper - Dispatch Window");
            }
            else
            {
                dispatcherNameLabel.clear();
                dispatcherPasswordLabel.clear();
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

    public void OpenMainWindow(ActionEvent event) throws IOException {
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

    public void OpenDispatchWindow() throws IOException
    {
        DispatchWindow.openWindow("DispatchWindow.fxml", "Dispatch Window");
    }

    public static void openWindow(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(LogInDispatchWindow.class.getResource("/com/example/bachprog/" + fxmlFile));
        Parent root = loader.load();

        LogInDispatchWindow controller = loader.getController();

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
