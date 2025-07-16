package com.example.bachprog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DispatchWindow
{
    @FXML
    private Button openOrderCreationWindow;

    @FXML
    private void openOrderCreationWindow() throws IOException
    {
        OrderCreationWindow.openWindow("OrderCreationWindow.fxml", "Create an order");
    }

    @FXML
    private ListView<String> requests;

    @FXML
    private ListView<String> mechanicList;

    @FXML
    private Label clientNameField;

    @FXML
    private Label clientPhNumberField;

    @FXML
    private Label clientEmailAddressField;

    @FXML
    private ImageView clientImage;

    public static String requestOrderInfo;
    public static String mechanicName;

    @FXML
    public void initializeTheListOfRequests() {
        List<String> orderRequests = DataBase.getOrderRequestDisplayStrings();
        ObservableList<String> observableList = FXCollections.observableArrayList(orderRequests);
        requests.setItems(observableList);

        requests.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            requestOrderInfo = newVal;
            if (newVal != null) {
                String[] parts = newVal.split(" - ");

                String [] clientInfo = DataBase.getClientContactInfo(parts[1]); // 0 - phNum; 1 - email; 2 - image

                clientNameField.setText(parts[1]);
                clientPhNumberField.setText(clientInfo[0]);
                clientEmailAddressField.setText(clientInfo[1]);

                if (clientImage != null) {
                    Image image = new Image(clientInfo[2]);
                    clientImage.setImage(image);
                } else {
                    System.err.println("clientImage is null!");
                }

            }
        });

        List<String> mechanicNames = DataBase.getMechanicNames();
        ObservableList<String> obsNames = FXCollections.observableArrayList(mechanicNames);
        mechanicList.setItems(obsNames);

        mechanicList.getSelectionModel().selectedItemProperty().addListener((obs1, oldVal1, selectedMechanicName) -> {
            if (selectedMechanicName != null)
            {
                mechanicName = selectedMechanicName;
            }
        });


    }

    @FXML
    private void selectedObjectFromList() {
        String selected = requests.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Selected: " + selected);
        } else {
            System.out.println("Nothing has been selected.");
        }
    }

    public static void openWindow(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(DispatchWindow.class.getResource("/com/example/bachprog/" + fxmlFile));
        Parent root = loader.load();

        DispatchWindow controller = loader.getController();

        controller.initializeTheListOfRequests();

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
