-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Dec 27, 2024 at 09:37 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hospitalmanagement`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE `appointment` (
  `id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `date_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`id`, `doctor_id`, `patient_id`, `date_time`) VALUES
(20, 2004, 5, '2024-12-29 04:50:15'),
(21, 2001, 1, '2023-12-01 09:00:00'),
(22, 2001, 1, '2024-01-10 14:00:00'),
(23, 2001, 2, '2024-12-05 11:30:00'),
(24, 2001, 2, '2024-12-28 13:00:00'),
(25, 2002, 3, '2023-11-15 10:00:00'),
(26, 2002, 3, '2024-02-20 15:30:00'),
(27, 2002, 4, '2023-12-20 08:45:00'),
(28, 2002, 4, '2024-01-15 11:00:00'),
(29, 2003, 5, '2024-01-18 09:30:00'),
(31, 2003, 6, '2023-10-10 14:15:00'),
(32, 2003, 6, '2024-02-25 09:45:00'),
(33, 2004, 7, '2023-09-12 13:00:00'),
(34, 2004, 7, '2024-12-31 17:00:00'),
(35, 2004, 8, '2024-01-05 10:45:00'),
(36, 2004, 8, '2024-12-26 08:30:00'),
(37, 2000, 9, '2023-11-10 16:30:00'),
(38, 2000, 9, '2024-01-20 14:45:00'),
(39, 2000, 10, '2023-12-15 15:15:00'),
(40, 2000, 10, '2024-12-24 12:30:00');

-- --------------------------------------------------------

--
-- Table structure for table `billing`
--

CREATE TABLE `billing` (
  `id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `service` varchar(255) DEFAULT NULL,
  `cost` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `billing`
--

INSERT INTO `billing` (`id`, `patient_id`, `service`, `cost`) VALUES
(1, 1, 'Routine Check-up', 150.00),
(2, 1, 'Follow-up Consultation', 100.00),
(3, 2, 'X-ray Imaging', 200.00),
(4, 2, 'Blood Tests', 50.00),
(5, 3, 'Allergy Testing', 120.00),
(6, 3, 'Medication Prescription', 80.00),
(7, 4, 'Surgery Consultation', 300.00),
(8, 4, 'Post-surgery Follow-up', 150.00),
(9, 5, 'Cardiac Screening', 250.00),
(10, 5, 'ECG Test', 100.00),
(11, 6, 'Diabetes Management', 180.00),
(12, 6, 'Dietary Consultation', 90.00),
(13, 7, 'Respiratory Therapy', 120.00),
(14, 7, 'Inhaler Prescription', 40.00),
(15, 8, 'Pain Management', 200.00),
(16, 8, 'Physical Therapy Session', 150.00),
(17, 9, 'Routine Blood Work', 100.00),
(18, 9, 'Vitamin Deficiency Screening', 80.00),
(19, 10, 'Chiropractic Consultation', 180.00),
(20, 10, 'Exercise Therapy', 90.00);

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

CREATE TABLE `doctor` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `contact_info` varchar(255) DEFAULT NULL,
  `specialty` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`id`, `name`, `contact_info`, `specialty`) VALUES
(2000, 'Dr. Meredith Grey', 'grey.m@hospital.com', 'General Surgery'),
(2001, 'Dr. Gregory House', 'house.g@hospital.com', 'Diagnostic Medicine'),
(2002, 'Dr. Sanjay Gupta', 'gupta.s@hospital.com', 'Neurosurgery'),
(2003, 'Dr. Oz', 'oz.m@hospital.com', 'Cardiology'),
(2004, 'Dr. Phil McGraw', 'phil.m@hospital.com', 'Psychology');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `id` int(11) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(9) DEFAULT NULL CHECK (`role` = 'Doctor' and `id` >= 2000 or `role` = 'Patient' and `id` < 2000)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `password`, `role`) VALUES
(1, '1111', 'Patient'),
(2, '1111', 'Patient'),
(3, '1111', 'Patient'),
(4, '1111', 'Patient'),
(5, '1111', 'Patient'),
(6, '1111', 'Patient'),
(7, '1111', 'Patient'),
(8, '1111', 'Patient'),
(9, '1111', 'Patient'),
(10, '1111', 'Patient'),
(11, '1111', 'Patient'),
(12, '1111', 'Patient'),
(13, '1111', 'Patient'),
(14, '1111', 'Patient'),
(15, '1111', 'Patient'),
(16, '1111', 'Patient'),
(17, '1111', 'Patient'),
(18, '1111', 'Patient'),
(19, '1111', 'Patient'),
(20, '1111', 'Patient'),
(2000, '1111', 'Doctor'),
(2001, '1111', 'Doctor'),
(2002, '1111', 'Doctor'),
(2003, '1111', 'Doctor'),
(2004, '1111', 'Doctor');

-- --------------------------------------------------------

--
-- Table structure for table `medicalrecord`
--

CREATE TABLE `medicalrecord` (
  `id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `history` text DEFAULT NULL,
  `prescription` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medicalrecord`
--

INSERT INTO `medicalrecord` (`id`, `patient_id`, `history`, `prescription`) VALUES
(1, 1, 'Diagnosed with hypertension', 'Lisinopril 10mg daily'),
(2, 2, 'Fractured arm, treated with cast', 'Painkillers as needed'),
(3, 3, 'Seasonal allergies', 'Loratadine 10mg daily'),
(4, 4, 'Recovering from knee surgery', 'Physiotherapy recommended'),
(5, 5, 'High cholesterol', 'Atorvastatin 20mg daily'),
(6, 6, 'Diabetes Type 2', 'Metformin 500mg twice daily'),
(7, 7, 'Asthma', 'Inhaler as needed'),
(8, 8, 'Migraine', 'Sumatriptan 50mg when needed'),
(9, 9, 'Anemia', 'Ferrous sulfate 325mg daily'),
(10, 10, 'Back pain', 'Physical therapy sessions recommended'),
(11, 11, 'Anxiety', 'Cognitive Behavioral Therapy'),
(12, 12, 'Gastrointestinal reflux disease', 'Omeprazole 20mg daily'),
(13, 13, 'Thyroid dysfunction', 'Levothyroxine 75mcg daily'),
(14, 14, 'Hypertension and obesity', 'Lifestyle changes advised'),
(15, 15, 'Seasonal flu', 'Rest and hydration'),
(16, 16, 'Vitamin D deficiency', 'Cholecalciferol 1000IU daily'),
(17, 17, 'Muscle strain', 'Ibuprofen 400mg as needed'),
(18, 18, 'Allergic rhinitis', 'Cetirizine 10mg daily'),
(19, 19, 'Acne', 'Topical retinoids'),
(20, 20, 'Bronchitis', 'Amoxicillin 500mg three times daily');

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `contact_info` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`id`, `name`, `contact_info`) VALUES
(1, 'Alice Johnson', 'alice.johnson@gmail.com'),
(2, 'Michael Green', 'michael.green@yahoo.com'),
(3, 'Sophia Turner', 'sophia.turner@hotmail.com'),
(4, 'Tom Hanks', 'tom.hanks@example.com'),
(5, 'Jennifer Lawrence', 'jennifer.lawrence@example.com'),
(6, 'Robert Downey Jr.', 'rdj@example.com'),
(7, 'Scarlett Johansson', 'scarlett.j@example.com'),
(8, 'Chris Evans', 'chris.evans@example.com'),
(9, 'Emma Watson', 'emma.watson@example.com'),
(10, 'Ryan Gosling', 'ryan.gosling@example.com'),
(11, 'Anne Hathaway', 'anne.h@example.com'),
(12, 'Leonardo DiCaprio', 'leo.d@example.com'),
(13, 'Brad Pitt', 'brad.pitt@example.com'),
(14, 'Angelina Jolie', 'angelina.j@example.com'),
(15, 'Natalie Portman', 'natalie.portman@example.com'),
(16, 'Chris Hemsworth', 'chris.hemsworth@example.com'),
(17, 'Dwayne Johnson', 'dwayne.johnson@example.com'),
(18, 'Gal Gadot', 'gal.gadot@example.com'),
(19, 'Henry Cavill', 'henry.cavill@example.com'),
(20, 'Zendaya', 'zendaya@example.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `doctor_id` (`doctor_id`),
  ADD KEY `patient_id` (`patient_id`);

--
-- Indexes for table `billing`
--
ALTER TABLE `billing`
  ADD PRIMARY KEY (`id`),
  ADD KEY `patient_id` (`patient_id`);

--
-- Indexes for table `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `medicalrecord`
--
ALTER TABLE `medicalrecord`
  ADD PRIMARY KEY (`id`),
  ADD KEY `patient_id` (`patient_id`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointment`
--
ALTER TABLE `appointment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `billing`
--
ALTER TABLE `billing`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `doctor`
--
ALTER TABLE `doctor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2005;

--
-- AUTO_INCREMENT for table `medicalrecord`
--
ALTER TABLE `medicalrecord`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `patient`
--
ALTER TABLE `patient`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointment`
--
ALTER TABLE `appointment`
  ADD CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `billing`
--
ALTER TABLE `billing`
  ADD CONSTRAINT `billing_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `medicalrecord`
--
ALTER TABLE `medicalrecord`
  ADD CONSTRAINT `medicalrecord_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
