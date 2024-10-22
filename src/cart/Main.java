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
// login [user]     -   login and displays user's cart items
// save             -   saves 
// users            -   lists registered users
public class Main {
    public static void main(String[] args) {
        String dirPath = Constants.DEFAULT_DIRPATH;      // db dir path (default is "db")

        // If args given, use arg[0] as db dir path
        if(args.length > 0)
            dirPath = args[0];

        // Create dir if it doesn't exist
        File newDirectory = new File(dirPath);
        if(!newDirectory.exists()) 
            newDirectory.mkdir();


        List<String> itemsList = new ArrayList<>();     // list to store cart items

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
                    if(itemsList.isEmpty())
                        System.out.println("Your cart is empty");
                    
                    // Print every item in itemsList
                    for (int i = 0; i < itemsList.size(); i++) 
                        System.out.printf("%d. %s\n", i+1, itemsList.get(i));
                    break;

                case Constants.CMD_ADD:
                    // Get remaining line of input. Note nextLine() includes surrounding whitespaces
                    String[] inputArr = inputScan.nextLine().trim().split(",");
                    
                    // Loops thru each item in inputArr
                    for(String item : inputArr) {
                        // Remove whitespaces surrounding item & convert to lower case
                        String itemProcessed = item.trim().toLowerCase();
                        
                        // If itemsList contains item
                        if(itemsList.contains(itemProcessed))
                            System.out.println("You have " + itemProcessed + " in your cart");
                        else
                        {
                            itemsList.add(itemProcessed);
                            System.out.println(itemProcessed + " added to cart");
                        }           
                    }      
                    break;

                case Constants.CMD_DELETE:                    
                    if(itemsList.isEmpty()) {
                        System.out.println("Your cart is empty");
                        break;
                    }
                        
                    // Get next token of input, ignoring whitespaces surrounding it
                    // Assumption: index given is an int & is +1 of real index in list
                    int index = Integer.parseInt(inputScan.next()) - 1;
                    
                    if(index < 0 || index > itemsList.size()-1) {
                        System.out.println("Incorrect item index");
                        break;
                    }

                    // Remove item as it is valid
                    String itemRemoved = itemsList.remove(index);
                    System.out.println(itemRemoved + " removed from cart");

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
