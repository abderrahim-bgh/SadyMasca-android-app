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
-- Structure de la table `promo`
--

CREATE TABLE `promo` (
  `id_y` int(11) NOT NULL,
  `promoNom` text NOT NULL,
  `department_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `promo`
--

INSERT INTO `promo` (`id_y`, `promoNom`, `department_id`) VALUES
(1, '2nd year computer science license', 2),
(2, '3rd year computer science license', 2),
(3, 'First Year Master ISI', 2),
(4, 'First Year Master RSD', 2),
(5, 'Première Année Master SITW', 2),
(6, 'Deuxième Année Master RSD', 2),
(7, 'Deuxième Année Master SITW', 2),
(8, 'Déuxième Année Master ISI', 2),
(9, '2nd Mathematical Year', 1),
(84, 'telecom L2', 197),
(85, 'telecom L3', 197),
(86, 'physic L2', 198),
(87, 'physic L3', 198),
(92, 'banck L2', 202),
(93, 'banck L3', 202),
(104, 'M1 analyse', 1),
(105, 'M2 analyse', 1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `promo`
--
ALTER TABLE `promo`
  ADD PRIMARY KEY (`id_y`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `promo`
--
ALTER TABLE `promo`
  MODIFY `id_y` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=110;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
