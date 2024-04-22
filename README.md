# DELICIVITE - SYSTÈME DE LIVRAISON DE REPAS À TROIS RIVIÈRES 

## Important  - À lire

## Description
DELICIVITE est un système de livraison de repas implémenté en JavaFX. Ce projet contient 4 interfaces utilisateurs fonctionnelles dont l'inscription des utilisateurs, la mise à jour des menus, et bien plus encore.

## Setup

### Prérequis
- JDK 17
- Gradle
- IntelliJ IDEA
- MySQLWorkbench
- MySQLConnector

### Bibliothèques JavaFX Utilisées
- Bootstrap FX
- ControlsFX
- FormsFx
- Ikonli
- TilesFX
- ValidatorFX

### Configuration du Projet
Assurez-vous de bien suivre ces étapes pour configurer correctement le projet :

1. Modifiez le fichier `java-module-info` et `build-gradle` conformément aux configurations fournies dans le projet (voir les fichiers correspondants dans ce repositoire).

2. Assurez-vous d'avoir une base de données locale MySQL Workbench installée sur votre ordinateur, pour accéder à toutes les interfaces du projet.

  ### Connexion entre la base de données et le projet sur INTELLIJ 

  1. Sur ce site (https://dev.mysql.com/downloads/connector/j/), sélectionner l'option "Platform Independant" 
  2. Téléchargez le fichier "Platform Independent (Architecture Independent), ZIP Archive" : c'Est un JDBC (Java Database Connectivity) 
  3. Allez dans le projet et si il n'est pas présent dans le dossier "lib", copie-coller le fichier .jar installé (qui devrait être nommmé "mysql-connector-j-8.3.0.jar")
  4. Dans le projet INTELLIJ, dans Paramètres, allez dans "Project Structure", puis dans "Modules", puis dans le premier module, puis dans la section "Dependencies"
  5. Cliquez sur "+" et l'option 1 "JAR...." 
  6. Chercher la localisation du fichier .jar que vous venez de placer dans le dossier lib et cliquez sur "OK"

  #### Si cela ne fonctionne pas, essayez ceci 
  
  1. Cliquer sur "Paramètres" dans votre projet INTELLIJ, puis sur "Project Structure" et "Libraries" 
  2. Cliquez sur "+" et choisir le fichier .jar que vous avez insérer dans le dossier lib et cliquez sur "OK" 

  Si cela ne marche toujours pas, veuillez contacter un des membres de notre équipe à nos courriels universitaires respectifs. (julien.desrosiers, elodie.occhibelli, oceane.rakotoarisoa) 

### Configuration de la base de données locale

3. Lors de l'installation de la base de données, utilisez les paramètres suivants :
   - Nom de l'utilisateur : root
   - Mot de passe : "OceSQL2005"

4. Utilisez le script fourni pour créer la base de données. Assurez-vous que la base de données porte le nom `delicivite`.

## Installation de la Base de Données
Utilisez le script suivant pour créer la base de données avec les tables nécessaires :

```sql
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: delicivite
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `inscription`
--

DROP TABLE IF EXISTS `inscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inscription` (
  `id_inscription` int NOT NULL AUTO_INCREMENT,
  `prenom_inscrit` varchar(45) NOT NULL,
  `nom_inscrit` varchar(45) NOT NULL,
  `courriel_inscrit` varchar(45) NOT NULL,
  `mot_de_passe_inscrit` varchar(45) NOT NULL,
  `type_utilisateur` enum('proprietaire','client','livreur','employe') NOT NULL,
  PRIMARY KEY (`id_inscription`),
  UNIQUE KEY `id_inscription_UNIQUE` (`id_inscription`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inscription`
--

LOCK TABLES `inscription` WRITE;
/*!40000 ALTER TABLE `inscription` DISABLE KEYS */;
INSERT INTO `inscription` VALUES (1,'Océane','Rakotoarisoa','oceane.srtr@gmail.com','oceane','client'),(2,'Jean','Dupont','employe1@gmail.com','motdepasse1','employe'),(3,'Marie','Tremblay','employe2@yahoo.com','motdepasse2','employe'),(4,'Carlos','Garcia','employe3@hotmail.com','motdepasse3','employe'),(5,'Linh','Nguyen','employe4@outlook.com','motdepasse4','employe'),(6,'Emily','Smith','employe5@hotmail.com','motdepasse5','employe'),(7,'Emily','Smith','employe5@hotmail.com','motdepasse5','employe'),(8,'Emily','Smith','employe5@hotmail.com','motdepasse5','employe'),(9,'Emily','Smith','employe5@hotmail.com','motdepasse5','employe'),(10,'Emily','Smith','employe5@hotmail.com','motdepasse5','employe'),(11,'Emily','Smith','employe5@hotmail.com','motdepasse5','employe'),(12,'Jean','Dupont','employe1@gmail.com','motdepasse1','employe'),(13,'Jean','Dupont','employe1@gmail.com','motdepasse1','employe'),(14,'Jean','Dupont','employe1@gmail.com','motdepasse1','employe'),(15,'Lily','Occhi','1','1','employe');
