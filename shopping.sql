-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 21, 2022 at 01:06 PM
-- Server version: 8.0.21-0ubuntu0.20.04.4
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shopping`
--

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `itemid` int NOT NULL,
  `product` varchar(200) NOT NULL,
  `company` varchar(50) NOT NULL,
  `color` varchar(50) NOT NULL,
  `price` int NOT NULL,
  `description` varchar(1000) NOT NULL,
  `image` varchar(50) NOT NULL,
  `isbestseller` tinyint(1) NOT NULL,
  `isNew` tinyint(1) NOT NULL,
  `inStock` tinyint(1) NOT NULL,
  `rating` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`itemid`, `product`, `company`, `color`, `price`, `description`, `image`, `isbestseller`, `isNew`, `inStock`, `rating`) VALUES
(1, 'iPad', 'Apple', 'White', 50000, 'description\":\"Lorem ipsum dolor sit amet, consectetur adipisicing elit fugit, error amet numquam iure provident voluptate esse quasi nostrum quisquam eum porro a pariatur veniam', './img/iPad.jpg', 1, 0, 1, 4),
(2, 'Headphones', 'Sony', 'Blue', 10000, '\"Lorem ipsum dolor sit amet, consectetur adipisicing elit fugit, error amet numquam iure provident voluptate esse quasi nostrum quisquam eum porro a pariatur veniam.', './img/headphone-blue.jpg', 1, 1, 1, 5),
(3, 'iMac 27', 'Apple', 'Grey', 100000, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit fugit, error amet numquam iure provident voluptate esse quasi nostrum quisquam eum porro a pariatur veniam.', './img/iMac.jpg', 1, 0, 1, 5),
(4, 'Dell V-964i', 'Dell', 'Black', 25000, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit fugit, error amet numquam iure provident voluptate esse quasi nostrum quisquam eum porro a pariatur veniam.', './img/dell-black.jpg', 0, 1, 1, 4),
(5, 'Asus GT67i', 'Asus', 'Black', 30000, 'Lorem ipsum dolor sit amet, consectetur adipisicing elit fugit, error amet numquam iure provident voluptate esse quasi nostrum quisquam eum porro a pariatur veniam.', './img/Asus-black.jpg', 1, 1, 1, 3),
(9, 'new item 2', 'test company', 'white', 10000, 'test', '../img/avatar.jpg', 1, 0, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `messageid` int NOT NULL,
  `message` varchar(255) NOT NULL,
  `scheduled_at` timestamp(6) NOT NULL,
  `destination_phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `userid` int NOT NULL,
  `created_at` timestamp(6) NULL DEFAULT NULL,
  `scheduled_status` tinyint(1) DEFAULT NULL,
  `submitted_at` timestamp(6) NULL DEFAULT NULL,
  `submitted_status_code` int NOT NULL,
  `submitted_status` varchar(50) DEFAULT NULL,
  `whatsapp_api_message_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`messageid`, `message`, `scheduled_at`, `destination_phone_number`, `userid`, `created_at`, `scheduled_status`, `submitted_at`, `submitted_status_code`, `submitted_status`, `whatsapp_api_message_id`) VALUES
(20, 'message to send for scheduling', '2022-07-19 16:26:00.000000', '919163214034', 2, '2022-07-18 14:41:07.000000', 0, '2022-07-21 07:11:08.000000', 202, 'submitted', '98e4811e-2c48-4ae8-88b3-a2aaf26c6c8b'),
(21, 'message to send for scheduling', '2022-07-21 04:16:00.000000', '919163214034', 2, '2022-07-21 04:11:43.000000', 0, '2022-07-21 07:11:09.000000', 202, 'submitted', 'bb8f0f02-0e91-46a8-9d46-0530636a905e'),
(22, 'message to send for scheduling', '2022-07-21 04:22:00.000000', '919163214034', 2, '2022-07-21 04:22:42.000000', 0, '2022-07-21 07:11:10.000000', 202, 'submitted', 'd6db32da-1ed7-4b3d-920f-639511935baf'),
(23, 'message to send for scheduling', '2022-07-21 04:27:00.000000', '919163214034', 2, '2022-07-21 04:26:33.000000', 0, '2022-07-21 07:11:10.000000', 202, 'submitted', 'fc7bef37-88c9-402f-bf99-07c95d2092a9'),
(24, 'message to send for scheduling', '2022-07-21 07:15:00.000000', '919163214034', 2, '2022-07-21 07:21:37.000000', 0, '2022-07-21 07:21:08.000000', 202, 'submitted', 'a7b2b49c-4af5-4538-8352-b4e3ca2a2c18');

-- --------------------------------------------------------

--
-- Table structure for table `mycart`
--

CREATE TABLE `mycart` (
  `cartid` int NOT NULL,
  `userid` int NOT NULL,
  `qty` int NOT NULL,
  `itemid` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `mycart`
--

INSERT INTO `mycart` (`cartid`, `userid`, `qty`, `itemid`) VALUES
(18, 1, 14, 5),
(19, 1, 6, 2),
(20, 2, 2, 2),
(21, 2, 1, 3);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userid` int NOT NULL,
  `name` varchar(200) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `authToken` varchar(200) NOT NULL,
  `isadmin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userid`, `name`, `email`, `password`, `authToken`, `isadmin`) VALUES
(1, 'Somen Admin', 'somen.das@gupshup.io', 'somend', '', 1),
(2, 'Somen User', 'dassomen1437@gmail.com', 'somend', '', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`itemid`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`messageid`);

--
-- Indexes for table `mycart`
--
ALTER TABLE `mycart`
  ADD PRIMARY KEY (`cartid`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `items`
--
ALTER TABLE `items`
  MODIFY `itemid` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `messageid` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `mycart`
--
ALTER TABLE `mycart`
  MODIFY `cartid` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `userid` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
