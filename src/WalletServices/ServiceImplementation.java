package WalletServices;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import WalletEntities.Customer;
import WalletEntities.Transaction;
import WalletException.InsufficientBalanceException;
import WalletException.InvalidCredentialException;
import WalletOperations.WalletOperations;
import WalletOperations.WalletoperationImplementation;

public class ServiceImplementation implements Service {

	WalletOperations wo = new WalletoperationImplementation();

	public Customer createWalletAccount(Customer c) {
		// TODO Auto-generated method stub
		return wo.addWalletAccount(c);

	}

	public Customer getWalletAccount(int userID, int walletID, int pin) {
		Customer c = wo.findWalletAccount(userID);

		if (c != null) {
			if (c.getWallet().getWalletID() == walletID && c.getWallet().getPin() == pin) {
				return c;
			}
		}
		return null;
	}

	public boolean validateFields(String firstName, String lastName, String mobileNumber, String email, int pin,
			int confirmPin) {
		if (firstName != null && lastName != null && mobileNumber != null && email != null && pin != 0
				&& confirmPin != 0) {

			if (mobileNumber.matches("[0-9]{10}") && email.matches("[A-Za-z0-9]+@[a-z]+.[a-z]+")
					&& Integer.toString(pin).matches("[0-9]{4}") && pin == confirmPin) {
				return true;
			}
		}

		try {
			throw new InvalidCredentialException("Invalid Credentials!");
		} catch (InvalidCredentialException e) {
			System.out.println(e);
			return false;
		}
	}

	public Customer addAmount(Customer c, int amount) {
		// TODO Auto-generated method stub
		int balance = c.getWallet().getBalance();
		c.getWallet().setBalance(balance + amount);
		
		Transaction transaction = new Transaction("      Deposit     ", LocalDate.now(), amount, c);
		wo.addTransaction(transaction);

		return wo.updateWallet(c);
	}

	public int getBalance(int userID) {
		// TODO Auto-generated method stub
		return wo.findBalance(userID);
	}

	public boolean recipientValidity(int userID, int walletID) {
		// TODO Auto-generated method stub
		Customer c = wo.findWalletAccount(userID);

		try {
			if (c != null && c.getWallet().getWalletID() == walletID) {
				return true;
			} else {
				throw new InvalidCredentialException("Invalid Recipient Credentials!");
			}
		} catch (InvalidCredentialException e) {
			System.out.println(e);
			return false;
		}

	}

	public void transferFunds(Customer sender, int recipientUserID, int amount) {
		// TODO Auto-generated method stub
		Customer recipient = wo.findWalletAccount(recipientUserID);

		int senderBalance = sender.getWallet().getBalance();
		int recipientBalance = recipient.getWallet().getBalance();

		sender.getWallet().setBalance(senderBalance - amount);
		recipient.getWallet().setBalance(recipientBalance + amount);

		wo.updateWallet(sender);
		wo.updateWallet(recipient);
		
		Transaction transaction1 = new Transaction("Amount Transferred", LocalDate.now(), amount, sender);
		wo.addTransaction(transaction1);
		
		Transaction transaction2 = new Transaction(" Amount Received  ", LocalDate.now(), amount, recipient);
		wo.addTransaction(transaction2);

	}

	public boolean balanceValidity(Customer c, int amount) {
		// TODO Auto-generated method stub
		try {
			if (c.getWallet().getBalance() < amount) {
				throw new InsufficientBalanceException("Insufficient Balance for given Amount!");
			} else {
				return true;
			}
		} catch (InsufficientBalanceException e) {
			System.out.println(e);
			return false;
		}

	}

	public Customer withdrawAmount(Customer c, int amount) {
		// TODO Auto-generated method stub
		int balance = c.getWallet().getBalance();

		c.getWallet().setBalance(balance - amount);
		
		Transaction transaction = new Transaction("     Withdraw     ", LocalDate.now(), amount, c);
		wo.addTransaction(transaction);
		

		return wo.updateWallet(c);
	}

	@Override
	public boolean amountValidity(int amount) {
		Predicate<Integer> check = (n) -> {
			try {
				if(n > 0) {
					return true;
				}
				else {
					throw new InvalidCredentialException("Invalid Amount!");
				}
			}
			catch(InvalidCredentialException e) {
				System.out.println(e);
				return false;
			}
		};
		
		return check.test(amount);
	}

	@Override
	public List<Transaction> getLastTenTransaction(Customer walletHolder) {
		return wo.findLastTenTransaction(walletHolder.getUserID());
	}

	@Override
	public boolean dateValidity(String date) {
		try {
			if(date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
				return true;
			}
			else {
				throw new InvalidCredentialException("Invalid Date!");
			}
		}
		catch(InvalidCredentialException e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public List<Transaction> getTransactionOnDate(Customer walletHolder, String date) {
		return wo.findTransactionOnDate(walletHolder.getUserID(), date);
	}

}
