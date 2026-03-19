## Personal Finance Tracker with API Integration (Playground Projectr)

**OBJECTIVE**

Build a tool that doesn't just store data, but interacts with the real world.

**Key Skills:** Spring Boot, Hibernate/JPA, and External API Consumption (using RestTemplate or WebClient).

&nbsp;

**General Acceptance Criteria:**

* As a developer, I need a testing suite to enable confident pushes to production
* As a user, I want to submit expense details that persist so that I can track my expenditures
* As a user, I want to be able to filter on records by any fields present to streamline data reviews
* As a user, I want to view general statistics on my spending based on active filters to better understand my spending
* As a user, I want to view the exchange rates for various currencies, in order to understand my national net worth.

**Tasks:**

* Integrate a currency exchange API (like Fixer.io or Open Exchange Rates) to convert expenses into a base currency.
* Implement JWT (JSON Web Tokens) for secure user login.
* Use Spring Data JPA to create complex queries (e.g., "Show me all grocery spending between March and April").

### Description of Learnings

Building a Personal Finance Tracker is an excellent intermediate project because it forces you to move beyond basic CRUD and deal with real-world complexities like security, external data, and complex business logic.

###### 1. The Tech Stack
Building a Personal Finance Tracker is an excellent intermediate project because it forces you to move beyond basic CRUD and deal with real-world complexities like security, external data, and complex business logic.

* **Backend**: Spring Boot 3 with Spring Security.
* **Database**: PostgreSQL or MySQL with Spring Data JPA.
* **Auth**: JWT (JSON Web Tokens) for stateless, secure user sessions.
* **External Integration**: Spring WebClient to fetch real-time exchange rates.

---

###### 2. Core Features & Architecture
The project follows a standard layered architecture to keep the code clean and maintainable.

* **Security Layer**: Implements a JwtAuthenticationFilter that intercepts every request. It checks for a valid token in the header and sets the user context in the SecurityContextHolder.
* **Service Layer**: This is where your logic lives. For example, when adding a transaction, the service might call an external API to convert the amount if it's in a different currency.
* **Persistence Layer**: Use JPA Repositories for standard operations and @Query for complex analytics, such as calculating "Total spending by category for the current month."

---

###### 3. Key Challenge: API Integration

To add a professional touch, you’ll integrate a currency exchange API like Fixer.io or ExchangeRate-API.

Instead of using the older `RestTemplate`, use WebClient (part of Spring WebFlux). It is non-blocking and provides a more modern, functional API for handling external calls.


```java

// Example WebClient call to convert currency
public Mono<Double> convertCurrency(String from, String to, Double amount) {
    return webClient.get()
        .uri("/convert?from={f}&to={t}&amount={a}", from, to, amount)
        .retrieve()
        .bodyToMono(ConversionResponse.class)
        .map(response -> response.getResult());
}

```
