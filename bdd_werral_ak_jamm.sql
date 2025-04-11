-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 11 avr. 2025 à 14:59
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `bdd_werral_ak_jamm`
--

-- --------------------------------------------------------

--
-- Structure de la table `assurance`
--

CREATE TABLE `assurance` (
  `id` bigint(20) NOT NULL,
  `nomAssurance` varchar(100) NOT NULL,
  `typeAssurance` varchar(50) NOT NULL,
  `dateDebut` date NOT NULL,
  `dateFin` date NOT NULL,
  `tauxCouverture` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `assurance`
--

INSERT INTO `assurance` (`id`, `nomAssurance`, `typeAssurance`, `dateDebut`, `dateFin`, `tauxCouverture`) VALUES
(1, 'Assurance A', 'Santé', '2025-01-01', '2025-12-31', 80),
(2, 'Assurance B', 'Vie', '2025-03-01', '2025-08-31', 75),
(3, 'Assurance C', 'Santé', '2025-02-01', '2025-11-30', 90),
(4, 'Assurance D', 'Habitation', '2025-04-01', '2025-12-31', 85),
(5, 'Assurance E', 'Automobile', '2025-01-01', '2025-12-31', 70);

-- --------------------------------------------------------

--
-- Structure de la table `chambre`
--

CREATE TABLE `chambre` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `numero` varchar(50) DEFAULT NULL,
  `capacite` int(11) DEFAULT NULL,
  `lits_occupes` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `chambre`
--

INSERT INTO `chambre` (`id`, `numero`, `capacite`, `lits_occupes`) VALUES
(1, 'A101', 4, 2),
(2, 'A101', 2, 1),
(3, 'B201', 1, 0),
(4, 'B202', 3, 1),
(5, 'C301', 2, 0),
(6, 'A101', 3, 1);

-- --------------------------------------------------------

--
-- Structure de la table `garde`
--

CREATE TABLE `garde` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `personnel_id` bigint(20) DEFAULT NULL,
  `date_debut` date NOT NULL,
  `date_fin` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `garde`
--

INSERT INTO `garde` (`id`, `personnel_id`, `date_debut`, `date_fin`) VALUES
(1, 1, '2025-04-10', '2025-04-11'),
(2, 2, '2025-04-11', '2025-04-12'),
(3, 3, '2025-04-12', '2025-04-13'),
(4, 4, '2025-04-13', '2025-04-14'),
(5, 5, '2025-04-14', '2025-04-15'),
(6, 6, '2025-04-15', '2025-04-16'),
(7, 7, '2025-04-16', '2025-04-17'),
(8, 8, '2025-04-17', '2025-04-18'),
(9, 9, '2025-04-18', '2025-04-19'),
(10, 10, '2025-04-19', '2025-04-20');

-- --------------------------------------------------------

--
-- Structure de la table `hospitalisation`
--

CREATE TABLE `hospitalisation` (
  `id` bigint(20) NOT NULL,
  `patientId` bigint(20) NOT NULL,
  `chambreId` bigint(20) NOT NULL,
  `motif` varchar(255) DEFAULT NULL,
  `dateEntree` date NOT NULL,
  `dateSortie` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `hospitalisation`
--

INSERT INTO `hospitalisation` (`id`, `patientId`, `chambreId`, `motif`, `dateEntree`, `dateSortie`) VALUES
(1, 1, 1, 'Chirurgie', '2025-04-01', '2025-04-10'),
(2, 2, 2, 'Observation', '2025-04-05', NULL),
(3, 3, 3, 'Consultation', '2025-04-02', '2025-04-07'),
(4, 4, 4, 'Accident', '2025-04-03', '2025-04-12'),
(5, 5, 5, 'Préparation chirurgicale', '2025-04-04', NULL),
(6, 1, 2, 'Observation après chirurgie', '2024-08-01', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `materiel`
--

CREATE TABLE `materiel` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `quantite` int(11) DEFAULT NULL,
  `fournisseur` varchar(100) DEFAULT NULL,
  `date_entree` varchar(50) DEFAULT NULL,
  `date_sortie` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `materiel`
--

INSERT INTO `materiel` (`id`, `nom`, `quantite`, `fournisseur`, `date_entree`, `date_sortie`) VALUES
(1, 'Lit médicalisé', 10, 'Fourniture Santé', '2024-08-01', NULL),
(2, 'Fauteuil roulant', 4, 'Mobility Plus', '2024-08-02', NULL),
(3, 'Matelas orthopédique', 8, 'OrthoPro', '2024-08-03', NULL),
(4, 'Thermomètre digital', 15, 'MediEquip', '2024-08-04', '2024-08-10'),
(5, 'Stéthoscope', 6, 'SantéTech', '2024-08-05', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `paiement`
--

CREATE TABLE `paiement` (
  `id` bigint(20) NOT NULL,
  `patientId` bigint(20) NOT NULL,
  `chambreId` bigint(20) NOT NULL,
  `montant` double NOT NULL,
  `datePaiement` date NOT NULL,
  `statut` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `paiement`
--

INSERT INTO `paiement` (`id`, `patientId`, `chambreId`, `montant`, `datePaiement`, `statut`) VALUES
(1, 1, 101, 150, '2025-04-10', 'Payé'),
(2, 2, 102, 200, '2025-04-11', 'En attente'),
(3, 3, 103, 120, '2025-04-12', 'Payé'),
(4, 4, 104, 180, '2025-04-13', 'Annulé'),
(5, 5, 105, 160, '2025-04-14', 'Payé'),
(6, 1, 101, 150, '2025-04-10', 'Payé');

-- --------------------------------------------------------

--
-- Structure de la table `patients`
--

CREATE TABLE `patients` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `code_patient` varchar(100) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `tel` varchar(20) NOT NULL,
  `numero_assurance` varchar(100) DEFAULT NULL,
  `notes_medicales` text DEFAULT NULL,
  `traitements` text DEFAULT NULL,
  `derniereHospitalisationId` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `patients`
--

INSERT INTO `patients` (`id`, `code_patient`, `nom`, `prenom`, `tel`, `numero_assurance`, `notes_medicales`, `traitements`, `derniereHospitalisationId`) VALUES
(1, 'CP001', 'Fall', 'Awa', '770000001', 'NA12345', 'Diabète de type 2', 'Insuline', NULL),
(2, 'CP002', 'Ba', 'Mamadou', '770000002', 'NA23456', 'Hypertension', 'Amlodipine', NULL),
(3, 'CP003', 'Ndiaye', 'Fatou', '770000003', NULL, 'Aucun', NULL, NULL),
(4, 'CP004', 'Sarr', 'Cheikh', '770000004', 'NA34567', 'Asthme sévère', 'Ventoline', NULL),
(5, 'CP005', 'Diallo', 'Moussa', '770000005', NULL, NULL, NULL, NULL),
(6, 'CP006', 'Faye', 'Mariama', '770000006', 'NA45678', 'Anémie', 'Fer', NULL),
(7, 'CP007', 'Sy', 'Abdoulaye', '770000007', NULL, NULL, NULL, NULL),
(8, 'CP008', 'Gueye', 'Astou', '770000008', 'NA56789', 'Grippe chronique', 'Doliprane', NULL),
(9, 'CP009', 'Kane', 'Oumar', '770000009', NULL, 'Diabète', 'Metformine', NULL),
(10, 'CP010', 'Lo', 'Khady', '770000010', 'NA67890', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `personnel`
--

CREATE TABLE `personnel` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `role` varchar(50) NOT NULL,
  `specialite` varchar(100) DEFAULT NULL,
  `telephone` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `personnel`
--

INSERT INTO `personnel` (`id`, `nom`, `prenom`, `role`, `specialite`, `telephone`) VALUES
(1, 'Diop', 'Awa', 'Médecin', 'Neurologue', '771234567'),
(2, 'Diouf', 'Fatou', 'infirmier', NULL, '771100002'),
(3, 'Fall', 'Mame', 'medecin', 'Pédiatrie', '771100003'),
(4, 'Ndoye', 'Ibrahima', 'infirmier', NULL, '771100004'),
(5, 'Ba', 'Seynabou', 'medecin', 'Neurologie', '771100005'),
(6, 'Camara', 'Demba', 'infirmier', NULL, '771100006'),
(7, 'Faye', 'Aminata', 'medecin', 'Dermatologie', '771100007'),
(8, 'Kane', 'Modou', 'infirmier', NULL, '771100008'),
(9, 'Gueye', 'Binta', 'medecin', 'ORL', '771100009'),
(10, 'Thiam', 'Lamine', 'infirmier', NULL, '771100010');

-- --------------------------------------------------------

--
-- Structure de la table `rendez_vous`
--

CREATE TABLE `rendez_vous` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `patient_id` bigint(20) DEFAULT NULL,
  `personnel_id` bigint(20) DEFAULT NULL,
  `date_heure` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `motif` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `rendez_vous`
--

INSERT INTO `rendez_vous` (`id`, `patient_id`, `personnel_id`, `date_heure`, `motif`) VALUES
(3, 3, 3, '2025-04-17 09:30:00', 'Consultation enfant'),
(4, 4, 4, '2025-04-18 16:00:00', 'Pansement'),
(5, 5, 5, '2025-04-19 11:00:00', 'Problème neurologique'),
(6, 6, 6, '2025-04-20 08:00:00', 'Vaccination'),
(7, 7, 7, '2025-04-21 15:00:00', 'Problème de peau'),
(8, 8, 8, '2025-04-22 12:00:00', 'Suivi traitement'),
(9, 9, 9, '2025-04-23 10:00:00', 'Douleurs oreille'),
(10, 10, 10, '2025-04-24 13:30:00', 'Soin infirmier'),
(11, 1, 2, '2024-09-15 10:30:00', 'Consultation générale');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `assurance`
--
ALTER TABLE `assurance`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `chambre`
--
ALTER TABLE `chambre`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `garde`
--
ALTER TABLE `garde`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `hospitalisation`
--
ALTER TABLE `hospitalisation`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `materiel`
--
ALTER TABLE `materiel`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `paiement`
--
ALTER TABLE `paiement`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `personnel`
--
ALTER TABLE `personnel`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `rendez_vous`
--
ALTER TABLE `rendez_vous`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `assurance`
--
ALTER TABLE `assurance`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `chambre`
--
ALTER TABLE `chambre`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `garde`
--
ALTER TABLE `garde`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT pour la table `hospitalisation`
--
ALTER TABLE `hospitalisation`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `materiel`
--
ALTER TABLE `materiel`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `paiement`
--
ALTER TABLE `paiement`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `patients`
--
ALTER TABLE `patients`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `personnel`
--
ALTER TABLE `personnel`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT pour la table `rendez_vous`
--
ALTER TABLE `rendez_vous`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
