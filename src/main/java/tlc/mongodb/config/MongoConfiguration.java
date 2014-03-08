package tlc.mongodb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories
@ComponentScan(basePackages = {"tlc.mongodb"})
@PropertySource("classpath:application.properties")
public class MongoConfiguration extends AbstractMongoConfiguration {

	@Autowired
    Environment env;
	
	@Override
	protected String getDatabaseName() {
		return env.getProperty("mongo.database");
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient(env.getProperty("mongo.host"), Integer.parseInt(env.getProperty("mongo.port")));
	}

}
