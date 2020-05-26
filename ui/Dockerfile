# base image 
FROM node:13.7.0-alpine

# working directory
WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

# install and cache app dependencies
COPY package.json /app/package.json
RUN npm install
RUN npm install react-scripts@3.3.1 -g

CMD ["npm", "start"]

FROM buildkite/puppeteer:latest
WORKDIR /app
COPY package.json yarn.lock ./
RUN yarn && yarn cache clean
COPY . .