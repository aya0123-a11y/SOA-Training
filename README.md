# Atelier REST n°1 – Gestion des UE et des Modules (JAX-RS / Jersey 2.27)

## Structure
```
src/main/java
├── entities/            Module (+ equals/hashCode), UniteEnseignement
├── metiers/             ModuleBusiness, UniteEnseignementBusiness
└── rest/
    ├── utilities/RestActivator.java          @ApplicationPath("rest")
    └── resources/
        ├── UniteEnseignementResource.java    @Path("UE")
        └── ModuleResource.java               @Path("modules")
src/main/webapp/WEB-INF/web.xml
```

## Déploiement (IntelliJ)
1. Ouvrir le projet (Maven), puis *Reload Maven project*.
2. Run > Edit Configurations > **Tomcat Server – Local** (Tomcat **9.x** : Jersey 2 utilise `javax.*`,
   il ne fonctionne PAS sur Tomcat 10/11 qui utilisent `jakarta.*`).
3. Onglet *Deployment* : ajouter l'artefact `Gestion_UE_VF:war exploded`,
   *Application context* = `/Gestion_UE`.
4. Lancer, puis tester avec Postman (importer `Gestion_UE.postman_collection.json`).

URL de base : `http://localhost:8080/Gestion_UE/rest`

## Ressource UniteEnseignement  (`/rest/UE`)
| # | Opération | Méthode | URI | Corps | Réponses |
|---|-----------|---------|-----|-------|----------|
| 1 | Ajouter une UE | POST | `/UE` | JSON UE | 201, 409 (code existant) |
| 2 | Lister toutes les UE | GET | `/UE` | – | 200 |
| 3 | UE d'un semestre | GET | `/UE/semestre/{semestre}` | – | 200, 404 |
| 4 | Supprimer une UE | DELETE | `/UE/{code}` | – | 200, 404 |
| 5 | Modifier une UE | PUT | `/UE/{code}` | JSON UE | 200, 404 |
| 6 | UE par code | GET | `/UE/{code}` | – | 200, 404 |

## Ressource Module  (`/rest/modules`)
| # | Opération | Méthode | URI | Corps | Réponses |
|---|-----------|---------|-----|-------|----------|
| 1 | Ajouter un module | POST | `/modules` | JSON Module | 201, 404 (UE inexistante), 409 |
| 2 | Lister tous les modules | GET | `/modules` | – | 200 |
| 3 | Module par matricule | GET | `/modules/{matricule}` | – | 200, 404 |
| 4 | Supprimer un module | DELETE | `/modules/{matricule}` | – | 200, 404 |
| 5 | Modifier un module | PUT | `/modules/{matricule}` | JSON Module | 200, 404 |
| 6 | Modules d'une UE | GET | `/modules/ue/{codeUE}` | – | 200, 404 |

### Exemples de corps JSON
UE :
```json
{ "code": 6, "domaine": "Réseaux", "responsable": "M. Ali Ben Salah", "credits": 4, "semestre": 2 }
```
Module (l'UE est référencée par son code, elle doit exister) :
```json
{ "matricule": "M301", "nom": "Réseaux", "coefficient": 2, "volumeHoraire": 21,
  "type": "RECHERCHE", "uniteEnseignement": { "code": 1 } }
```
`type` ∈ `TRANSVERSAL`, `PROFESSIONNEL`, `RECHERCHE`.

## Remarques
- Les listes des classes métier sont maintenant initialisées dans un bloc `static` : avant, chaque
  `new ...Business()` réinitialisait les données, donc un ajout disparaissait à la requête suivante.
- `jersey-media-json-jackson` est aligné sur la version 2.27 comme les autres modules Jersey.
