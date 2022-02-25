package jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminJDBC extends User{
	
	public AdminJDBC() {
	}
	
	@Override
	public void signIn() {
		signIn(this.uid,this.pwd);
	}
	
	
	@Override
	public boolean signIn(String uid, String pwd) {
		try {
			pst=con.prepareStatement("select * from users where utype=\"admin\" and uid=? and pwd=?");
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
		System.out.println("Menu");
		System.out.println("1. Add Product\n2. View Product\n3. Update Product\n4. Delete Product\n0. Exit");
			
	}

	@Override
	public int getUtype() {
		if(pwd != null && uid != null)
			return 1;
		else
			return 0;
	}
	
	public void addProduct(int id, String name, double price, int stock) throws SQLException {
		pst= con.prepareStatement("insert into Products values(?,?,?,?)");

		pst.setInt(1,id);
		pst.setString(2, name);
		pst.setDouble(3, price);
		pst.setInt(4, stock);
		
		pst.executeUpdate();
	}

	public void updateProduct(int lookUp, String newName) throws SQLException {
		pst = con.prepareStatement("UPDATE Products SET name=? where id=?");
		pst.setString(1, newName);
		pst.setInt(2, lookUp);
		pst.executeUpdate();
		ResultSet rs = pst.executeQuery();
	    if(rs.next())
		{
			System.out.println("Login Complete!");
		}
		else
		{
			System.out.println("User not found...");
			this.pwd=null;
			this.uid=null;
		}
		
	}

	public void updateProduct(int lookUp, double newPrice) throws SQLException {
		pst = con.prepareStatement("UPDATE Products SET price=? where id=?");
		pst.setDouble(1, newPrice);
		pst.setInt(2, lookUp);
		pst.executeUpdate();
		
	}

	public void updateProduct(int lookUp, int newStock) throws SQLException {
		pst = con.prepareStatement("UPDATE Products SET stock=? where id=?");
		pst.setInt(1, newStock);
		pst.setInt(2, lookUp);
		pst.executeUpdate();
		
	}
	
	public void removeProduct(int lookUp) throws SQLException {
		pst = con.prepareStatement("Delete From Products where id=?");
		pst.setInt(1, lookUp);
		pst.executeUpdate();
	}

}
