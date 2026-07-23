# Clothing Capsule Wardrobe System

A Java desktop application that helps users organize their wardrobe, generate personalized outfit recommendations, and save outfits based on their clothing preferences. The application combines object-oriented design, design patterns, Java Swing, JDBC, and MySQL to provide a complete wardrobe management experience.

---
## Features

- User registration and login
- Manage a personal digital wardrobe
- Add, view, and remove clothing items
- Generate outfit recommendations based on preferred clothing style
- Support for multiple recommendation strategies:
    - Casual
    - Formal
    - Sporty
    - Streetwear
    - Business Casual
- Color compatibility and outfit scoring
- Save recommended outfits
- View previously saved outfits
- Persistent data storage using MySQL
- Desktop graphical user interface built with Java Swing

---
## Technologies Used
- Java
- Java Swing
- JDBC
- MySQL
- Object-Oriented Programming (OOP)
- Strategy Design Pattern
- Repository Design Pattern
- Layered Architecture

---
## Prerequisites
Install the following before running the application.
- Java JDK 17 or newer
- MySQL Workbench
- MySQL Connector/J (JDBC Driver)
- IntelliJ IDEA (recommended)


---
## Installation

### 1. Clone the Repository

```
git clone https://github.com/Lovfy85/javaccws.git ClothingCapsuleWardrobe
```

## 2. Database Setup

Open MySQL Workbench.
Run the SQL script included with the project to create the database and required tables.
Example:

```
database/schema.sql
```

## 3. Configure Database Connection

Open the database connection class.
Update the following values with your own MySQL credentials.

```
private static final String URL =
        "jdbc:mysql://localhost:3306/clothing_capsule";

private static final String USER = "your_username";

private static final String PASSWORD = "your_password";
```

## 4. Add the MySQL JDBC Driver

Download **MySQL Connector/J** from the official MySQL website.
Add the JAR file to your IDE (can be seen on the project itself).

---
## Running the Application

Run:

```
app/Main.java
```

The login window will open.

---
# Using the Application
1. Register a new account.
2. Log in.
3. Add clothing items to your wardrobe.
4. Select your preferred clothing style.
5. Generate outfit recommendations.
6. Save outfits you like.
7. View saved outfits at any time.

---
## Project Structure

```
ClothingCapsuleWardrobe/
├── database
│   └── schema.sql
├── lib
│   ├── metadata-extractor-2.19.0.jar
│   ├── mysql-connector-j-9.6.0.jar
│   └── xmpcore-6.1.11.jar
├── src
│   ├── app
│   │   └── Main.java
│   ├── db
│   │   ├── DBConnection.java
│   │   └── DBConnectionTest.java
│   ├── exception
│   │   ├── AuthenticationException.java
│   │   ├── InvalidClothingException.java
│   │   └── UserAlreadyExistsException.java
│   ├── model
│   │   ├── clothing
│   │   │   ├── Bottom.java
│   │   │   ├── ClothingItem.java
│   │   │   ├── Footwear.java
│   │   │   └── Top.java
│   │   ├── ClothingStyle.java
│   │   ├── Outfit.java
│   │   ├── OutfitOptions.java
│   │   ├── StylesProfile.java
│   │   ├── User.java
│   │   └── Wardrobe.java
│   ├── repository
│   │   ├── OutfitRepository.java
│   │   ├── UserRepository.java
│   │   └── WardrobeRepository.java
│   ├── service
│   │   ├── OutfitScorer.java
│   │   ├── RecommendationEngine.java
│   │   ├── UserService.java
│   │   └── WardrobeService.java
│   ├── strategy
│   │   ├── BusinessCasualStrategy.java
│   │   ├── CasualStrategy.java
│   │   ├── DefaultStrategy.java
│   │   ├── FormalStrategy.java
│   │   ├── RecommendationStrategy.java
│   │   ├── SportyStrategy.java
│   │   └── StreetwearStrategy.java
│   ├── ui
│   │   ├── auth
│   │   │   ├── LoginPanel.java
│   │   │   └── RegisterPanel.java
│   │   ├── outfit
│   │   │   ├── CategorySelectionPanel.java
│   │   │   ├── ClothingCardPanel.java
│   │   │   ├── OutfitDisplayUI.java
│   │   │   ├── OutfitSelectionPanel.java
│   │   │   └── UserInfoPanel.java
│   │   ├── tests
│   │   │   ├── ColorMatcherTestUI.java
│   │   │   ├── OutfitRepositoryTest.java
│   │   │   ├── UserRepositoryTest.java
│   │   │   ├── WardrobeRepositoryTest.java
│   │   │   └── WardrobeTestUI.java
│   │   ├── wardrobe
│   │   │   ├── EditClothingPanel.java
│   │   │   ├── OutfitCardPanel.java
│   │   │   ├── SavedOutfitViewDialog.java
│   │   │   ├── UploadClothingPanel.java
│   │   │   └── WardrobeManagementPanel.java
│   │   └── Menu.java
│   └── util
│       ├── ColorMatcher.java
│       ├── ImageLoader.java
│       └── ImageProcessor.java
├── .gitignore
└── README.md

```

---
## Project Architecture

The application follows a **layered architecture** to separate responsibilities and improve maintainability.

```
Presentation Layer
        │
        ▼
Service Layer
        │
        ▼
Repository Layer
        │
        ▼
MySQL Database
```

### Layers

**Presentation Layer**
- Java Swing user interface
- Displays clothing items, generated outfits, and saved outfits
- Handles user interaction

**Service Layer**
- Business logic
- Outfit generation
- Outfit scoring
- Recommendation engine
- User authentication

**Repository Layer**
- Handles all database operations
- Reads and writes users, wardrobe items, and saved outfits

**Model Layer**
- Represents application data
- Users
- Wardrobes
- Clothing items
- Outfits

---
## Design Patterns

### Strategy Pattern
Used to support multiple outfit recommendation styles without modifying the recommendation engine.

Examples:
- Casual
- Formal
- Sporty
- Streetwear
- Business Casual

### Repository Pattern
Separates database access from application logic.

Repositories include:
- UserRepository
- WardrobeRepository
- OutfitRepository

---
## Author

Developed by **Cedar Ancheta** as a personal portfolio project demonstrating object-oriented programming, Java application development, desktop GUI design, database integration, software architecture, and design pattern implementation using Java.

## License
Copyright (c) 2026 Cedar Ancheta
All rights reserved.
