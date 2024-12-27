package base;

import java.sql.*;
import java.util.ArrayList;
import static databaseConfig.DatabaseConfig.*;
public class Patient {
    private int id;
    private String name;
    private String contactInfo;
    private MedicalRecord medicalRecord;
    private Billing billing;

    public Patient(int id) {
        this.id = id;
        loadPatientFromDatabase();
    }

    public Patient(String name, String contactInfo, MedicalRecord medicalRecord) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.medicalRecord = medicalRecord;
        saveToDatabase();
        this.billing = new Billing(this.id, this);
    }

    public Patient(int id, String name, String contactInfo, MedicalRecord medicalRecord) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.medicalRecord = medicalRecord;
        this.billing = new Billing(this.id, this);
    }

    public Patient(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.medicalRecord = new MedicalRecord();
        saveToDatabase();
        this.billing = new Billing(this.id, this);
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
    public Billing getBilling() {
        return billing;
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
            String query = "SELECT name, contact_info FROM Patient WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.name = resultSet.getString("name");
                this.contactInfo = resultSet.getString("contact_info");
                this.medicalRecord = new MedicalRecord();
                this.medicalRecord.loadMedicalRecordFromDatabase(id);
            }
            this.billing = new Billing(this.id, this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToDatabase() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO Patient (id, name, contact_info) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            String maxIdQuery = "SELECT COALESCE(MAX(id), 0) AS max_id FROM Patient";
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

    public String getNextAppointmentDoctorName() {
        String doctorName = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT d.name FROM Appointment a " +
                    "JOIN Doctor d ON a.doctor_id = d.id " +
                    "WHERE a.patient_id = ? AND a.date_time > NOW() ORDER BY a.date_time ASC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                doctorName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doctorName;
    }

    public String getNextAppointmentDate() {
        String appointmentDate = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT date_time FROM Appointment " +
                    "WHERE patient_id = ? AND date_time > NOW() ORDER BY date_time ASC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                appointmentDate = resultSet.getTimestamp("date_time").toLocalDateTime().toString();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointmentDate;
    }

    public String getMostRecentAppointmentDoctorName() {
        String doctorName = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT d.name FROM Appointment a " +
                    "JOIN Doctor d ON a.doctor_id = d.id " +
                    "WHERE a.patient_id = ? AND a.date_time <= NOW() ORDER BY a.date_time DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                doctorName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doctorName;
    }

    public String getMostRecentAppointmentDate() {
        String appointmentDate = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT date_time FROM Appointment " +
                    "WHERE patient_id = ? AND date_time <= NOW() ORDER BY date_time DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                appointmentDate = resultSet.getTimestamp("date_time").toLocalDateTime().toString();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointmentDate;
    }

    public int getTotalAppointments() {
        int totalAppointments = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT COUNT(*) AS count FROM Appointment WHERE patient_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalAppointments = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalAppointments;
    }
    public ArrayList<Appointment> getUpcomingAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT id FROM Appointment WHERE patient_id = ? AND date_time > NOW() ORDER BY date_time ASC";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                appointments.add(new Appointment(resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointments;
    }
    public ArrayList<Appointment> getPastAppointments(){
        ArrayList<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT id FROM Appointment WHERE patient_id = ? AND date_time <= NOW() ORDER BY date_time DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                appointments.add(new Appointment(resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointments;
    }

}