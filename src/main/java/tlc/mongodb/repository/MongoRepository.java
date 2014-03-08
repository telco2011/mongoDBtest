package tlc.mongodb.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import tlc.mongodb.model.User;

@Repository
public class MongoRepository {

	@Autowired
    MongoTemplate mongoTemplate;
	
	public void saveUser(User user) {
		mongoTemplate.save(user);
	}
	
	public User findUser(Query query) {
		 return mongoTemplate.findOne(query, User.class);
	}
	
	public List<User> findAllUsers() {
		return mongoTemplate.findAll(User.class);
	}
	
	public void updateUser(Query query, Update update) {
		mongoTemplate.updateFirst(query, update, User.class);
	}
	
	public void removeUser(Query query) {
		mongoTemplate.remove(query, User.class);
	}
}
