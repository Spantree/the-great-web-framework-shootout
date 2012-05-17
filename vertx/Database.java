import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.json.impl.Json;
import org.vertx.java.deploy.Verticle;
import java.sql.*;
import java.util.Map;

public class Database extends Verticle {

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    Connection connection;
    Statement statement;

    @Override
    public void start() throws Exception {
        connection = DriverManager.getConnection("jdbc:sqlite:hello.db");
        statement = connection.createStatement();

        EventBus eb = vertx.eventBus();

        Handler<Message> fetchHandler = new Handler<Message>() {
            public void handle(Message message) {
                try {
                    ResultSet result = null;
                    result = statement.executeQuery("select * from hello");
                    
                    JsonArray array = new JsonArray();
                    JsonObject json = new JsonObject();
                    while(result.next())
                    {
                        JsonObject entry = new JsonObject();
                        entry.putString("id", result.getString("id"));
                        entry.putString("data", result.getString("data"));
                        array.addObject(entry);
                    }
                    
                    json.putArray("hellos", array);
                    message.reply(json.toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        eb.registerHandler("db.fetch", fetchHandler);
        
    }
    
/*public void handle(Message message) {
        try {
            ResultSet result = null;
            result = statement.executeQuery("select * from hello");
            
            JsonArray array = new JsonArray();
            while(result.next())
            {
                JsonObject entry = new JsonObject();
                entry.putString("id", result.getString("id"));
                entry.putString("hello", result.getString("data"));
                array.addObject(entry);
            }
            System.out.println(array.toString());
            message.reply(array.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    
    public void stop() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
