/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.presentation;

import library.businesslogic.BestellingManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import library.domain.Gerecht;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import library.domain.Kassa;

/**
 *
 * @author Erik
 */
public class BestellingUI extends javax.swing.JFrame {

    // These GUI components have been defined as fields, because they are
    // used in several methods.
    private JTextField textFieldGerechtNr;
    private JTextArea textAreaGerechtInfo;
    private JTextField textFieldAfrekenNum;
    private JTextField textFieldRijNr;
    private JButton searchButton;
    private JButton clearButton;
    private JButton bestelButton;
    private final Kassa kassa;
    double totaalprijs = 0.00;
    public ArrayList namen = new ArrayList();
    public ArrayList aantallen = new ArrayList();
    
    String[] columns = {"Rijnummer","Naam","Prijs in €", "Omschrijving","Aantal","Subtotaal in €"};
    DefaultTableModel model = new DefaultTableModel(columns,0);
        
    JTable table = new JTable(model);
    private final TableColumnModel tcm = table.getColumnModel();
            
    // The BestellingManager to delegate the real work (use cases!) to.
    private final BestellingManager manager;

    // A reference to the last gerecht that has been found. At start up and
    // in case a gerecht could not be found for some gerecht nr, this
    // field has the value null.
    private Gerecht currentGerecht;
    
    /**
     * Creates new form BestellingUI
     */
    public BestellingUI() {
        initComponents();
        setupFrame();
        
        manager = new BestellingManager();
        currentGerecht = null;
        kassa = new Kassa();
    }

    private void setupFrame()
    {
        setTitle("Bestel uw gerecht!");
        
        // The layout is a Borderlayout with
        // North: search panel
        // Center: used to display information about the member; for this
        //         simple POC, it is just a multiline text box.
        // South: panel for operations on the currently displayed member.
        //        For this POC it is just the delete button; a possible extension
        //        is edit.
        // East + west: not used
        JPanel contentPane = (JPanel)getContentPane();
        contentPane.setLayout(new BorderLayout(5, 5));
        
        // Setup of the north-area
        JPanel searchPanel = createSearchPanel();

         // Setup of the center-area
        JPanel gerechtInfoPanel = new JPanel();
        GridLayout grid = new GridLayout();
        gerechtInfoPanel.setLayout(grid);
      
        gerechtInfoPanel.add(table);
        //add scrollpane
        JScrollPane scrollPane = new JScrollPane(table);
        gerechtInfoPanel.add(scrollPane);
        
        // Setup of the south-area
        
        JPanel gerechtOperationsPanel = createGerechtOperationsPanel();
           
        contentPane.add(searchPanel, BorderLayout.NORTH);
        contentPane.add(gerechtInfoPanel, BorderLayout.CENTER);
        contentPane.add(gerechtOperationsPanel, BorderLayout.SOUTH);

        // Event handlers
        searchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int gerechtNr = Integer.parseInt(textFieldGerechtNr.getText());
                doFindGerecht(gerechtNr);
            }
        });  
        
        clearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int rijNr = Integer.parseInt(textFieldRijNr.getText());
                double subtot = Double.parseDouble(model.getValueAt(rijNr,5).toString());
                
                kassa.haalAf(subtot);
                textFieldAfrekenNum.setText("Totaal: € " + kassa.getSubtotaal());
                model.removeRow(rijNr);
                
                if(model.getRowCount() >0){
                    
                    for(int i=0;i<model.getRowCount(); i++){
                        model.setValueAt(i, i, 0);
                    }
                }                
            }
        });
        
        bestelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                doAfrekenBestelling();
            }
        });
         
        textFieldGerechtNr.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int gerechtNr = Integer.parseInt(textFieldGerechtNr.getText());
                doFindGerecht(gerechtNr);
            }
        });
        
        // Now let the layout managers do their job.
        pack();
        
        // At last, set the size of the window.
        setSize(1200, 768);
        
        //center screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
    }
    
    private JPanel createSearchPanel()
    {
        // Make a panel with controls to be able to search a gerecht based on
        // its gerecht number.
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        
        searchPanel.add(new JLabel("Voer gerechtnummer in:"));
        
        textFieldGerechtNr = new JTextField(10);
        textFieldGerechtNr.setSize(new Dimension(6, 20));
        searchPanel.add(textFieldGerechtNr);
        
        searchButton = new JButton("Voeg toe");
        searchButton.setSize(new Dimension(73, 23));
        searchPanel.add(searchButton);
        
        return searchPanel;
    }
    
    
    private JPanel createGerechtOperationsPanel()
    {
        JPanel gerechtOperationsPanel = new JPanel();
        gerechtOperationsPanel.setLayout(
                new BoxLayout(gerechtOperationsPanel, BoxLayout.X_AXIS));
        
        gerechtOperationsPanel.add(new JLabel("Delete rijnummer van bestelling: "));
        
        textFieldRijNr = new JTextField(10);
        textFieldRijNr.setSize(new Dimension(6, 20));
        gerechtOperationsPanel.add(textFieldRijNr);
        
        clearButton = new JButton("Delete");
        clearButton.setSize(new Dimension(73, 23));
        gerechtOperationsPanel.add(clearButton); 
        
        textFieldAfrekenNum = new JTextField(10);
        textFieldAfrekenNum.setSize(new Dimension(6, 20));
        textFieldAfrekenNum.setEditable(false);
        textFieldAfrekenNum.setHorizontalAlignment(JTextField.RIGHT);
        gerechtOperationsPanel.add(textFieldAfrekenNum);
        
        bestelButton = new JButton("Bestellen");
        bestelButton.setSize(new Dimension(103, 23));
        gerechtOperationsPanel.add(bestelButton);
        
        return gerechtOperationsPanel;
    }
    
    private void doFindGerecht(int gerechtNr)
    {
        currentGerecht = manager.findGerecht(gerechtNr);
        
        if (currentGerecht == null)
        {
            JOptionPane.showMessageDialog(null,"Gerecht is niet gevonden");
        }
        
        String afrekenInfo = "";
        
                
        double prijs;
        
        if(currentGerecht != null)
        {
            //maak de naam kolom en omschrijving kolom groter
            tcm.getColumn(3).setPreferredWidth(500);
            tcm.getColumn(1).setPreferredWidth(170);
            
            
            //eerste rij
            if(model.getRowCount() == 0){
                
                model.addRow(new Object[]{0,currentGerecht.getName(),currentGerecht.getPrijs(),currentGerecht.getOmschrijving(),1,currentGerecht.getPrijs()});
                
            }
            //als het niet de eerste rij is
            else{
                int duplicateRow = -1;
                //check voor duplicates
                for(int row=0; row<model.getRowCount();  row++){
                    if(currentGerecht.getName().equals(model.getValueAt(row,1))){
                        duplicateRow = row;
                        break;
                    }
                }
                //geen duplicate dus normaal toevoegen
                if(duplicateRow==-1){
                    int rijnummer = model.getRowCount();
                    model.addRow(new Object[]{rijnummer,currentGerecht.getName(),currentGerecht.getPrijs(),currentGerecht.getOmschrijving(),1,currentGerecht.getPrijs()});
                    
                }
                //wel duplicate dus aantal aanpassen
                else{
                    int aantal = Integer.parseInt(model.getValueAt(duplicateRow,4).toString());
                    
                    double subtotaal = currentGerecht.getPrijs() * (aantal+1);
                    model.setValueAt(aantal+1, duplicateRow,4 );
                    model.setValueAt(subtotaal,duplicateRow,5);
                }
            }
            //houd totaal bij in kassa klasse
            prijs = currentGerecht.getPrijs();
            kassa.telOp(prijs);
            //sla totaal op
            afrekenInfo = 
                   "Totaal: € " + kassa.getSubtotaal();
            
        }

        //zet totaal op het scherm
        textFieldAfrekenNum.setText(afrekenInfo);
        
    }
    
    private void doAfrekenBestelling()
    {
        totaalprijs = kassa.getSubtotaal();
        if(totaalprijs != 0.00)
        {
            //sla de namen en aantallen van de finale rijen op in een array
            for(int i=0;i<model.getRowCount(); i++){
                
                String naam = model.getValueAt(i,1).toString();
                namen.add(naam);
                int finalaantal = Integer.parseInt(model.getValueAt(i,4).toString());
                aantallen.add(finalaantal);
                    
            }
            //stuur array mee naar DAO's
            boolean gerechtBesteld = manager.addBestelling(totaalprijs);
            boolean bestelregel = manager.addBestelregel(namen, aantallen);
            
            if(gerechtBesteld ==true && bestelregel==true)
            {
                JOptionPane.showMessageDialog(null,"Gerecht is succesvol besteld!");
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Er is iets fout gegaan, vraag om hulp.");
            }

            currentGerecht = null;
            textFieldRijNr.setText("");
            textFieldAfrekenNum.setText("");
            textFieldGerechtNr.setText("");
            kassa.reset();
            model.setRowCount(0);
            namen.clear();
            aantallen.clear();
            
        }
    }
            
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
