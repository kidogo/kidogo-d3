package kidogod3.core.persistence.client;

import java.util.ArrayList;
import java.util.List;

public class QueryResultImpl<T> implements QueryResult<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2974103636556281298L;
	private List<T> list;
	private String cursor;
	
	public QueryResultImpl() {
	}
	
	@Override
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = new ArrayList<T>(list);
	}

	@Override
	public String getNextCursor() {
		return cursor;
	}
	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

}
