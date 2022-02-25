package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerJDBC extends User{
	ArrayList cart = new ArrayList<Integer>();
	
	public CustomerJDBC() {
		
	}
	
	public void addProductToCart(int pid) {
		try {
			pst = con.prepareStatement("Select * Products where id=?");
			pst.setInt(1, pid);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public boolean signIn(String uid, String pwd) {
		try {
			pst=con.prepareStatement("select * from users where utype=\"customer\" and uid=? and pwd=?");
			pst.setString(1, uid);
			pst.setString(2, pwd);
			
		    ResultSet rs = pst.executeQuery();
		    if(rs.next())
			{
				System.out.println("Login Complete!");
				this.pwd=pwd;
				this.uid=uid;
				return true;
			}
			else
			{
				System.out.println("User not found...");
				this.pwd=null;
				this.uid=null;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void getMenuOptions() {
		System.out.println("A. View Products\nB. Add to Carts\nC. View Carts\nD. Remove from carts\nE. Exit");
		
	}
	
	
	
	public int getUtype() {return 2;}

	@Override
	public void signIn() {
		signIn(uid,pwd);
	}

}
