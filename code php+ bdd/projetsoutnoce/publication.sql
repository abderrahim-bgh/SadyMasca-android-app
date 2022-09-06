-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 12 oct. 2021 à 14:46
-- Version du serveur :  10.4.16-MariaDB
-- Version de PHP : 7.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `projetsoutnoce`
--

-- --------------------------------------------------------

--
-- Structure de la table `publication`
--

CREATE TABLE `publication` (
  `id_pub` int(11) NOT NULL,
  `title` text NOT NULL,
  `datte` text NOT NULL,
  `id_t` int(11) NOT NULL,
  `transmitter` text NOT NULL,
  `receiver` text NOT NULL,
  `department` text NOT NULL,
  `faculty` text NOT NULL,
  `pic` text NOT NULL,
  `textt` varchar(5000) NOT NULL,
  `fcm_key` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `publication`
--

INSERT INTO `publication` (`id_pub`, `title`, `datte`, `id_t`, `transmitter`, `receiver`, `department`, `faculty`, `pic`, `textt`, `fcm_key`) VALUES
(232, 'bienvenue sur l application study masca', '30 juin 2021', 52, 'User admin', 'All university', 'comuter science', 'Exact Sciences', 'IMG156946435.jpg', '', ''),
(234, 'emploi du temp', '30 juin 2021', 64, 'prof prof', '3rd year computer science license', 'comuter science', 'Exact Sciences', 'IMG1562885228.jpg', '', ''),
(235, 'Affichage', '30 juin 2021', 64, 'prof prof', '3rd year computer science license', 'comuter science', 'Exact Sciences', 'IMG843200660.jpg', 'L affichage du note de application  ABDD 22/05/2021', ''),
(236, 'consultation TP', '30 juin 2021', 64, 'prof prof', '2nd year computer science license', 'comuter science', 'Exact Sciences', 'IMG805381309.jpg', '24/06/2021', ''),
(238, 'fiche tp', '30 juin 2021', 70, 'marwa ikram', '3rd year computer science license', 'comuter science', 'Exact Sciences', 'IMG661655596.jpg', 'module compilation', ''),
(239, 'bon courage', '30 juin 2021', 70, 'marwa ikram', '3rd year computer science license', 'comuter science', 'Exact Sciences', 'IMG209425636.jpg', '😊😊', ''),
(240, 'salam', '30 juin 2021', 52, 'User admin', 'All university', 'comuter science', 'Exact Sciences', 'IMG1296721153.jpg', '', '');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `publication`
--
ALTER TABLE `publication`
  ADD PRIMARY KEY (`id_pub`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `publication`
--
ALTER TABLE `publication`
  MODIFY `id_pub` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=242;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
