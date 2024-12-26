package base;

import java.sql.*;
import java.util.ArrayList;

import static databaseConfig.DatabaseConfig.*;
public class MedicalRecord {
    private ArrayList<String> history;
    private ArrayList<String> prescriptions;

    public MedicalRecord() {
        this.history = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
    }

    public void addHistory(String record) {
        history.add(record);
    }

    public void addPrescription(String prescription) {
        prescriptions.add(prescription);
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public ArrayList<String> getPrescriptions() {
        return prescriptions;
    }

    public void loadMedicalRecordFromDatabase(int patientId) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT history, prescription FROM MedicalRecord WHERE patient_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                history.add(resultSet.getString("history"));
                prescriptions.add(resultSet.getString("prescription"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToDatabase(int patientId) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            for (String record : history) {
                String query = "INSERT INTO MedicalRecord (patient_id, history, prescription) VALUES (?, ?, NULL)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, patientId);
                preparedStatement.setString(2, record);
                preparedStatement.executeUpdate();
            }

            for (String prescription : prescriptions) {
                String query = "INSERT INTO MedicalRecord (patient_id, history, prescription) VALUES (?, NULL, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, patientId);
                preparedStatement.setString(2, prescription);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}