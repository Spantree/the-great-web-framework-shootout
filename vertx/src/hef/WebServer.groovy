package hef
import groovy.json.JsonSlurper
import groovy.text.GStringTemplateEngine
import org.vertx.groovy.core.http.RouteMatcher

def server = vertx.createHttpServer()
def routeMatcher = new RouteMatcher()
def gste = new GStringTemplateEngine()

def eb = vertx.eventBus
def slurper = new JsonSlurper()

routeMatcher.get("/") { req ->
    req.response.end "Hello, World"
 
}

routeMatcher.get("/template") { req ->
    def f = new File("webroot/template.gsp")
    def binding = [message: '''
    Lorem ipsum dolor sit amet, consecteteur adipiscing elit nisi ultricies.
    Condimentum vel, at augue nibh sed. Diam praesent metus ut eros, sem
    penatibus. Pellentesque. Fusce odio posuere litora non integer habitant
    proin. Metus accumsan nibh facilisis nostra lobortis cum diam tellus.
    Malesuada nostra a volutpat pede primis congue nisl feugiat in fermentum.
    Orci in hymenaeos. Eni tempus mi mollis lacinia orci interdum lacus.
    Sollicitudin aliquet, etiam. Ac. Mi, nullam ligula, tristique penatibus
    nisi eros nisl pede pharetra congue, aptent nulla, rhoncus tellus morbi,
    ornare. Magna condimentum erat turpis. Fusce arcu ve suscipit nisi phasellus
    rutrum a dictumst leo, laoreet dui, ultricies platea. Porta venenatis
    fringilla vestibulum arcu etiam condimentum non.
    ''']
    template = gste.createTemplate(f).make(binding) 
    req.response.end template.toString()
 
}

routeMatcher.get("/sql") { req ->
    def f = new File("webroot/sql.gsp")
    eb.send("db.fetch", null) { message ->
        payload = slurper.parseText(message.body)
        template = gste.createTemplate(f).make(payload)
        req.response.end template.toString()
    }
 
}

server.requestHandler(routeMatcher.asClosure()).listen(8080, "localhost");
