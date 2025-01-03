document.addEventListener('DOMContentLoaded', () => {
    // Navigation functions
    const navigateTo = (page) => {
        switch (page) {
            case 'dashboard':
                window.location.href = '/html/doctor/doctorDashboard.html';
                break;
            case 'prescriptions':
                window.location.href = '/html/doctor/prescription.html';
                break;
            case 'appointments':
                window.location.href = '/html/doctor/appointments.html';
                break;
            case 'newPrescription':
                window.location.href = '/html/doctor/newPrescription.html';
                break;
            default:
                break;
        }
    };

    // Logout function
    const logout = () => {
        window.location.href = '/html/login.html';
        alert('Logged out');
    };

    // Search Appointments function
    const searchAppointments = () => {
        const searchBar = document.getElementById('searchbar');
        alert(`Searching for: ${searchBar.value}`);
    };

    // Cancel Appointment function
    const cancelAppointment = (event) => {
        const appointmentCard = event.target.closest('.appointment-card');
        appointmentCard.remove();
        alert('Appointment cancelled');
    };

    // Attach event listeners to cancel buttons
    document.querySelectorAll('.cancel-button').forEach(button => {
        button.addEventListener('click', cancelAppointment);
    });

    // Expose functions to global scope
    window.navigateTo = navigateTo;
    window.logout = logout;
    window.searchAppointments = searchAppointments;
    window.cancelAppointment = cancelAppointment;
});