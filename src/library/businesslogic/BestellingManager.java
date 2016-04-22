/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.businesslogic;

import java.util.ArrayList;
import java.util.HashMap;
import library.datastorage.GerechtDAO;
import library.datastorage.BestellingDAO;
import library.datastorage.BestelregelDAO;
import library.domain.Gerecht;

/**
 *
 * @author Erik
 */
public class BestellingManager {
    
    private final HashMap<Integer, Gerecht> gerechten;
    
    public BestellingManager()
    {
        gerechten = new HashMap();
        
    }
    
    public Gerecht findGerecht(int gerechtNumber)
    {
        Gerecht gerecht = gerechten.get(gerechtNumber);
        
        if(gerecht == null)
        {
            // Gerecht may not have been loaded from the database yet. Try to
            // do so.
            GerechtDAO gerechtDAO = new GerechtDAO();
            gerecht = gerechtDAO.findGerecht(gerechtNumber);
                       
        }

        return gerecht;
    }
    
    public boolean addBestelling(double totaalprijs)
    {
        boolean result = true;
                          
            if(result)
            {
                // zet de bestelling in de database
                BestellingDAO bestellingDAO = new BestellingDAO();
                result = bestellingDAO.addBestelling(totaalprijs);
                
            }
            else {
            result = false;
            }
        
        return result;
    }
    
    public boolean addBestelregel(ArrayList namen, ArrayList aantallen){
        boolean result = true;
        
        if (result){
            BestelregelDAO bestelregelDAO = new BestelregelDAO();
            result = bestelregelDAO.addBestelregel(namen, aantallen);
        }
        else{
            result = false;
        }
        return result;
    }
}
