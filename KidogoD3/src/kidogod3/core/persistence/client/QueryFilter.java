package kidogod3.core.persistence.client;

import java.io.Serializable;

public class QueryFilter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4815840510941200157L;
	private String cursor;
	private int limit;

	
	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
}
