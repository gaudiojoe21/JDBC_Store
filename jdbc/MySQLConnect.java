package jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnect {

	public static void main(String[] args) {
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myapp","root","admin");
		if(con!=null)
		{
			System.out.println("MySQL DB Connected..");
		}
		else
		{
			System.out.println("Not Connected..!!");
		}
		Statement st = con.createStatement();
		String createtable="create table Emp(id int(5),name varchar(15),sal float(10))";
		st.execute(createtable);
		
		
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}

}