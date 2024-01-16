FROM node:18-alpine
WORKDIR .

RUN apk add xsel

COPY public public
COPY src src
COPY package.json package.json
COPY tsconfig.json tsconfig.json

RUN ["npm", "install", "-g", "serve"]
RUN npm install
RUN npm run build

ENTRYPOINT ["serve", "-s", "build"]