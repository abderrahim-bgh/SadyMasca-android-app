-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 12 oct. 2021 à 14:45
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
-- Structure de la table `adherent`
--

CREATE TABLE `adherent` (
  `id` int(11) NOT NULL,
  `firstName` text NOT NULL,
  `lastName` text NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` text NOT NULL,
  `numRegst` int(11) NOT NULL,
  `department` text NOT NULL,
  `faculty` text NOT NULL,
  `status` text NOT NULL,
  `p1` text NOT NULL,
  `p2` text NOT NULL,
  `p3` text NOT NULL,
  `photo` text NOT NULL,
  `block` text NOT NULL,
  `responsibility` text NOT NULL,
  `N1` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `adherent`
--

INSERT INTO `adherent` (`id`, `firstName`, `lastName`, `email`, `password`, `numRegst`, `department`, `faculty`, `status`, `p1`, `p2`, `p3`, `photo`, `block`, `responsibility`, `N1`) VALUES
(51, 'USER', 'user', 'user66@gmail.com', '$2y$10$.osnSk6WyQcdwms.mH3BPOp7Wpaiuim.LP5NjRH2QQafpm3fdpHaC', 1235656, 'comuter science', 'Exact Sciences', 'etudient', '2nd year computer science license', '', '', '', 'yes', '', '1'),
(52, 'User', 'admin', 'admin@gmail.com', '$2y$10$bEQAQhakUo41zT05NjN/QuBaAhA.9B6q/fE9vKZwqcX7MBS6WYgpC', 0, 'comuter science', 'Exact Sciences', 'admin', 'teacher', '', '', 'http://192.168.43.22/StadyMasca/profile_image/52.jpeg', '', '', '1'),
(53, 'abdoum', 'abdou', 'abdou66@gmail.com', '$2y$10$z6QqvLQ4PlOI83WjvwKyb.hCdgSx0vtYgSNcd7Bah23.nGCuEK31K', 0, 'comuter science', 'Exact Sciences', 'prof', 'teacher', '', '', '', 'no', '1', '1'),
(54, 'aaaa', 'aaaa', 'user22@gmail.com', '$2y$10$2nkKlZU5g2W55QTDfaMnqOr6ch0GqJBtNVtO69A7uniIqfd2bnXc2', 0, 'math ', 'Exact Sciences', 'prof', 'teacher', '', '', '', 'yes', '1', '1'),
(55, 'user', 'samir', 'sar@gmail.com', '$2y$10$j0GhEkKlpHfDMNUq.dPc0OccyZTzL14Sy7GHG5uSVXAxmhSAlzQrK', 12345679, 'comuter science', 'Exact Sciences', 'prof', 'teacher', '', '', 'http://192.168.43.22/StadyMasca/profile_image/55.jpeg', 'no', '2', '1'),
(56, 'Baghdad', 'Abderrahim', 'baghdad@gmail.com', '$2y$10$UnvuMEXr.vHlWBAkss0IjuZcvJFhao7CYEdGQDFH62jYOOFYe0cBG', 1335656, 'comuter science', 'Exact Sciences', 'etudient', '3rd year computer science license', '', '', '', '', '', '1'),
(57, 'pppp', 'pppp', 'pp@gmail.com', '$2y$10$YclucJnDz09jDpwC6wWK.O.ZE585ZPxrG3hTSW4hASPqVqHYbV57W', 2155656, 'banck', 'economic', 'etudient', 'banck L2', '', '', '', '', '', '1'),
(58, 'user', 'USER', 'userr@gmail.com', '$2y$10$8bwAZhIyCe3qCbtkvafoee9IJ.WMsBTcL2MD6kf516wmqMVMSFHxy', 1235223, 'math ', 'Exact Sciences', 'etudient', '2nd Mathematical Year', '', '', '', 'no', '', '1'),
(60, 'aaaa', 'aaaaa', 'aaaaaa2@wwww.ww', '$2y$10$.tgFvYaf7egnnrCMz7EnvelzwPM6gIRF6TGV.SWRWHoLyNoOI7hxu', 32232332, 'math ', 'Exact Sciences', 'etudient', '2nd Mathematical Year', '', '', '', 'yes', '', '1'),
(61, 'belkis', 'aicha', 'belkis66@gmail.com', '$2y$10$GI0CV33l9kTSsv7hObvKtO6TH4oAP7SCYJrMdK/ujGNk2UOw6mnhq', 44444444, 'comuter science', 'Exact Sciences', 'etudient', 'Deuxième Année Master SITW', '', '', '', '', '', '1'),
(62, 'user', 'tetcher', 'usertetch@gmail.com', '$2y$10$68pzt/oeEXqqhrjLjZOEGeeGeg03pk4pB2gepWgeHIYe5nq2/e9im', 0, 'physic', 'Exact Sciences', 'prof', 'teacher', '', '', '', 'no', '0', '1'),
(63, 'ikram', 'marwa', 'ikram@gmail.com', '$2y$10$Jv5l789QYKZKbIlIDHOw..jjSIQD2rycom4XomrAQZFgQZRX5vo0G', 12345679, 'comuter science', 'Exact Sciences', 'etudient', '3rd year computer science license', '', '', '', '', '', '1'),
(64, 'prof', 'prof', 'prof@gmail.com', '$2y$10$YnmuxcycWbBjtFj6zWp6oeoC2kPdnt6LbC88vsVXxsSsboatnHlhS', 0, 'comuter science', 'Exact Sciences', 'prof', 'teacher', '', '', 'http://192.168.43.22/StadyMasca/profile_image/64.jpeg', '', '', '1'),
(66, 'user', 'userr', 'abdoupp@gmail.com', '$2y$10$Fuhlj7/4CMNnhkUL6RDbRehnm4W1o9JVg6D1.bIywRv/fchE0SaSe', 2147483647, 'math ', 'Exact Sciences', 'etudient', '2nd Mathematical Year', '', '', '', '', '', '1'),
(67, 'samir29', 'samir', 'ssss29@gmail.com', '$2y$10$oYcd4YzjnV5n98cNRSkvkOds4xoDKCWYIZA.3kBoiYjIqwjL.rNAe', 0, 'math ', 'Exact Sciences', 'prof', 'teacher', '', '', '', '', '2', '1'),
(68, 'amine', 'amine', 'amine@gmail.com', '$2y$10$w60IoPEGlgGaRpZaG3vwjOTfVSwgToiWFxYghC2iSr5t3wBQvOMKu', 123546855, 'math ', 'Exact Sciences', 'etudient', 'M1 analyse', '', '', '', '', '', '1'),
(69, 'amine29', 'amine29', 'amine16@gmail.com', '$2y$10$A1Lut9vbPEUO7w7stL4Ax.SKSYDTaB7Ke11HBa44Ne95nb5dJfZZa', 1234567895, 'math ', 'Exact Sciences', 'etudient', 'M1 analyse', '2nd Mathematical Year', 'M2 analyse', '', '', '', '1'),
(70, 'marwa', 'ikram', 'sahridjikram22@gmail.com', '$2y$10$Rpmead8dW2YtNiMNi2ZjF.pCfu0ksfmKkQc7QvXtwCyDKFJE.35C.', 0, 'comuter science', 'Exact Sciences', 'prof', 'teacher', '', '', 'http://192.168.43.22/StadyMasca/profile_image/70.jpeg', '', '2', '1'),
(71, 'samir', 'setaouti', 'samsam@gmail.com', '$2y$10$9IuDSU6XFqfNISwvcJaZoOb6/NHpMGxdzpG5emFrQUnjRyd2QZjzK', 123456789, 'comuter science', 'Exact Sciences', 'prof', 'teacher', '', '', '', '', '', '1'),
(72, 'seta', 'samir', 'samsam29@gmail.com', '$2y$10$BhdALJeXmn6XxyGj/FCY1O35eNqAqIDVTSi3OBbN11rN0ks9Q4gk2', 123456789, 'comuter science', 'Exact Sciences', 'prof', 'teacher', '', '', '', '', '2', '1'),
(74, 'ikram', 'marwa', 'usermarwa@gmail.com', '$2y$10$3LYl4qtg/VuMOg4yJu5vpuEY3cMilTSNmYu50VYlxpwYW51mWumCK', 147258, 'math ', 'Exact Sciences', 'etudient', '2nd Mathematical Year', '', '', '', '', '', '1'),
(75, 'user', 'prof', 'usermarwa11@gmail.com', '$2y$10$LxEfqMIZ6yBxwNOgo5w9n.AQrCUucBbpl2Aa9g8eVJdQ59.Q89R7W', 0, 'comuter science', 'Exact Sciences', 'prof', 'teacher', '', '', '', '', '', '1'),
(76, 'aaaaa', 'aaaaa', 'user123@gmail.com', '$2y$10$hIDvTI61mC9sApc5yHbj3OJRl7a8ey4Hx3iWdn3cQDiZS5axWik7O', 411111, 'math ', 'Exact Sciences', 'etudient', '2nd Mathematical Year', '', '', '', '', '', ''),
(77, 'sarr', 'abdou', 'abdou@gmail.com', '$2y$10$2V6.1F8RXQzNh3u7FTuWvexb6cuOxXgqYayl/tLRoy.1T0mziQ84K', 1235645, 'math ', 'Exact Sciences', 'etudient', 'M1 analyse', '', '', '', '', '', '');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `adherent`
--
ALTER TABLE `adherent`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `adherent`
--
ALTER TABLE `adherent`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=78;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
