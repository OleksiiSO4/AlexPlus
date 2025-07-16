package com.example.bachprog;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderCreationWindow
{
    @FXML
    private DatePicker orderDate;

    @FXML
    private TextField carMake;

    @FXML
    private TextField carModel;

    @FXML
    private TextField repairement;

    @FXML
    private TextField cost;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private TextField mechanic;

    @FXML
    private TextField client;

    @FXML
    private TextField mileage;

    @FXML
    private TextArea instruction;

    @FXML
    private ChoiceBox<String> orderStatus;

    @FXML
    private ListView<String> currentOrderList;
    @FXML
    public void initializeRequestedServiceList()
    {
        orderStatus.getItems().addAll("IN PROGRESS", "DONE");
        orderStatus.setValue("IN PROGRESS");
    }

    @FXML
    public void initializeOrderCreationWindowFields()
    {
        String info = DispatchWindow.requestOrderInfo;

        String[] requestInfo = info.split(" - "); // 0 - date; 1 - client; 2 - Make; 3 - Model; 4 - ServiceType

        client.setText(requestInfo[1]);
        carMake.setText(requestInfo[2]);
        carModel.setText(requestInfo[3]);
        repairement.setText(requestInfo[4]);

        mechanic.setText(DispatchWindow.mechanicName);
    }

    @FXML
    public void initializeOrderList()
    {
        orderDate.valueProperty().addListener((observable, oldValue, selectedDate) -> {
            List<String[]> repairements = DataBase.getRepairementsForMechanicAndDate(DispatchWindow.mechanicName, selectedDate);
            List<String> displayList = new ArrayList<>();

            if (repairements.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Data");
                alert.setHeaderText(null);
                alert.setContentText("Nothing was set up on that date.");
                alert.showAndWait();

                currentOrderList.getItems().clear();
                return;
            }

            for (String[] row : repairements) {
                String line = row[1] + " - " + row[2] + " | " + row[3] + " " + row[4] +
                        " | " + row[6] + " | Status: " + row[9];
                displayList.add(line);
            }

            currentOrderList.setItems(FXCollections.observableArrayList(displayList));

        });
    }

    @FXML
    private void clearAllFields()
    {
        //mechanic.setText(null);
        //orderDate.setValue(null);
        client.setText(null);
        carMake.setText(null);
        carModel.setText(null);
        repairement.setText(null);
        cost.setText(null);
        startTime.setText(null);
        endTime.setText(null);
        mileage.setText(null);
        instruction.setText(null);
    }
    @FXML
    private void addAnOrder()
    {
        if ((client.getText() == null) ||(orderDate.getValue() == null) || (carMake.getText() == null)
        || (carModel.getText() == null) || (repairement.getText() == null) || (cost.getText() == null)
        || (startTime.getText() == null) || (endTime.getText() == null) || (mechanic.getText() == null)
        || (mileage.getText() == null) || (instruction.getText() == null))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields.");
            alert.showAndWait();
        }
        else
        {
            char costValue = (char) Integer.parseInt(cost.getText());
            //char mileageValue = (char) Integer.parseInt(mileage.getText());
            if (DataBase.insertRepairement(client.getText(), mechanic.getText(),carMake.getText(),carModel.getText(),
                orderDate.getValue(),repairement.getText(),costValue,instruction.getText(),mileage.getText(),
                orderStatus.getValue(), startTime.getText(), endTime.getText()))
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Order successfully created");
                alert.showAndWait();

                initializeOrderList();
                clearAllFields();
            }

        }
    }

    public static void openWindow(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(OrderCreationWindow.class.getResource("/com/example/bachprog/" + fxmlFile));
        Parent root = loader.load();

        OrderCreationWindow controller = loader.getController();
        controller.initializeRequestedServiceList();
        controller.initializeOrderCreationWindowFields();
        controller.initializeOrderList();


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
