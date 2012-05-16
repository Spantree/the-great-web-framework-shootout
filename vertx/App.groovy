def webServerConf = [
  port: 8080,
  host: 'localhost'
]

// Start the web server, with the config we defined above

container.deployVerticle('web-server', webServerConf);
