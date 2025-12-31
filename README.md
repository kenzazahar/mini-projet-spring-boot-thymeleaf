# Application de Gestion Scolaire

## Technologies Utilisées

- Backend: Spring Boot 4.0.0
- ORM: Spring Data JPA / Hibernate
- Base de données: H2 (en mémoire)
- Frontend: Thymeleaf + Bootstrap 5 + Font Awesome
- Build: Maven
- Langage: Java 17
- Lombok: Pour réduire le code boilerplate

## Comment Lancer l'Application

### Prérequis
- JDK 17 ou supérieur
- Maven 3.6+

### Étapes

1. Cloner le projet
```bash
cd school
```

2. Compiler
```bash
mvn clean install
```

3. Lancer
```bash
mvn spring-boot:run
```

4. Accéder à l'application
- Application : http://localhost:8080
- Console H2 : http://localhost:8080/h2-console
    - JDBC URL: jdbc:h2:mem:schooldb
    - Username: sa
    - Password: (vide)

## Fonctionnalités

- CRUD complet pour Filières, Cours et Élèves
- Génération automatique des dossiers administratifs
- Numéro d'inscription au format: FILIERE-ANNEE-ID
- Inscription des élèves aux cours
- Interface moderne et responsive

## Structure du Projet
```
src/main/java/com/example/school/
├── entities/           # Entités JPA
├── repositories/       # Repositories Spring Data
├── services/          # Couche service
└── controllers/       # Contrôleurs MVC

src/main/resources/
├── templates/         # Templates Thymeleaf
└── application.properties
```

## Relations entre Entités

- Élève - DossierAdministratif : One-to-One
- Élève - Filière : Many-to-One
- Élève - Cours : Many-to-Many
- Filière - Cours : One-to-Many