package hef

container.with {

    // Start the db server.
   deployVerticle('Database.groovy');
    
    // Start the web server
    deployVerticle('WebServer.groovy');
}
