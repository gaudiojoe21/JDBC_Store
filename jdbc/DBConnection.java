package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static Connection CreateConnection() {
		Connection con=null;
		String driver="com.mysql.cj.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/datatest";
		String user = "root";
		String pwd = "asdf";
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pwd);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
}
