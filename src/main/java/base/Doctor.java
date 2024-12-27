package base;

import java.sql.*;
import java.util.ArrayList;

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

    public String getMostRecentAppointmentDate() {
        String date = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT a.date_time FROM Appointment a " +
                    "WHERE a.doctor_id = ? AND a.date_time <= NOW() ORDER BY a.date_time DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                date = resultSet.getString("date_time");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
    public String getMostRecentAppointmentPatientName() {
        String patientName = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT p.name FROM Appointment a " +
                    "JOIN Patient p ON a.patient_id = p.id " +
                    "WHERE a.doctor_id = ? AND a.date_time <= NOW() ORDER BY a.date_time DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                patientName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patientName;
    }
    public String getNextAppointmentDate() {
        String date = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT a.date_time FROM Appointment a " +
                    "WHERE a.doctor_id = ? AND a.date_time > NOW() ORDER BY a.date_time ASC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                date = resultSet.getString("date_time");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public String getNextAppointmentPatientName() {
        String patientName = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT p.name FROM Appointment a " +
                    "JOIN Patient p ON a.patient_id = p.id " +
                    "WHERE a.doctor_id = ? AND a.date_time > NOW() ORDER BY a.date_time ASC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                patientName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patientName;
    }

    public int getPendingAppointments() {
        int count = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT COUNT(*) AS count FROM Appointment a " +
                    "WHERE a.doctor_id = ? AND a.date_time > NOW()";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public int getTotalAppointments() {
        int count = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT COUNT(*) AS count FROM Appointment a " +
                    "WHERE a.doctor_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }


    public ArrayList<Appointment> getUpcomingAppointments(){
        ArrayList<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT a.id, a.date_time, p.id AS patient_id, p.name AS patient_name FROM Appointment a " +
                    "JOIN Patient p ON a.patient_id = p.id " +
                    "WHERE a.doctor_id = ? AND a.date_time > NOW() ORDER BY a.date_time ASC";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("id");
                Timestamp dateTime = resultSet.getTimestamp("date_time");
                int patientId = resultSet.getInt("patient_id");
                Patient patient = new Patient(patientId);
                appointments.add(new Appointment(appointmentId, this, patient, dateTime.toLocalDateTime()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointments;
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
    public ArrayList<Patient> getPatients() {
        ArrayList<Patient> Patientss = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT DISTINCT patient_id FROM Appointment WHERE doctor_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Patientss.add(new Patient(resultSet.getInt("patient_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Patientss;
    }


}