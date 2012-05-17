
container.with {

    // Start the db server.
    deployVerticle('Database');
    
    // Start the web server, with the config we defined above
    deployVerticle('WebServer.groovy');
}
