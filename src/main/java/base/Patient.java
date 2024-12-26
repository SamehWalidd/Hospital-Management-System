package base;

import java.sql.*;
import java.util.ArrayList;
import static databaseConfig.DatabaseConfig.*;
public class Patient {
    private int id;
    private String name;
    private String contactInfo;
    private MedicalRecord medicalRecord;

    public Patient(int id) {
        this.id = id;
        loadPatientFromDatabase();
    }

    public Patient(String name, String contactInfo, MedicalRecord medicalRecord) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.medicalRecord = medicalRecord;
        saveToDatabase();
    }

    public Patient(int id, String name, String contactInfo, MedicalRecord medicalRecord) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.medicalRecord = medicalRecord;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public ArrayList<String> viewPrescription() {
        return medicalRecord.getPrescriptions();
    }

    public ArrayList<String> viewMedicalHistory() {
        return medicalRecord.getHistory();
    }

    private void loadPatientFromDatabase() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT name, contact_info, medical_record_id FROM Patient WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.name = resultSet.getString("name");
                this.contactInfo = resultSet.getString("contact_info");
                int medicalRecordId = resultSet.getInt("medical_record_id");
                this.medicalRecord = new MedicalRecord();
                this.medicalRecord.loadMedicalRecordFromDatabase(medicalRecordId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToDatabase() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO Patient (id, name, contact_info) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            String maxIdQuery = "SELECT COALESCE(MAX(id), 0) AS max_id FROM Doctor";
            PreparedStatement maxIdStatement = connection.prepareStatement(maxIdQuery);
            ResultSet resultSet = maxIdStatement.executeQuery();
            if (resultSet.next()) {
                this.id = resultSet.getInt("max_id") + 1;
            }
            preparedStatement.setInt(1,this.id);
            preparedStatement.setString(2, this.name);
            preparedStatement.setString(3, this.contactInfo);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}