package jdbc;
import java.util.*;
import java.sql.*;


public class InventoryManagementSystem {
	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {
		int option, lookUp;
		//Step-1 Load the JDBC Driver (Oracle)
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/datatest","root","asdf");
			PreparedStatement ps = null;
			Statement st=con.createStatement();
			if(con!=null)
			{
				System.out.println("MySQL DB Connected..");
			}
			else
			{
				System.out.println("Not Connected..!!");
			}
			do {
				System.out.println("Menu");
				System.out.println("1. Add Product\n2. View Product\n3. Update Product\n4. Delete Product\n0. Exit");
				
				option=in.nextInt();
				switch(option) {
				case 1:
					int id,stock;
					String name;
					double price;
					ps = con.prepareStatement("insert into Products values(?,?,?,?)");
			
					System.out.println("Enter Product ID: ");
					id=in.nextInt();
					System.out.println("Enter Product Name: ");
					name=in.next();
					System.out.println("Enter Product Price:");
					price=in.nextDouble();
					System.out.println("Enter Product Stock: ");
					stock=in.nextInt();
					
					ps.setInt(1,id);
					ps.setString(2, name);
					ps.setDouble(3, price);
					ps.setInt(4, stock);
					
					ps.executeUpdate();
					
					System.out.println("Product Created");
					break;
				case 2:
					ResultSet rs = st.executeQuery("Select * from Products");
					while(rs.next()) {
						System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4));
					}
					break;
				case 3:
					lookUp=LookUpProduct();
					System.out.println("Update product "+lookUp);
					System.out.println("A. Update Product Name\nB. Update Price\nC. Update Stock");
					String stringOption=in.next();
					char option1=stringOption.charAt(0);
					switch(option1) {
					case 'A':
					case 'a':
						System.out.println("Enter new name for "+lookUp);
						String newName = in.next();
						ps = con.prepareStatement("UPDATE Products SET name=? where id=?");
						ps.setString(1, newName);
						break;
					case 'B':
					case 'b':
						System.out.println("Enter new Price for "+lookUp);
						double newPrice = in.nextDouble();
						ps = con.prepareStatement("UPDATE Products SET price=? where id=?");
						ps.setDouble(1, newPrice);
						break;
					case 'C':
					case 'c':
						System.out.println("Update Stock for "+lookUp);
						int newStock = in.nextInt();
						ps = con.prepareStatement("UPDATE Products SET stock=? where id=?");
						ps.setInt(1, newStock);
						break;
					default:
						System.err.println("No proper value was entered");
					}
					//End of inner switch
					ps.setInt(2, lookUp);
					ps.executeUpdate();
					break;
				case 4:
					lookUp=LookUpProduct();
					ps = con.prepareStatement("Delete From Products where id=?");
					ps.setInt(1, lookUp);
					ps.executeUpdate();
					System.out.println("Product Deleted");
					break;
				default:
					System.out.println("Exiting Program");
				}
			}while(option!=0);
					
			
		}catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static int LookUpProduct() {
		System.out.println("Enter Product ID: ");
		return in.nextInt();
	}

}
