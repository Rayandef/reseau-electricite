# Projet PAA 2025-2026 - Reseau de distribution d'electricite

## Présentation

Ce projet simule un réseau électrique simplifié composé de générateurs, de maisons et de connexions.
Il permet de construire un réseau pas à pas, d importer des instances, puis d évaluer et d optimiser le coût du réseau.

---

## Structure du projet

```
reseau-electricite/
  src/
    exception/
      ComposantException.java
      ConnexionNotFoundExeception.java
    io/
      ExportFichier.java
      ImportFichier.java
    menus/
      MenuGestion.java
      MenuSynthese.java
    model/
      Connexion.java
      Generateur.java
      Maison.java
      Reseau.java
    utils/
      AlgoBestConnexion.java
      CalculateurCout.java
    Main.java
  instances/
  test/
    io/
      ExportFichierTest.java
      ImportFichierTest.java
    menus/
      MenuGestionTest.java
      MenuSyntheseTest.java
    model/
      ConnexionTest.java
      GenerateurTest.java
      MaisonTest.java
      ReseauTest.java
    utils/
      AlgoBestConnexionTest.java
      CalculateurCoutTest.java
    MainTest.java
  lib/
    junit.jar
  doc/
    index.html
  README.md
```

## Prérequis

Les programmes étant écrits en Java, il est nécessaire d avoir un compilateur Java installé.

- Windows : installer MinGW
- Linux (Debian/Ubuntu) :
  ```bash
  sudo apt install build-essential
  ```
- macOS :
  ```bash
  xcode-select --install
  ```

## Compilation et exécution

### Depuis un terminal

1. Se placer à la racine du projet :
   ```bash
   cd reseau-electricite
   ```

2. Compiler :
   ```bash
   javac -d bin src/Main.java src/exception/*.java src/io/*.java src/menus/*.java src/model/*.java src/utils/*.java
   ```

3. Exécuter :
   ```bash
   java -cp bin Main
   ```

### Arguments possibles

- Aucun argument : lance le menu de gestion puis le menu de synthèse.
- 1 argument (chemin fichier) : importe le réseau puis lance le menu de synthèse.
- 2 arguments (chemin fichier, lambda) : importe le réseau, fixe lambda puis lance le menu de synthèse.

Exemples :

```bash
java -cp bin Main instances/instance1.txt
java -cp bin Main instances/instance1.txt 10
```

### Depuis un IDE

Importer le dossier `reseau-electricite` comme projet Java et exécuter la classe `Main`.

## Fonctionnalités principales

- Ajouter, mettre à jour et supprimer des connexions entre maisons et générateurs.
- Créer un réseau manuellement ou l importer depuis un fichier texte.
- Exporter le réseau courant vers un fichier texte.
- Optimiser automatiquement les connexions avec l algorithme de répartition.
- Calculer le coût avec dispersion et surcharge (lambda = 10 par défaut).

## Algorithme d optimisation

L algorithme (AlgoBestConnexion) trie les maisons par consommation décroissante puis les affecte aux générateurs.
Il cherche d abord une connexion sans surcharge qui minimise la dispersion, sinon il choisit le générateur
qui minimise le coût (dispersion + lambda * surcharge). Les charges et connexions sont mises à jour à chaque ajout.

## Format des fichiers d instances

Chaque ligne respecte un des formats suivants :

- generateur(Nom,Capacite).
- maison(Nom,Type) avec Type = BASSE, NORMALE/NORMAL, FORTE.
- connexion(Generateur,Maison).

Voir `instances/` pour des exemples.

## Tests

Les tests JUnit sont dans `test/`. Le jar JUnit est fourni dans `lib/junit.jar`.

Exemple (PowerShell) :

```powershell
javac -d bin -cp lib/junit.jar;bin test/MainTest.java
java -cp lib/junit.jar;bin org.junit.runner.JUnitCore MainTest
```

## Exemple de sortie

```
--- Reseau electrique ---
Generateurs : [G1 (60 kW max), G2 (60 kW max)]
Maisons : [M1 (40 kW), M2 (40 kW), M3 (20 kW)]
Connexions :
  M1 - G1
  M2 - G2
  M3 - G1

Disp(S)=0.167, Surcharge(S)=0.000, Cout(S)=0.167
```

## Documentation

La documentation complète du projet (Javadoc) est trouvable en ouvrant le fichier `doc/index.html`.

## Auteurs

- Rayan Defoor
- Ronan Lallouet
- Kevin Chen
