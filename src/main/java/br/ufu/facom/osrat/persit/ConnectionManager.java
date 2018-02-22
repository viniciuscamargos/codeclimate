package br.ufu.facom.osrat.persit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import br.ufu.facom.osrat.exception.PersistenceException;

/**
 * Componente responsável por abrir e encerrar a conexão com o banco de dados.
 *
 */
public final class ConnectionManager {

	// Informac�es para conex�o com banco de dados HSQLDB.
	private static final String STR_DRIVER = "org.hsqldb.jdbcDriver";
	private static final String DATABASE = "osrat";
	private static final String STR_CON = "jdbc:hsqldb:file:" + DATABASE;
	private static final String USER = "sa";
	private static final String PASSWORD = "";

	private static Logger log = Logger.getLogger(ConnectionManager.class);

	private ConnectionManager() { }

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(STR_DRIVER);
			conn = DriverManager.getConnection(STR_CON, USER, PASSWORD);
			conn.setAutoCommit(false);

			log.debug("Open the connection with database!");
			return conn;
		} catch (ClassNotFoundException e) {
			String errorMsg = "Driver (JDBC) not found";
			log.error(errorMsg, e);
			throw new PersistenceException(errorMsg, e);
		} catch (SQLException e) {
			String errorMsg = "Error getting connection";
			log.error(errorMsg, e);
			throw new PersistenceException(errorMsg, e);
		}
	}

	public static void closeAll(final Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				log.debug("Closed the connection to the database!");
			}
		} catch (Exception e) {
			log.error("Could not close connection to the database!", e);
		}
	}

	public static void closeAll(final Connection conn, final Statement stmt) {
		try {
			if (conn != null) {
				closeAll(conn);
			}
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
			log.error("Could not close the statement!", e);
		}
	}

	public static void closeAll(final Connection conn, final Statement stmt, final ResultSet rs) {
		try {
			if (conn != null || stmt != null) {
				closeAll(conn, stmt);
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			log.error("Could not close the resultSet!", e);
		}
	}

}
