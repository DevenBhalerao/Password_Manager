package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Account {

	private String userID;
	private Connection connection;
	private AES a;

	public Account(String userID) {
		// TODO Auto-generated constructor stub
		this.userID = userID;
		System.out.println("account cosnt" + userID);
		a = new AES(userID);

	}

	private void dbConnect() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub0000000000000000
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:F:\\College\\SE\\Mini Project\\Eldian.sqlite");
	}

	public ArrayList<UserEntry> getEntries() throws ClassNotFoundException, SQLException {
		dbConnect();
		ArrayList<UserEntry> entryList = new ArrayList<>();
		String sql = "Select * from entries where user_id= ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, userID);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			UserEntry userEntries = new UserEntry(userID);
			a.decrypt(rs.getString("account_name"));
			userEntries.setAccount_name(a.getDecryptedString());
			a.decrypt(rs.getString("category"));
			userEntries.setCategory(a.getDecryptedString());
			a.decrypt(rs.getString("password"));
			userEntries.setPassword(a.getDecryptedString());
			a.decrypt(rs.getString("login_id"));
			userEntries.setLogin_id(a.getDecryptedString());
			userEntries.setEntry_id(rs.getString("entry_id"));
			entryList.add(userEntries);
		}
		dbClose();
		return entryList;
	}

	public void AddEntry(String accountName, String category, String Password, String loginID)
			throws ClassNotFoundException, SQLException {
		dbConnect();
		String sql = "insert into entries(account_name,password,login_id,category,user_id) values(?,?,?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		a.encrypt(accountName);
		preparedStatement.setString(1, a.getEncryptedString());
		a.encrypt(Password);
		preparedStatement.setString(2, a.getEncryptedString());
		a.encrypt(loginID);
		preparedStatement.setString(3, a.getEncryptedString());
		a.encrypt(category);
		preparedStatement.setString(4, a.getEncryptedString());
		preparedStatement.setString(5, userID);
		preparedStatement.executeUpdate();
		dbClose();
	}

	public void deleteEntry(ArrayList<String> idListofSelectedItems) throws ClassNotFoundException, SQLException {
		for (String entryID : idListofSelectedItems) {
			dbConnect();
			System.out.println(entryID);
			String sql = "delete from entries where entry_id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, entryID);
			preparedStatement.executeUpdate();
			dbClose();
		}
	}

	public void editEntry(String accountName, String category, String Password, String loginID, String entryID)
			throws ClassNotFoundException, SQLException {
		dbConnect();
		String sql = "UPDATE entries SET account_name = ?, password = ?, login_id = ?, category = ? WHERE entry_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		a.encrypt(accountName);
		preparedStatement.setString(1, a.getEncryptedString());
		a.encrypt(Password);
		preparedStatement.setString(2, a.getEncryptedString());
		a.encrypt(loginID);
		preparedStatement.setString(3, a.getEncryptedString());
		a.encrypt(category);
		preparedStatement.setString(4, a.getEncryptedString());
		preparedStatement.setString(5, entryID);
		preparedStatement.executeUpdate();
		dbClose();

	}

	private void dbClose() throws SQLException {
		// TODO Auto-generated method stub
		connection.close();
	}

	public ArrayList<UserEntry> getSearchEntries(String searchString) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		dbConnect();
		ArrayList<UserEntry> searchItemsList = new ArrayList<>();
		a.encrypt(searchString);
		String encryptedSearchString = a.getEncryptedString();
		System.out.println(encryptedSearchString);
		String sql = "SELECT * FROM entries WHERE account_name LIKE ? or password LIKE ? or category LIKE ? or login_id LIKE ? ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, "%" + encryptedSearchString + "%");
		preparedStatement.setString(2, "%" + encryptedSearchString + "%");
		preparedStatement.setString(3, "%" + encryptedSearchString + "%");
		preparedStatement.setString(4, "%" + encryptedSearchString + "%");
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			UserEntry userEntries = new UserEntry(userID);
			a.decrypt(rs.getString("account_name"));
			userEntries.setAccount_name(a.getDecryptedString());
			a.decrypt(rs.getString("category"));
			userEntries.setCategory(a.getDecryptedString());
			a.decrypt(rs.getString("password"));
			userEntries.setPassword(a.getDecryptedString());
			a.decrypt(rs.getString("login_id"));
			userEntries.setLogin_id(a.getDecryptedString());
			userEntries.setEntry_id(rs.getString("entry_id"));
			searchItemsList.add(userEntries);
		}
		dbClose();
		return searchItemsList;
	}

}
