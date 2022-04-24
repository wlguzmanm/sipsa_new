package gov.dane.indices.sqlite.manager;

import java.lang.reflect.Field;

import co.gov.dane.util.Util;

/**
 * An abstract class that handles insert/select-operations into/from a database
 * 
 * @author Tino for http://www.java-blog.com
 * 
 * @param <T>
 */
public abstract class AbstractDatabaseHandler<T> {
	/**
	 * The type of the objects that should be created and filled with values
	 * from the database or inserted into the database
	 */
	protected Class<T>     type;
	/**
	 * Contains the settings to create a connection to the database like
	 * host/port/database/user/password
	 */
	protected DatabaseConnecter     databaseConnecter;
	/** The SQL-select-query */
	protected final String     query;
	/**
	 * Constructor
	 * 
	 * @param type
	 *            The type of the objects that should be created and filled with
	 *            values from the database or inserted into the database
	 * @param databaseConnecter
	 *            Contains the settings to create a connection to the database
	 *            like host/port/database/user/password
	 */
	protected AbstractDatabaseHandler(Class<T> type,DatabaseConnecter databaseConnecter) {
		this.databaseConnecter = databaseConnecter;
		this.type = type;
		this.query = createQuery();
	}
	/**
	 * Create the SQL-String to insert into / select from the database
	 * 
	 * @return the SQL-String
	 */
	protected abstract String createQuery();
	/**
	 * 
	 * Creates a comma-separated-String with the names of the variables in this
	 * class
	 * 
	 * @param usePlaceHolders
	 *            true, if PreparedStatement-placeholders ('?') should be used
	 *            instead of the names of the variables
	 * @return
	 */
	protected String getColumns(boolean usePlaceHolders) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		/* Iterate the column-names */
		for (Field f : type.getDeclaredFields()) {
			if (first)
				first = false;
			else
				sb.append(", ");

			if (usePlaceHolders){
				sb.append("?");
			}else{
				if( f.getType().isAssignableFrom(Long.class) && f.getName().equals("id")){
					sb.append("_id");
				}else{
					sb.append(Util.convertirCamelCaseToUnderScore(f.getName()));
				}
				
			}
		}
		return sb.toString();
	}
}
