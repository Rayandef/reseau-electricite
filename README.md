# Projet PAA 2025–2026 — Réseau de distribution d’électricité

## Présentation

Ce projet vise à **modéliser un réseau électrique simplifié**, composé de générateurs et de maisons reliés entre eux par des connexions.  
L’utilisateur peut **créer manuellement un réseau**, **visualiser sa structure** et **calculer son coût** en fonction de critères de dispersion et de surcharge.

---

## Structure du projet

```
reseau-electricite/
 ├── src/
 │   ├── Menus/
 │   │   ├── MenuGestion.java
 │   │   └── MenuSynthese.java
 │   ├── model/
 │   │   ├── Connexion.java
 │   │   ├── Generateur.java
 │   │   ├── Maison.java
 │   │   └── Reseau.java
 │   ├── utils/
 │   │   └── CalculateurCout.java
 │   └── Main.java
 └── README.md
```

## Préréquis

Les programmes étant rédigés en **Java**, il est nécessaire d’avoir un **compilateur Java** installé sur votre système :

- **Windows** : installer [MinGW](https://osdn.net/projects/mingw/releases/)  
- **Linux (Debian/Ubuntu)** :  
  ```bash
  sudo apt install build-essential
  ```
- **macOS** :  
  ```bash
  xcode-select --install
  ```


## Compilation et exécution

### Depuis un terminal

1. Se placer dans le dossier `src` :
   ```bash
   cd src
   ```

2. Compiler :
   ```bash
   javac Main.java Menus/*.java model/*.java utils/*.java
   ```

3. Exécuter :
   ```bash
   java Main
   ```


### Depuis un IDE
Importer le dossier `reseau-electricite` comme projet Java et exécuter la classe `Main`.

## Fonctionnalités principales

- Ajout de générateurs avec leur capacité maximale.
- Ajout de maisons selon trois types de consommation : basse (10 kW), normale (20 kW), forte (40 kW).
- Gestion des relations entre maisons et générateurs existants.
- Affichage du réseau configuré.
- Calcul du coût du réseau à l’aide des formules de dispersion et de surcharge (λ = 10).

## Exemple de sortie

```
--- Réseau électrique ---
Générateurs : [G1 (60 kW max), G2 (60 kW max)]
Maisons : [M1 (40 kW), M2 (40 kW), M3 (20 kW)]
Connexions :
  M1 ↔ G1
  M2 ↔ G2
  M3 ↔ G1

Disp(S)=0.167, Surcharge(S)=0.000, Cout(S)=0.167
```

## Auteurs

- **Rayan Defoor**
- **Ronan Lallouet**
- **Kévin Chen**


