package WalletOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import WalletEntities.Customer;
import WalletEntities.Transaction;

public class WalletoperationImplementation implements WalletOperations {

	@Override
	public Customer addWalletAccount(Customer c) {
		// TODO Auto-generated method stub
		c.setUserID(misc.USER_ID_COUNTER++);
		c.getWallet().setWalletID(misc.WALLET_ID_COUNTER++);
		c.getWallet().setBalance(0);

		misc.customers.put(c.getUserID(), c);

		return c;
	}

	@Override
	public Customer findWalletAccount(int userID) {
		// TODO Auto-generated method stub
		return misc.customers.get(userID);

	}

	@Override
	public Customer updateWallet(Customer c) {
		// TODO Auto-generated method stub
		misc.customers.put(c.getUserID(), c);
		return misc.customers.get(c.getUserID());
	}

	@Override
	public int findBalance(int userID) {
		// TODO Auto-generated method stub
		return misc.customers.get(userID).getWallet().getBalance();

	}

	@Override
	public void addTransaction(Transaction transaction) {
		
		transaction.setTransactionID(misc.TRANSACTION_ID_COUNTER++);
		
		misc.transactions.add(transaction);
		
	}
	
	public List<Transaction> findLastTenTransaction(int userID) {

		List<Transaction> lastTenTransactions = new ArrayList<Transaction>();
	
		int n = misc.transactions.size();
		int count = 0;
		
		for(int i = n-1; i >= 0; i--) {
			
			Transaction temp = misc.transactions.get(i);
			
			if(temp.getUser().getUserID() == userID) {
				
				lastTenTransactions.add(temp);
				count++;
				
				if(count == 10) {
					break;
				}
			}
		}

		return lastTenTransactions;
	}
	
	public List<Transaction> findTransactionOnDate(int userID, String date) {
		//List<Transaction> transactionsOnDate = new ArrayList<Transaction>();
		
		List<Transaction>transactionsOnDate=misc.transactions.stream()
				.filter(i->((i.getUser().getUserID()==userID) && (i.getTransactionDate().toString().equals(date))))
				.collect(Collectors.toList());
	//	System.out.println("Trial check-->"+transactionsOnDate);
				
		/*int n = misc.transactions.size();
		
		for(int i = 0; i < n; i++) {
			
			Transaction temp = misc.transactions.get(i);
			
			String transactionDate = temp.getTransactionDate().toString();
			
			if(temp.getUser().getUserID() == userID && transactionDate.equals(date)) {
				
				transactionsOnDate.add(temp);
			}
		}*/
		return transactionsOnDate;
	}

}
