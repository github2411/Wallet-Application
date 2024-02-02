package WalletOperations;

import java.util.ArrayList;
import java.util.HashMap;

import WalletEntities.Customer;
import WalletEntities.Transaction;

public class misc {
	public static int USER_ID_COUNTER = 1001;
	public static int WALLET_ID_COUNTER = 2001;
	public static int TRANSACTION_ID_COUNTER = 3001;
	
	public static HashMap<Integer, Customer> customers = new HashMap<Integer, Customer>();
	public static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
}
