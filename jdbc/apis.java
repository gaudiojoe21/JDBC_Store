package jdbc;
import java.sql.*;

//Java Database Connectivity
//Each driver takes a different class name
/*
 * MS Access, Oracle, MySQL
 * */

public class apis {
	
	public static void main(String[]args) {
		//Step 1 Load the JDBC Drive(Oracle)
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");//May throw exception
		//Step 2 Establish connection using Connection
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521","system","kunal123");
			if(con!=null) {
				System.out.println("Connected to Oracle DB");
			}else {
				System.out.println("Something went Wrong!");
			}
		//Step 3 Execute SQL statement
			Statement st = con.createStatement();
		//Step 4 write SQL query
			//String createTable = "create table jdbcTable(id number(5),name varchar(20))";
			//st.execute(createTable);
			
			String insert = "insert into jdbctable values(111,'Yasmani')";
			int count = st.executeUpdate(insert);
			
			//view reocrds
			ResultSet rs = st.executeQuery("select * from jdbctable");
			while(rs.next()) {
				System.out.println(rs.getInt(1)+" "+rs.getString(2));
				System.out.println("------------");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
