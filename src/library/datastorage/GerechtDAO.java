/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.datastorage;

import library.domain.Gerecht;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Erik
 */
public class GerechtDAO {
    
    public GerechtDAO()
    {
        // Nothing to be initialized. This is a stateless class. Constructor
        // has been added to explicitely make this clear.
    }
    
    /**
     * 
     * @param gerechtNumber identifies the member to be loaded from the database
     * 
     * @return the Gerecht object to be found. In case gerecht could not be found,
 null is returned.
     */
    public Gerecht findGerecht(int gerechtNumber)
    {
        Gerecht gerecht = null;
        
        // First open a database connnection
        DatabaseConnection connection = new DatabaseConnection();
        if(connection.openConnection())
        {
            // If a connection was successfully setup, execute the SELECT statement.
            ResultSet resultset = connection.executeSQLSelectStatement(
                "SELECT * FROM gerecht WHERE GerechtNumber = " + gerechtNumber + ";");

            if(resultset != null)
            {
                try
                {
                    // The gerechtNumber for a gerecht is unique, so in case the
                    // resultset does contain data, we need its first entry.
                    if(resultset.next())
                    {
                        int gerechtNumberFromDb = resultset.getInt("GerechtNumber");
                        String NameFromDb = resultset.getString("Name");
                        double prijsFromDb = resultset.getDouble("Prijs");

                        gerecht = new Gerecht(
                            gerechtNumberFromDb,
                            NameFromDb,
                            prijsFromDb);
                        
                        gerecht.setBereidingstijd(resultset.getString("Bereidingstijd"));
                        gerecht.setOmschrijving(resultset.getString("Omschrijving"));
                        gerecht.setAllergieinfo(resultset.getString("Allergieinfo"));
                        gerecht.setVegetarisch(resultset.getInt("Vegetarisch"));
                    }
                }
                catch(SQLException e)
                {
                    System.out.println(e);
                    gerecht = null;
                }
            }
            // else an error occurred leave 'gerecht' to null.
            
            // We had a database connection opened. Since we're finished,
            // we need to close it.
            connection.closeConnection();
        }
        
        return gerecht;
    }

}
