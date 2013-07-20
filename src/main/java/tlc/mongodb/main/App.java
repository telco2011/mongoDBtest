package tlc.mongodb.main;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import tlc.mongodb.model.SocialAccount;
import tlc.mongodb.model.SocialType;
import tlc.mongodb.model.User;


public class App {

	private static int counter = 1;

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		ApplicationContext ctx = new GenericXmlApplicationContext("spring-context.xml");
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoOperations");

		User user1 = new User("user1username", "user1psw", "user1nickname");
		User user2 = new User("user2username", "user2psw", "user2nickname");

		// Se guarda el usuario en la base de datos
		mongoOperation.save(user1);
		mongoOperation.save(user2);

		// El usuario introducido
		printConsole("Usuario introducido: " + user1);

		// Sentencia de búsqueda de usuario por NICKNAME
		Query searchUser1Query = new Query(Criteria.where(User.NICKNAME).is(user1.getNickname()));
		Query searchUser2Query = new Query(Criteria.where(User.NICKNAME).is(user2.getNickname()));

		// Búsqueda de usuario en la base de datos
		User savedUser = mongoOperation.findOne(searchUser1Query, User.class);
		printConsole("Usuario recuperado de base de datos: " + savedUser);

		// Actualización del nombre de usuario y contraseña de usuario 1
		mongoOperation.updateFirst(searchUser1Query, Update.update(User.USERNAME, "newuser1username"), User.class);
		mongoOperation.updateFirst(searchUser1Query, Update.update(User.PASSWORD, "newuser1psw"), User.class);

		// Búsqueda del usuario modificado en la base de datos
		User updatedUser = mongoOperation.findOne(new Query(Criteria.where(User.NICKNAME).is(user1.getNickname())), User.class);
		printConsole("Usuario modificado recuperado de base de datos: " + updatedUser);

		// Actualización de las cuentas sociales del usuario 2
		User savedUser2 = mongoOperation.findOne(searchUser2Query, User.class);
		savedUser2.addSocialAccount(new SocialAccount(SocialType.FACEBOOK));
		mongoOperation.save(savedUser2);
		savedUser2 = mongoOperation.findOne(searchUser2Query, User.class);
		printConsole("Usuario recuperado de base de datos: " + savedUser2);
		
		// Lista los usuarios de la base de datos
		printConsole("Total de usuarios en la base de datos= " + mongoOperation.findAll(User.class).size());
		printUsers(mongoOperation.findAll(User.class));
		
		// Se eliminia un usuario de la base de datos
		printConsole("Se elimina el usuario 1.");
		mongoOperation.remove(searchUser1Query, User.class);

		// Lista los usuarios de la base de datos
		printConsole("Total de usuarios en la base de datos= " + mongoOperation.findAll(User.class).size());
		printUsers(mongoOperation.findAll(User.class));
		
		mongoOperation.dropCollection(User.COLLECTION);
		printConsole("Total de usuarios en la base de datos= " + mongoOperation.findAll(User.class).size());
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