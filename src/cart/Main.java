package cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

// Packaging Java app as JAR
// jar -cvfe cart.jar cart.Main -C classes .
// Running JAR
// java -jar cart.jar

// Console-based shopping cart
// list             -   lists the contents of the cart; 
//                      if cart is empty, prints appropriate msg;
//                      contents of cart are numbered on listing them;
// add [item, ...]  -   adds one or more items to the cart;
//                      multiple items are separated by a comma (,);  
//                      users cannot add an item that is already in the cart;
//                      item names are case insensitive; 
// delete [index]   -   deletes an item from the cart based on item’s index (from list cmd);
//                      for incorrect index provided, an error msg is displayed; 
// D3 extensions
// login [user]     -   load the specified user’s database file from cart db dir;
//                      if db doesn't exist, create new file;
//                      e.g. login fred -> loads file from cartdb/fred.db
//                      list the cart items;
// save             -   saves contents of cart to user's cart file;
//                      prints error msg if save cmd given without login of any user;
// users            -   lists registered users
public class Main {
    public static void main(String[] args) {
        String dirPath = Constants.DEFAULT_DIRPATH;      // db dir path (default is "db")

        // If args given, use arg[0] as db dir path
        if(args.length > 0)
            dirPath = args[0];

        // Instantiate db & cart app
        ShoppingCartDB cartDB = new ShoppingCartDB(dirPath);
        Cart cart = new Cart(cartDB);

        Scanner inputScan = new Scanner(System.in);
        String cmd = "";

        System.out.println("Welcome to your shopping cart");
        
        while(!cmd.equals(Constants.CMD_EXIT)) 
        {
            // Get prompt from user
            System.out.printf("> ");
            // Get first token of input, ignores whitespaces surrounding it
            // (e.g. if input is " add item1, item2", cmd = "add")
            cmd = inputScan.next();  

            switch (cmd) {
                case Constants.CMD_LIST:
                    cart.listCartItems();
                    break;

                case Constants.CMD_ADD:
                    // Get remaining line of input. Note nextLine() includes surrounding whitespaces
                    String[] inputArr = inputScan.nextLine().trim().split(",");
                    cart.add(inputArr);
                    break;

                case Constants.CMD_DELETE:                                            
                    // Get next token of input, ignoring whitespaces surrounding it
                    int index = Integer.parseInt(inputScan.next());
                    cart.delete(index);
                    break;
                    
                case Constants.CMD_LOGIN:
                    String user = inputScan.nextLine().trim();
                    cart.login(user);
                    break;
                
                case Constants.CMD_SAVE:
                    cart.save();
                    break;
                    
                case Constants.CMD_USERS:
                    cart.listUsers();
                    break;

                case Constants.CMD_EXIT:
                    System.out.println("Exiting program...");
                    inputScan.close();
                    System.exit(0);
                    break;

                // invalid cmd - prints error
                default:
                    System.out.println("INVALID COMMAND ENTERED!");
                    break;
            }
        } 
        inputScan.close();
        System.out.println("Exiting program...");
    }

    
}
