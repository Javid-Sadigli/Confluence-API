# Confluence API

This is a Spring Boot project designed to interact with the Confluence API provided by Atlassian. The application exposes several endpoints to fetch data from Confluence and store it in a database, as well as retrieve stored data with pagination.

## Features 
* Takes data from Confluence API (provided by Atlassian), and saves it to the database. 
* Displays data using pagination. 
* Calculates the number of lines written by the user. 

## Getting started 
Let's see how you can get started with our application. You should go through these steps : 

#### Step 1 : Clone the repository 
Firstly, you need to clone the repository by using the following commands : 
```sh
git clone https://github.com/Javid-Sadigli/Confluence-API.git
cd Confluence-API
```

#### Step 2 : Configure the Confluence API Credentials
Of course, for using the Confluence API, you need your Confluence credentials. In `application-local.properties` and `application-prod.properties` files, you need to configure it in the below code : 
```properties
# Feign Configuration 
feign.client.url=https://your-confluence.atlassian.net/wiki
feign.auth.username=your-confluence-username
feign.auth.token=your-confluence-api-token
```

#### Step 3 : Run the database
You need to have database, for running the project. And you can run it in Docker. In the main project directory you can see `docker-compose.yaml` file. Using this file, you can run the PostgreSQL database and pgadmin tool (for viewing the database), by typing the following command : 
```sh
docker-compose up --build -d 
``` 
This command will download the images from the corresponding resources, and will run container for each of the services (database, pgadmin tool).

Now, you have a PostgreSQL database and pgadmin tool for connecting and managing the database manually. For using it, you can go to the `localhost:2000` in your browser. 

#### Step 4 : Run the application 
Now, you can run the Spring Boot application using the following command : 
```bash
./gradlew build bootRun
```

Congratulations, you have successfully started the application. Now you can test the API endpoints, using API Testing tools like Postman. 

## Contributing 
Contributions are welcome! Follow these steps to contribute:
* Fork the project.
* Create a new branch: `git checkout -b feature/your-feature`.
* Make your changes.
* Submit a pull request.

## Thanks for your attention! 