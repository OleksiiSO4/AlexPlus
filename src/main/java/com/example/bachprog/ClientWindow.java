package com.example.bachprog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ClientWindow {

    private String SelectedInvoicedCar;
    private String SelectedInvoiceOrder;

    @FXML
    private Button getInvoice;

    @FXML
    private void onGeneratePdfButtonClick()
    {
        Invoice.generateCustomerInvoice("invoices/invoice_001.pdf", DataBase.getHistoryForCient(LogInClientWindow.clientName, SelectedInvoiceOrder), SelectedInvoicedCar);

        Invoice opener = new Invoice();
        opener.openPdf("invoices/invoice_001.pdf");
    }


    @FXML
    private ImageView carImage;

    @FXML
    private ImageView statusImage;

    @FXML
    private ListView<String> listOfCars;

    @FXML
    private ListView<String> listOfHistory;

    @FXML
    private Label orderDate;

    @FXML
    private Label typeOfRepairement;

    @FXML
    private Label mechanicName;

    @FXML
    private Label mileage;

    @FXML
    private Label cost;

    @FXML
    private Label status;

    @FXML
    private MenuItem aboutProg;

    @FXML
    private void handleMenuClick(ActionEvent event) {
        Object source = event.getSource();

        if (source == aboutProg) {
            try {
                Info.openInfoWindow("Info.fxml", "CarHelper - Info");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void newOrderWindowOpen()
    {
        try {
            OrderRequest.openOrderRequestWindow("OrderRequest.fxml", "CarHelper - Order Request");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void updateLabels(String[] repairDetailsArray) {
        for (String repairDetail : repairDetailsArray) {
            // Split each string in the array into parts
            String[] parts = repairDetail.split(" - ");

            // Extract individual fields from the parts
            String date = parts[0]; // Order date
            String typeofRepairement = parts[1]; // Type of repair
            String mechanicname = parts[2]; // Mechanic name
            String mileAge = parts[3]; // Mileage (e.g., "80.041 km")
            String Cost = parts[4]; // Cost (e.g., "$200.0")
            String Status = parts [5]; // StatusOfOrder

            initializeStatusImage(parts [5]);

            // Update the labels
            orderDate.setText("Date: " + date);
            typeOfRepairement.setText("Type of repairement: " + typeofRepairement);
            mechanicName.setText("Mechanic name: " + mechanicname);
            mileage.setText("Mileage: " + mileAge);
            cost.setText("Cost: " + Cost);
            status.setText("Status: " + Status);


            break;
        }
    }

    @FXML
    public void initializeList() {
        //String[] cars = DataBase.getCarsByClientName(LogInClientWindow.clientName);

        Set<String> carSet = new HashSet<>(Arrays.asList(DataBase.getCarsByClientName(LogInClientWindow.clientName)));

        List<String> carList = new ArrayList<>(carSet);

        listOfCars.getItems().setAll(carList);

        listOfCars.getSelectionModel().clearSelection();

        listOfCars.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
//                System.out.println("Selected car: " + newValue); // carValueName
//                System.out.println("Link: " + DataBase.getCarImageByValue(newValue)); //carLink
                initializeCarImage(DataBase.getCarImageByValue(newValue));
                SelectedInvoicedCar = newValue;
                // small records
                //String[] generalRepairments = DataBase.getRepairementsByClientAndCar(LogInClientWindow.clientName, newValue);

                Set<String> generalRepairmentsSet = new HashSet<>(Arrays.asList(DataBase.getRepairementsByClientAndCar(LogInClientWindow.clientName, newValue)));

                List<String> generalRepairments = new ArrayList<>(generalRepairmentsSet);

                listOfHistory.getItems().setAll(generalRepairments);

                listOfHistory.getSelectionModel().clearSelection();

                // full history description
                listOfHistory.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1 != null)
                    {

                        /*
                        for (String detail : DataBase.getHistoryForCient(LogInClientWindow.clientName, newValue1)) {
                            System.out.println(detail);
                        }
                        */

                        String[] historyDetails = DataBase.getHistoryForCient(LogInClientWindow.clientName, newValue1);
                        updateLabels(historyDetails);
                        SelectedInvoiceOrder = newValue1;
                        //listOfHistory.getSelectionModel().clearSelection();
                    }
                });

            }
        });

    }

    @FXML
    public void initializeCarImage(String imageLink)
    {
        Image image = new Image(imageLink);
        carImage.setImage(image);
    }

    @FXML
    public void initializeStatusImage(String status)
    {
        if (status.equals("DONE")) {
            Image image = new Image("doneIcon.png");
            statusImage.setImage(image);
        } else {
            Image image = new Image("inProgress.png");
            statusImage.setImage(image);
        }
    }

    public static void openWindow(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(ClientWindow.class.getResource("/com/example/bachprog/" + fxmlFile));
        Parent root = loader.load();

        ClientWindow controller = loader.getController();
        controller.initializeList();

        //controller.initializeCarImage();


        Stage stage = new Stage();
        Scene scene = new Scene(root);

        Image icon = new Image("Logo.png");
        stage.getIcons().add(icon);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
    public static void openWindow(ActionEvent event, String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(ClientWindow.class.getResource("/com/example/bachprog/" + fxmlFile));
        Parent root = loader.load();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

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
