package com.luotuo.tool.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOperation {
	private String connectionURL;

	private Configuration config;

	public DBOperation(Configuration configuration) {
		
		this.config = configuration;
		connectionURL = config.url + "&user=" + config.user + "&password=" + config.password;

	}

	/**
	 * 连接数据库执行sql
	 * @param sql
	 */
	public void execute(String sql) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("[ERROR]找不到Mysql-jdbc驱动\n" + e.getMessage());
		}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(connectionURL);
		} catch (SQLException e) {
			System.out.println("[ERROR]连接到数据库失败\n" + e.getMessage());
		}

		PreparedStatement ps = null;
		try {
			
			
			ps = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, "lujiliang");
			ps.setLong(2, 1);
			ps.execute();


		} catch (Exception e) {
			System.out.println("[ERROR]数据库执行sql语句异常异常\n" + e.getMessage());

		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
			}
		}
	}
}
