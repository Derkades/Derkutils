package xyz.derkades.derkutils;

import com.google.common.base.Preconditions;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.*;
import java.util.Objects;

public class DatabaseUtils {

	public static void createTableIfNonexistent(final @NonNull Connection connection,
												final @NonNull String tableName,
												final @NonNull String sql) throws SQLException {
		Objects.requireNonNull(connection, "Connection is null");
		Preconditions.checkArgument(!connection.isClosed(), "Connection is closed");
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
