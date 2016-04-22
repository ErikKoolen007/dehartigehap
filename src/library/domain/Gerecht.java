/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.domain;


/**
 *
 * @author Erik
 */

public class Gerecht {
    
    private int gerechtNumber;
    private String Name;
    private double prijs;
    private String bereidingstijd;
    private String omschrijving;
    private String allergieinfo;
    private int    vegetarisch;
            
    public Gerecht(int gerechtNumber, String Name, double prijs)
    {
        this.gerechtNumber = gerechtNumber;
        this.Name = Name;
        this.prijs = prijs;
        
        bereidingstijd = "";
        omschrijving = "";
        allergieinfo = "";
        vegetarisch = 0;
       
    }
    
    public double getPrijs()
    {
        return prijs;
    }

    public void setPrijs(double prijs)
    {
        this.prijs = prijs;
    }

    public String getOmschrijving()
    {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving)
    {
        this.omschrijving = omschrijving;
    }

    public int getGerechtNumber()
    {
        return gerechtNumber;
    }

    public void setGerechtNumber(int gerechtNumber)
    {
        this.gerechtNumber = gerechtNumber;
    }

    public String getAllergieinfo() 
    {
        return allergieinfo;
    }

    public void setAllergieinfo(String allergieinfo)
    {
        this.allergieinfo = allergieinfo;
    }

    public String getBereidingstijd()
    {
        return bereidingstijd;
    }

    public void setBereidingstijd(String bereidingstijd)
    {
        this.bereidingstijd = bereidingstijd;
    }

    public int getVegetarisch()
    {
        return vegetarisch;
    }

    public void setVegetarisch(int vegetarisch)
    {
        this.vegetarisch = vegetarisch;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String Name)
    {
        this.Name = Name;
    }
    
    @Override
    public boolean equals(Object o)
    {
        boolean equal = false;
        
        if(o == this)
        {
            // Dezelfde instantie van de klasse, dus per definitie hetzelfde.
            equal = true;
        }
        else
        {
            if(o instanceof Gerecht)
            {
                Gerecht l = (Gerecht)o;
                
                // Boek wordt geidentificeerd door ISBN, dus alleen hierop
                // controlleren is voldoend.
                equal = this.gerechtNumber == l.gerechtNumber;
            }
        }
        
        return equal;
    }
    
    @Override
    public int hashCode()
    {
        // Deze implementatie is gebaseerd op de best practice zoals beschreven
        // in Effective Java, 2nd edition, Joshua Bloch.
        
        // gerechtNumber is uniek, dus voldoende als hashcode.
        return gerechtNumber;
    }
}
