package jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class User {
	
	public static Connection con = DBConnection.CreateConnection();
	PreparedStatement pst = null;
	Statement st=null;
	
	String uid;
	String pwd;
	
	public abstract boolean signIn(String uid, String pwd);
	public abstract void getMenuOptions();
	public void viewProducts() {
		ResultSet rs;
		try {
			st=con.createStatement();
			rs = st.executeQuery("Select * from Products");
			while(rs.next()) {
				System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void viewProduct(int pid) {
		ResultSet rs;
		try {
			pst= con.prepareStatement("Select * from Products where id=?");
			pst.setInt(1, pid);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void setUid(String uid) {this.uid=uid;}
	protected void setPwd(String pwd) {this.pwd=pwd;}
	
	public int getUtype() {return 0;}
	public abstract void signIn();
	
}
