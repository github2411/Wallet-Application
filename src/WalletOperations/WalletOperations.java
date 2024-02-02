package WalletOperations;

import java.util.List;

import WalletEntities.Customer;
import WalletEntities.Transaction;

public interface WalletOperations {
	Customer addWalletAccount(Customer c);

	Customer findWalletAccount(int userID);

	Customer updateWallet(Customer c);

	int findBalance(int userID);

	void addTransaction(Transaction transaction);
	
	List<Transaction> findLastTenTransaction(int userID);

	
	List<Transaction> findTransactionOnDate(int userID, String date);

}
