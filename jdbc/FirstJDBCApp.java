package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FirstJDBCApp {

	public static void main(String[] args) {
		
//Step-1 Load the JDBC Driver (Oracle)
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
//Step-2 Establish connection using Connection 
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","kunal123");
			if(con!=null)
			{
				System.out.println("Connected to Oracle DB");
			}
			else
			{
				System.out.println("Something went wrong..!!");
			}
//Step-3 Execute SqL statements
			Statement st = con.createStatement();
//Step-4 write a Sql queries
			String createtable = "create table JdbcTable(id number(5),name varchar(20))";
			st.execute(createtable);
			String insert = "insert into jdbctable values(333,'Ramone')";
			int count=st.executeUpdate(insert);
						System.out.println("Record Inserted: "+count);
			String update = "update jdbctable set name='muhammed' where id=333";
				int updatecount = st.executeUpdate(update);
				System.out.println("Record Updated.."+updatecount);
		//view the records from tables
			ResultSet rs = st.executeQuery("select * from jdbctable");
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+"  "+rs.getString(2));
				System.out.println("-----------------------");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}