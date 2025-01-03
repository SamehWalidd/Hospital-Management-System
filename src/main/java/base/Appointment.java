package base;

import java.sql.*;
import java.time.LocalDateTime;

import static databaseConfig.DatabaseConfig.*;
public class Appointment {
    private int id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime dateTime;

    public Appointment(Doctor doctor, Patient patient, LocalDateTime dateTime) {
        this.doctor = doctor;
        this.patient = patient;
        this.dateTime = dateTime;
        addAppointmentToDatabase();
    }

    public Appointment(int id) {
       loadFromDatabase(id);
    }

    public Appointment(int id, Doctor doctor, Patient patient, LocalDateTime dateTime) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void reschedule(LocalDateTime newDateTime) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "UPDATE Appointment SET date_time = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(newDateTime));
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
            this.dateTime = newDateTime;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void addAppointmentToDatabase() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO Appointment (doctor_id, patient_id, date_time) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, doctor.getId());
            preparedStatement.setInt(2, patient.getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(dateTime));
            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                this.id = keys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadFromDatabase(int appointmentId) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT id, doctor_id, patient_id, date_time FROM Appointment WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, appointmentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.id = resultSet.getInt("id");
                int doctorId = resultSet.getInt("doctor_id");
                int patientId = resultSet.getInt("patient_id");
                this.dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();
                this.doctor = new Doctor(doctorId);
                this.patient = new Patient(patientId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}