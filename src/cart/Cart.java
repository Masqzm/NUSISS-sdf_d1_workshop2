package cart;

import java.util.ArrayList;
import java.util.List;

// Class to manage shopping cart functions
public class Cart {
    private String currentUser;
    private List<String> cartList;      // cartList of last user logged in 
    private ShoppingCartDB db;

    public Cart(ShoppingCartDB db) {
        currentUser = "";
        cartList = new ArrayList<>();
        this.db = db;
    }

    public void login(String user) {
        if(user.isEmpty())
        {
            System.out.println("Username given is blank! Please try again");
            return;
        }

        cartList = db.load(user);
        currentUser = user;

        System.out.printf("%s, your cart ", user);

        if(cartList.isEmpty())
            System.out.println("is empty");
        else
        {
            System.out.println("contains the following items");
            for(int i = 0; i < cartList.size(); i++)
                System.out.println((i+1) + ". " + cartList.get(i));
        }
    }

    public void save() {
        if(currentUser.isEmpty())
            System.out.println("Please login first to save cart items!");
        else {
            db.save(currentUser, cartList);

            System.out.println("Your cart has been saved");
        }
    }
    
    public void add(String[] itemsArr) {        
        // Loops thru each item in inputArr
        for(String item : itemsArr) {
            // Remove whitespaces surrounding item & convert to lower case
            String itemProcessed = item.trim().toLowerCase();
            
            // If itemsList contains item
            if(cartList.contains(itemProcessed))
                System.out.println("You have " + itemProcessed + " in your cart");
            else
            {
                cartList.add(itemProcessed);
                System.out.println(itemProcessed + " added to cart");
            }           
        }    
    }
    
    public void delete(int itemIndex) {  
        if(cartList.isEmpty()) {
            System.out.println("Your cart is empty");
            return;
        }

        // Adjust itemIndex input (to match user input index to actual list index) 
        itemIndex -= 1;

        if(itemIndex < 0 || itemIndex > cartList.size()-1) {
            System.out.println("Incorrect item index");
            return;
        }

        // Remove item as it is valid
        String itemRemoved = cartList.remove(itemIndex);
        System.out.println(itemRemoved + " removed from cart");
    }

    public void listCartItems() {
        if(cartList.isEmpty())
            System.out.println("Your cart is empty");
        
        // Print every item in itemsList
        for (int i = 0; i < cartList.size(); i++) 
            System.out.printf("%d. %s\n", i+1, cartList.get(i));
    }

    public void listUsers() {
        int totalUsers = db.getUsersList().size();

        if(totalUsers > 0) 
        {
            System.out.println("The following users are registered");
            for(int i = 0; i < totalUsers; i++)
                System.out.println((i+1) + ". " + db.getUsersList().get(i));
        }
        else
            System.out.println("No registered users found!");        
    }
}
