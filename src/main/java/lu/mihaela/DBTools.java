package lu.mihaela;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lu.mihaela.Cake;
import lu.mihaela.DBInfo;

public class DBTools {
	
	private long lastInsertedRowID;

	public DBTools() {
		try {
			// registering the driver
			Class.forName(DBInfo.getDriver());
		} catch (ClassNotFoundException e) {
			System.out.println("Error. Driver class not found: " + e);
		}
	}

	public long getlastInsertedRowID() {
		return lastInsertedRowID;
	}

	public void setlastInsertedRowID(long lastInsertedRowID) {
		this.lastInsertedRowID = lastInsertedRowID;
	}

	Connection getConnection() {
		String dbURL = DBInfo.getDBURL();
		String user = DBInfo.getUser();
		String password = DBInfo.getPassword();
		Connection connection = null;
		
		// connecting
		try {
			connection = DriverManager.getConnection(dbURL, user, password);

		} catch (SQLException e) {
			System.out.println("Error. Connection problem: " + e);
		}
		return connection;
	}

	//*********************
	//Here I will use Statement as no user input.
	//For user input, i will have to use PreparedStatements
	ArrayList<Cake> getCakesList(int page, int step){// all
		ArrayList<Cake> list = new ArrayList<Cake>();
		Statement statement = null;
		ResultSet result = null;
		
		Connection connection = getConnection();
		
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Statement creation error: " + e);
			return null;
		}
		
		String searchString = "SELECT * FROM cakes LIMIT " + step + (page==1?"":" OFFSET " + ((page-1)*step));
		
		
		try {
			result = statement.executeQuery(searchString);
		} catch (SQLException e) {
			System.out.println("Execution error: " + e);
			return null;
		}

		try {
			while (result.next()) {
				//instantiate a bean
				Cake cake = new Cake();
				//extract all bean properties from result set, and set bean's fields
				long id = result.getInt("id");
				cake.setId(id);
				
				String imgName = result.getString("img_name");
				cake.setImgName(imgName);
				
				String name = result.getString("name");
				cake.setName(name);

				String ingredients = result.getString("ingredients");
				cake.setIngredients(ingredients);

				String method = result.getString("method");
				cake.setMethod(method);

				double price = Double.parseDouble(result.getString("price"));
				cake.setPrice(price);
				
				//add bean to list
				list.add(cake);
			}
		} catch (SQLException e) {
			System.out.println("Data retrieving/reading problem: " + e);
		}

		try {
			result.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;//the list with cakes	
	}
	//*********************
		//Here I will use PreparedStatement as user input.
		Cake getCake(long edit_id){// all
			Cake cake = new Cake();
			PreparedStatement pstmt = null;
			ResultSet result = null;
			
			Connection connection = getConnection();
			try {
				pstmt = connection.prepareStatement("SELECT * FROM cakes WHERE id=?");
				pstmt.setLong(1, edit_id);
			} catch (SQLException e) {
				System.out.println("Statement creation error: " + e);
				return null;
			}
			
			try {
				result = pstmt.executeQuery();
			} catch (SQLException e) {
				System.out.println("Execution error: " + e);
				return null;
			}

			try {
				while (result.next()) {
					//extract all bean properties from result set, and set bean's fields
					long id = result.getLong("id");
					cake.setId(id);
					
					String imgName = result.getString("img_name");
					cake.setImgName(imgName);
					
					String name = result.getString("name");
					cake.setName(name);

					String ingredients = result.getString("ingredients");
					cake.setIngredients(ingredients);

					String method = result.getString("method");
					cake.setMethod(method);

					double price = result.getDouble("price");
					cake.setPrice(price);
				}
			} catch (SQLException e) {
				System.out.println("Data retrieving/reading problem: " + e);
			}

			try {
				result.close();
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return cake;//the list with cakes	
		}


	//*********************
	//Again PreparedStatement because the data comes from user 
	void addCake(String img_name, String name, String ingredients, String method, double price){// add
		
		Connection connection = getConnection();
		
		try {// Statement.RETURN_GENERATED_KEYS as 2nd arg to prep stmt is for returning generated ID of last inserted row
			PreparedStatement statement = 
					connection.prepareStatement("INSERT INTO cakes (img_name, name, ingredients, method, price) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, img_name);
			statement.setString(2, name);
			statement.setString(3, ingredients);
			statement.setString(4, method);
			statement.setDouble(5, price);
			
			statement.executeUpdate(); 
			
			ResultSet rs = statement.getGeneratedKeys();
            if(rs.next())
            {
            	lastInsertedRowID = rs.getInt(1);//the id of last inserted row
            }
			
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("There was an error when adding your cake: " + e);
		}
	}

	//*********************
	//We use PreparedStatement because the data comes from user; not used at the mo
	void updateCake(long id, String imgName, String name, String ingredients, String method, double price){//update
		
		Connection connection = getConnection();
		
		try {
			PreparedStatement pstmt = 
					connection.prepareStatement("UPDATE cakes SET img_name=?, name=?, ingredients=?, method=?, price=? WHERE id=?");

			pstmt.setString(1, imgName);
			pstmt.setString(2, name);
			pstmt.setString(3, ingredients);
			pstmt.setString(4, method);
			pstmt.setDouble(5, price);
			pstmt.setLong(6, id);//should change type to long in DB; int at the mo
			
			pstmt.executeUpdate();
			
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("There was an error when updating your cake: " + e);
		}
	}
	
	//****************************
	void setNewPrice(long id, double price){
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE cakes SET price = ? WHERE id = ?");
			statement.setDouble(1, price);
			statement.setLong(2, id);
			
			statement.executeUpdate();

			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error when executing the update: " + e);
		}
	}
	
	//*******************************
	void deleteCakes(ArrayList<Integer> list){// delete; how will I have an array of IDs to delete??
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM cakes WHERE id=?");
			
			for (long id : list) {//My id is integer
				statement.setLong(1, id);//attention, my ID is integer
				statement.executeUpdate();
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Cakes deleting error: " + e);
		}
	}
}