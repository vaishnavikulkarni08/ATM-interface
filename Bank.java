import java.util.*;
public class Bank{
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    public Bank(String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNewUserUUID(){
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
        do{
            uuid="";
            for(int i = 0;i<len;i++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            nonUnique = false;
            for(User u:this.users){
                if(uuid.compareTo(u.getUUID())==0){
                    nonUnique = true;
                    break;
                }
            }
        }while(nonUnique);
        return uuid;
    }
    public String getNewAccountUUID(){
      String uuid;
      Random rng = new Random();
      int len = 10;
      boolean nonUnique;
      do{
          uuid="";
          for(int i = 0;i<len;i++){
              uuid += ((Integer)rng.nextInt(10)).toString();
          }
          nonUnique = false;
          for(Account a:this.accounts){
              if(uuid.compareTo(a.getUUID())==0){
                  nonUnique = true;
                  break;
              }
          }
      }while(nonUnique);
      return uuid;  

    }
    public void addAccount(Account anAct){
        this.accounts.add(anAct);
    }

    public User addUser(String firstName,String lastName,String pin){
        User newUser = new User(firstName,lastName,pin,this);
        this.users.add(newUser);
        Account newAccount = new Account("Savings",newUser,this);
     
         newUser.addAccount(newAccount);
         this.accounts.add(newAccount);

         return newUser;
    }
    public User userLogin(String userID,String pin){
        for(User u:this.users){
            if(u.getUUID().compareTo(userID)==0 && u.validatePin(pin)){
                return u;
            }
        }
        return null;

    }
    public String getName(){
        return this.name;
    }
}
