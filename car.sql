-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/


SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `car`
--

-- --------------------------------------------------------

--
-- Table structure for table `billings`
--

CREATE TABLE `billings` (
  `bill_id` int(11) NOT NULL,
  `job_id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `payment_status` varchar(20) NOT NULL,
  `payment_method` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `billings`
--

INSERT INTO `billings` (`bill_id`, `job_id`, `amount`, `payment_status`, `payment_method`) VALUES
(2, 2, 2000, 'Paid', 'Cash'),
(3, 3, 150, 'Pending', 'Credit Card'),
(4, 4, 250, 'Unpaid', 'Debit'),
(5, 5, 300, 'Unpaid', 'Credit Card');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `contact_number` varchar(15) NOT NULL,
  `vehicle_details` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customer_id`, `name`, `contact_number`, `vehicle_details`) VALUES
(1, 'John Doe', '123-456-7890', 'Toyota Corolla'),
(2, 'Jane Smi', '987-654-3210', 'Honda Civic'),
(3, 'Emily Davis', '555-666-7777', 'Ford Focus'),
(4, 'Michael Brown 2', '222-333-4444', 'Chevy Malibu'),
(5, 'Sarah Wilson', '111-222-3333', 'Nissan Sentra');

-- --------------------------------------------------------

--
-- Table structure for table `repair_jobs`
--

CREATE TABLE `repair_jobs` (
  `job_id` int(11) NOT NULL,
  `appointment_id` int(11) NOT NULL,
  `tire_id` int(11) NOT NULL,
  `technician_id` int(11) NOT NULL,
  `start_time` varchar(20) DEFAULT NULL,
  `end_time` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `repair_jobs`
--

INSERT INTO `repair_jobs` (`job_id`, `appointment_id`, `tire_id`, `technician_id`, `start_time`, `end_time`, `status`) VALUES
(1, 101, 201, 301, '09:00:00', '11:00:00', 'Completed'),
(2, 102, 202, 302, '10:00:00', '12:00:00', 'InProgress'),
(3, 103, 203, 303, '11:00:00', '13:00:00', 'Completed'),
(4, 104, 204, 304, '12:00:00', '14:00:00', 'InProgress'),
(5, 105, 205, 305, '13:00:00', '15:00:00', 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `service_appointments`
--

CREATE TABLE `service_appointments` (
  `appointment_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `service_date` date NOT NULL,
  `issues_reported` text DEFAULT NULL,
  `is_completed` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `service_appointments`
--

INSERT INTO `service_appointments` (`appointment_id`, `customer_id`, `service_date`, `issues_reported`, `is_completed`) VALUES
(1, 1, '2023-01-01', 'Tire replacement', 1),
(2, 2, '2023-01-02', 'Oil change', 0),
(3, 3, '2023-01-05', 'General inspection(1)', 1),
(4, 4, '2023-01-04', 'Brake repair', 0),
(5, 5, '2023-01-05', 'Engine tuning', 1);

-- --------------------------------------------------------

--
-- Table structure for table `technicians`
--

CREATE TABLE `technicians` (
  `technician_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `specialization` varchar(50) DEFAULT NULL,
  `hours_worked` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `technicians`
--

INSERT INTO `technicians` (`technician_id`, `name`, `specialization`, `hours_worked`) VALUES
(1, 'John Tech', 'Tire Specialist', 40),
(2, 'Jane Mechanic', 'Engine Specialist', 35),
(3, 'Mike Repairer', 'Brake Specialist', 38),
(4, 'Susan Fixer', 'Transmission Specialist', 42),
(5, 'Bob Builder', 'Generalist', 30);

-- --------------------------------------------------------

--
-- Table structure for table `tire_inventory`
--

CREATE TABLE `tire_inventory` (
  `tire_id` int(11) NOT NULL,
  `brand` varchar(50) DEFAULT NULL,
  `size` varchar(10) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tire_inventory`
--

INSERT INTO `tire_inventory` (`tire_id`, `brand`, `size`, `type`, `quantity`) VALUES
(1, 'Michelin', '205/55R16', 'All-Season', 50),
(2, 'Goodyear', '215/65R15', 'Winter', 30),
(3, 'Bridgestone', '225/45R17', 'Summer', 40),
(4, 'Pirelli', '235/40R18', 'Performance', 15);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `billings`
--
ALTER TABLE `billings`
  ADD PRIMARY KEY (`bill_id`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `repair_jobs`
--
ALTER TABLE `repair_jobs`
  ADD PRIMARY KEY (`job_id`);

--
-- Indexes for table `service_appointments`
--
ALTER TABLE `service_appointments`
  ADD PRIMARY KEY (`appointment_id`);

--
-- Indexes for table `technicians`
--
ALTER TABLE `technicians`
  ADD PRIMARY KEY (`technician_id`);

--
-- Indexes for table `tire_inventory`
--
ALTER TABLE `tire_inventory`
  ADD PRIMARY KEY (`tire_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `billings`
--
ALTER TABLE `billings`
  MODIFY `bill_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `repair_jobs`
--
ALTER TABLE `repair_jobs`
  MODIFY `job_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `service_appointments`
--
ALTER TABLE `service_appointments`
  MODIFY `appointment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `technicians`
--
ALTER TABLE `technicians`
  MODIFY `technician_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tire_inventory`
--
ALTER TABLE `tire_inventory`
  MODIFY `tire_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
