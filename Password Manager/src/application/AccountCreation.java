package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountCreation {
	private String AesPass;
	private Connection connection;
	private AES a;

	public AccountCreation(String userID) {
		// TODO Auto-generated constructor stub
		this.AesPass = userID;
		a = new AES(userID);

	}

	private void dbConnect() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub0000000000000000
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:F:\\College\\SE\\Mini Project\\Eldian.sqlite");
	}
	
	private void dbClose() throws SQLException {
		// TODO Auto-generated method stub
		connection.close();
	}
	
	public void AddEntry(String username, String password,String Answer)
			throws ClassNotFoundException, SQLException {
		dbConnect();
		System.out.println("lol");
		String sql = "insert into users(username,password,last_accessed,security_answer) values(?,?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		a.encrypt(username);
		preparedStatement.setString(1, a.getEncryptedString());
		a.encrypt(password);
		preparedStatement.setString(2, a.getEncryptedString());
		preparedStatement.setString(3, "lol");
		a.encrypt(Answer);
		preparedStatement.setString(4, a.getEncryptedString());
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
}
