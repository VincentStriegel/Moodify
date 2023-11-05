# Moodify

Is an upcoming Music streaming platform

## Running App using Docker-Compose

First a file called .env.dev needs be to created.

Within the file a env var called deezerApiKey needs to bet set, the value being the API key.

Example:

```
deezerApiKey=3435h34deezerNotaRealapikey4324234
```

Now to start the docker containers run the command:

```
docker compose up
```

Or right click the docker-compose.yml file in VS Code and choose compose up

Everything should be up and running, accessing the website is done with **http://localhost/**



## Development 

### Frontend

---

To run the frontend, first navigate to the frontend directory.

```
cd .\frontend\  
```

Next the packages have to installed using: 

```
npm install
```

Now to run the frontend, use the command:

```
ng serve --open
```

Opens the browser with the frontend running at: http://localhost:4200/

### Backend

---

First set an environment variable of deezerApiKey with the value being the API key.

Use Maven to install the dependencies 

Then Run the BackendApplication, if the application successfully starts the backend will be available at: http://localhost:8080/