# Java Banking App - Copilot Workspace Instructions

## Project Overview

This is a **Personal Finance Tracker** Java application with planned API integration capabilities. The project aims to build a tool that interacts with real-world financial data through external APIs.

**Key Goals:**
- Currency exchange API integration (Fixer.io/Open Exchange Rates)
- JWT authentication for secure user login
- Complex JPA queries for expense analysis
- Spring Boot + Hibernate/JPA architecture

## Development Environment

### Prerequisites
- **Java 23** (configured via Gradle toolchain)
- **Gradle 9.4.0** (wrapper included)
- **JavaFX 25** (for GUI components)

### Build Commands
```bash
# Primary Gradle commands
./gradlew build          # Compile and package
./gradlew test           # Run TestNG tests
./gradlew run            # Execute application
./gradlew clean          # Clean build artifacts

# Development workflow
./gradlew compileJava    # Compile main sources only
./gradlew compileTestJava # Compile test sources only
```

### Project Structure
```
app/
├── src/main/java/com/github/wningram/financeapp/
│   ├── App.java                    # Main application class
│   └── model/                      # Domain entities (planned)
├── src/test/java/org/example/       # TestNG tests
└── build.gradle.kts                # Subproject configuration
```

## Architecture & Conventions

### Package Naming
- **Base package:** `com.github.wningram.financeapp`
- **Models:** `com.github.wningram.financeapp.model`
- **Tests:** Currently `org.example.*` (consider aligning with main packages)

### Code Patterns

#### Main Class Structure
```java
package com.github.wningram.financeapp;

public class App {
    public static void main(String[] args) {
        // Application entry point
    }
}
```

#### TestNG Test Structure
```java
package org.example;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class AppTest {
    @Test
    public void testSomething() {
        // Test implementation
    }
}
```

### Dependencies Management
- **Version catalog:** `gradle/libs.versions.toml`
- **Key dependencies:**
  - `libs.guava` - Google Guava utilities
  - `libs.testng` - Testing framework
  - `org.openjfx.javafxplugin` - JavaFX integration

## Common Issues & Solutions

### 🔴 Critical Issues

#### Main Class Configuration Error
**Problem:** `build.gradle.kts` sets `mainClass = "com.github.wningram.financeapp"` (package name)
**Solution:** Change to `mainClass = "com.github.wningram.financeapp.App"`

#### Architecture Decision Needed
**Problem:** App initializes Swing GUI but README describes REST API project
**Solution:** Choose between:
- Swing/JavaFX desktop app
- Spring Boot web application
- Hybrid approach

### 🟡 Development Pitfalls

#### Java Version Compatibility
- Java 23 may cause issues with Spring/Hibernate libraries
- Consider Java 21 LTS for better ecosystem compatibility

#### Test Package Mismatch
- Main code: `com.github.wningram.financeapp.*`
- Tests: `org.example.*`
- **Recommendation:** Move tests to `com.github.wningram.financeapp.*`

#### Dual Build Systems
- Gradle is primary, Makefile is legacy
- **Use Gradle exclusively** for consistency

### 🟢 Best Practices

#### Model Layer Implementation
```java
// Planned domain entities
public class Account { /* ... */ }
public class Transaction { /* ... */ }
public class User { /* ... */ }
```

#### API Integration Pattern
```java
// Planned external API consumption
// - RestTemplate or WebClient for HTTP calls
// - Currency conversion services
// - JWT token handling
```

## Validation & Testing

### Automated Validation
After any code changes, run:
```bash
./gradlew test           # Run all tests
./gradlew build          # Full build validation
./gradlew check          # Code quality checks
```

### Manual Testing
- GUI functionality (if Swing/JavaFX)
- API endpoints (when implemented)
- Database operations (when JPA added)

## Next Development Steps

1. **Fix main class reference** in `app/build.gradle.kts`
2. **Implement core domain models** in `model/` package
3. **Choose architecture path:** Desktop GUI vs. Web API
4. **Add Spring Boot dependencies** for web functionality
5. **Implement JWT authentication** framework
6. **Integrate currency exchange API**

## Code Generation Guidelines

When generating code:
- Follow Java naming conventions
- Use meaningful variable/method names
- Include proper JavaDoc comments
- Handle exceptions appropriately
- Write corresponding TestNG tests
- Align with planned Spring Boot architecture

## File Organization

- **Keep related classes together** in appropriate packages
- **Separate concerns:** Models, Services, Controllers, Utils
- **Test files mirror source structure** (when package alignment is fixed)
- **Resources in `src/main/resources`** for configuration files</content>
<parameter name="filePath">c:\Users\wning\src\wningram2\java-banking-app\.github\copilot-instructions.md