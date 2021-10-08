package xyz.derkades.derkutils;

import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Objects;

public class DatabaseUtils {

	public static void createTableIfNonexistent(@NotNull final Connection connection, @NotNull final String tableName,
												@NotNull final String sql) throws SQLException {
		Objects.requireNonNull(connection, "Connection is null");
		Validate.isTrue(!connection.isClosed(), "Connection is closed");
		Objects.requireNonNull(tableName, "Table name is null");
		Objects.requireNonNull(sql, "Query is null");

		final DatabaseMetaData meta = connection.getMetaData();
		final ResultSet result = meta.getTables(null, null, tableName, null);

		if (result != null) {
			if (result.next()) {
				return; // Table exists
			}
	
			result.close();
		}
		
		try (final PreparedStatement statement = connection.prepareStatement(sql)){
			statement.execute();
		}
	}

}
