# Exchange Rate App

This is a simple Exchange Rate App written in SpringBoot + Angular using an external API from Bank of Lithuania.

## Running the application

Both the SpringBoot back-end and Angular front-end are located in this repository. Angular files are under `src/main/resources/static/`. The SpringBoot app will run on port `8080` while the Angular app on `4200`.

### Starting the back-end
Spring-boot app can be started using `mvn` or the maven wrapper `mvnw` for UNIX/MacOS systems and `mvnw.cmd` for Windows. Executing `mvn spring-boot:run` should start the application

### Starting the front-end
The front-end can be started in two ways:
1. Install dependencies `npm install` as a pre-requisite.  
2. First way: run `ng serve`.
3. Alternative way: run `npm run build`, then `npm start`.  
Additional information can also be found in [README.md](src/main/resources/static/exchange-app-fe/README.md) under front-end related files.
