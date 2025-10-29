# Projet PAA 2025–2026 — Réseau de distribution d’électricité

## Présentation

L’objectif est de modéliser un réseau électrique simplifié composé de maisons (consommateurs) et de générateurs, puis de permettre
la création manuelle du réseau et le calcul de son coût selon les critères d’équilibre et de surcharge.

## Structure du projet

```
reseau-electricite/
 ├── src/
 │   ├── Main.java
 │   ├── model/
 │   │   ├── Maison.java
 │   │   ├── Generateur.java
 │   │   ├── Connexion.java
 │   │   └── Reseau.java
 │   └── utils/
 │       └── CalculateurCout.java
 └── README.md
```

## Compilation et exécution

### Depuis un terminal

1. Se placer dans le dossier `src` :
   ```
   cd src
   ```

2. Compiler :
   ```
   javac Main.java model/*.java utils/*.java
   ```

3. Exécuter :
   ```
   java Main
   ```

### Depuis un IDE
Importer le dossier `reseau-electricite` comme projet Java et exécuter la classe `Main`.

## Fonctionnalités principales

- Ajout de générateurs avec leur capacité maximale.
- Ajout de maisons selon trois types de consommation : basse (10 kW), normale (20 kW), forte (40 kW).
- Création de connexions entre maisons et générateurs existants.
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

DEFOOR Rayan
LALLOUET Ronan
CHEN Kévin
