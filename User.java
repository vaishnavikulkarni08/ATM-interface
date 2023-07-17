import java.util.*;
import java.security.*;
import java.lang.*;
public class User{
    private String firstName; 
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account>accounts;
    
    public User(String firstName,String lastName,String pin,Bank theBank){
        this.firstName = firstName;
        this.lastName = lastName;
		try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        }catch(NoSuchAlgorithmException e){
            System.err.println("error,caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
		this.uuid = theBank.getNewUserUUID();
        this.accounts = new ArrayList<Account>();
		System.out.println("New User : "+firstName+" "+lastName+" with ID : "+this.uuid+" created");
    }
    public void addAccount(Account anAct){
        this.accounts.add(anAct);
    }
    public String getUUID(){
        return this.uuid;
    }
    public boolean validatePin(String aPin){
         try{
             MessageDigest md = MessageDigest.getInstance("MD5");
             return MessageDigest.isEqual(md.digest(aPin.getBytes()),this.pinHash);
         }catch(NoSuchAlgorithmException e){
            System.err.println("error,caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
         }
         return false;
    }
	public String getFirstName(){
        return this.firstName;
    }

    public void printAccountSummary(){
        System.out.println(this.firstName+"'s account Summary :");
        for(int a = 0;a<this.accounts.size();a++){
            System.out.println((a+1)+" "+this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }
    public int numAccounts(){
        return this.accounts.size();
    }
    public void printActTransHistory(int actIdx){
        this.accounts.get(actIdx).printTransHistory();
    }
    public double getAccountBalance(int actIdx){
        return this.accounts.get(actIdx).getBalance();
    }
    public String getActUUID(int actIdx){
        return this.accounts.get(actIdx).getUUID();
    }
    public void addActTransaction(int actIdx,double amount,String memo){
        this.accounts.get(actIdx).addTransaction(amount,memo);
    }
}
