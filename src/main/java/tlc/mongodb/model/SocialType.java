package tlc.mongodb.model;

public enum SocialType {
	FACEBOOK(1), GOOGLEPLUS(2), TWITTER(3);

	private int code;

	private SocialType(int c) {
		code = c;
	}

	public int getCode() {
		return code;
	}

}
