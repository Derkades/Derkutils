package xyz.derkades.derkutils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang.Validate;

@Deprecated
public class DatabaseHandler {

	private final boolean debug;
	private final Connection databaseConnection;

	public DatabaseHandler(final String host, final int port, final String database, final String user, final String password, final boolean debug) throws SQLException {
		Validate.notNull(host, "Host is null");
		Validate.isTrue(port > 0 && port < 65535, "Port out of range");
		Validate.notNull(database, "Database name is null");
		Validate.notNull(user, "Username is null");
		Validate.notNull(password, "Password is null");
		
		this.debug = debug;

		final Properties properties = new Properties();
		properties.setProperty("user", user);
		properties.setProperty("password", password);
		properties.setProperty("useSSL", "false");
		properties.setProperty("autoReconnect", "true");

		this.databaseConnection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, properties);
	}

	public DatabaseHandler(final String host, final int port, final String database, final String user, final String password) throws SQLException {
		this(host, port, database, user, password, false);
	}

	public PreparedStatement prepareStatement(final String sql, final Object... parameters) throws SQLException {
		Validate.notNull(sql, "Query is null");
		Validate.notNull(parameters, "Parameters varargs is null");
		
		if (this.debug) {
			System.out.println("Making query: " + sql);
			for (final Object parameter : parameters) {
				System.out.println("with parameter: " + parameter);
			}
		}

		final PreparedStatement statement = this.databaseConnection.prepareStatement(sql);

		int i = 1;
		for (final Object parameter : parameters) {
			if (parameter instanceof String) {
				statement.setString(i, (String) parameter);
			} else if (parameter instanceof Integer) {
				statement.setInt(i, (int) parameter);
			} else if (parameter instanceof Double) {
				statement.setDouble(i, (double) parameter);
			} else {
				statement.setString(i, parameter.toString());
			}

			i++;
		}

		return statement;
	}
	
	public void createTableIfNonexistent(final String table, final String sql) throws SQLException {
		Validate.notNull(table, "Table is null");
		Validate.notNull(sql, "Query is null");
		
		final DatabaseMetaData meta = this.getConnection().getMetaData();
		final ResultSet result = meta.getTables(null, null, table, null);

		if (result != null && result.next()) {
			return; // Table exists
		}

		result.close();

		final PreparedStatement statement = this.prepareStatement(sql);
		statement.execute();
		statement.close();
	}

	public Connection getConnection() {
		return this.databaseConnection;
	}

}
