package cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Console-based shopping cart
// • list               -   lists the contents of the cart; 
//                          if cart is empty, prints appropriate msg;
//                          contents of cart are numbered on listing them;
// • add [item, ...]    -   adds one or more items to the cart;
//                          multiple items are separated by a comma (,);  
//                          users cannot add an item that is already in the cart;
//                          item names are case insensitive; 
// • delete [index]     -   deletes an item from the cart based on item’s index (from list cmd);
//                          for incorrect index provided, an error msg is displayed; 
public class Main {
    public static void main(String[] args) {
        List<String> itemsList = new ArrayList<>();     // list to store cart items

        Scanner inputScan = new Scanner(System.in);
        String cmd = "";

        System.out.println("Welcome to your shopping cart");
        
        while(!cmd.equals("exit")) 
        {
            // Get prompt from user
            System.out.printf("> ");
            // Get first token of input, ignores whitespaces surrounding it
            // (e.g. if input is " add item1, item2", cmd = "add")
            cmd = inputScan.next();  

            switch (cmd) {
                case "list":
                    if(itemsList.isEmpty())
                        System.out.println("Your cart is empty");
                    
                    // Print every item in itemsList
                    for (int i = 0; i < itemsList.size(); i++) 
                        System.out.printf("%d. %s\n", i+1, itemsList.get(i));
                    break;

                case "add":
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

                case "delete":                    
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

                case "q":
                case "exit":
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
