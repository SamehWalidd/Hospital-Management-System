package base;

import java.sql.*;
import java.util.ArrayList;

import static databaseConfig.DatabaseConfig.*;

public class Billing {
    private int id;
    private Patient patient;
    private ArrayList<String> services;
    private double total;

    public Billing(int id, Patient patient) {
        this.id = id;
        this.patient = patient;
        this.services = new ArrayList<>();
        this.total = 0.0;
        loadBillingFromDatabase();
    }

    public int getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public void updateTotal(String service, double cost) {
        services.add(service + " $" + cost);
        total += cost;
        saveBillingToDatabase(service, cost);
    }

    public String generateInvoice() {
        StringBuilder invoice = new StringBuilder();
        for (String service : services) {
            invoice.append(service).append("\n");
        }
        return invoice.toString();
    }

    public ArrayList<String> getServices() {
        return services;
    }

    private void loadBillingFromDatabase() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT service, cost FROM Billing WHERE patient_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.patient.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String service = resultSet.getString("service");
                double cost = resultSet.getDouble("cost");
                services.add(service + " $" + cost);
                total += cost;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveBillingToDatabase(String service, double cost) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO Billing (id, service, cost) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            preparedStatement.setString(2, service);
            preparedStatement.setDouble(3, cost);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

