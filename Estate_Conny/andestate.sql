-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 10, 2016 at 06:36 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 7.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `andestate`
--

-- --------------------------------------------------------

--
-- Table structure for table `image`
--

CREATE TABLE `image` (
  `id` int(11) NOT NULL,
  `url` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `image`
--

INSERT INTO `image` (`id`, `url`, `name`) VALUES
(48, 'http://172.16.2.133/Estate_Conny/upload/jackSun_Oct_23_12_13_59_GMT_05_30_2016.jpg', 'jackSun_Oct_23_12_13_59_GMT_05_30_2016'),
(49, 'http://172.16.2.133/Estate_Conny/upload/jackSun_Oct_23_12_51_08_GMT_05_30_2016.jpg', 'jackSun_Oct_23_12_51_08_GMT_05_30_2016'),
(50, 'http://172.16.2.133/Estate_Conny/upload/adminMon_Nov_07_22_53_43_GMT_05_30_2016.png', 'adminMon_Nov_07_22_53_43_GMT_05_30_2016'),
(51, 'http://172.16.2.133/Estate_Conny/upload/adminMon_Nov_07_22_53_31_GMT_05_30_2016.png', 'adminMon_Nov_07_22_53_31_GMT_05_30_2016'),
(52, 'http://172.16.2.133/Estate_Conny/upload/adminMon_Nov_07_22_53_13_GMT_05_30_2016.png', 'adminMon_Nov_07_22_53_13_GMT_05_30_2016'),
(53, 'http://172.16.2.133/Estate_Conny/upload/adminMon_Nov_07_22_55_09_GMT_05_30_2016.png', 'adminMon_Nov_07_22_55_09_GMT_05_30_2016'),
(54, 'http://172.16.2.133/Estate_Conny/upload/adminMon_Nov_07_22_53_13_GMT_05_30_2016.png', 'adminMon_Nov_07_22_53_13_GMT_05_30_2016'),
(55, 'http://172.16.2.133/Estate_Conny/upload/adminTue_Nov_08_01_06_03_GMT_05_30_2016.jpg', 'adminTue_Nov_08_01_06_03_GMT_05_30_2016'),
(56, 'http://192.168.43.44/Estate_Conny/upload/adminTue_Nov_08_11_30_39_GMT_05_30_2016.png', 'adminTue_Nov_08_11_30_39_GMT_05_30_2016'),
(57, 'http://192.168.43.44/Estate_Conny/upload/adminTue_Nov_08_12_41_41_GMT_05_30_2016.png', 'adminTue_Nov_08_12_41_41_GMT_05_30_2016'),
(58, 'http://192.168.43.44/Estate_Conny/upload/sandeshTue_Nov_08_12_50_00_GMT_05_30_2016.png', 'sandeshTue_Nov_08_12_50_00_GMT_05_30_2016'),
(59, 'http://192.168.43.44/Estate_Conny/upload/sandeshTue_Nov_08_12_50_14_GMT_05_30_2016.png', 'sandeshTue_Nov_08_12_50_14_GMT_05_30_2016'),
(60, 'http://192.168.43.44/Estate_Conny/upload/adminTue_Nov_08_12_59_59_GMT_05_30_2016.png', 'adminTue_Nov_08_12_59_59_GMT_05_30_2016'),
(61, 'http://172.16.2.133/Estate_Conny/upload/userWed_Nov_09_09_45_58_GMT_05_30_2016.png', 'userWed_Nov_09_09_45_58_GMT_05_30_2016');

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `mid` int(12) NOT NULL,
  `name` varchar(43) NOT NULL,
  `email` varchar(43) NOT NULL,
  `msg` varchar(122) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`mid`, `name`, `email`, `msg`) VALUES
(1, 'Kalumba Derick', 'kk22@dero.com', 'Maann..for me i want to buy without coming to you.s'),
(2, 'Mukulu Jonathan', 'mukulu@gmail.com', 'I need land for my farm.'),
(3, 'Jack', 'abx@email.com', 'Okay'),
(4, 'Okay', 'shsh@gma', 'snnzn');

-- --------------------------------------------------------

--
-- Table structure for table `property`
--

CREATE TABLE `property` (
  `pid` int(12) NOT NULL,
  `userName` varchar(100) NOT NULL,
  `contact` varchar(100) NOT NULL,
  `name` varchar(123) NOT NULL,
  `location` varchar(123) NOT NULL,
  `city` varchar(100) NOT NULL,
  `status` varchar(123) NOT NULL,
  `price` int(12) NOT NULL,
  `description` varchar(213) NOT NULL,
  `activation` varchar(23) NOT NULL,
  `Image` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `property`
--

INSERT INTO `property` (`pid`, `userName`, `contact`, `name`, `location`, `city`, `status`, `price`, `description`, `activation`, `Image`) VALUES
(78, 'jack', '12345679', 'Rajket', 'Near bear point', 'Mangalore', 'For-Sale', 256, 'Wow noic ', '', 'jackSun_Oct_23_12_13_59_GMT_05_30_2016.jpg'),
(79, 'jack', '256978', 'Mang bhoom', 'Gangolli', 'KUNDAPURA', 'For-Sale', 5867, 'Well water supply available', '', 'jackSun_Oct_23_12_51_08_GMT_05_30_2016.jpg'),
(80, 'admin', '1234567890', 'Land ring', 'Google', 'honey', 'For-Sale', 1254, 'Good', '', 'adminMon_Nov_07_22_55_09_GMT_05_30_2016.png'),
(84, 'admin', '99985698', 'Tea plant', 'Mangalore', 'Mangalore', 'For-Sale', 9066, 'Good maintained', '', 'adminTue_Nov_08_11_30_39_GMT_05_30_2016.png'),
(85, 'admin', '985065096', 'West cost paper mill', 'Bailor', 'KUNDAPURA', 'For-Sale', 900003, '4 coconut tree', '', 'adminTue_Nov_08_12_41_41_GMT_05_30_2016.png'),
(86, 'sandesh', '9620417063', 'sandesh', 'hemmadi', 'hemmadi', 'For-Sale', 5000000, 'good water supply\n', '', 'sandeshTue_Nov_08_12_50_14_GMT_05_30_2016.png'),
(87, 'admin', '9877665438', 'Ramesh', 'KUNDAPURA', 'kodi', 'Sold', 980006, 'Well water supply', '', 'adminTue_Nov_08_12_59_59_GMT_05_30_2016.png'),
(88, 'user', '8567933458', 'Mysore Palace', 'Mysore', 'Mysore', 'Sold', 15, 'Well equiped', '', 'userWed_Nov_09_09_45_58_GMT_05_30_2016.png');

-- --------------------------------------------------------

--
-- Table structure for table `tb_user`
--

CREATE TABLE `tb_user` (
  `user_id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `firstname` varchar(100) NOT NULL,
  `lastname` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_user`
--

INSERT INTO `tb_user` (`user_id`, `username`, `password`, `firstname`, `lastname`) VALUES
(4, 'admin', 'admin', 'Shaftee', 'Sy'),
(3, 'moses', 'moses', 'Moses', 'Gre');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `uid` int(11) NOT NULL,
  `firstname` varchar(100) NOT NULL,
  `lastname` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `location` varchar(100) NOT NULL,
  `contact` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`uid`, `firstname`, `lastname`, `username`, `password`, `location`, `contact`) VALUES
(1, 'User', 'User', 'user', '1234', 'Kundapra', '12345678'),
(3, 'admin', 'Dini', 'admin', '1234', 'Mang', '1111111'),
(4, 'Jack', 'jack', 'jack', '1234', 'Kundapra', '12345678'),
(5, 'jhon', 'dsa', 'jhon', '123', 'Mangalore', '9900665580'),
(7, 'sandesh', 'hemmadi', 'sandesh', '123456789', 'hemmadi', '9620417063');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`mid`);

--
-- Indexes for table `property`
--
ALTER TABLE `property`
  ADD PRIMARY KEY (`pid`);

--
-- Indexes for table `tb_user`
--
ALTER TABLE `tb_user`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`uid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `image`
--
ALTER TABLE `image`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;
--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `mid` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `property`
--
ALTER TABLE `property`
  MODIFY `pid` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;
--
-- AUTO_INCREMENT for table `tb_user`
--
ALTER TABLE `tb_user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
