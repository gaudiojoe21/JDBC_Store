package jdbc;

import java.util.*;
import java.sql.*;


public class JDBCfinal {
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		try {
			User user;
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/datatest","root","asdf");
			PreparedStatement pst = null;
			Statement st=con.createStatement();
			int utype;
			do {

				System.out.println("Choose Login Type");
				System.out.println("1. Admin\n2. Customer\n0. Exit");
				utype=sc.nextInt();
				
				user = isUser(utype); 
				
				if(user==null)break;
				
				switch(user.getUtype()) {
				case 1:// Admin
					AdminJDBC admin=(AdminJDBC) user;
					int adminMenu;
					do {
						user.getMenuOptions();
						adminMenu=sc.nextInt();

						switch(adminMenu) {
						case 1:
							int id, stock;
							String name;
							double price;
							System.out.println("Enter Product ID:");
							id=sc.nextInt();
							System.out.println("Enter Product Name:");
							name=sc.next();
							System.out.println("Enter Product Price:");
							price=sc.nextDouble();
							System.out.println("Enter Product Stock:");
							stock=sc.nextInt();
							
							admin.addProduct(id,name,price,stock);
							break;
						case 2:
							admin.viewProducts();
							break;
						case 3:
							int lookUp=LookUpProduct();
							System.out.println("Update product "+lookUp);
							System.out.println("A. Update Product Name\nB. Update Price\nC. Update Stock");
							String stringOption=sc.next();
							char option1=stringOption.charAt(0);
							switch(option1) {
							case 'A':
							case 'a':
								System.out.println("Enter new name for "+lookUp);
								String newName = sc.next();
								admin.updateProduct(lookUp,newName);
								break;
							case 'B':
							case 'b':
								System.out.println("Enter new Price for "+lookUp);
								double newPrice = sc.nextDouble();
								admin.updateProduct(lookUp,newPrice);
								break;
							case 'C':
							case 'c':
								System.out.println("Update Stock for "+lookUp);
								int newStock = sc.nextInt();
								admin.updateProduct(lookUp,newStock);
								break;
							default:
								System.err.println("No proper value was entered");
							}
							admin.viewProduct(lookUp);
							//End of Update Product switch
							break;
						case 4:
							lookUp=LookUpProduct();
							admin.removeProduct(lookUp);
							System.out.println("Product Deleted");
							break;
						default:
							System.out.println("Logging out Admin!\n");
						}//End of Admin Menu Switch
					}while(adminMenu!=0);//End of Admin Loop
				break;	//End of Admin
					
				case 2://Customer
					String stringOption;
					char userChoice='E';
					do {
						System.out.println("A. View Products\nB. Add to Carts\nC. View Carts\nD. Remove from carts\nE. Exit");
						stringOption=sc.next();
						userChoice=stringOption.charAt(0);
						switch(userChoice) {
						case 'A':
						case 'a'://View Products
							ResultSet rs = st.executeQuery("Select * from Products");
							while(rs.next()) {
								System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getDouble(3));
							}
							break;
						case 'b':
						case 'B'://Add to cart
							pst=con.prepareStatement("insert into customerorder values(?,?,?)");
							String option1;
							do
							{
								System.out.println("Enter Product ID:");
								int pid=sc.nextInt();
								System.out.println("Enter Product Name:");
								String pname=sc.next();
								System.out.println("Enter Product Price:");
								double price=sc.nextDouble();
								pst.setInt(1, pid);
								pst.setString(2, pname);
								pst.setDouble(3, price);
								//addBatch() is used to add the records or data into sql buffer area.
								pst.addBatch();
								
								System.out.println("Do You Want To Add One More Item: Y/N");
								option1=sc.next();
							}while(option1.equalsIgnoreCase("yes")||option1.equals("y"));
							
							int[] arr=pst.executeBatch();
							int length=arr.length;
							System.out.println(length+" Item Records Inserted...!! ");
							break;
						case 'c'://View Cart
						case 'C':
							ResultSet cc = st.executeQuery("Select * from customerorder");
							while(cc.next()) {
								System.out.println(cc.getInt(1)+"\t"+cc.getString(2)+"\t"+cc.getDouble(3));
							}
							break;
						case 'd':
						case 'D'://Remove item
							int lookUp=LookUpProduct();
							pst = con.prepareStatement("Delete From customerorder where pid=?");
							pst.setInt(1, lookUp);
							pst.executeUpdate();
							System.out.println("Product Removed");
							break;
						default:
							System.out.println("Invalid input");
						case 'e':
						case 'E':
							System.out.println("Logging out Customer!\n");
						}//End of Customer Option
					}while (userChoice!='E' && userChoice!='e');
					//End of Customer Menu Loop
				
				}//End of Menu Switch
				
			}while(user!=null);//Exit Project
			System.out.println("Project Terminated");
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Checks to see if user exists
	 * @param utype
	 * @return utype if user exists, 0 if not
	 */
	private static User isUser(int in) {
		User out=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/datatest","root","asdf");
			PreparedStatement pst=con.prepareStatement("select * from users where utype=? and uid=? and pwd=?");
			
			String utype;
			switch(in) {

			case 0:
				//Short circuits exit as well as exits project
				return null;
			case 1:
				utype="admin";
				out = new AdminJDBC();
				break;
			case 2:
				utype="customer";
				out = new CustomerJDBC();
				break;
			default:
				System.err.println("Menu option not avaiable\n");
				return null;
			}
			
			System.out.println("Enter User ID:");
		    out.setUid(sc.next());
		    System.out.println("Enter User PWD:");
		    out.setPwd(sc.next());
		    out.signIn();
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return out;
	}


	private static int LookUpProduct() {
		System.out.println("Enter Product ID: ");
		return sc.nextInt();
	}
}
