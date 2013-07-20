package tlc.mongodb.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class SocialAccount {
	
	@Id
	private ObjectId _id;
	private SocialType socialType;

	public SocialAccount(){}
	
	public SocialAccount(SocialType socialType) {
		this.socialType = socialType;
	}
	
	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public SocialType getSocialType() {
		return socialType;
	}

	public void setSocialType(SocialType socialType) {
		this.socialType = socialType;
	}

}
