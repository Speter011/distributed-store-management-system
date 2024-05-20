package com.napier.presentation;

import com.napier.business.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class setUpView extends View{

    // Window used to display the details
    private JFrame window;
    //buttons
    private JButton PriceControlButton, LoyaltyCardButton, FinanceButton,
                    AnalysisButton, Button1, Button2, Button3, OKButton,
                    StatsButton, MStatsButton, Button4;

    //labels, and textfields
    private JLabel message, message2, analysis;
    private JTextField input, input2, input3;
    private String id, loyalty, customerName, purchaseNum, approval, price, offer;
    private int flag = 1; //to switch the functionality of ok button

    //creating class instances from business layer
    DBQueryLoyalty queryLoyalty = new DBQueryLoyalty();
    DBQueryCustInsert queryNewCust = new DBQueryCustInsert();
    DBQueryCustDelete queryCustDelete = new DBQueryCustDelete();
    DBQueryInvPrice queryInvPrice = new DBQueryInvPrice();
    DBQueryStatistics queryStatistics = new DBQueryStatistics();
    DBQueryFinance queryFinance = new DBQueryFinance();
    DBQueryCheck queryCheck = new DBQueryCheck();

    public setUpView()
    {

        // Create the GUI components
        window = new JFrame();

        //initializing ui elements
        PriceControlButton = new JButton("Price Control");
        LoyaltyCardButton = new JButton("Loyalty Cards");
        FinanceButton = new JButton("Finance Approval");
        AnalysisButton = new JButton("Get Statistics");
        Button1 = new JButton("Click to Update!");
        Button2 = new JButton("Click to Add!");
        Button3 = new JButton("Click to Delete!");
        Button4 = new JButton("Update financial approval status");
        StatsButton = new JButton("Get Statistics!");
        MStatsButton = new JButton("More Statistics!");
        OKButton = new JButton("Ok");


        //more elements in ui
        message = new JLabel();
        message2 = new JLabel();
        analysis = new JLabel("");
        input = new JTextField(10);
        input2 = new JTextField(10);
        input3 = new JTextField(10);
        // Set the GUI layout
        window.setLayout(new GridLayout(2,2));

        //add elements
        window.add(PriceControlButton);
        window.add(LoyaltyCardButton);
        window.add(FinanceButton);
        window.add(AnalysisButton);
        // Set the window title
        window.setTitle("View");

        // Resize the window to fit the components
        window.pack();
        window.setSize(450, 130);
        window.setLocationRelativeTo(null);
        // Display the window


        //inventory checking
        String name = queryCheck.check();
        if(name != null){
            JOptionPane.showMessageDialog(null, "Warning! " + name + " is running out of stock!");
        }
        window.setVisible(true);

    }
    public void update(Observable o, Object arg)
    {
        window.pack();
    }
    //mediator, event listeners
    public void eventHandling() {
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == PriceControlButton){

                    //new window
                    JFrame priceWindow = new JFrame("Price Control");
                    Container cp = priceWindow.getContentPane();
                    FlowLayout flow = new FlowLayout();
                    cp.setLayout(flow);

                    //elements
                    cp.add(input);
                    cp.add(input2);
                    cp.add(input3);
                    cp.add(message);
                    cp.add(message2);
                    OKButton.setText("OK");
                    cp.add(OKButton);


                    message.setText("Provide id of item, set price and offer state, then click OK: ");
                    message2.setText("Check console for item list!");
                    queryInvPrice.list();
                    //change OKbutton
                    flag = 5;
                    priceWindow.setSize(350, 150);
                    priceWindow.setLocationRelativeTo(null);
                    priceWindow.setVisible(true);

                }
                //loyalty feature and customer insertion/deletion
                else if(e.getSource() == LoyaltyCardButton){
                    JFrame LoyaltyWindow = new JFrame("Loyalty Card");
                    Container cp = LoyaltyWindow.getContentPane();
                    FlowLayout flow = new FlowLayout();
                    cp.setLayout(flow);
                    cp.add(Button1);
                    cp.add(Button2);
                    cp.add(Button3);

                    LoyaltyWindow.setSize(500, 130);
                    cp.add(input);
                    cp.add(message);
                    cp.add(OKButton);
                    OKButton.setVisible(false);
                    LoyaltyWindow.setLocationRelativeTo(null);
                    LoyaltyWindow.setVisible(true);
                    message.setText("Provide id of Customer, then choose button: ");

                }
                else if(e.getSource() == Button1){
                    flag = 1;
                    id = input.getText();
                    message.setText("Provide loyalty card status(yes/no), then press OK: ");
                    OKButton.setVisible(true);


                } else if (e.getSource() == OKButton) {

                    switch(flag){

                            //loyalty card attribute update
                        case 1:
                            loyalty = input.getText();
                            System.out.println(id);
                            System.out.println(loyalty);
                            queryLoyalty.update(id, loyalty);
                            message.setText("Provide id of Customer, then choose button: ");
                            //reset text
                            input.setText("");
                            break;

                        case 2:
                            customerName = input.getText();
                            System.out.println(customerName);
                            message.setText("Provide loyalty card status (yes/no), then press OK: ");
                            flag = 3; //ask for purchase number
                            OKButton.setVisible(true);
                            //reset text
                            input.setText("");
                            break;

                        case 3:
                            loyalty = input.getText();
                            System.out.println(loyalty);
                            message.setText("Provide customer purchase number: ");
                            flag = 4; // send to approval status
                            OKButton.setVisible(true);
                            //reset text
                            input.setText("");
                            break;

                            //adding new customer
                        case 4:
                            purchaseNum = input.getText();
                            System.out.println(purchaseNum);
                            message.setText("Provide approval status: ");
                            flag = 6; //insert query
                            OKButton.setVisible(true);
                            //reset text
                            input.setText("");
                            break;

                        case 6:
                            approval = input.getText();
                            System.out.println(approval);
                            queryNewCust.insert(id, customerName, loyalty, purchaseNum, approval);
                            message.setText("Provide id of Customer, then choose button: ");
                            //reset text
                            input.setText("");

                        //changing price column
                        case 5:
                            id = input.getText();
                            price = input2.getText();
                            offer = input3.getText();
                            queryInvPrice.priceChange(id, price, offer);
                            //reset textfields
                            input.setText("");
                            input2.setText("");
                            input3.setText("");
                    }


                } else if (e.getSource() == Button2) {
                    id = input.getText();
                    System.out.println(id);
                    //send to customer loyalty ask
                    flag = 2;
                    message.setText("Provide customer name, then press OK: ");
                    OKButton.setVisible(true);
                    //reset text
                    input.setText("");

                } else if (e.getSource() == Button3) {
                    id = input.getText();
                    System.out.println(id);
                    //send to deletion query
                    queryCustDelete.delete(id);
                    //reset text
                    input.setText("");


                //financial approval
                }else if (e.getSource() == Button4){
                 String text = input.getText();
                 String approval = input2.getText();
                 queryFinance.update(text, approval);
                 JOptionPane.showMessageDialog(null, "Finance approval in progress!");
                    //reset text
                    input.setText("");

                } else if (e.getSource() == FinanceButton) {
                    //new window
                    JFrame FinanceWindow = new JFrame("Financial approval");
                    JLabel text = new JLabel();
                    text.setText("Input the id of customer and financial approval status: ");
                    Container cp = FinanceWindow.getContentPane();
                    FlowLayout flow = new FlowLayout();
                    cp.setLayout(flow);

                    cp.add(text);
                    cp.add(input);
                    cp.add(input2);
                    cp.add(Button4);

                    FinanceWindow.pack();
                    FinanceWindow.setSize(350, 120);
                    FinanceWindow.setLocationRelativeTo(null);
                    FinanceWindow.setVisible(true);

                    //statistics
                } else if (e.getSource() == AnalysisButton) {

                    JFrame analysisWindow = new JFrame("Analysis");
                    Container cp = analysisWindow.getContentPane();
                    FlowLayout flow = new FlowLayout();
                    cp.setLayout(flow);

                    //show analysis
                    cp.add(StatsButton);
                    cp.add(MStatsButton);
                    StatsButton.setBounds(20, 20, 80, 10);
                    cp.add(analysis);

                    //analysisWindow.pack();
                    analysisWindow.setSize(100, 100);
                    analysisWindow.setLocationRelativeTo(null);
                    analysisWindow.setVisible(true);

                } else if (e.getSource() == StatsButton){

                    queryStatistics.stats();
                }
                else if (e.getSource() == MStatsButton){

                    queryStatistics.moreStats();
                    queryStatistics.list();
                }


            }
        };

        //listeners
        //price control
        PriceControlButton.addActionListener(buttonListener);

        //loyalty
        LoyaltyCardButton.addActionListener(buttonListener);
        Button1.addActionListener(buttonListener);
        Button2.addActionListener(buttonListener);
        Button3.addActionListener(buttonListener);
        OKButton.addActionListener(buttonListener);

        //finance
        FinanceButton.addActionListener(buttonListener);
        Button4.addActionListener(buttonListener);

        //analysis
        AnalysisButton.addActionListener(buttonListener);
        StatsButton.addActionListener(buttonListener);
        MStatsButton.addActionListener(buttonListener);
    }
}