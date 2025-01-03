document.addEventListener('DOMContentLoaded', () => {
    // Dashboard Button
    const dashBoardButton = () => {
        window.location.href = '/html/patient/patientDashboard.html';
    };

    // View Appointments Button
    const handleViewAppointmentsButton = () => {
        window.location.href = '/html/patient/myAppointments.html';
    };

    // View Past Appointments Button
    const pastAppointmentsButton = () => {
        window.location.href = '/html/patient/pastAppointments.html';
    };

    // Create Appointment Button
    const createAppointmentButton = () => {
        window.location.href = '/html/patient/createAppointment.html';
    };

    // View Prescriptions Button
    const handleViewPrescriptionButton = () => {
        window.location.href = '/html/patient/myPrescriptions.html';
    };

    // View Bills Button
    const handleViewBillsButton = () => {
        window.location.href = '/html/patient/myBills.html';
    };

    // Logout Button
    const handleLogoutButton = () => {
        window.location.href = '/html/login.html';
        alert('Logged out');
    };

    // Search Button Click
    const searchButtonClick = () => {
        const searchBar = document.getElementById('searchBar');
        alert(`Searching for: ${searchBar.value}`);
    };

    // Cancel Appointment Button
    const cancelAppointmentButton = (event) => {
        const appointmentCard = event.target.closest('.appointment-card');
        appointmentCard.remove();
        alert('Appointment cancelled');
    };

    // Attach event listener to search button
    const searchButton = document.getElementById('searchButton');
    if (searchButton) {
        searchButton.addEventListener('click', searchButtonClick);
    }

    // Attach event listeners to cancel buttons
    document.querySelectorAll('.cancel-button').forEach(button => {
        button.addEventListener('click', cancelAppointmentButton);
    });

    // Expose functions to global scope
    window.dashBoardButton = dashBoardButton;
    window.handleViewAppointmentsButton = handleViewAppointmentsButton;
    window.pastAppointmentsButton = pastAppointmentsButton;
    window.createAppointmentButton = createAppointmentButton;
    window.handleViewPrescriptionButton = handleViewPrescriptionButton;
    window.handleViewBillsButton = handleViewBillsButton;
    window.handleLogoutButton = handleLogoutButton;
    window.searchButtonClick = searchButtonClick;
    window.cancelAppointmentButton = cancelAppointmentButton;
});