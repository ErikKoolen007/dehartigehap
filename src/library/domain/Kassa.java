/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.domain;

/**
 *
 * @author Erik
 */
public class Kassa {
    private double subtotaal;
    
    public void telOp(double bedrag){
        subtotaal+=bedrag;
    }
    
    public void haalAf(double bedrag1){
        subtotaal-=bedrag1;
    }
    
    public double getSubtotaal(){
        return subtotaal;
    }
    
    public void reset(){
        subtotaal = 0;
    }
    
}
