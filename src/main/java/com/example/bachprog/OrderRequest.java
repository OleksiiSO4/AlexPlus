package com.example.bachprog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;

public class OrderRequest
{

    @FXML
    private Label greetingLabel;

    @FXML
    private DatePicker dateRequest;

    @FXML
    public void setDateRequestLimit() {
        dateRequest.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE; -fx-text-fill: gray;");
                }
            }
        });

        dateRequest.setValue(LocalDate.now()); // set today's date by default
    }

    @FXML
    private TextField makeOfCarRequested;

    @FXML
    private TextField modelOfCarRequested;

    @FXML
    private ChoiceBox<String> requestedServiceType;

    @FXML
    public void initializeRequestedServiceList()
    {
        requestedServiceType.getItems().addAll(
                "Oil Change",
                "Brake Inspection",
                "Tire Rotation",
                "Battery Replacement",
                "Wheel Alignment",
                "Air Filter Replacement",
                "Coolant Flush",
                "Transmission Service",
                "Engine Diagnostics",
                "AC System Repair",
                "Other"
        );

        requestedServiceType.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if ("Other".equals(newVal)) {
                initializeAdditionalRequest();
            }
            else
            {
                disableAdditionalRequest();
                requestedIssue.clear();
            }
        });
    }

    @FXML
    private TextField requestedIssue;

    @FXML
    private Label describeIssueLabel;

    @FXML
    public void initializeAdditionalRequest() {
        requestedIssue.setDisable(false);
        describeIssueLabel.setDisable(false);
    }

    @FXML
    public void disableAdditionalRequest() {
        requestedIssue.setDisable(true);
        describeIssueLabel.setDisable(true);
    }

    @FXML
    private Button addButton;

    @FXML
    public void addRequestToDB()
    {
        if ("Other".equals(requestedServiceType.getValue()))
        {
            DataBase.insertOrderRequest(LogInClientWindow.clientName, makeOfCarRequested.getText(), modelOfCarRequested.getText(),dateRequest.getValue(),requestedIssue.getText());
            repairRequests.getItems().clear();
            initializeRequestList();

            dateRequest.setValue(LocalDate.now());
            requestedIssue.clear();
            makeOfCarRequested.clear();
            modelOfCarRequested.clear();
        }
        else
        {
            DataBase.insertOrderRequest(LogInClientWindow.clientName, makeOfCarRequested.getText(), modelOfCarRequested.getText(),dateRequest.getValue(),requestedServiceType.getValue());
            repairRequests.getItems().clear();
            initializeRequestList();

            dateRequest.setValue(LocalDate.now());
            requestedServiceType.setValue(" ");
            makeOfCarRequested.clear();
            modelOfCarRequested.clear();
        }
    }

    @FXML
    private Button deleteButton;

    @FXML
    private void removeRequestFromDB()
    {
        DataBase.deleteOrderRequest(repairRequests.getSelectionModel().getSelectedItem(),LogInClientWindow.clientName);
        repairRequests.getItems().clear();
        initializeRequestList();
    }

    @FXML
    private ListView <String> repairRequests;

    @FXML
    public void initializeRequestList()
    {
        String[] requests = DataBase.getRequestsByClient(LogInClientWindow.clientName);
        repairRequests.getItems().addAll(requests);
    }
    public void setGreeting(String name) {
        if (name != null && !name.isEmpty()) {
            String capitalized = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            greetingLabel.setText("Dear,   " + capitalized);
        } else {
            greetingLabel.setText("Dear, Client");
        }
    }
    public static void openOrderRequestWindow(String fxmlFile, String title) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(Info.class.getResource("/com/example/bachprog/" + fxmlFile));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        Image icon = new Image("Logo.png");
        stage.getIcons().add(icon);

        OrderRequest controller = loader.getController();
        controller.setGreeting(LogInClientWindow.clientName);
        controller.disableAdditionalRequest();
        controller.initializeRequestedServiceList();
        controller.setDateRequestLimit();
        controller.initializeRequestList();


        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }


}
