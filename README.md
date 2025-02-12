# Currency Converter API

A Spring Boot application that provides real-time currency conversion functionality using the Exchange Rates API.

## Features

- Fetch real-time exchange rates for a base currency
- Convert amounts between different currencies
- Error handling for invalid currencies and API unavailability
- Unit tests for service layer

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Getting Started

1. Clone the repository:
```bash
git clone [repository-url]
```

2. Navigate to the project directory:
```bash
cd currency-converter-api
```

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

### 1. Get Exchange Rates

```
GET /api/rates?base=USD
```

Parameters:
- `base` (optional): Base currency code (default: USD)

### 2. Convert Currency

```
POST /api/convert
Content-Type: application/json

{
    "from": "USD",
    "to": "EUR",
    "amount": 100
}
```

Response:
```json
{
    "from": "USD",
    "to": "EUR",
    "amount": 100,
    "convertedAmount": 85.0
}
```

## Error Handling

The API handles the following error cases:
- Invalid currency codes
- External API unavailability
- Invalid request parameters

## Testing

Run the tests using:
```bash
mvn test
```

## Technologies Used

- Spring Boot 2.7.0
- Java 11
- Maven
- JUnit 5
- Mockito
- Lombok
