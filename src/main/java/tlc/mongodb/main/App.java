package tlc.mongodb.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import tlc.mongodb.config.MongoConfiguration;
import tlc.mongodb.model.SocialAccount;
import tlc.mongodb.model.SocialType;
import tlc.mongodb.model.User;
import tlc.mongodb.repository.MongoRepository;


public class App {

	private static final String JSON_PATH = "json/";
	private static final String JSON_EXT = ".json";
	
	private static int counter = 1;

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfiguration.class);
		MongoRepository mongoRepository = ctx.getBean(MongoRepository.class);

		User user1 = new User("user1username", "user1psw", "user1nickname");
		User user2 = new User("user2username", "user2psw", "user2nickname");

		// Save user in mongo database
		mongoRepository.saveUser(user1);
		mongoRepository.saveUser(user2);

		// The user
		printConsole("Usuario introducido: " + user1);

		// Query to search the user by NICKNAME
		Query searchUser1Query = new Query(Criteria.where(User.NICKNAME).is(user1.getNickname()));
		Query searchUser2Query = new Query(Criteria.where(User.NICKNAME).is(user2.getNickname()));

		// Searching user in the mongo database
		User savedUser = mongoRepository.findUser(searchUser1Query);
		printConsole("Usuario recuperado de base de datos: " + savedUser);

		// Update user name and password
		mongoRepository.updateUser(searchUser1Query, Update.update(User.USERNAME, "newuser1username"));
		mongoRepository.updateUser(searchUser1Query, Update.update(User.PASSWORD, "newuser1psw"));

		// Searching modified user in the mongo database
		User updatedUser = mongoRepository.findUser(new Query(Criteria.where(User.NICKNAME).is(user1.getNickname())));
		printConsole("Usuario modificado recuperado de base de datos: " + updatedUser);

		// Update user social accounts
		User savedUser2 = mongoRepository.findUser(searchUser2Query);
		savedUser2.addSocialAccount(new SocialAccount(SocialType.FACEBOOK));
		mongoRepository.saveUser(savedUser2);
		savedUser2 = mongoRepository.findUser(searchUser2Query);
		printConsole("Usuario recuperado de base de datos: " + savedUser2);
		
		// User list in the mongo database
		printConsole("Total de usuarios en la base de datos= " + mongoRepository.findAllUsers().size());
		printUsers(mongoRepository.findAllUsers());
		
		createJSONfile(mongoRepository.findAllUsers());
		
		// Remove a user in the mongo database
		printConsole("Se elimina el usuario 1.");
		mongoRepository.removeUser(searchUser1Query);

		// User list in the mongo database
		printConsole("Total de usuarios en la base de datos= " + mongoRepository.findAllUsers().size());
		printUsers(mongoRepository.findAllUsers());
		
		//mongoOperation.dropCollection(User.COLLECTION);
		printConsole("Total de usuarios en la base de datos= " + mongoRepository.findAllUsers().size());
	}

	private static void createJSONfile(List<User> users) {
		for(User u : users) {
			createJSONfile(u, u.getNickname());
		}
	}

	private static void createJSONfile(User user, String fileName) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			mapper.writeValue(new File(JSON_PATH + fileName + JSON_EXT), user);
		} catch (JsonGenerationException jge) {
			jge.printStackTrace();
		} catch (JsonMappingException jme) {
			jme.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private static void printUsers(List<User> listUser) {
		for(User us : listUser) {
			printConsole(us.toString());
		}
	}

	private static void printConsole(String message) {
		System.out.println((counter++) + ". " + message);
	}

}