# ChatBot

## About

The aim of the project is to provide a learning Chat bot which is capable of conversing with people about a given subject.

## Requirements

- [node.js and npm](https://nodejs.org/)
- [Java SDK](https://www.oracle.com/downloads/index.html)

### Optional

- [AngularJS command line interface](https://github.com/angular/angular-cli)
- [Gradle](https://gradle.org/)

## Project file structure

- Exactly the same as Spring-boot or AngularJS, except the files for AngularJS are located in `src/webapp` to avoid confusion
- Folder `src/webapp/` contains the sources AngularJS app
- Folder `src/main/` contains the sources for the Spring-boot app
- Folder `src/test/` contains the sources for the Spring-boot app JUnit testing

- The compiled AngularJS 2 javascript files are put into the folder `src/main/resources/static/`. Once the Spring-boot server is launched they are available at `http://localhost:8080`.

## Commands

### Launching

- Run `./gradlew` in root directory to do all the install tasks and compiling
- Run  `ng build` in root directory to build the angular app
- Run `./gradlew build` in root directory to build the spring-boot application. Make sure to have done an `ng build` before or you won't have a front end
- Change the working directory to `build/libs/` and run `java -jar project-0.0.1-SNAPSHOT.jar` to launch the server
- Once the project is launched, go to `http://localhost:8080` in your web browser and test the app

#### OR

- Run `./start.sh` to start the server
- Run `./stop.sh` to stop the server
- While the server is running, the process id is stored inside the file pid.txt - DO NOT DELETE IT

### Production

- To build the app for production mode, run `./gradlew build -Pprod=true` and make sure to have edited the environment.prod.ts configuration file in the angular2 app

### CORS error with chrome
- Make a shortcut to chrome web browser on the desktop, edit the properties of the shortcut and under "target" add the following `--disable-web-security --user-data-dir="c:/chromeUserData"`

### Testing

- Run `./gradlew test` to run the Spring-boot JUnit tests
- Run `ng test` to run Angularjs 2 tests

### Live Reload

- To use the app with live reload you need to run it using the CORS shortcut described above for chrome and change `<base href="/public/">` to `<base href="/">` in order for the app to work.
- Run `ng serve --proxy-config proxy.conf.json` in root directory. To change the port (default : 8080), edit the proxy.conf.json file.


## User authentication

The application uses Cross Site Request Forgery protection using csrf tokens for authentication.

- Users can obtain a token by doing a Http GET request on `/api/token` with BASIC authentication
- The token must then be placed inside a header called X-XSRF-TOKEN
- As long as it is valid the user is logged in
- If the user calls a resource that requires authentication and does not possess the X-XSRF-TOKEN header, the user session is destroyed
