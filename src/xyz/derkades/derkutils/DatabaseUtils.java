package xyz.derkades.derkutils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import org.apache.commons.lang.Validate;

public class DatabaseUtils {

	public static void createTableIfNonexistent(final Connection connection, final String tableName, final String sql) throws SQLException {
		Objects.requireNonNull(connection, "Connection is null");
		Validate.isTrue(!connection.isClosed(), "Connection is closed");
		Objects.requireNonNull(tableName, "Table name is null");
		Objects.requireNonNull(sql, "Query is null");

		final DatabaseMetaData meta = connection.getMetaData();
		final ResultSet result = meta.getTables(null, null, tableName, null);

		if (result != null && result.next()) {
			return; // Table exists
		}

		result.close();

		final PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		statement.close();
	}

}
