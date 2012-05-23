

import groovy.json.JsonBuilder

def eb = vertx.eventBus
def sql = groovy.sql.Sql.newInstance("jdbc:sqlite:hello.db", "org.sqlite.JDBC")

eb.registerHandler("db.fetch") { message ->
	result = ["hellos": sql.rows("select id, data from hello;").collect()]
	def json = new JsonBuilder(result)
	message.reply(json.toString())
}