package me.cbitler.raidbot.database;

import me.cbitler.raidbot.utility.Variables;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

import static me.cbitler.raidbot.utility.Variables.RaidBotProperty.DATABASE;
import static me.cbitler.raidbot.utility.Variables.RaidBotProperty.TEST_DATABASE;
import static me.cbitler.raidbot.utility.Variables.RaidBotProperty.VERSION_DATABASE;

/**
 * Class for managing the SQLite database for this bot
 *
 * @author Christopher Bitler
 */
public class Database implements DatabaseOpenHelper {
	private static Database INSTANCE;
	private static Connection connection;

	@Override
	public void onCreate() {
		try {
			tableInits();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade() {
		try {
			tableUpgrade();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a new database with the specific filename provided by the configuration.properties
	 */
	private Database() {
		try {
			String url = "jdbc:sqlite:" + Variables.getINSTANCE().getStringProperty(DATABASE.toString());
			connection = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println("Database connection error");
			System.exit(1);
		}
	}

	/**
	 * Connect to the SQLite database and create the tables if they don't exist
	 */
	public static void connect() {
		if (INSTANCE == null) {
			INSTANCE = new Database();
		}
		INSTANCE.onCreate();
		INSTANCE.onUpgrade();
	}

	public static Database getDatabase(){
		if (INSTANCE != null){
			return INSTANCE;
		}else {
			throw new NullPointerException("Database not initialized, please call connect() before trying to send request");
		}
	}

	/**
	 * Run a query and return the results using the specified query and parameters
	 *
	 * @param query The query with ?s where the parameters need to be placed
	 * @param data  The parameters to put in the query
	 * @return QueryResult representing the statement used and the ResultSet
	 * @throws SQLException
	 */
	public static QueryResult query(String query, String[] data) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(query);
		int i = 1;
		for (String input : data) {
			stmt.setObject(i, input);
			i++;
		}

		ResultSet rs = stmt.executeQuery();

		return new QueryResult(stmt, rs);
	}

	/**
	 * Run an update query with the specified parameters
	 *
	 * @param query The query with ?s where the parameters need to be placed
	 * @param data  The parameters to put in the query
	 * @throws SQLException
	 */
	public void update(String query, String[] data) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(query);
		int i = 1;
		for (String input : data) {
			stmt.setObject(i, input);
			i++;
		}

		stmt.execute();
		stmt.close();
	}

	/**
	 * Create the database tables.
	 *
	 * @throws SQLException
	 */
	private void tableInits() throws SQLException {
		try {
			connection.setAutoCommit(false);
			StringBuilder stringBuilder = new StringBuilder();
			Files.lines(Paths.get(Database.class.getClassLoader().getResource("database-1.sql").toURI()), StandardCharsets.UTF_8)
					.forEach(stringBuilder::append);

			String[] inst = stringBuilder.toString().split(";");

			for (String s : inst){
				connection.createStatement().execute(s);
			}

			connection.commit();
		} catch (IOException | SQLException | URISyntaxException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			connection.setAutoCommit(true);
		}
	}

	/**
	 * Alters tables to add columns if it doesn't exist.
	 * @throws SQLException
	 */
	private void tableUpgrade() throws SQLException {
		int databaseVersion = Integer.valueOf(Variables.getINSTANCE().getStringProperty(VERSION_DATABASE.toString()));
		StringBuilder stringBuilder = new StringBuilder();
		String prefixFilename = "database-";
		String suffixFilename = ".sql";

		synchronized (connection) {
			try {
				connection.setAutoCommit(false);

				for (int i = 2; i <= databaseVersion; i++) {
					Files.lines(Paths.get(Database.class.getClassLoader().getResource(prefixFilename + String.valueOf(i) + suffixFilename).toURI()))
							.forEach(stringBuilder::append);

					// here is our splitter ! We use ";" as a delimiter for each request
					// then we are sure to have well formed statements
					String[] inst = stringBuilder.toString().split(";");

					for (String s : inst){
						connection.createStatement().execute(s);
					}

					stringBuilder = new StringBuilder();
				}

				connection.commit();

			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			} finally {
				connection.setAutoCommit(true);
			}
		}
	}
}

