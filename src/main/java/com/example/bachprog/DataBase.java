package com.example.bachprog;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataBase
{
    public static void dataBase()
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root",
                    "password123");

            System.out.println("Database has been successfully connected!");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CLIENTS");

            while(resultSet.next())
            {
                System.out.print(resultSet.getString("idclients"));
                System.out.print(" ");
                System.out.print(resultSet.getString("clientName"));
                System.out.print(" ");
                System.out.println(resultSet.getString("clientPassword"));
            }
        }
            catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean dispatcherLogInDataBaseCheckUp(String dispatcherLogin, String dispatcherPassword) {
        boolean dispatcherExists = false;

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root", "password123"
            );

            String query = "SELECT * FROM DISPATCHER WHERE dispatcherLogin = ? AND dispatcherPassword = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dispatcherLogin);
            preparedStatement.setString(2, dispatcherPassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                dispatcherExists = true;
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dispatcherExists;
    }

    public static boolean clientLogInDataBaseCheckUp(String clientNameLabel, String clientPasswordLabel) {
        boolean clientExists = false;

        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root", "password123");

            String query = "SELECT * FROM CLIENTS WHERE clientName = ? AND clientPassword = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, clientNameLabel);
            preparedStatement.setString(2, clientPasswordLabel);


            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                clientExists = true;
            }


            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientExists;
    }

    public static boolean mechanicLogInDataBaseCheckUp(String mechanicNameLabel, String mechanicPasswordLabel) {
        boolean clientExists = false;

        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root", "password123");

            String query = "SELECT * FROM MECHANICS WHERE mechanicName = ? AND mechanicPassword = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, mechanicNameLabel);
            preparedStatement.setString(2, mechanicPasswordLabel);


            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                clientExists = true;
            }


            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientExists;
    }

    public static String[] getCarsByClientName(String clientName) {
        String[] cars = {};
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root", "password123");

            String query = "SELECT carMake, carModel FROM repairements WHERE clientName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, clientName);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> carList = new ArrayList<>();

            while (resultSet.next()) {
                String carMake = resultSet.getString("carMake");
                String carModel = resultSet.getString("carModel");
                carList.add(carMake + " " + carModel);
            }

            // list to array
            cars = carList.toArray(new String[0]);

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    public static String getCarImageByValue(String carValue) {
        String carImage = null;

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root", "password123");

            String[] carParts = carValue.split(" ");
            if (carParts.length == 2) {
                String carMake = carParts[0];
                String carModel = carParts[1];

                String query = "SELECT picture FROM repairements WHERE carMake = ? AND carModel = ? LIMIT 1";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, carMake);
                preparedStatement.setString(2, carModel);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    carImage = resultSet.getString("picture");
                }

                resultSet.close();
                preparedStatement.close();
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carImage;
    }

    public static String[] getRepairementsByClientAndCar(String clientName, String carValue) {
        String[] repairements = {};
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root",
                    "password123");

            String query = "SELECT orderDate, typeOfRepairement, mileage " +
                    "FROM repairements WHERE clientName = ? " +
                    "AND CONCAT(carMake, ' ', carModel) = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, clientName);
            preparedStatement.setString(2, carValue);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> repairementList = new ArrayList<>();

            while (resultSet.next()) {
                String orderDate = resultSet.getString("orderDate");
                String typeOfRepairement = resultSet.getString("typeOfRepairement");
                double mileage = resultSet.getDouble("mileage"); // Getting mileage
                repairementList.add(orderDate + " - " + typeOfRepairement + " - " + mileage + " km");
            }

            // Convert list to array
            repairements = repairementList.toArray(new String[0]);

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return repairements;
    }

    public static String[] getHistoryForCient(String clientName, String carValue) {
        String[] repairementsDetails = {};
        try {
            String[] carValueParts = carValue.split(" - ");
            if (carValueParts.length != 3) {
                throw new IllegalArgumentException("carValue must include 'orderDate', 'typeOfRepairement', and 'mileage', separated by ' - '.");
            }
            String orderDate = carValueParts[0];
            String typeOfRepairement = carValueParts[1];
            double mileage = Double.parseDouble(carValueParts[2].replace(" km", "").trim());

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root",
                    "password123");

            String query = "SELECT orderDate, typeOfRepairement, mechanicName, mileage, cost, status " +
                    "FROM repairements WHERE clientName = ? " +
                    "AND orderDate = ? AND typeOfRepairement = ? AND mileage = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, clientName);
            preparedStatement.setString(2, orderDate);
            preparedStatement.setString(3, typeOfRepairement);
            preparedStatement.setDouble(4, mileage);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> repairementList = new ArrayList<>();

            while (resultSet.next()) {
                String resultOrderDate = resultSet.getString("orderDate");
                String resultTypeOfRepairement = resultSet.getString("typeOfRepairement");
                String mechanicName = resultSet.getString("mechanicName");
                double resultMileage = resultSet.getDouble("mileage");
                double cost = resultSet.getDouble("cost");
                String status = resultSet.getString("status");

                repairementList.add(resultOrderDate + " - " + resultTypeOfRepairement + " - " + mechanicName +
                        " - " + resultMileage + " km - $" + cost + " - " + status);
            }

            repairementsDetails = repairementList.toArray(new String[0]);

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return repairementsDetails;
    }

    public static String[] getRequestsByClient(String clientName) {
        String[] requests = {};
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root",
                    "password123");

            String query = "SELECT requestedDate, carMakeRequest, carModelRequest, requestedTypeOfService " +
                    "FROM order_request " +
                    "WHERE clientName = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, clientName);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> requestList = new ArrayList<>();

            while (resultSet.next()) {
                String requestedDate = resultSet.getString("requestedDate");
                String make = resultSet.getString("carMakeRequest");
                String model = resultSet.getString("carModelRequest");
                String service = resultSet.getString("requestedTypeOfService");

                requestList.add(requestedDate + " - " + make + " " + model + " - " + service);
            }

            requests = requestList.toArray(new String[0]);

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }

    public static boolean insertOrderRequest(String clientName,
                                             String carMake,
                                             String carModel,
                                             LocalDate requestedDate,
                                             String requestedServiceType) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root",
                    "password123");

            String query = "INSERT INTO order_request " +
                    "(clientName, carMakeRequest, carModelRequest, requestedDate, requestedTypeOfService) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, clientName);
            preparedStatement.setString(2, carMake);
            preparedStatement.setString(3, carModel);
            preparedStatement.setDate(4, Date.valueOf(requestedDate));
            preparedStatement.setString(5, requestedServiceType);

            int rowsInserted = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteOrderRequest(String displayValue, String clientName) {
        try {
            String[] parts = displayValue.split(" - ");
            if (parts.length != 3) {
                System.out.println("Невірний формат рядка.");
                return false;
            }

            String requestedDate = parts[0];
            String[] carParts = parts[1].split(" ", 2);
            if (carParts.length != 2) {
                System.out.println("Невірний формат марки/моделі.");
                return false;
            }

            String make = carParts[0];
            String model = carParts[1];
            String service = parts[2];

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root",
                    "password123");

            String sql = "DELETE FROM order_request " +
                    "WHERE clientName = ? " +
                    "AND requestedDate = ? " +
                    "AND carMakeRequest = ? " +
                    "AND carModelRequest = ? " +
                    "AND requestedTypeOfService = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, clientName);
            stmt.setString(2, requestedDate);
            stmt.setString(3, make);
            stmt.setString(4, model);
            stmt.setString(5, service);

            int rowsDeleted = stmt.executeUpdate();

            stmt.close();
            connection.close();

            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getOrderRequestDisplayStrings() {
        List<String> displayList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root",
                    "password123");

            String sql = "SELECT requestedDate, clientName, carMakeRequest, carModelRequest, requestedTypeOfService FROM order_request";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String requestedDate = rs.getString("requestedDate");
                String clientName = rs.getString("clientName");
                String carMake = rs.getString("carMakeRequest");
                String carModel = rs.getString("carModelRequest");
                String service = rs.getString("requestedTypeOfService");

                String display = requestedDate + " - " + clientName + " - " + carMake + " - " + carModel + " - " + service;
                displayList.add(display);
            }

            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return displayList;
    }

    public static String[] getClientContactInfo(String clientName) {
        String[] contactInfo = new String[3]; // [0] = phone, [1] = email, [2] = image

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root",
                    "password123");

            String sql = "SELECT clientPhoneNumber, clientEmail, imageClient FROM clients WHERE clientName = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, clientName);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                contactInfo[0] = rs.getString("clientPhoneNumber");
                contactInfo[1] = rs.getString("clientEmail");
                contactInfo[2] = rs.getString("imageClient");
            }

            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contactInfo;
    }

    public static List<String> getMechanicNames() {
        List<String> mechanicNames = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root",
                    "password123");

            String sql = "SELECT mechanicName FROM mechanics";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                mechanicNames.add(rs.getString("mechanicName"));
            }

            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mechanicNames;
    }


    public static List<String[]> getRepairementsForMechanicAndDate(String mechanicName, LocalDate orderDate) {
        List<String[]> repairements = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/login_schema",
                    "root",
                    "password123");

            String sql = "SELECT orderDate, startTime, endTime, carMake, carModel, mileage, " +
                    "typeOfRepairement, cost, mechanicInstructions, status " +
                    "FROM repairements " +
                    "WHERE mechanicName = ? AND orderDate = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mechanicName);
            stmt.setDate(2, Date.valueOf(orderDate)); // LocalDate into SQL Date

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] row = new String[10];
                row[0] = rs.getString("orderDate");
                row[1] = rs.getString("startTime");
                row[2] = rs.getString("endTime");
                row[3] = rs.getString("carMake");
                row[4] = rs.getString("carModel");
                row[5] = rs.getString("mileage");
                row[6] = rs.getString("typeOfRepairement");
                row[7] = rs.getString("cost");
                row[8] = rs.getString("mechanicInstructions");
                row[9] = rs.getString("status");

                repairements.add(row);
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return repairements;
    }

    public static boolean insertRepairement(
            String clientName,
            String mechanicName,
            String carMake,
            String carModel,
            LocalDate orderDate,
            String typeOfRepairement,
            double cost,
            String mechanicInstructions,
            String mileage,
            String status,
            String startTime,
            String endTime) {

        boolean indicator;
        String sql = "INSERT INTO repairements (clientName, mechanicName, carMake, carModel, orderDate, " +
                "typeOfRepairement, cost, mechanicInstructions, mileage, status, startTime, endTime) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/login_schema",
                "root",
                "password123");
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, clientName);
            stmt.setString(2, mechanicName);
            stmt.setString(3, carMake);
            stmt.setString(4, carModel);
            stmt.setDate(5, java.sql.Date.valueOf(orderDate));
            stmt.setString(6, typeOfRepairement);
            stmt.setDouble(7, cost);
            stmt.setString(8, mechanicInstructions);
            stmt.setString(9, mileage);
            stmt.setString(10, status);
            stmt.setTime(11, java.sql.Time.valueOf(startTime));
            stmt.setTime(12, java.sql.Time.valueOf(endTime));

            stmt.executeUpdate();
            indicator = true;

        } catch (Exception e) {
            indicator = false;
            e.printStackTrace();
        }

        return indicator;
    }


}
