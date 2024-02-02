package WalletMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import WalletEntities.Customer;
import WalletEntities.Transaction;
import WalletEntities.Wallet;

import WalletServices.Service;
import WalletServices.ServiceImplementation;

public class MainClass {

	public static void main(String[] args) {

		Service walletService = new ServiceImplementation();
		MainClass obj = new MainClass();
		Customer c;
		Wallet wallet;

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			System.out.println(
					"\n****************************Welcome to Payment Wallet Application***************************");

			int option;

			do {

				System.out.println("\nChoose an option:");
				System.out.println("\n1. Register");
				System.out.println("2. Login");
				System.out.println("3. Exit");

				System.out.println("\nEnter your option (1/2/3): ");

				option = Integer.parseInt(br.readLine());

				switch (option) {

				case 1:
					System.out.println("\nEnter your First Name: ");
					String firstName = br.readLine();

					System.out.println("\nEnter your Last Name: ");
					String lastName = br.readLine();

					System.out.println("\nEnter your Mobile Number: ");
					String mobileNumber = br.readLine();

					System.out.println("\nEnter your Email ID: ");
					String email = br.readLine();

					System.out.println("\nEnter 4 Digit Pin: ");
					int pin = Integer.parseInt(br.readLine());

					System.out.println("\nConfirm Pin: ");
					int confirmPin = Integer.parseInt(br.readLine());

					boolean valid = walletService.validateFields(firstName, lastName, mobileNumber, email, pin,
							confirmPin);

					if (valid) {
						wallet = new Wallet(pin);
						c = new Customer(firstName, lastName, mobileNumber, email, wallet);

						c = walletService.createWalletAccount(c);

						System.out.println("\nNew Wallet Created!");
						System.out.println("\nYour User ID   : " + c.getUserID());
						System.out.println("Your Wallet ID : " + c.getWallet().getWalletID());

						obj.menu(c);
					}

					break;

				case 2:

					System.out.println("\nEnter your User ID: ");
					int userID = Integer.parseInt(br.readLine());

					System.out.println("\nEnter your Wallet ID: ");
					int walletID = Integer.parseInt(br.readLine());

					System.out.println("\nEnter your Wallet Pin: ");
					int walletpin = Integer.parseInt(br.readLine());

					c = walletService.getWalletAccount(userID, walletID, walletpin);

					if (c == null) {
						System.out.println("\nInvalid Login Credentials!");
					} else {
						System.out.println("\nUser Logged In Successfully! Welcome " + c.getFirstName());
						obj.menu(c);
					}

					break;

				case 3:
					System.out.println("\nExited from Payment wallet Application!");
					break;

				default:
					System.out.println("\nInvalid option!");
					break;
				}
			} while (option != 3);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	void menu(Customer c) {

		Service walletService = new ServiceImplementation();

		int optionMenu;

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			do {

				System.out.println("\n*********************MENU*********************");
				System.out.println("\n1. Add amount to the payment wallet account\r");
				System.out.println("2.  Show payment wallet account balance");
				System.out.println("3. Transfer funds from one account to another");
				System.out.println("4. Withdraw Money");
				System.out.println("5. View Last 10 Transactions");
				System.out.println("6. View Transactions of a Date");
				System.out.println("7. Logout");

				System.out.println("\nEnter your option: ");

				optionMenu = Integer.parseInt(br.readLine());

				switch (optionMenu) {

				case 1:

					System.out.println("Your Current Balance: " + c.getWallet().getBalance());

					System.out.println("\nEnter the Amount to Deposit: ");
					int amount = Integer.parseInt(br.readLine());

					c = walletService.addAmount(c, amount);

					System.out.println("\nYour Updated Balance: " + c.getWallet().getBalance());

					break;

				case 2:

					System.out.println("\nYour Wallet Balance: " + walletService.getBalance(c.getUserID()));

					break;

				case 3:

					System.out.println("\nEnter Recipients' Wallet ID: ");
					int recipientWalletID = Integer.parseInt(br.readLine());

					System.out.println("Enter Recipients' User ID: ");
					int recipientUserID = Integer.parseInt(br.readLine());

					System.out.println("\nYour Current Balance: " + c.getWallet().getBalance());

					System.out.println("\nEnter Amount to Transfer: ");
					int transferAmount = Integer.parseInt(br.readLine());

					if (walletService.recipientValidity(recipientUserID, recipientWalletID)) {

						if (walletService.balanceValidity(c, transferAmount) == true
								&& walletService.amountValidity(transferAmount) == true) {

							walletService.transferFunds(c, recipientUserID, transferAmount);

							System.out.println("\nAmount Tranferred Successfully");
							System.out.println("\nYour Updated Balance: " + c.getWallet().getBalance());
						}
					}

					break;

				case 4:

					System.out.println("\nEnter Amount to Withdraw: ");
					int withdrawAmount = Integer.parseInt(br.readLine());

					if(walletService.amountValidity(withdrawAmount)) {

						if (walletService.balanceValidity(c, withdrawAmount)) {

							c = walletService.withdrawAmount(c, withdrawAmount);
							System.out.println("\nAmount Withdraw Successful");
							System.out.println("\nAmount Withdrawn    : " + withdrawAmount);
							System.out.println("Your Updated Balance: " + c.getWallet().getBalance());
						}
					}

					break;

				case 5:

					List<Transaction> lastTenTransactions = walletService.getLastTenTransaction(c);

					if(lastTenTransactions.isEmpty()) {

						System.out.println("\nNo Transactions");
					}
					else {
						System.out.println("\n--------------------------------------------Last Ten Transaction---------------------------------------------\n");

						for(Transaction t : lastTenTransactions) {
							System.out.println(t);
						}
					}

					break;

				case 6:

					System.out.println("\nEnter the Date in YYYY-MM-DD format: ");;
					String date = br.readLine();

					if(walletService.dateValidity(date)) {
   
						List<Transaction> transactionsOnDate = walletService.getTransactionOnDate(c, date);

						if(transactionsOnDate.isEmpty()) {

							System.out.println("\nNo Transactions on Specified Date");
						}
						else {

							System.out.println("\n---------------------------------------Transactions on Given Date----------------------------------------\n");
							
							transactionsOnDate.stream().forEach(t->System.out.println(t));
							/*for(Transaction t : transactionsOnDate) {
								System.out.println(t);
							}*/
						}
					}
					break;

				case 7:
					System.out.println("\nUser Logged Out from Payment wallet Application!");
					break;

				default:
					System.out.println("Invalid Option!");
					break;
				}

			} while (optionMenu != 7);
		} catch (NumberFormatException | IOException e) {

			e.printStackTrace();
		}
	}
}
