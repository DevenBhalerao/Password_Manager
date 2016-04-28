package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AccountCreation {
	private String AesPass;
	private Connection connection;
	private AES a;
	private String usernameandPassword;
	private String userID;

	
	public AccountCreation(String masterpass) {
		// TODO Auto-generated constructor stub
	    a = new AES(masterpass);
		
	}

	private void dbConnect() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub0000000000000000
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:"+ System.getProperty("user.dir")+"\\db\\Eldian.sqlite");
	}
	
	private void dbClose() throws SQLException {
		// TODO Auto-generated method stub
		connection.close();
	}
	
	public void AddEntry(String username, String password)throws ClassNotFoundException, SQLException {
		dbConnect();
		//System.out.println("Add entry init");
		String sql = "insert into users(username,password,last_accessed) values(?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		a.encrypt(username);
		preparedStatement.setString(1, a.getEncryptedString());
		a.encrypt(password);
		preparedStatement.setString(2, a.getEncryptedString());
		preparedStatement.setString(3, new SimpleDateFormat("MM/dd/yyyy h:mm:ss a").format(new Date()));
		preparedStatement.executeUpdate();
		dbClose();
	}
	
	
	public String getPass(String username) throws SQLException, ClassNotFoundException{
	String pass = null;
	dbConnect();
	String sql = "Select password from users where username = ?";
	PreparedStatement pstmt = connection.prepareStatement(sql);
	pstmt.setString(1, username);
	ResultSet rs = pstmt.executeQuery();
	while (rs.next()) {
		pass = rs.getString("password");
	}
	dbClose();
	return pass;
	}
	
	
	
	public String getUid(String username) throws SQLException, ClassNotFoundException{
	String uid = null;
	dbConnect();
	String sql = "Select user_id from users where username = ?";
	
	PreparedStatement pstmt = connection.prepareStatement(sql);
	pstmt.setString(1, username);
	ResultSet rs = pstmt.executeQuery();
	while (rs.next()) {
		uid = rs.getString("user_id");
	}
	dbClose();
	return uid;
	}

	
	
	public void updateTimeStamp(String username) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		dbConnect();
		String sql = "UPDATE users SET last_accessed = ? WHERE username= ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1,new SimpleDateFormat("MM/dd/yyyy h:mm:ss a").format(new Date()));
		preparedStatement.setString(2,username);
		preparedStatement.executeUpdate();
		dbClose();
		
		
	}
}
