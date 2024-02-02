package WalletServices;

import java.util.List;

import WalletEntities.Customer;
import WalletEntities.Transaction;


public interface Service {
	
	boolean validateFields(String firstName, String lastName, String mobileNumber, String email, int pin, int confirmPin);
	
	Customer createWalletAccount(Customer c);
	
	Customer getWalletAccount(int userID, int walletID, int pin);
	
	Customer addAmount(Customer c, int amount);
	
	boolean amountValidity(int amount);
	
	int getBalance(int userID);
	
	boolean recipientValidity(int userID, int walletID);
	
	void transferFunds(Customer c, int recipientUserID, int amount);
	
	boolean balanceValidity(Customer c, int amount);
	
	Customer withdrawAmount(Customer c, int amount);
	
	List<Transaction> getLastTenTransaction(Customer walletHolder);
	
	boolean dateValidity(String date);
	
	List<Transaction> getTransactionOnDate(Customer walletHolder, String date);

}
