package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Account {

	private String userID;
	private String usernameandPassword;
	private Connection connection;
	private AES aesEncryption;

	public Account(String userID,String usernameandPassword) {
		// TODO Auto-generated constructor stub
		this.userID = userID;
		this.usernameandPassword =  usernameandPassword;
		//System.out.println("account cosnt" + userID);
		aesEncryption = new AES(usernameandPassword);

	}
	
	public String getUserID(){
		return this.userID;
	}
	
	private void dbConnect() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub0000000000000000
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:"+ System.getProperty("user.dir")+"\\db\\Eldian.sqlite");
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
			aesEncryption.decrypt(rs.getString("account_name"));
			userEntries.setAccount_name(aesEncryption.getDecryptedString());
			aesEncryption.decrypt(rs.getString("category"));
			userEntries.setCategory(aesEncryption.getDecryptedString());
			aesEncryption.decrypt(rs.getString("password"));
			userEntries.setPassword(aesEncryption.getDecryptedString());
			aesEncryption.decrypt(rs.getString("login_id"));
			userEntries.setLogin_id(aesEncryption.getDecryptedString());
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
		aesEncryption.encrypt(accountName);
		preparedStatement.setString(1,aesEncryption.getEncryptedString());
		aesEncryption.encrypt(Password);
		preparedStatement.setString(2,aesEncryption.getEncryptedString());
		aesEncryption.encrypt(loginID);
		preparedStatement.setString(3,aesEncryption.getEncryptedString());
		aesEncryption.encrypt(category);
		preparedStatement.setString(4,aesEncryption.getEncryptedString());
		preparedStatement.setString(5, userID);
		preparedStatement.executeUpdate();
		dbClose();
	}

	public void deleteEntry(ArrayList<String> idListofSelectedItems) throws ClassNotFoundException, SQLException {
		for (String entryID : idListofSelectedItems) {
			dbConnect();
			//System.out.println(entryID);
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
		aesEncryption.encrypt(accountName);
		preparedStatement.setString(1,aesEncryption.getEncryptedString());
		aesEncryption.encrypt(Password);
		preparedStatement.setString(2,aesEncryption.getEncryptedString());
		aesEncryption.encrypt(loginID);
		preparedStatement.setString(3,aesEncryption.getEncryptedString());
		aesEncryption.encrypt(category);
		preparedStatement.setString(4,aesEncryption.getEncryptedString());
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
		aesEncryption.encrypt(searchString);
		String encryptedSearchString =aesEncryption.getEncryptedString();
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
			aesEncryption.decrypt(rs.getString("account_name"));
			userEntries.setAccount_name(aesEncryption.getDecryptedString());
			aesEncryption.decrypt(rs.getString("category"));
			userEntries.setCategory(aesEncryption.getDecryptedString());
			aesEncryption.decrypt(rs.getString("password"));
			userEntries.setPassword(aesEncryption.getDecryptedString());
			aesEncryption.decrypt(rs.getString("login_id"));
			userEntries.setLogin_id(aesEncryption.getDecryptedString());
			userEntries.setEntry_id(rs.getString("entry_id"));
			searchItemsList.add(userEntries);
		}
		dbClose();
		return searchItemsList;
	}

	public void updatePassword(String newPassword) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		dbConnect();
		String sql = "UPDATE users SET password = ? WHERE user_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		aesEncryption.encrypt(newPassword);
		preparedStatement.setString(1,aesEncryption.getEncryptedString());
		preparedStatement.setString(2,userID);
		preparedStatement.executeUpdate();
		dbClose();
		
	}

	public String getSecurityQuestionID() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		dbConnect();
		String securityqID = "";
		String sql = "Select security_question_id from users where user_id= ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, userID);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			securityqID = rs.getString("security_question_id");
		}
		dbClose();
		
		return securityqID;
	}

	public String getSecurityQuestionText(String security_question_id) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		dbConnect();
		String securityqQText = "";
		String sql = "Select question_text from security_questions where security_question_id= ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, security_question_id);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			securityqQText = rs.getString("question_text");
		}
		dbClose();
		
		return securityqQText;
	}

	public String getSecurityQuestionAnswer() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		dbConnect();
		String securityqAns = "";
		String sql = "Select security_answer from users where user_id= ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, userID);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			securityqAns = rs.getString("security_answer");
		}
		dbClose();
		
		return securityqAns;
	}

}
