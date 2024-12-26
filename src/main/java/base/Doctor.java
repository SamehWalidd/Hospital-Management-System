package base;

import java.sql.*;

import static databaseConfig.DatabaseConfig.*;

public class Doctor {
    private int id;
    private String name;
    private String contactInfo;
    private String specialty;

    public Doctor(int id) {
        this.id = id;
        loadDoctorFromDatabase();
    }

    public Doctor(String name, String contactInfo, String specialty) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.specialty = specialty;
        saveToDatabase();
    }

    public Doctor(int id, String name, String contactInfo, String specialty) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.specialty = specialty;
    }

    public void saveToDatabase() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String maxIdQuery = "SELECT COALESCE(MAX(id), 0) AS max_id FROM Doctor";
            PreparedStatement maxIdStatement = connection.prepareStatement(maxIdQuery);
            ResultSet resultSet = maxIdStatement.executeQuery();
            if (resultSet.next()) {
                this.id = resultSet.getInt("max_id") + 1;
            }

            String query = "INSERT INTO Doctor (id, name, contact_info, specialty) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            preparedStatement.setString(2, this.name);
            preparedStatement.setString(3, this.contactInfo);
            preparedStatement.setString(4, this.specialty);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public String getSpecialty() {
        return specialty;
    }

    public void assignAppointment(Appointment appointment) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO Appointment (doctor_id, patient_id, date_time) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, appointment.getDoctor().getId());
            preparedStatement.setInt(2, appointment.getPatient().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(appointment.getDateTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelAppointment(int appointmentId) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "DELETE FROM Appointment WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, appointmentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDoctorFromDatabase() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT name, contact_info, specialty FROM Doctor WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.name = resultSet.getString("name");
                this.contactInfo = resultSet.getString("contact_info");
                this.specialty = resultSet.getString("specialty");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}