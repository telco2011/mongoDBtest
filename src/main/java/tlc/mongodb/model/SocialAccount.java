package tlc.mongodb.model;


public class SocialAccount {
	
	private SocialType socialType;

	public SocialAccount(){}
	
	public SocialAccount(SocialType socialType) {
		this.socialType = socialType;
	}

	public SocialType getSocialType() {
		return socialType;
	}

	public void setSocialType(SocialType socialType) {
		this.socialType = socialType;
	}

}
