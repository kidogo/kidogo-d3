package kidogod3.core.clientchannel.client;

import java.io.Serializable;
import java.util.Date;

public class ChannelToken implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5777371279099431248L;
	
	private String clientId;
	private String token;
	private Date expiery;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getExpiery() {
		return expiery;
	}
	public void setExpiery(Date expiery) {
		this.expiery = expiery;
	}
	
	
}