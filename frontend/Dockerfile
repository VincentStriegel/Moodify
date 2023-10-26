### STAGE 1:BUILD ###
ARG buildType
FROM node:18-alpine AS build
# Create a Virtual directory inside the docker image
WORKDIR /dist/src/app
# Copy files to virtual directory
# COPY package.json package-lock.json ./
# Run command in Virtual directory
RUN npm cache clean --force
# Copy files from local machine to virtual directory in docker image
COPY . .
RUN npm install
ARG buildType
RUN npm run ${buildType}


### STAGE 2:RUN ###
FROM nginx:latest
# Copying compiled code and nginx config to different folder
COPY --from=build /dist/src/app/dist/moodify /usr/share/nginx/html
COPY /nginx.conf  /etc/nginx/conf.d/default.conf
# the app will be using Port 80 while running
EXPOSE 80