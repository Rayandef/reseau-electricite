# Projet PAA 2025-2026 - Reseau de distribution d'electricite

## Presentation

Ce projet simule un reseau electrique simplifie compose de generateurs, de maisons et de connexions.
Il permet de construire un reseau pas a pas, d importer des instances, puis d evaluer et d optimiser le cout du reseau.

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
  README.md
```

## Prerequis

Les programmes etant ecrits en Java, il est necessaire d avoir un compilateur Java installe.

- Windows : installer MinGW
- Linux (Debian/Ubuntu) :
  ```bash
  sudo apt install build-essential
  ```
- macOS :
  ```bash
  xcode-select --install
  ```

## Compilation et execution

### Depuis un terminal

1. Se placer dans le dossier `src` :
   ```bash
   cd src
   ```

2. Compiler :
   ```bash
   javac Main.java Menus/*.java model/*.java utils/*.java
   ```

3. Executer :
   ```bash
   java Main
   ```

### Depuis un IDE

Importer le dossier `reseau-electricite` comme projet Java et executer la classe `Main`.

## Fonctionnalites principales

- Ajouter, mettre a jour et supprimer des connexions entre maisons et generateurs.
- Creer un reseau manuellement ou l importer depuis un fichier texte.
- Exporter le reseau courant vers un fichier texte.
- Optimiser automatiquement les connexions avec l algorithme de repartition.
- Calculer le cout avec dispersion et surcharge (lambda = 10 par defaut).

## Algorithme d optimisation

L algorithme (AlgoBestConnexion) trie les maisons par consommation decroissante puis les affecte aux generateurs.
Il cherche d abord une connexion sans surcharge qui minimise la dispersion, sinon il choisit le generateur
qui minimise le cout (dispersion + lambda * surcharge). Les charges et connexions sont mises a jour a chaque ajout.

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

La documentation complete du projet (Javadoc) est trouvable en ouvrant le fichier `doc/index.html`.

## Auteurs

- Rayan Defoor
- Ronan Lallouet
- Kevin Chen


