import java.util.*;
import java.lang.*;
public class ATM{
    public static void main(String[]args){
        Scanner sc = new Scanner(System.in);
        Bank theBank = new Bank("State Bank Of India");
        User aUser = theBank.addUser("Shraddha","Kandagal","1111");
        Account newAccount = new Account("Checking",aUser,theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true){
            curUser = ATM.mainMenuPrompt(theBank,sc);
			ATM.printUserMenu(curUser,sc);
        }
    }
    public static User mainMenuPrompt(Bank theBank,Scanner sc){
        String userID;
        String pin;
        User authUser;
        do{
            System.out.println("\n\nWelcome to "+theBank.getName()+"\n\n");
            System.out.print("Enter User ID : ");
            userID = sc.nextLine();
            System.out.println();
            System.out.print("Enter Pin : ");
            pin = sc.nextLine();
			authUser = theBank.userLogin(userID,pin);
            if(authUser == null){
                System.out.println("incorrect User ID/Pin, Please try again");
            }
        }while(authUser==null);//continue login until successful login
        return authUser;
    }
    public static void printUserMenu(User theUser,Scanner sc){
        theUser.printAccountSummary();
		int choice;
        do{
            System.out.println("Welcome "+theUser.getFirstName()+" What would you like to do");
            System.out.println("1>Show Transaction History");
            System.out.println("2>withdraw");
            System.out.println("3>Deposite");
            System.out.println("4>Transfer");
            System.out.println("5>Quit");
            System.out.println();
            System.out.println("Enter Your Choice : ");
            choice = sc.nextInt();
            if(choice<1 || choice>5){
                System.out.println("Invalid Choice"+" Please choose between 1-5 ");  
            } 
        }while(choice<1 || choice>5);
        switch(choice){
            case 1:
                ATM.showTransactionHistory(theUser,sc);
                break;
            case 2:
                ATM.withdrawFunds(theUser,sc);
                break;
            case 3:
                ATM.depositeFunds(theUser,sc);
                break;
            case 4:
                ATM.transferFunds(theUser,sc);
                break;
            case 5:
                 sc.nextLine();
                 break;
        }
        if(choice != 5){
            ATM.printUserMenu(theUser,sc);
        }
    }
    public static void showTransactionHistory(User theUser,Scanner sc){
        int theAct;
        do{
            System.out.printf("Enter the number (1-%d) of the account\n"+" whose transaction you waant to see : ",theUser.numAccounts());
            theAct = sc.nextInt()-1;
            if(theAct < 0 || theAct >= theUser.numAccounts()){
                System.out.println("Invalid account. please try again.....");
            }
        }while(theAct < 0 || theAct >= theUser.numAccounts());
        theUser.printActTransHistory(theAct);
    }
    public static void transferFunds(User theUser,Scanner sc){
        int fromAct;
        int toAct;
        double amount;
        double actBal;
        do{
            System.out.printf("Enter the number (1-%d) of the account\n"+"to transfer from:",theUser.numAccounts());
            fromAct = sc.nextInt()-1;
            if(fromAct < 0 || fromAct >= theUser.numAccounts()){
                System.out.println("Invalid account. please try again.....");
            }
        }while(fromAct < 0 || fromAct >= theUser.numAccounts());
        actBal = theUser.getAccountBalance(fromAct);

        do{
            System.out.printf("Enter the number (1-%d) of the account\n"+"to transfer to:",theUser.numAccounts());
            toAct = sc.nextInt()-1;
            if(toAct < 0 || toAct >= theUser.numAccounts()){
                System.out.println("Invalid account. please try again.....");
            }
        }while(toAct < 0 || toAct >= theUser.numAccounts());
        do{
            System.out.println("Enter the amount to transfer (max than $"+actBal+") : $");
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero");
            }else if(amount > actBal){
                System.out.println("Amount must not be greater than \n"+"balance of $"+actBal);
            }
        }while(amount < 0 || amount>actBal);
        theUser.addActTransaction(fromAct,-1*amount,String.format("Transfer to account "+theUser.getActUUID(toAct)));
        theUser.addActTransaction(toAct,amount,String.format("Transfer to account "+theUser.getActUUID(fromAct)));
    }
    public static void withdrawFunds(User theUser,Scanner sc){
        int fromAct;
        String memo;
        double amount;
        double actBal;
        do{
            System.out.printf("Enter the number (1-%d) of the account\n"+"Where to withdraw :",theUser.numAccounts());
            fromAct = sc.nextInt()-1;
            if(fromAct < 0 || fromAct >= theUser.numAccounts()){
                System.out.println("Invalid account. please try again.....");
            }
        }while(fromAct < 0 || fromAct >= theUser.numAccounts());
        actBal = theUser.getAccountBalance(fromAct);
        do{
            System.out.println("Enter the amount to withdraw (max $ "+actBal+"): $");
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero");
            }else if(amount > actBal){
                System.out.println("Amount must not be greater than \n"+"balance of $"+actBal);
            }
        }while(amount < 0 || amount>actBal);
        sc.nextLine();
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();
        theUser.addActTransaction(fromAct,-1*amount,memo);        
    }
    public static void depositeFunds(User theUser,Scanner sc){
        int toAct;
        String memo;
        double amount;
        double actBal;
        do{
            System.out.printf("Enter the number (1-%d) of the account\n"+"Where to Deposite:",theUser.numAccounts());
            toAct = sc.nextInt()-1;
            if(toAct < 0 || toAct >= theUser.numAccounts()){
                System.out.println("Invalid account. please try again.....");
            }
        }while(toAct < 0 || toAct >= theUser.numAccounts());
        actBal = theUser.getAccountBalance(toAct);
        do{
            System.out.print("Enter the amount to deposite (max than $"+actBal+") :$");
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than zero");
            }
        }while(amount < 0 );
        sc.nextLine();
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();
        theUser.addActTransaction(toAct,amount,memo);             
    }
}
