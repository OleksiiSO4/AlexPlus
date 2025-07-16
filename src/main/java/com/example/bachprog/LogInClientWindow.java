package com.example.bachprog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInClientWindow
{
    private Stage stage;
    private Scene scene;
    private Parent root;

    public static String clientName;

    @FXML
    private Label alertLabel;
    @FXML
    private TextField clientNameLabel;
    @FXML
    private PasswordField clientPasswordLabel;
    @FXML
    private Button clientLoginButton;

    @FXML
    private CheckBox showThePasswordButton;

    @FXML
    private TextField unhiddenPassword;

    @FXML
    void showThePassword(ActionEvent event)
    {
        if(showThePasswordButton.isSelected())
        {
            unhiddenPassword.setText(clientPasswordLabel.getText());
            unhiddenPassword.setVisible(true);
            clientPasswordLabel.setVisible(false);
            return;
        }
        clientPasswordLabel.setText(unhiddenPassword.getText());
        clientPasswordLabel.setVisible(true);
        unhiddenPassword.setVisible(false);
    }

    public void OpenLogInClientWindow(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("LogInClientWindow.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void submit(ActionEvent event)
    {
        try
        {
            System.out.println("Client name: "+ clientNameLabel.getText());
            System.out.println("Password: " + clientPasswordLabel.getText());
                if (DataBase.clientLogInDataBaseCheckUp((clientNameLabel.getText()),(clientPasswordLabel.getText())))
                {
                    clientName = clientNameLabel.getText(); // 4database

                    // successfully proceeded
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("You've entered the correct user name or password");
                    alert.showAndWait();

                    clientNameLabel.clear();
                    clientPasswordLabel.clear();
                    unhiddenPassword.clear();

                    Stage currentStage = (Stage) clientNameLabel.getScene().getWindow();
                    currentStage.close();

                    ClientWindow.openWindow("ClientWindow.fxml", "CarHelper - Client Window");
                }
                else
                {
                    clientNameLabel.clear();
                    clientPasswordLabel.clear();
                    unhiddenPassword.clear();

                    Alert alert = new Alert(AlertType.INFORMATION);
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

    public void OpenClientWindow() throws IOException {
        ClientWindow.openWindow("ClientWindow.fxml", "Client Window");
    }

    public static void openWindow(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(LogInClientWindow.class.getResource("/com/example/bachprog/" + fxmlFile));
        Parent root = loader.load();

        LogInClientWindow controller = loader.getController();

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
