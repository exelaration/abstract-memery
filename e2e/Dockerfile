FROM buildkite/puppeteer:latest
# COPY package.json package-lock.json ./
# ENV PATH="${PATH}:/node_modules/.bin"
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json /app/package.json
RUN npm install
RUN npm install --save-dev jest
RUN npm i jest-puppeteer
# RUN npm install