package kidogod3.core.users.client;

import java.io.Serializable;

public interface IUser extends Serializable{
	Long getUserId();
	String getName();
	String getEmail();
	boolean isVerfied();
}