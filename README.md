# Projet PAA 2025–2026 — Réseau de distribution d’électricité

## Presentation

Ce projet modele un reseau electrique simplifie compose de generateurs, de maisons et de connexions.
L utilisateur peut construire un reseau, l importer depuis un fichier texte, puis calculer et optimiser son cout.

---

## Structure du projet

```
reseau-electricite/
 ├── src/
 │   ├── exception/
 │   │   ├── ComposantException.java
 │   │   └── ConnexionNotFoundExeception.java
 │   ├── io/
 │   │   ├── ExportFichier.java
 │   │   └── ImportFichier.java
 │   ├── menus/
 │   │   ├── MenuGestion.java
 │   │   └── MenuSynthese.java
 │   ├── model/
 │   │   ├── Connexion.java
 │   │   ├── Generateur.java
 │   │   ├── Maison.java
 │   │   └── Reseau.java
 │   ├── utils/
 │   │   ├── AlgoBestConnexion.java
 │   │   └── CalculateurCout.java
 │   └── Main.java
 ├── test/
 │   ├── io/
 │   │   ├── ExportFichierTest.java
 │   │   └── ImportFichierTest.java
 │   ├── menus/
 │   │   ├── MenuGestionTest.java
 │   │   └── MenuSyntheseTest.java
 │   ├── model/
 │   │   ├── ConnexionTest.java
 │   │   ├── GenerateurTest.java
 │   │   ├── MaisonTest.java
 │   │   └── ReseauTest.java
 │   ├── utils/
 │   │   ├── AlgoBestConnexionTest.java
 │   │   └── CalculateurCoutTest.java
 │   └── MainTest.java
 └── README.md

```

## Presentation

Ce projet modele un reseau electrique simplifie compose de generateurs, de maisons et de connexions.
L utilisateur peut construire un reseau, l importer depuis un fichier texte, puis calculer et optimiser son cout.

--- Réseau électrique ---
Générateurs : [G1 (60 kW max), G2 (60 kW max)]
Maisons : [M1 (40 kW), M2 (40 kW), M3 (20 kW)]
Connexions :
  M1 ↔ G1
  M2 ↔ G2
  M3 ↔ G1

Disp(S)=0.167, Surcharge(S)=0.000, Cout(S)=0.167
```
## Documentation

La documentation complète du projet (Javadoc) est trouvable en ouvrant le fichier doc/index.html


## Auteurs

- **Rayan Defoor**
- **Ronan Lallouet**
- **Kévin Chen**




