package kidogod3.core.users.client;

public class IUserImpl implements IUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6459292399622129645L;
	
	private Long userId;
	private String name;
	private String email;
	private boolean verified;
	
	@Override
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean isVerfied() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

}
