
## Hva jeg har implementert
- REST API for komponentene
- Persistent database i MySQL
- Validering
- Error håndtering
- Tester

## Tekonologivalg
- Java 25
- Spring boot
- Spring data JPA
- MySQL
- Docker
- Maven

## Prosjektstruktur
### Backend
- Controller: Rest endepunkter
- Service: Programlogikken
- Repository: Databasetilgang
- Model (Component): Komponentdata
- Exceptions: Error håndtering

## Forutsetninger
- Java 25
- Maven
- Docker

## Hvordan kjøre programmet
Start databasen fra repo root mappen med:

`Docker compose up -d`

Start \Backend ved å navigere til backendmappen
og kjør:

`.\mvnw spring-boot:run`

For å starte frontend så naviger til \frontend mappen og kjør dette
`npm install`

`npm run dev`
