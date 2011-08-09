package kidogod3.core.persistence.client;

import java.io.Serializable;
import java.util.List;

public interface QueryResult<T> extends Serializable{
	List<T> getList();
	String getNextCursor();
}
