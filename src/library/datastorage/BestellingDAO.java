/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.datastorage;

/**
 *
 * @author Erik
 */
public class BestellingDAO {
    
    public boolean addBestelling(double totaalprijs)
    {
        boolean result = false;
        
        if(totaalprijs != 0.00)
        {
            // First open the database connection.
            DatabaseConnection connection = new DatabaseConnection();
            if(connection.openConnection())
            {
               
                result = connection.executeSQLUpdateStatement(
                    "INSERT INTO bestelling(Totaalprijs,Tafel) VALUES ('"+totaalprijs+"','2');");
                
                // Finished with the connection, so close it.
                connection.closeConnection();
            }
           
        }
        
        return result;
    }
}
