package gov.dane.indices.sqlite.manager;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseCreater<T> extends AbstractDatabaseHandler<T> {

	public DatabaseCreater(Class<T> type,
			DatabaseConnecter databaseConnecter) {
		super(type, databaseConnecter);
	}

	@Override
	protected String createQuery() {

		String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
		
		StringBuilder sb = new StringBuilder();

		sb.append("CREATE TABLE IF NOT EXISTS ");
		sb.append(type.getSimpleName().replaceAll(regex, replacement).toUpperCase());
	
		return sb.toString();
	}

	public void createDatabase() throws SQLException,
			SecurityException, IllegalArgumentException,
			InstantiationException, IllegalAccessException,
			IntrospectionException, InvocationTargetException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = databaseConnecter.createConnection();
			preparedStatement = connection.prepareStatement(query);
		} finally {
			DatabaseResourceCloser.close(preparedStatement);
			DatabaseResourceCloser.close(connection);
		}
	}
}
