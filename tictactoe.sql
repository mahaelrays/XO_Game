-- phpMyAdmin SQL Dump
-- version 4.5.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 28, 2017 at 08:53 AM
-- Server version: 5.7.11
-- PHP Version: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tictactoe`
--

-- --------------------------------------------------------

--
-- Table structure for table `game`
--

CREATE TABLE `game` (
  `id` int(10) UNSIGNED NOT NULL,
  `winner` int(10) UNSIGNED NOT NULL DEFAULT '0',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `game`
--

INSERT INTO `game` (`id`, `winner`, `date`) VALUES
(5, 15, '2017-02-27 07:00:18'),
(6, 15, '2017-02-27 07:16:05'),
(7, 1, '2017-02-27 07:37:49'),
(8, 13, '2017-02-27 07:38:15'),
(9, 1, '2017-02-27 09:59:34'),
(10, 13, '2017-02-27 10:04:34'),
(11, 15, '2017-02-27 10:22:14'),
(12, 1, '2017-02-27 10:27:26'),
(13, 1, '2017-02-27 10:29:04'),
(14, 13, '2017-02-27 11:33:08'),
(15, 15, '2017-02-27 11:33:47'),
(16, 15, '2017-02-27 11:34:27'),
(17, 13, '2017-02-27 11:34:59'),
(18, 1, '2017-02-27 11:35:34'),
(19, 13, '2017-02-27 12:02:47'),
(20, 15, '2017-02-27 12:06:03'),
(21, 15, '2017-02-27 12:08:19'),
(22, 13, '2017-02-27 12:08:53'),
(23, 15, '2017-02-27 12:13:57'),
(24, 15, '2017-02-27 12:16:20'),
(25, 15, '2017-02-27 12:19:52'),
(26, 19, '2017-02-27 12:30:21'),
(27, 19, '2017-02-27 12:30:59'),
(28, 19, '2017-02-27 12:31:48'),
(29, 19, '2017-02-27 12:32:26'),
(30, 15, '2017-02-27 12:47:17'),
(31, 13, '2017-02-27 12:55:01'),
(32, 15, '2017-02-28 03:10:58'),
(33, 13, '2017-02-28 04:32:31');

-- --------------------------------------------------------

--
-- Table structure for table `gamelog`
--

CREATE TABLE `gamelog` (
  `game_id` int(10) UNSIGNED NOT NULL,
  `player_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `gamelog`
--

INSERT INTO `gamelog` (`game_id`, `player_id`) VALUES
(32, 12),
(6, 13),
(7, 13),
(8, 13),
(9, 13),
(10, 13),
(11, 13),
(14, 13),
(15, 13),
(16, 13),
(17, 13),
(18, 13),
(19, 13),
(20, 13),
(21, 13),
(22, 13),
(23, 13),
(24, 13),
(25, 13),
(26, 13),
(27, 13),
(28, 13),
(29, 13),
(30, 13),
(31, 13),
(33, 13),
(6, 15),
(7, 15),
(8, 15),
(9, 15),
(10, 15),
(11, 15),
(14, 15),
(15, 15),
(16, 15),
(17, 15),
(18, 15),
(19, 15),
(20, 15),
(21, 15),
(22, 15),
(23, 15),
(24, 15),
(25, 15),
(30, 15),
(31, 15),
(32, 15),
(33, 15),
(12, 16),
(13, 16),
(12, 17),
(13, 18),
(26, 19),
(27, 19),
(28, 19),
(29, 19);

-- --------------------------------------------------------

--
-- Table structure for table `gamesteps`
--

CREATE TABLE `gamesteps` (
  `game_id` int(10) UNSIGNED NOT NULL,
  `saver_id` int(10) UNSIGNED NOT NULL,
  `step1` int(11) NOT NULL,
  `step2` int(11) NOT NULL,
  `step3` int(11) NOT NULL,
  `step4` int(11) NOT NULL,
  `step5` int(11) NOT NULL,
  `step6` int(11) NOT NULL,
  `step7` int(11) NOT NULL,
  `step8` int(11) NOT NULL,
  `step9` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `gamesteps`
--

INSERT INTO `gamesteps` (`game_id`, `saver_id`, `step1`, `step2`, `step3`, `step4`, `step5`, `step6`, `step7`, `step8`, `step9`) VALUES
(11, 13, 6, 1, 4, 2, 8, 0, -1, -1, -1),
(11, 15, 6, 1, 4, 2, 8, 0, -1, -1, -1),
(12, 16, 2, 4, 0, 1, 7, 6, 5, 8, 3),
(12, 17, 2, 4, 0, 1, 7, 6, 5, 8, 3),
(13, 16, 2, 4, 8, 5, 3, 7, 1, 0, 6),
(13, 18, 2, 4, 8, 5, 3, 7, 1, 0, 6),
(14, 13, 6, 3, 7, 4, 8, -1, -1, -1, -1),
(14, 15, 6, 3, 7, 4, 8, -1, -1, -1, -1),
(15, 13, 6, 3, 7, 4, 0, 5, -1, -1, -1),
(15, 15, 6, 3, 7, 4, 0, 5, -1, -1, -1),
(16, 13, 6, 3, 0, 7, 4, 1, 5, 2, 8),
(16, 15, 6, 3, 0, 7, 4, 1, 5, 2, 8),
(17, 13, 6, 4, 2, 1, 7, 8, 0, 5, 3),
(17, 15, 6, 4, 2, 1, 7, 8, 0, 5, 3),
(18, 13, 6, 7, 4, 2, 3, 0, 8, 5, 1),
(18, 15, 6, 7, 4, 2, 3, 0, 8, 5, 1),
(19, 13, 0, 3, 4, 7, 8, -1, -1, -1, -1),
(19, 15, 0, 3, 4, 7, 8, -1, -1, -1, -1),
(20, 13, 6, 3, 0, 4, 1, 5, -1, -1, -1),
(20, 15, 6, 3, 0, 4, 1, 5, -1, -1, -1),
(21, 13, 0, 3, 6, 4, 7, 5, -1, -1, -1),
(21, 15, 0, 3, 6, 4, 7, 5, -1, -1, -1),
(22, 13, 0, 3, 4, 6, 8, -1, -1, -1, -1),
(22, 15, 0, 3, 4, 6, 8, -1, -1, -1, -1),
(23, 13, 6, 3, 4, 1, 2, -1, -1, -1, -1),
(23, 15, 6, 3, 4, 1, 2, -1, -1, -1, -1),
(24, 13, 6, 7, 4, 5, 2, -1, -1, -1, -1),
(24, 15, 6, 7, 4, 5, 2, -1, -1, -1, -1),
(25, 13, 6, 3, 4, 1, 2, -1, -1, -1, -1),
(25, 15, 6, 3, 4, 1, 2, -1, -1, -1, -1),
(26, 13, 6, 7, 4, 2, 0, 8, 3, -1, -1),
(26, 19, 6, 7, 4, 2, 0, 8, 3, -1, -1),
(27, 13, 6, 7, 4, 2, 3, 0, 5, -1, -1),
(27, 19, 6, 7, 4, 2, 3, 0, 5, -1, -1),
(28, 13, 1, 4, 6, 0, 8, 7, 5, 3, 2),
(28, 19, 1, 4, 6, 0, 8, 7, 5, 3, 2),
(29, 13, 0, 3, 4, 6, 8, -1, -1, -1, -1),
(29, 19, 0, 3, 4, 6, 8, -1, -1, -1, -1),
(31, 13, 6, 3, 4, 1, 2, -1, -1, -1, -1),
(32, 12, 0, 3, 4, 7, 8, -1, -1, -1, -1),
(32, 15, 0, 3, 4, 7, 8, -1, -1, -1, -1),
(33, 13, 4, 1, 3, 0, 5, -1, -1, -1, -1);

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

CREATE TABLE `player` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `points` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`id`, `name`, `password`, `points`) VALUES
(1, 'draw', 'draw', 0),
(12, 'monaali', 'monapass', 0),
(13, 'samarammar', 'samarpass', 0),
(15, 'fodafoda', 'fodapass', 0),
(16, 'esraaali', 'esraapass', 0),
(17, 'abdelazim', 'abdelazim', 0),
(18, 'fatmaelzahraa', 'fatmaelzahraa', 0),
(19, 'lobnaayman', 'lobnapass', 0),
(20, 'mkarem', 'mkarempass', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `game`
--
ALTER TABLE `game`
  ADD PRIMARY KEY (`id`),
  ADD KEY `winner` (`winner`);

--
-- Indexes for table `gamelog`
--
ALTER TABLE `gamelog`
  ADD PRIMARY KEY (`game_id`,`player_id`),
  ADD KEY `gameLog_pk_2` (`player_id`);

--
-- Indexes for table `gamesteps`
--
ALTER TABLE `gamesteps`
  ADD PRIMARY KEY (`game_id`,`saver_id`),
  ADD KEY `gs_tb_2` (`saver_id`);

--
-- Indexes for table `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `game`
--
ALTER TABLE `game`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;
--
-- AUTO_INCREMENT for table `player`
--
ALTER TABLE `player`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `game`
--
ALTER TABLE `game`
  ADD CONSTRAINT `game_pfk_1` FOREIGN KEY (`winner`) REFERENCES `player` (`id`);

--
-- Constraints for table `gamelog`
--
ALTER TABLE `gamelog`
  ADD CONSTRAINT `gameLog_pk_1` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `gameLog_pk_2` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `gamesteps`
--
ALTER TABLE `gamesteps`
  ADD CONSTRAINT `gs_tb_1` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`),
  ADD CONSTRAINT `gs_tb_2` FOREIGN KEY (`saver_id`) REFERENCES `player` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
