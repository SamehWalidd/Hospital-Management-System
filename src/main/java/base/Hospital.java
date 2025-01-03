package base;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Hospital {
    private static Hospital instance;
    private ArrayList<Doctor> doctors;
    private ArrayList<Patient> patients;
    private ArrayList<Appointment> appointments;

    private Hospital() {
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    public static Hospital getInstance() {
        if (instance == null) {
            instance = new Hospital();
        }
        return instance;
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        doctor.saveToDatabase();
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
        patient.saveToDatabase();
    }

    public void scheduleAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.addAppointmentToDatabase();
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void loadHospitalFromDatabase(String url, String username, String password) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Load doctors
            String doctorQuery = "SELECT * FROM Doctor";
            Statement doctorStatement = connection.createStatement();
            ResultSet doctorResultSet = doctorStatement.executeQuery(doctorQuery);
            while (doctorResultSet.next()) {
                Doctor doctor = new Doctor(
                        doctorResultSet.getInt("id"),
                        doctorResultSet.getString("name"),
                        doctorResultSet.getString("contact_info"),
                        doctorResultSet.getString("specialty")
                );
                addDoctor(doctor);
            }

            // Load patients
            String patientQuery = "SELECT * FROM Patient";
            Statement patientStatement = connection.createStatement();
            ResultSet patientResultSet = patientStatement.executeQuery(patientQuery);
            while (patientResultSet.next()) {
                Patient patient = new Patient(
                        patientResultSet.getInt("id"),
                        patientResultSet.getString("name"),
                        patientResultSet.getString("contact_info"),
                        new MedicalRecord()
                );
                patient.getMedicalRecord().loadMedicalRecordFromDatabase(patient.getId());
                addPatient(patient);
            }

            // Load appointments
            String appointmentQuery = "SELECT * FROM Appointment";
            Statement appointmentStatement = connection.createStatement();
            ResultSet appointmentResultSet = appointmentStatement.executeQuery(appointmentQuery);
            while (appointmentResultSet.next()) {
                int doctorId = appointmentResultSet.getInt("doctor_id");
                int patientId = appointmentResultSet.getInt("patient_id");

                Doctor doctor = null;
                for (Doctor d : doctors) {
                    if (d.getId() == doctorId) {
                        doctor = d;
                        break;
                    }
                }

                Patient patient = null;
                for (Patient p : patients) {
                    if (p.getId() == patientId) {
                        patient = p;
                        break;
                    }
                }

                if (doctor != null && patient != null) {
                    Appointment appointment = new Appointment(
                            appointmentResultSet.getInt("id"),
                            doctor,
                            patient,
                            appointmentResultSet.getTimestamp("date_time").toLocalDateTime()
                    );
                    scheduleAppointment(appointment);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void scheduleAppointment(Patient patient, Doctor doctor, LocalDate value) {
        Appointment appointment = new Appointment(doctor, patient, value.atStartOfDay());
        scheduleAppointment(appointment);
    }
}
