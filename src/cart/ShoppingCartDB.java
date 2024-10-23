package cart;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Class to manage shopping cart database
public class ShoppingCartDB {
    private final String dirPath;
    private List<String> usersList;

    public List<String> getUsersList() {
        return usersList;
    }

    public ShoppingCartDB(String dirPath) {
        // Create dir if it doesn't exist
        File newDir = new File(dirPath);
        if(!newDir.exists()) 
            newDir.mkdir();

        this.dirPath = dirPath;

        // Get record of users (by looking at db files found)
        File[] files = newDir.listFiles();
        usersList = new ArrayList<>();

        if(files.length > 0)
        {
            for (File file : files)
            {
                String filename = file.getName();

                if (file.isFile() && filename.contains(Constants.DEFAULT_DB_EXTENSION))
                {
                    // remove .db and to get username
                    String user = filename.replace(Constants.DEFAULT_DB_EXTENSION, "");
                    usersList.add(user);
                }
            }
        }
    }

    // Reads user's cart from db file
    public List<String> load(String user) {        
        List<String> cartList = new ArrayList<>();

        // Get abstract pathname (e.g. "cartdb" + "/" + user + ".db")
        String absPathName = dirPath + File.separator + user + Constants.DEFAULT_DB_EXTENSION;
        
        File file = new File(absPathName); 

        // Create new db file if file doesnt exist (ie. new user)
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch(IOException ex) {
                System.err.println("ERROR: DB FILE CREATION ERROR!");
                ex.printStackTrace();
            }
        }

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            // Read and store each line into cart list
            String line = "";

            try {
                while((line = br.readLine()) != null) 
                    cartList.add(line);
            } catch(IOException ex) {
                System.err.println("ERROR: DB FILE READ ERROR!");
                ex.printStackTrace();
            }

            br.close();
            fr.close();
        } catch(FileNotFoundException ex) {
            System.err.println("ERROR: DB FILE NOT FOUND!");
            ex.printStackTrace();
        } catch(IOException ex) {
            System.err.println("ERROR: DB FILE IO ERROR!");
            ex.printStackTrace();
        } 

        // User succesfully registered, add them to the list
        if(!usersList.contains(user))
            usersList.add(user);

        return cartList;
    }

    // Writes user's cart to db file
    public void save(String user, List<String> cartList) {
        String absPathName = dirPath + File.separator + user + Constants.DEFAULT_DB_EXTENSION;

        try {
            FileWriter fw = new FileWriter(absPathName);
            BufferedWriter bw = new BufferedWriter(fw);

            for (String item : cartList) {
                bw.write(item);
                bw.newLine();
            }

            bw.flush();
            bw.close();
            fw.close();
        } catch(IOException ex) {
            System.err.println("ERROR: DB FILE WRITE ERROR!");
            ex.printStackTrace();
        }
    }
}


// TO DO: 
// - load cart from file
// - save cart to file