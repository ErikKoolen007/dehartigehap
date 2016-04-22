/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Erik
 */
public class BestelregelDAO {
    
    int lastai;
    int gerechtnrFromDb;
    double prijsFromDb;
    
    public boolean addBestelregel(ArrayList namen, ArrayList aantallen)
    {
        boolean result = false;
         
        
            // First open the database connection.
            DatabaseConnection connection = new DatabaseConnection();
            
            if(connection.openConnection())
            {
                // If a connection was successfully setup, execute the SELECT statement.
                        ResultSet resultset1 = connection.executeSQLSelectStatement(
                            "SELECT MAX(BestelNr) FROM bestelling;");

                    if(resultset1 != null)
                    {
                        try
                        {
                           
                            if(resultset1.next())
                            {
                                lastai = resultset1.getInt("MAX(BestelNr)");

                            }
                        }
                        catch(SQLException e)
                        {
                            System.out.println(e);

                        }
                    }
                for (int i =0; i !=namen.size(); i++) {
                    // If a connection was successfully setup, execute the SELECT statement.
                    ResultSet resultset = connection.executeSQLSelectStatement(
                        "SELECT GerechtNumber,Prijs FROM gerecht WHERE Name = '" + namen.get(i) + "';");
                
                    if(resultset != null)
                    {
                        try
                        {
                            // The gerechtNumber for a gerecht is unique, so in case the
                            // resultset does contain data, we need its first entry.
                            if(resultset.next())
                            {
                                gerechtnrFromDb = resultset.getInt("GerechtNumber");
                                prijsFromDb = resultset.getDouble("Prijs");

                            }
                        }
                        catch(SQLException e)
                        {
                            System.out.println(e);

                        }
                    }                                                          
                   
                    connection.executeSQLUpdateStatement(
                    "INSERT INTO bestelregel VALUES ('"+lastai+"','"+gerechtnrFromDb+"','"+prijsFromDb+"','"+aantallen.get(i)+"');");
                        
                                                   
                }
                // Finished with the connection, so close it.
                connection.closeConnection();
                
                result=true;
            }                  
        
        return result;
    }
}
