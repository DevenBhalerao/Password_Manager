package application;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.ObservableList;

public class DeleteEntry {
	private Account account;
	
	public DeleteEntry(Account account) {
		// TODO Auto-generated constructor stub
		this.account = account;
		
	}
	
	public void deleteEntries(ObservableList<UserEntry> selectedItems) throws ClassNotFoundException, SQLException{
		ArrayList<String> idList = new ArrayList<>();
		for(UserEntry userEntry : selectedItems){
			idList.add(userEntry.getEntry_id());
		}
		account.deleteEntry(idList);
	}
}
