package W21_GUI_Project;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.*;
/**
 * The InvestmentGUI class is responsible for creating Stock and MutualFund investments, but
 * implementing a graphical user interface. This UI lets users buy, sell, update investment prices,
 * compute the gain the their portfolio, and search their portfolio for existing investments via symbol,
 * name keyword, and/or price range with ease.
 * 
 * @author Roandre Villegas
 * @version 1.0
 */
public class InvestmentGUI extends JFrame{
    /**
     * Panel that holds all the panels
     */
    private JPanel controlPanel;

    /**
     * Panel for each menu option
     */
    private JPanel panel = new JPanel();

    /**
     * TextField for symbol input
     */
    private JTextField symInput;

    /**
     * TextField for name input
     */
    private JTextField nameInput;

    /**
     * TextField for quantity input
     */
    private JTextField quantityInput;

    /**
     * TextField for price input
     */
    private JTextField priceInput;

    /**
     * TextField for symbol to sell
     */
    private JTextField sellSymInput;

    /**
     * TextField for quantity to sell
     */
    private JTextField sellQuanInput;

    /**
     * TextField for price to sell
     */
    private JTextField sellPriceInput;

    /**
     * TextField for symbol to update
     */
    private JTextField symUpdate;

    /**
     * TextField for name to update
     */
    private JTextField nameUpdate;

    /**
     * TextField for price to update 
     */
    private JTextField priceUpdate;

    /**
     * TextField for the gain of the Portfolio
     */
    private JTextField gain;

    /**
     * TextField for the keywords to search
     */
    private JTextField keywordsInput;

    /**
     * TextField for lower price to search
     */
    private JTextField lowInput;

    /**
     * TextField for upper price to search
     */
    private JTextField highInput;

    /**
     * TextArea for messages to appear to the user
     */
    private JTextArea messages;

    /**
     * TextArea for showing the individual gain of each investment
     */
    private JTextArea individualGain;

    /**
     * Label shown at initial interface
     */
    private JLabel title;

    /**
     * "Update" menu item
     */
    private JMenuItem update;

    /**
     * "Next" button in update
     */
    private JButton next;

    /**
     * "Prev" button in update
     */
    private JButton previous;

    /**
     * Combo box that contains "Stock" and "MutualFund"
     */
    private JComboBox<String> typeList;

    /**
     * Stock object
     */
    Stock newStock = null;

    /**
     * MutualFund object
     */
    MutualFund newMutualFund = null;

    /**
     * Strings that go into ComboBox
     */
    String[] type = {"Stock", "MutualFund"};

    /**
     * Array where parsed name words are stored
     */
    String[] parsedName;

    /**
     * MenuListener for menu options
     */
    MenuListener ear = new MenuListener();

    /**
     * ButtonListener for buttons
     */
    ButtonListener back = new ButtonListener();

    /**
     * Flag if user chose "buy"
     */
    boolean buy = false;

    /**
     * Flag if user chose "sell"
     */
    boolean sell = false; 

    /**
     * Flag if user chose "search"
     */
    boolean search = false;

    /**
     * Flag if symbol is found
     */
    boolean found = false;

    /**
     * Flag if investment already exists
     */
    boolean exists = false;

    /**
     * Flag if exception was thrown
     */
    boolean exception;

    /**
     * Flag if updating is successful
     */
    boolean success;

    /**
     * Number of shares to sell
     */
    Integer sharesToSell = 0;

    /**
     * For loop incrementor 
     */
    int i;

    /**
     * For loop incrementor
     */
    int j;

    /**
     * Price to sell for each share
     */
    double sellPrice;

    /**
     * Current gain of the portfolio
     */
    double currGain;

    /**
     * Total gain of the portfolio
     */
    double totalGain;

    /**
     * Represents "share" or "shares"
     */
    String share;

    /**
     * Represents "share" or "shares"
     */
    String share2;

    /**
     * Represents "lost" or "gained"
     */
    String loss;

    /**
     * Represents name of the investment
     */
    String investmentName;

    /**
     * ArrayList to hold all investment objects
     */
    ArrayList<Investment> investments = new ArrayList<>();

    /**
     * ArrayList to hold duplicate objects
     */
    ArrayList<Investment> duplicates = new ArrayList<>();

    /**
     * Positions in the ArrayList where a keyword occurs
     */
    ArrayList<Integer> positions = null;

    /**
     * HashMap that contains the parsed words of investment names
     */
    HashMap<String, ArrayList<Integer>> keyWords = new HashMap<String, ArrayList<Integer>>();
    
    /**
     * Constructor to call GUI method
     */
    public InvestmentGUI(){
        super();
        prepareGUI();
    }
    /**
     * Clears all the text fields in the "buy" menu option
     */
    public void clearBuyFields(){
        symInput.setText("");
        nameInput.setText("");
        quantityInput.setText("");
        priceInput.setText("");
    }
    /**
     * Clears all the text fields in the "sell" menu option
     */
    public void clearSellFields(){
        sellSymInput.setText("");
        sellQuanInput.setText("");
        sellPriceInput.setText("");
    }
    /**
     * Clears all the text fields in the "search" menu option
     */
    public void clearSearchFields(){
        symInput.setText("");
        keywordsInput.setText("");
        lowInput.setText("");
        highInput.setText("");
    }
    /**
     * Finds the intersection of ArrayLists in HashMap
     * @param inputList the ArrayList to compare to
     * @return intersection of ArrayLists
     */
    public static ArrayList<Integer> findIntersection(ArrayList<ArrayList<Integer>> inputList){
        ArrayList<ArrayList<Integer>> set = new ArrayList<>();
        ArrayList<Integer> intersection = new ArrayList<Integer>();
        ArrayList<Integer> returnSet = new ArrayList<>();
        int j;
        for(int i = 0; i < inputList.size(); i++){
            intersection.addAll(inputList.get(0));
        }
        for(int i = 0; i < inputList.size(); i++){
            set.add(inputList.get(i));
            intersection.retainAll(set.get(i));
        }
        for(int i = 0; i < intersection.size(); i++){
            for(j = 0; j < i; j++){
                if(intersection.get(i) == intersection.get(j)){
                    break;
                }
            }
            if(i == j){
                returnSet.add(intersection.get(i));
            }
        }
        return returnSet;
    }
    /**
     * Formats and sets up the GUI 
     */
    public void prepareGUI(){
        setTitle("ePortfolio");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout());

        /**
         * Custom font
         */
        Font titleFont = new Font(Font.SERIF, Font.BOLD, 16);
        title = new JLabel("<html>Welcome to ePortfolio.<br><br>Choose a command from the Commands menu to buy or sell an investment, update prices for all investments, get gain for the portfolio, search for relevant investments, or quit the program.<html>", JLabel.LEFT);
        title.setFont(titleFont);

        /**
         * Menu that holds all commands
         */
        JMenu commands = new JMenu("Commands");

        /**
         * "Buy" menu option
         */
        JMenuItem buy = new JMenuItem("Buy New Investments");
        buy.addActionListener(ear);
        commands.add(buy);

        /**
         * "Sell" menu option
         */
        JMenuItem sell = new JMenuItem("Sell Existing Investments");
        sell.addActionListener(ear);
        commands.add(sell);

        /**
         * "Update" menu option
         */
        update = new JMenuItem("Update Prices of Existing Investments");
        update.addActionListener(ear);
        commands.add(update);

        /**
         * "GetGain" menu option
         */
        JMenuItem getGain = new JMenuItem("Get The Gain of Your Portfolio");
        getGain.addActionListener(ear);
        commands.add(getGain);

        /**
         * "Search" menu option
         */
        JMenuItem search = new JMenuItem("Search For An Investment");
        search.addActionListener(ear);
        commands.add(search);

        /**
         * "Quit" menu option
         */
        JMenuItem exit = new JMenuItem("Quit");
        exit.addActionListener(ear);
        commands.add(exit);

        /**
         * MenuBar that holds the "commands" menu
         */
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(commands);
        controlPanel.add(menuBar);

        setJMenuBar(menuBar);
        add(title);
    }
    /**
     * Responsible for listening to actions performed on the menubar and performing tasks
     * based on the menu option chosen
     */
    private class MenuListener implements ActionListener{
        /**
         * Opens new formatted panels based on menu option chosen by the user
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            buy = false;
            sell = false;
            search = false;
            String menuOption = e.getActionCommand();
            Font labelFont = new Font(Font.SERIF, Font.PLAIN, 14);
            Font headerFont = new Font(Font.SERIF, Font.BOLD, 16);
            if(menuOption.equals("Buy New Investments")){
                buy = true;
                panel.setVisible(false);
                controlPanel.remove(panel);

                title.setText("");
                panel = new JPanel();
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                /**
                 * Header for the "buy" panel
                 */
                JLabel header = new JLabel("Buying an investment");
                header.setBounds(10, 20, 200, 25);
                header.setFont(headerFont);
                panel.add(header);

                typeList = new JComboBox<>(type);
                typeList.addActionListener(back);
                typeList.setBounds(150, 50, 165, 25);
                panel.add(typeList);

                //text fields
                symInput = new JTextField(20);
                symInput.setBounds(150, 80, 165, 25);
                panel.add(symInput);

                nameInput = new JTextField(20);
                nameInput.setBounds(150, 110, 225, 25);
                panel.add(nameInput);

                quantityInput = new JTextField(20);
                quantityInput.setBounds(150, 140, 165, 25);
                panel.add(quantityInput);

                priceInput = new JTextField(20);
                priceInput.setBounds(150, 170, 165, 25);
                panel.add(priceInput);

                /**
                 * Label for the "type" TextField
                 */
                JLabel typeLabel = new JLabel("Type");
                typeLabel.setBounds(10, 50, 110, 25);
                typeLabel.setFont(labelFont);
                panel.add(typeLabel);

                /**
                 * Label for the "symbol" TextField
                 */
                JLabel symLabel = new JLabel("Symbol");
                symLabel.setBounds(10, 80, 110, 25);
                symLabel.setFont(labelFont);
                panel.add(symLabel);

                /**
                 * Label for the "name" TextField
                 */
                JLabel nameLabel = new JLabel("Name");
                nameLabel.setBounds(10, 110, 110, 25);
                nameLabel.setFont(labelFont);
                panel.add(nameLabel);

                /**
                 * Label for the "quantity" TextField
                 */
                JLabel quantityLabel = new JLabel("Quantity");
                quantityLabel.setBounds(10, 140, 110, 25);
                quantityLabel.setFont(labelFont);
                panel.add(quantityLabel);
                
                /**
                 * Label for the "price" TextField
                 */
                JLabel priceLabel = new JLabel("Price");
                priceLabel.setBounds(10, 170, 110, 25);
                priceLabel.setFont(labelFont);
                panel.add(priceLabel);

                /**
                 * Label for the "messages" TextArea
                 */
                JLabel msgLabel = new JLabel("Messages");
                msgLabel.setBounds(10, 250, 110, 25);
                msgLabel.setFont(labelFont);
                panel.add(msgLabel);

                //text areas
                messages = new JTextArea();
                messages.setBounds(10, 280, 560, 240);
                messages.setEditable(false);
                panel.add(messages);

                /**
                 * Scroll bar for TextArea
                 */
                JScrollPane scrolledText = new JScrollPane(messages);
                scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrolledText.setBounds(10, 280, 560, 240);
                panel.add(scrolledText);

                /**
                 * Reset button
                 */
                JButton reset = new JButton("Reset");
                reset.setBounds(400, 80, 110, 45);
                reset.addActionListener(back);
                panel.add(reset);

                /**
                 * Buy button
                 */
                JButton buy = new JButton("Buy");
                buy.setBounds(400, 140, 110, 45);
                buy.addActionListener(back);
                panel.add(buy);

                controlPanel.add(panel);
                add(controlPanel);
            }else if(menuOption.equals("Sell Existing Investments")){
                sell = true;
                panel.setVisible(false);
                controlPanel.remove(panel);

                title.setText("");
                panel = new JPanel();
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                /**
                 * Header for "sell" panel
                 */
                JLabel header = new JLabel("Selling an investment");
                header.setBounds(10, 20, 200, 25);
                header.setFont(headerFont);
                panel.add(header);

                //text fields
                sellSymInput = new JTextField(20);
                sellSymInput.setBounds(150, 50, 165, 25);
                panel.add(sellSymInput);

                sellQuanInput = new JTextField(20);
                sellQuanInput.setBounds(150, 110, 165, 25);
                panel.add(sellQuanInput);

                sellPriceInput = new JTextField(20);
                sellPriceInput.setBounds(150, 170, 165, 25);
                panel.add(sellPriceInput);

                /**
                 * Label for the "symbol" TextField
                 */
                JLabel symLabel = new JLabel("Symbol");
                symLabel.setBounds(10, 50, 110, 25);
                symLabel.setFont(labelFont);
                panel.add(symLabel);

                /**
                 * Label for the "quantity" TextField
                 */
                JLabel quanLabel = new JLabel("Quantity");
                quanLabel.setBounds(10, 110, 110, 25);
                quanLabel.setFont(labelFont);
                panel.add(quanLabel);

                /**
                 * Label for the "price" TextField
                 */
                JLabel priceLabel = new JLabel("Price");
                priceLabel.setBounds(10, 170, 110, 25);
                priceLabel.setFont(labelFont);
                panel.add(priceLabel);

                /**
                 * Label for the "messages" TextArea
                 */
                JLabel msgLabel = new JLabel("Messages");
                msgLabel.setBounds(10, 250, 110, 25);
                msgLabel.setFont(labelFont);
                panel.add(msgLabel);

                //text areas
                messages = new JTextArea();
                messages.setBounds(10, 280, 560, 240);
                messages.setEditable(false);
                panel.add(messages);

                /**
                 * Scroll bar for the TextArea
                 */
                JScrollPane scrolledText = new JScrollPane(messages);
                scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrolledText.setBounds(10, 280, 560, 240);
                panel.add(scrolledText);

                /**
                 * Reset button
                 */
                JButton reset = new JButton("Reset");
                reset.setBounds(400, 80, 110, 45);
                reset.addActionListener(back);
                panel.add(reset);

                /**
                 * Sell button
                 */
                JButton sell = new JButton("Sell");
                sell.setBounds(400, 140, 110, 45);
                sell.addActionListener(back);
                panel.add(sell);

                if(investments.size() == 0){
                    messages.append("You do not have any investments to sell\n");
                    sell.setEnabled(false);
                }else{
                    sell.setEnabled(true);
                }
                controlPanel.add(panel);
                add(controlPanel);
            }else if(menuOption.equals("Update Prices of Existing Investments")){
                panel.setVisible(false);
                controlPanel.remove(panel);

                title.setText("");
                panel = new JPanel();
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                /**
                 * Header for the "update" panel
                 */
                JLabel header = new JLabel("Updating investments");
                header.setBounds(10, 20, 200, 25);
                header.setFont(headerFont);
                panel.add(header);

                //text fields
                symUpdate = new JTextField(20);
                symUpdate.setBounds(150, 50, 165, 25);
                symUpdate.setEditable(false);
                panel.add(symUpdate);

                nameUpdate = new JTextField(20);
                nameUpdate.setBounds(150, 110, 165, 25);
                nameUpdate.setEditable(false);
                panel.add(nameUpdate);

                priceUpdate = new JTextField(20);
                priceUpdate.setBounds(150, 170, 165, 25);
                panel.add(priceUpdate);

                /**
                 * Label for symbol TextField
                 */
                JLabel symLabel = new JLabel("Symbol");
                symLabel.setBounds(10, 50, 110, 25);
                symLabel.setFont(labelFont);
                panel.add(symLabel);
            
                /**
                 * Label for name TextField
                 */
                JLabel nameLabel = new JLabel("Name");
                nameLabel.setBounds(10, 110, 110, 25);
                nameLabel.setFont(labelFont);
                panel.add(nameLabel);

                /**
                 * Label for price TextField
                 */
                JLabel priceLabel = new JLabel("Price");
                priceLabel.setBounds(10, 170, 110, 25);
                priceLabel.setFont(labelFont);
                panel.add(priceLabel);

                /**
                 * Label for messages TextArea
                 */
                JLabel msgLabel = new JLabel("Messages");
                msgLabel.setBounds(10, 250, 110, 25);
                msgLabel.setFont(labelFont);
                panel.add(msgLabel);

                //text areas
                messages = new JTextArea();
                messages.setBounds(10, 280, 560, 240);
                messages.setEditable(false);
                panel.add(messages);

                /**
                 * Scroll bar for TextArea
                 */
                JScrollPane scrolledText = new JScrollPane(messages);
                scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrolledText.setBounds(10, 280, 560, 240);
                panel.add(scrolledText);

                previous = new JButton("Prev");
                previous.setBounds(400, 50, 110, 45);
                previous.addActionListener(back);
                if(j == 0){
                    previous.setEnabled(false);
                }
                panel.add(previous);

                next = new JButton("Next");
                next.setBounds(400, 110, 110, 45);
                if(j == investments.size() - 1){
                    next.setEnabled(true);
                }
                next.addActionListener(back);
                panel.add(next);

                /**
                 * Save button
                 */
                JButton save = new JButton("Save");
                save.setBounds(400, 170, 110, 45);
                save.addActionListener(back);
                panel.add(save);

                if(investments.size() == 1 || investments.size() == 0){
                    previous.setEnabled(false);
                    next.setEnabled(false);
                    if(investments.size() == 0){
                        messages.append("You have no investments to update\n");
                    }
                }

                if(investments.size() > 0){
                    symUpdate.setText(investments.get(0).getSymbol());
                    nameUpdate.setText(investments.get(0).getName());
                }
                controlPanel.add(panel);
                add(controlPanel);
            }else if(menuOption.equals("Get The Gain of Your Portfolio")){ //fix this
                double currentGain = 0.0;
                double getGain = 0.0;
                panel.setVisible(false);
                controlPanel.remove(panel);

                title.setText("");
                panel = new JPanel();
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                /**
                 * GetGain header
                 */
                JLabel header = new JLabel("Getting total gain");
                header.setBounds(10, 15, 200, 25);
                header.setFont(headerFont);
                panel.add(header);

                /**
                 * Label for total gain TextField
                 */
                JLabel totalGain = new JLabel("Total Gain");
                totalGain.setBounds(10, 50, 200, 25);
                totalGain.setFont(labelFont);
                panel.add(totalGain);

                /**
                 * Label for individual gain TextArea
                 */
                JLabel individual = new JLabel("Individual Gains");
                individual.setBounds(10, 80, 200, 25);
                individual.setFont(labelFont);
                panel.add(individual);

                gain = new JTextField(20);
                gain.setEditable(false);
                gain.setBounds(100, 50, 200, 25);
                panel.add(gain);

                individualGain = new JTextArea();
                individualGain.setBounds(10, 110, 550, 425);
                individualGain.setEditable(false);
                panel.add(individualGain);

                /**
                 * Scroll bars for TextArea
                 */
                JScrollPane scrolledText = new JScrollPane(individualGain);
                scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrolledText.setBounds(10, 110, 550, 425);
                panel.add(scrolledText);

                for(int i = 0; i < investments.size(); i++){ //fix this
                    currentGain = investments.get(i).getGain();
                    individualGain.append(investments.get(i).getName() + ":\n");
                    individualGain.append("$"+String.valueOf(String.format("%.2f", currentGain) + "\n"));
                    getGain += currentGain;
                }
                gain.setText(String.valueOf(String.format("%.2f",getGain)));

                controlPanel.add(panel);
                add(controlPanel);

            }else if(menuOption.equals("Search For An Investment")){ //works
                search = true;
                panel.setVisible(false);
                controlPanel.remove(panel);

                title.setText("");
                panel = new JPanel();
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);

                /**
                 * Header for search panel
                 */
                JLabel header = new JLabel("Search investments");
                header.setBounds(10, 20, 200, 25);
                header.setFont(headerFont);
                panel.add(header);

                symInput = new JTextField(20);
                symInput.addActionListener(back);
                symInput.setBounds(150, 50, 165, 25);
                panel.add(symInput);

                //text fields
                keywordsInput = new JTextField(20);
                keywordsInput.setBounds(150, 80, 225, 25);
                panel.add(keywordsInput);

                lowInput = new JTextField(20);
                lowInput.setBounds(150, 110, 165, 25);
                panel.add(lowInput);

                highInput = new JTextField(20);
                highInput.setBounds(150, 140, 165, 25);
                panel.add(highInput);
                
                /**
                 * Label for symbol TextField
                 */
                JLabel symLabel = new JLabel("Symbol");
                symLabel.setBounds(10, 50, 110, 25);
                symLabel.setFont(labelFont);
                panel.add(symLabel);

                /**
                 * Label for keywords TextField
                 */
                JLabel nameLabel = new JLabel("Name keywords");
                nameLabel.setBounds(10, 80, 110, 25);
                nameLabel.setFont(labelFont);
                panel.add(nameLabel);

                /**
                 * Label for lower price TextField
                 */
                JLabel lowLabel = new JLabel("Low");
                lowLabel.setBounds(10, 110, 110, 25);
                lowLabel.setFont(labelFont);
                panel.add(lowLabel);
                
                /**
                 * Label for upper price TextField
                 */
                JLabel highLabel = new JLabel("High");
                highLabel.setBounds(10, 140, 110, 25);
                highLabel.setFont(labelFont);
                panel.add(highLabel);

                /**
                 * Label for results TextArea
                 */
                JLabel resultsLabel = new JLabel("Search results");
                resultsLabel.setBounds(10, 190, 110, 25);
                resultsLabel.setFont(labelFont);
                panel.add(resultsLabel);

                //text areas
                messages = new JTextArea();
                messages.setBounds(10, 220, 560, 240);
                messages.setEditable(false);
                panel.add(messages);

                /**
                 * Scroll bars for TextArea
                 */
                JScrollPane scrolledText = new JScrollPane(messages);
                scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrolledText.setBounds(10, 220, 560, 240);
                panel.add(scrolledText);

                /**
                 * Reset button
                 */
                JButton reset = new JButton("Reset");
                reset.setBounds(400, 50, 110, 45);
                reset.addActionListener(back);
                panel.add(reset);

                /**
                 * Search button
                 */
                JButton search = new JButton("Search");
                search.setBounds(400, 110, 110, 45);
                search.addActionListener(back);
                panel.add(search);

                controlPanel.add(panel);
                add(controlPanel);
            }else if(menuOption.equals("Quit")){
                System.exit(0);
            }else{
                System.out.println("Unexpected error");
            }
        }
    }
    /**
     * Responsible for listening to actions performed on the buttons and performing tasks
     * based on the button clicked
     */
    private class ButtonListener implements ActionListener{
        /**
         * Performs actions based on the button clicked by the user
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String button = e.getActionCommand();
            if(button.equals("Reset")){
                if(buy){
                    symInput.setText("");
                    nameInput.setText("");
                    quantityInput.setText("");
                    priceInput.setText("");
                }else if(sell){
                    sellSymInput.setText("");
                    sellQuanInput.setText("");
                    sellPriceInput.setText("");
                }else if(search){
                    symInput.setText("");
                    nameInput.setText("");
                    keywordsInput.setText("");
                    lowInput.setText("");
                    highInput.setText("");
                }
            }else if(button.equals("Buy")){ //done
                exception = false;
                messages.setText("");

                /**
                 * Stock or MutualFund
                 */
                String investmentType;

                /**
                 * Symbol to buy
                 */
                String symbol = "";

                /**
                 * Quantity to buy
                 */
                String quantity = "";

                /**
                 * Price of each share
                 */
                String price = "";

                /**
                 * Testing parse
                 */
                Double tempPrice = 0.0;

                /**
                 * Testing parse
                 */
                Integer tempQuantity = 0;
                investmentType = (String)typeList.getSelectedItem();
                if(symInput.getText().isEmpty() || symInput.getText().isBlank()){
                    messages.append("Please enter a valid symbol\n");
                    exception = true;
                }
                if(nameInput.getText().isEmpty() || nameInput.getText().isBlank()){
                    messages.append("Please enter a valid name\n");
                    exception = true;
                }
                if(quantityInput.getText().isEmpty() || quantityInput.getText().isBlank()){
                    messages.append("Please enter a valid quantity\n");
                    exception = true;
                }
                if(priceInput.getText().isEmpty() || priceInput.getText().isBlank()){
                    messages.append("Please enter a valid price\n");
                    exception = true;
                }else{
                    symbol = symInput.getText();
                    if(symbol.contains(" ")){
                        symbol = symbol.trim();
                    }
                    investmentName = nameInput.getText();
                    if(investmentName.contains(" ")){
                        investmentName = investmentName.trim();
                    }
                    quantity = quantityInput.getText();
                    if(quantity.contains(" ")){
                        quantity = quantity.trim();
                    }
                    price = priceInput.getText();
                    if(price.contains(" ")){
                        price = price.trim();
                    }
                    double bookValue = 0.0;
                    try{
                        tempPrice = Double.parseDouble(price);
                    }catch(NumberFormatException nfe){
                        messages.append("Please enter a valid price\n");
                        exception = true;
                    }
                    try{
                        tempQuantity = Integer.parseInt(quantity);
                    }catch(NumberFormatException nfe){
                        messages.append("Please enter a valid quantity\n");
                        exception = true;
                    }
                    if(tempPrice < 0.0){
                        messages.append("Please enter a valid price\n");
                        exception = true;
                    }
                    if(tempQuantity < 0){
                        messages.append("Please enter a valid quantity\n");
                        exception = true;
                    }
                    if(investmentType.equals("Stock")){
                        found = false;
                        
                        for(i = 0; i < investments.size(); i++){
                            if(symbol.equalsIgnoreCase(investments.get(i).getSymbol())){
                                found = true;
                                break;
                            }
                        }
                        if(found){
                            if(investments.get(i) instanceof MutualFund){
                                messages.append("Symbol exists in MutualFunds");
                                exception = true;
                            }else{
                                try{
                                    bookValue = (tempPrice * tempQuantity) + 9.99;
                                    investments.get(i).addQuantity(tempPrice, tempQuantity, bookValue);
                                }catch(NumberFormatException nfe){
                                    messages.append("Please enter a valid quantity and price\n");
                                    exception = true;
                                }
                            }
                        }else{
                            newStock = new Stock();
                            try{
                                newStock.setSymbol(symbol); 
                            }catch(Exception r){
                                messages.append(r.getMessage());
                                exception = true;
                            }
                            try{
                                newStock.setName(investmentName); 
                            }catch(Exception r){
                                messages.append(r.getMessage());
                                exception = true;
                            }
                            try{
                                newStock.setQuantity(tempQuantity);
                            }catch(NumberFormatException r){
                                messages.append(r.getMessage());
                                exception = true;
                            }
                            try{
                                newStock.setPrice(tempPrice);
                            }catch(NumberFormatException r){
                                messages.append(r.getMessage());
                                exception = true;
                            }
                            try{
                                newStock.setBookValue(tempPrice, tempQuantity);
                            }catch(NumberFormatException r){
                                messages.append("Error creating bookValue\n");
                            }
                            if(!exception){
                                investments.add(newStock);

                                /**
                                 * Duplicate object instantiation
                                 */
                                Stock saveStock = new Stock(newStock);
                                duplicates.add(saveStock);
                            }
                        }
                    }else{
                        found = false;
                        
                        for(i = 0; i < investments.size(); i++){
                            if(symbol.equalsIgnoreCase(investments.get(i).getSymbol())){
                                found = true;
                                break;
                            }
                        }
                        if(found){
                            if(investments.get(i) instanceof Stock){
                                messages.append("Symbol exists in Stocks");
                                exception = true;
                            }else{
                                try{
                                    bookValue = (tempPrice * tempQuantity) + 9.99;
                                    investments.get(i).addQuantity(tempPrice, tempQuantity, bookValue);
                                }catch(NumberFormatException nfe){
                                    messages.append("Please enter a valid quantity and price\n");
                                    exception = true;
                                }
                            }
                        }else{
                            newMutualFund = new MutualFund();
                            try{
                                newMutualFund.setSymbol(symbol);
                            }catch(Exception r){
                                messages.append(r.getMessage());
                                exception = true;
                            }
                            try{
                                newMutualFund.setName(investmentName);
                            }catch(Exception r){
                                messages.append(r.getMessage());
                                exception = true;
                            }
                            try{
                                newMutualFund.setQuantity(tempQuantity);
                            }catch(NumberFormatException r){
                                messages.append(r.getMessage());
                                exception = true;
                            }
                            try{
                                newMutualFund.setPrice(tempPrice);
                            }catch(NumberFormatException r){
                                messages.append(r.getMessage());
                                exception = true;
                            }
                            try{
                                newMutualFund.setBookValue(tempPrice, tempQuantity);
                            }catch(NumberFormatException r){
                                messages.append("Error creating bookValue\n");
                            }
                            if(!exception){
                                investments.add(newMutualFund);

                                /**
                                 * Duplicate object instantiation
                                 */
                                MutualFund saveMutualFund = new MutualFund(newMutualFund);
                                duplicates.add(saveMutualFund);
                            }
                        }
                    }
                }
                if(!exception){
                    keyWords.clear();
                    for(i = 0; i < investments.size(); i++){
                        parsedName = investments.get(i).getName().toLowerCase().split("[ ]+");
                        for(int k = 0; k < parsedName.length; k++){
                            positions = new ArrayList<Integer>();
                            if(!keyWords.containsKey(parsedName[k])){
                                positions.add(i);
                                keyWords.put(parsedName[k], positions);
                            } else{
                                keyWords.get(parsedName[k]).add(i);
                            }
                        }
                    }
                    messages.append("Stock bought successfully!");
                }
                clearBuyFields();
            }else if(button.equals("Sell")){
                exception = false;

                /**
                 * Symbol to sell
                 */
                String symToSell = "";

                /**
                 * Price to sell
                 */
                String priceToSell = "";

                /**
                 * Quantity to sell
                 */
                String quanToSell = "";
                messages.setText("");
                if(sellSymInput.getText().isEmpty() || sellSymInput.getText().isBlank()){
                    messages.append("Please enter the symbol of the investment you would like to sell\n");
                    exception = true;
                }
                if(sellQuanInput.getText().isEmpty() || sellQuanInput.getText().isBlank()){
                    messages.append("Please enter how many shares you would like to sell\n");
                    exception = true;
                }
                if(sellPriceInput.getText().isEmpty() || sellPriceInput.getText().isBlank()){
                    messages.append("Please enter how much you would like to sell each share for\n");
                    exception = true;
                }else{
                    symToSell = sellSymInput.getText();
                    if(symToSell.contains(" ")){
                        symToSell = symToSell.trim();
                    }
                    quanToSell = sellQuanInput.getText();
                    if(quanToSell.contains(" ")){
                        quanToSell = quanToSell.trim();
                    }
                    priceToSell = sellPriceInput.getText();
                    if(priceToSell.contains(" ")){
                        priceToSell = priceToSell.trim();
                    }
                    exists = false;
                    for(i = 0; i < investments.size(); i++){
                        if(symToSell.equalsIgnoreCase(investments.get(i).getSymbol())){
                            exists = true;
                            break;
                        }
                    }
                    if(exists){
                        if(investments.get(i) instanceof Stock){
                            try{
                                sharesToSell = Integer.parseInt(quanToSell);
                            }catch(NumberFormatException nfe){
                                messages.append("Please enter a valid quantity\n");
                                exception = true;
                            }
                            if(sharesToSell < 0){
                                messages.append("Please enter a valid quantity\n");
                                exception = true;
                            }
                            if(investments.get(i).getQuantity() < sharesToSell){
                                share = (investments.get(i).getQuantity()) == 1 ? "share" : "shares";
                                share2 = (sharesToSell == 1) ? "share" : "shares";
                                messages.append("You cannot sell "+sharesToSell+" "+share2+". You only have "+investments.get(i).getQuantity()+" "+share+".\n");
                                exception = true;
                            }else if(investments.get(i).getQuantity() > sharesToSell){
                                if(!exception){
                                    try{
                                        sellPrice = Double.parseDouble(priceToSell);
                                    }catch(NumberFormatException nfe){
                                        messages.append("Please enter a valid price\n");
                                        exception = true;
                                    }
                                    if(sellPrice < 0.0){
                                        messages.append("Please enter a valid price\n");
                                        exception = true;
                                    }
                                    currGain = ((Stock)investments.get(i)).partial(sellPrice, sharesToSell);
                                    totalGain += currGain;
                                    share2 = (sharesToSell == 1) ? "share" : "shares";
                                    loss = (currGain < 0) ? "lost" : "gained";
                                    if(currGain < 0)
                                        currGain *= -1;
    
                                    messages.append("You have "+loss+" $"+ String.format("%.2f",currGain) + " from selling "+sharesToSell+" "+share2+".\n");
                                    messages.append("The total gain of your portfolio right now is: $"+String.format("%.2f", totalGain) + "\n");
                                }
                            }else if(investments.get(i).getQuantity() == sharesToSell){
                                try{
                                    sellPrice = Double.parseDouble(priceToSell);
                                }catch(NumberFormatException nfe){
                                    messages.append("Please enter a valid price\n");
                                    exception = true;
                                }
                                if(sellPrice < 0.0){
                                    messages.append("Please enter a valid price\n");
                                    exception = true;
                                }
                                if(!exception){
                                    currGain = ((Stock)investments.get(i)).full(sellPrice, sharesToSell);
                                    totalGain += currGain;
                                    share2 = (sharesToSell == 1) ? "share" : "shares";
                                    loss = (currGain < 0) ? "lost" : "gained";
                                    if(currGain < 0)
                                        currGain *= -1;

                                    messages.append("You have "+loss+" $"+ String.format("%.2f",currGain) + " from selling "+sharesToSell+" "+share2+".\n");
                                    messages.append("The total gain of your portfolio right now is: $"+String.format("%.2f", totalGain) + "\n");
                                    investments.remove(i);
                                }
                                
                            }
                        }else if(investments.get(i) instanceof MutualFund){
                            try{
                                sharesToSell = Integer.parseInt(quanToSell);
                            }catch(NumberFormatException nfe){
                                messages.append("Please enter a valid quantity\n");
                                exception = true;
                            }
                            if(sharesToSell < 0){
                                messages.append("Please enter a valid quantity\n");
                                exception = true;
                            }
                            if(investments.get(i).getQuantity() < sharesToSell){
                                share = (investments.get(i).getQuantity()) == 1 ? "share" : "shares";
                                share2 = (sharesToSell == 1) ? "share" : "shares";
                                messages.append("You cannot sell "+sharesToSell+" "+share2+". You only have "+investments.get(i).getQuantity()+" "+share+".\n");
                                exception = true;
                            }else if(investments.get(i).getQuantity() > sharesToSell){
                                try{
                                    sellPrice = Double.parseDouble(priceToSell);
                                }catch(NumberFormatException nfe){
                                    messages.append("Please enter a valid price\n");
                                    exception = true;
                                }
                                if(sellPrice < 0.0){
                                    messages.append("Please enter a valid price\n");
                                    exception = true;
                                }
                                if(!exception){
                                    currGain = ((MutualFund)investments.get(i)).partial(sellPrice, sharesToSell);
                                    totalGain += currGain;
                                    share2 = (sharesToSell == 1) ? "share" : "shares";
                                    loss = (currGain < 0) ? "lost" : "gained";
                                    if(currGain < 0)
                                        currGain *= -1;
    
                                    messages.append("You have "+gain+" $"+ String.format("%.2f",currGain) + " from selling "+sharesToSell+" "+share2+".\n");
                                    messages.append("The total gain of your portfolio right now is: $"+String.format("%.2f", totalGain) + "\n");
                                }
                            }else if(investments.get(i).getQuantity() == sharesToSell){
                                try{
                                    sellPrice = Double.parseDouble(priceToSell);
                                }catch(NumberFormatException nfe){
                                    messages.append("Please enter a valid price\n");
                                    exception = true;
                                }
                                if(sellPrice < 0.0){
                                    messages.append("Please enter a valid price\n");
                                    exception = true;
                                }
                                if(!exception){
                                    currGain = ((MutualFund)investments.get(i)).full(sellPrice, sharesToSell);
                                    totalGain += currGain;
                                    share2 = (sharesToSell == 1) ? "share" : "shares";
                                    loss = (currGain < 0) ? "lost" : "gained";
                                    if(currGain < 0)
                                        currGain *= -1;
    
                                    messages.append("You have "+gain+" $"+ String.format("%.2f",currGain) + " from selling "+sharesToSell+" "+share2+".\n");
                                    messages.append("The total gain of your portfolio right now is: $"+String.format("%.2f", totalGain) + "\n");
                                    investments.remove(i);
                                }
                            }
                        }
                    }else{
                        messages.append("Symbol does not exist in your investments\n");
                        exception = true;
                    }
                    keyWords.clear();
                    for(i = 0; i < investments.size(); i++){
                        parsedName = investments.get(i).getName().toLowerCase().split("[ ]+");
                        for(int k = 0; k < parsedName.length; k++){
                            positions = new ArrayList<Integer>();
                            if(!keyWords.containsKey(parsedName[k])){
                                positions.add(i);
                                keyWords.put(parsedName[k], positions);
                            } else{
                                keyWords.get(parsedName[k]).add(i);
                            }
                        }
                    }
                    if(!exception){
                        messages.append("Investment sold successfully!\n");
                    }
                }
                clearSellFields();
            }else if(button.equals("Prev")){
                next.setEnabled(true);
                j--;
                if(j == 0){
                    previous.setEnabled(false);
                }
                symUpdate.setText(investments.get(j).getSymbol());
                nameUpdate.setText(investments.get(j).getName());
                priceUpdate.setText("");
            }else if(button.equals("Next")){
                previous.setEnabled(true);
                j++;
                if(j == investments.size() - 1){
                    next.setEnabled(false);
                }
                symUpdate.setText(investments.get(j).getSymbol());
                nameUpdate.setText(investments.get(j).getName());
                priceUpdate.setText("");
            }else if(button.equals("Save")){
                if(priceUpdate.getText().isEmpty() || priceUpdate.getText().isBlank()){
                    messages.append("Please enter a valid price\n");
                }else{
                    /**
                     * Price to save
                     */
                    String savePrice = priceUpdate.getText();
                    if(savePrice.contains(" ")){
                        savePrice.trim();
                    }
                    try{
                        if(investments.get(j) instanceof Stock){
                            ((Stock)investments.get(j)).updatePrice(Double.parseDouble((savePrice)));
                        }else if(investments.get(j) instanceof MutualFund){
                            ((MutualFund)investments.get(j)).updatePrice(Double.parseDouble((savePrice)));
                        }
                        success = true;
                    }catch(NumberFormatException nfe){
                        messages.append("Please enter a valid price\n");
                        success = false;
                    }
                    if(success){
                        messages.append("The updated investment:\n");
                        messages.append(investments.get(j).toString());
                    }
                }
            }else if(button.equals("Search")){
                messages.setText("");
                exception = false;

                /**
                 * Symbol to search
                 */
                String searchSym = "";

                /**
                 * Words to search
                 */
                String keywordSearch = "";

                /**
                 * Lower price to search
                 */
                int lowerPrice = 0;

                /**
                 * Upper price to search
                 */
                int upperPrice = 0;

                /**
                 * Number of keywords inputted
                 */
                int numWords = 0;

                /**
                 * Flag if keywords were blank
                 */
                boolean keywordBlank = false;

                /**
                 * Flag is symbol is blank
                 */
                boolean symBlank = false;

                /**
                 * Flag if lower price is blank
                 */
                boolean lowerBlank = false;

                /**
                 * Flag if upper price is blank
                 */
                boolean upperBlank = false;

                /**
                 * Value in HashMap
                 */
                ArrayList<ArrayList<Integer>> indexes = null;

                /**
                 * Reduced list after FindIntersection method
                 */
                ArrayList<Integer> reducedList = null;

                /**
                 * Array with parsed keyword search
                 */
                String[] parsedSearch;
                if(symInput.getText().isBlank() || symInput.getText().isEmpty()){
                    symBlank = true;
                }else{
                    searchSym = symInput.getText();
                }
                if(keywordsInput.getText().isBlank() || keywordsInput.getText().isEmpty()){
                    keywordBlank = true;
                }else{
                    keywordSearch = keywordsInput.getText();
                }
                parsedSearch = keywordSearch.split("[ ]+");
                if(lowInput.getText().isBlank() || lowInput.getText().isEmpty()){
                    lowerBlank = true;
                }else{
                    try{
                        lowerPrice = Integer.parseInt(lowInput.getText());
                    }catch(NumberFormatException nfe){
                        messages.append("Please enter a valid lower price\n");
                        exception = true;
                    }
                    if(lowerPrice < 0){
                        messages.append("Please enter a valid lower price\n");
                        exception = true;
                    }
                }
                if(highInput.getText().isBlank() || highInput.getText().isEmpty()){
                    upperBlank = true;
                }else{
                    try{
                        upperPrice = Integer.parseInt(highInput.getText());
                    }catch(NumberFormatException nfe){
                        messages.append("Please enter a valid upper price\n");
                        exception = true;
                    }
                    if(upperPrice < 0){
                        messages.append("Please enter a valid upper price\n");
                        exception = true;
                    }
                }
                if(lowerPrice > upperPrice){
                    messages.append("Lower price cannot be higher than upper price\n");
                    exception = true;
                }
                if(!exception){
                    if(!keywordBlank){
                        reducedList = new ArrayList<Integer>();
                        indexes = new ArrayList<ArrayList<Integer>>();
                        for(i = 0; i < parsedSearch.length; i++){
                            numWords++;
                            for(Map.Entry<String, ArrayList<Integer>> set : keyWords.entrySet()){
                                if(set.getKey().toLowerCase().contains(parsedSearch[i].toLowerCase())){
                                    indexes.add(set.getValue());
                                    reducedList = findIntersection(indexes);
                                }
                            }
                        }
                        if(numWords == 1){ 
                            reducedList = new ArrayList<Integer>();
                            for(i = 0; i < indexes.size(); i++){
                                reducedList.addAll(indexes.get(i));
                            }
                            messages.append("These are the investments that match your search: \n");
                            for(i = 0; i < investments.size(); i++){
                                for(j = 0; j < reducedList.size(); j++){
                                    if(i == reducedList.get(j)){
                                        if(symBlank && lowerBlank && upperBlank){
                                            messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                        }else if(!symBlank && lowerBlank && upperBlank){
                                            if(investments.get(reducedList.get(j)).getSymbol().equalsIgnoreCase(searchSym)){
                                                messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                                break;
                                            }
                                        }else if(!symBlank && !lowerBlank && upperBlank){
                                            if(investments.get(reducedList.get(j)).getSymbol().equalsIgnoreCase(searchSym) && investments.get(j).getPrice() >= lowerPrice){
                                                messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                                break;
                                            }
                                        }else if(!symBlank && lowerBlank && !upperBlank){ //double check logic here
                                            if(investments.get(reducedList.get(j)).getSymbol().equalsIgnoreCase(searchSym) && investments.get(j).getPrice() <= upperPrice){
                                                messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                                break;
                                            }
                                        }else if(symBlank && !lowerBlank && !upperBlank){
                                            if(lowerPrice <= investments.get(reducedList.get(j)).getPrice() && investments.get(reducedList.get(j)).getPrice() <= upperPrice){
                                                messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                                break;
                                            }
                                        }else if(symBlank && lowerBlank && !upperBlank){
                                            if(investments.get(reducedList.get(j)).getPrice() <= upperPrice){
                                                messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                                break;
                                            }
                                        }else if(symBlank && !lowerBlank && upperBlank){
                                            if(lowerPrice <= investments.get(reducedList.get(j)).getPrice()){
                                                messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                                break;
                                            }
                                        }else{
                                            if(investments.get(reducedList.get(j)).getSymbol().equalsIgnoreCase(searchSym) && lowerPrice <= investments.get(reducedList.get(j)).getPrice() && investments.get(reducedList.get(j)).getPrice() <= upperPrice){
                                                messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        } else{
                            if(reducedList.size() == 0){
                                messages.append("There are no investments that match your search...\n");
                            }
                            messages.append("These are the investments that match your search: \n");
                            for(i = 0; i < investments.size(); i++){
                                for(j = 0; j < reducedList.size(); j++){
                                    if(i == reducedList.get(j)){
                                        if(symBlank && lowerBlank && upperBlank){
                                            messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                        }else if(!symBlank && lowerBlank && upperBlank){
                                            if(investments.get(reducedList.get(j)).getSymbol().equalsIgnoreCase(searchSym)){
                                                messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                            }
                                        }else if(!symBlank && !lowerBlank && upperBlank){
                                            if(investments.get(reducedList.get(j)).getSymbol().equalsIgnoreCase(searchSym) && investments.get(j).getPrice() >= lowerPrice){
                                                messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                            }
                                        }else if(!symBlank && lowerBlank && !upperBlank){ //double check logic here
                                            if(investments.get(reducedList.get(j)).getSymbol().equalsIgnoreCase(searchSym) && investments.get(j).getPrice() <= upperPrice){
                                                messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                            }
                                        }else if(symBlank && !lowerBlank && !upperBlank){
                                            if(lowerPrice <= investments.get(reducedList.get(j)).getPrice() && investments.get(reducedList.get(j)).getPrice() <= upperPrice){
                                                messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                            }
                                        }else if(symBlank && lowerBlank && !upperBlank){
                                            if(investments.get(reducedList.get(j)).getPrice() <= upperPrice){
                                                messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                            }
                                        }else if(symBlank && !lowerBlank && upperBlank){
                                            if(lowerPrice <= investments.get(reducedList.get(j)).getPrice()){
                                                messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                            }
                                        }else{
                                            if(investments.get(reducedList.get(j)).getSymbol().equalsIgnoreCase(searchSym) && lowerPrice <= investments.get(reducedList.get(j)).getPrice() && investments.get(reducedList.get(j)).getPrice() <= upperPrice){
                                                messages.append(investments.get(reducedList.get(j)).toString()+"\n");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        messages.append("These are the investments that match your search: \n");
                        for(i = 0; i < investments.size(); i++){
                            if(symBlank && lowerBlank && upperBlank){
                                messages.append(investments.get(i).toString()+"\n");
                            }else if(!symBlank && lowerBlank && upperBlank){
                                if(investments.get(i).getSymbol().equalsIgnoreCase(searchSym)){
                                    messages.append(investments.get(i).toString()+"\n");
                                }
                            }else if(!symBlank && !lowerBlank && upperBlank){
                                if(investments.get(i).getSymbol().equalsIgnoreCase(searchSym) && investments.get(i).getPrice() >= lowerPrice){
                                    messages.append(investments.get(i).toString()+"\n");
                                }
                            }else if(!symBlank && lowerBlank && !upperBlank){ //double check logic here
                                if(investments.get(i).getSymbol().equalsIgnoreCase(searchSym) && investments.get(i).getPrice() <= upperPrice){
                                    messages.append(investments.get(i).toString()+"\n");
                                }
                            }else if(symBlank && !lowerBlank && !upperBlank){
                                if(lowerPrice <= investments.get(i).getPrice() && investments.get(i).getPrice() <= upperPrice){
                                    messages.append(investments.get(i).toString()+"\n");
                                }
                            }else if(symBlank && lowerBlank && !upperBlank){
                                if(investments.get(i).getPrice() <= upperPrice){
                                    messages.append(investments.get(i).toString()+"\n");
                                }
                            }else if(symBlank && !lowerBlank && upperBlank){
                                if(lowerPrice <= investments.get(i).getPrice()){
                                    messages.append(investments.get(i).toString()+"\n");
                                }
                            }else{
                                if(investments.get(i).getSymbol().equalsIgnoreCase(searchSym) && lowerPrice <= investments.get(i).getPrice() && investments.get(i).getPrice() <= upperPrice){
                                    messages.append(investments.get(i).toString()+"\n");
                                }
                            }
                        }
                    }
                }
                clearSearchFields();
            }   
        }
    }
    /**
     * Used to test the GUI. Creates a new instance of the InvestmentGUI and makes it visible to the user
     * @param args not used
     */
    public static void main(String[] args){
        InvestmentGUI gui = new InvestmentGUI();
        gui.setVisible(true);
    }
}
