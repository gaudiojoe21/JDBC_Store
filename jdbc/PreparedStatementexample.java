package jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PreparedStatementexample {
	
	public static void main(String[] args) {
	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/datatest","root","asdf");
			if(con!=null)
			{
				System.out.println("MySQL DB Connected..");
			}
			else
			{
				System.out.println("Not Connected..!!");
			}
			Scanner sc = new Scanner(System.in);
			System.out.println(":::User Home Page:::");
			System.out.println("1. User Login");
			System.out.println("2. User Register");
			System.out.println("Enter Your Option");
			int option=sc.nextInt();
			if(option==1)
			{
				System.out.println("Welcome to Login Page");
				  System.out.println("Enter User ID:");
				     String uid=sc.next();
				     System.out.println("Enter User PWD:");
				     String pwd=sc.next();
				     PreparedStatement ps = con.prepareStatement("select * from Reg where uid=? and pwd=?");
				     ps.setString(1, uid);
				     ps.setString(2, pwd);
				     ResultSet rs = ps.executeQuery();
				     int count=0;
				     while(rs.next())
				     {
				    	 count++;
				    	 
				     }
				     if(count>0)
				     {
				    	 System.out.println("User Login Successful\n User Id is: "+uid);
				     }
				     else
				     {
				    	 System.err.println("Invalid UID or PWD..");
				     }
			}
			else if(option==2)
			{
		     System.out.println("::::User Registration Page::::");
		     System.out.println("Enter User ID:");
		     String uid=sc.next();
		     System.out.println("Enter User PWD:");
		     String pwd=sc.next();
		     System.out.println("Enter User Email:");
		     String email=sc.next();
		     
		     PreparedStatement pst = con.prepareStatement("insert into Reg values(?,?,?)");
				pst.setString(1, uid);
				pst.setString(2, pwd);
				pst.setString(3, email);
				int count=pst.executeUpdate();
				if(count>0)
				{
					System.out.println("Registered has done..!!");
				}
				else
				{
					System.out.println("Something went wrong...!!");
				}
				
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}