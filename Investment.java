package W21_GUI_Project;

/**
 * Represents an investment of either Stock or MutualFund type
 * Can have multiple investments of either type
 * 
 * @author Roandre Villegas
 * @version 2.0
 */
public abstract class Investment { //need a copy constructor

    /**
     * Commission fee when buying and selling shares of a Stock
     * Only applicable for Stocks
     */
    public static final double COMMISSION = 9.99;

    /**
     * Redemption fee when selling a MutualFund
     * Only applicable for mutualfunds
     */
    public static final int REDEMPTION = 45;

     /**
     * The symbol of the investment
     */
    protected String symbol;

     /**
     * The name of the investment
     */
    protected String name;

    /**
     * The amount of investment shares 
     */
    protected int quantity;

     /**
     * The price of each investment share
     */
    protected double price;

     /**
     * The BookValue of the investment
     */
    protected double bookValue;

    /**
     * Sets the symbol of the current Stock
     * @param inputSymbol the symbol of the current Stock object
     * @exception Exception if a String symbol is not inputted
     */
    public void setSymbol(String inputSymbol) throws Exception{
        if(inputSymbol.isEmpty()){
            throw new Exception("Please enter a valid symbol\n");
        }else{
            this.symbol = inputSymbol;
        }
    }

    /**
     * Gets the current symbol of the Stock
     * @return this Stock's symbol
     */
    public String getSymbol(){
        return this.symbol;
    }

    /**
     * Sets the name of the current Stock
     * @param inputName the name of the current Stock object
     * @exception Exception if a String name is not inputted
     */
    public void setName(String inputName) throws Exception{
        if(inputName.isEmpty()){
            throw new Exception("Please enter a name\n");
        }else{
            this.name = inputName;
        }
    }

     /**
     * Gets the current name of the Stock
     * @return this Stock's name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Sets the amount of shares of a Stock investment
     * @param inputQuantity the quantity of the current Stock
     * @exception NumberFormatException if an Integer is not inputted
     */
    public void setQuantity(Integer inputQuantity) throws NumberFormatException{ //need further testing
        if(!(inputQuantity instanceof Integer)){
            throw new NumberFormatException("Please enter a valid number\n");
        }else{
            this.quantity = inputQuantity;
        }
    }

    /**
     * Gets the current amount of Stock shares
     * @return this Stock's number of shares
     */
    public int getQuantity(){
        return this.quantity;
    }

    /**
     * Sets the price of each share of the Stock
     * @param inputPrice the current price of each share
     * @exception NumberFormatException if a Double is not inputted
     */
    public void setPrice(Double inputPrice) throws NumberFormatException{
        if(!(inputPrice instanceof Double)){
            throw new NumberFormatException("Please enter a valid price\n");
        }else{
            this.price = inputPrice;
        }
    }

    /**
     * Gets the price current price of each share
     * @return this price of each Stock share
     */
    public double getPrice(){
        return this.price;
    }

    /**
     * Sets the current BookValue of the Stock investment
     * @param inputPrice the current price of each BookValue
     * @param inputQuantity the number of shares invested
     */
    public abstract void setBookValue(double inputPrice, int inputQuantity);

    /**
     * Gets the BookValue of the Stock
     * @return this Stock BookValue
     */
    public double getBookValue(){
        return this.bookValue;
    }

    /**
     * @return the MutualFund info properly formatted
     */     
    public String toString(){
        return "Symbol: "+this.getSymbol()+"\n"+"Name: "+this.getName()+"\n"+"Quantity: "+this.getQuantity()+"\n"+"Price: $"+String.format("%.2f",this.getPrice())+"\n"+"BookValue: $"+String.format("%.2f",this.getBookValue()) + "\n";
    }

    /**
     * Computes the theoretical gain of the portfolio based on current prices of each investment
     * @return the calculated theoretical gain of the portfolio
     */
    public double getGain(){
        return (this.quantity*this.price) - this.bookValue;
    }

    /**
     * Computes the gain when some shares are sold for Investments
     * Updates quantity, price, and BookValue after certain shares are sold
     * @param inputPrice the price of each share
     * @param inputQuantity the number of shares sold
     * @return gain after selling shares
     */
    public abstract double partial(double inputPrice, int inputQuantity);

    /**
     * Computes the gain when all shares are sold for Investments
     * @param inputPrice the price of each share 
     * @param inputQuantity the number of shares sold
     * @return gain after selling shares
     */
    public abstract double full(double inputPrice, int inputQuantity);
    

    /**
     * Updates the current object's BookValue, quantity, and price
     * @param inputPrice the price of each new share
     * @param inputQuantity the number of shares to add
     * @param inputBookValue the bookValue to be added to the current investment
     */
    public void addQuantity(double inputPrice, int inputQuantity, double inputBookValue){
        this.bookValue += inputBookValue;
        this.quantity += inputQuantity;
        this.price = inputPrice;
    }

    /**
     * Writes current investments to text file properly formatted
     * @param inputType either Stock or MutualFund
     * @return the output file properly formatted
     */
    public String writeOutput(String inputType){
        return "type = \"" + inputType +"\"\nsymbol = \"" + this.getSymbol() + "\"\nname = \"" + this.getName() + "\"\nquantity = \"" + this.getQuantity() + "\"\nprice = \"" + this.getPrice() + "\"\nbookValue = \"" + this.getBookValue() + "\"\n\n";
    }
    
}
class Stock extends Investment{
    /**
     * Constructor to initialize Stock attributes
     */
    public Stock(){
        this.symbol = "";
        this.name = "";
        this.quantity = 0;
        this.price = 0.0;
        this.bookValue = 0.0;
    }
    
    /**
     * Copy constructor to save a duplicate object
     * @param stock the object to be duplicated
     */
    public Stock(Stock stock){
        this.symbol = stock.symbol;
        this.name = stock.name;
        this.quantity = stock.quantity;
        this.price = stock.price;
        this.bookValue = stock.bookValue;
    }

    /**
     * Computes the gain when some shares are sold for MutualFunds
     * Updates quantity, price, and BookValue after certain shares are sold
     * @param inputPrice the price of each share
     * @param inputQuantity the number of shares sold
     * @return the total gain after shares are sold
     */
    public double partial(double inputPrice, int inputQuantity){
        double tempBookVal = 0.0;
        double payment = (inputQuantity * inputPrice) - COMMISSION;
        double gain = payment - (this.bookValue * (inputQuantity / this.quantity));
        tempBookVal = this.bookValue * ((double)inputQuantity / this.quantity);
        this.bookValue -= tempBookVal;
        this.quantity -= inputQuantity;
        return gain;
    }

    /**
     * Computes the gain when all shares are sold for MutualFunds
     * @param inputPrice the price of each share 
     * @param inputQuantity the number of shares sold
     * @return the total gain after all shares are sold
     */
    public double full(double inputPrice, int inputQuantity){
        double payment = (inputPrice*inputQuantity) - COMMISSION;
        double gain = payment - this.bookValue;
        return gain;
    }

    /**
     * Updates price of each investment
     * @param inputPrice the new price of the investment
     */
    public void updatePrice(Double inputPrice) throws NumberFormatException{
        this.price = inputPrice;
        this.bookValue = (this.quantity*inputPrice) + COMMISSION;
    }

    /**
     * Sets the current BookValue of the Stock investment
     * @param inputBookValue the current price of each BookValue
     */
    public void setBookValue(double inputPrice, int inputQuantity) throws NumberFormatException{
        this.bookValue = (inputPrice * inputQuantity) + COMMISSION;
    }

    /**
     * Checks to see if the current object equals the input Investment object
     * @param investment the input object to be compared
     * @return either true or false
     */
    @Override
    public boolean equals(Object investment){
        return (investment == null || this.getClass() != investment.getClass() || !this.symbol.equals(((Stock)investment).symbol) || !this.name.equals(((Stock)investment).name) || this.quantity != ((Stock)investment).quantity || this.price != ((Stock)investment).price || this.bookValue != ((Stock)investment).bookValue) ? false : true;
    }
}
class MutualFund extends Investment{
    /**
     * Constructor to initialize MutualFund attributes
     */
    public MutualFund(){
        this.symbol = "";
        this.name = "";
        this.quantity = 0;
        this.price = 0.0;
        this.bookValue = 0.0;
    }
    /**
     * Copy constructor to save a duplicate object
     * @param mutualFund the object to be duplicated
     */
    public MutualFund(MutualFund mutualFund){
        this.symbol = mutualFund.symbol;
        this.name = mutualFund.name;
        this.quantity = mutualFund.quantity;
        this.price = mutualFund.price;
        this.bookValue = mutualFund.bookValue;
    }

    /**
     * Computes the gain when some shares are sold for MutualFunds
     * Updates quantity, price, and BookValue after certain shares are sold
     * @param inputPrice the price of each share
     * @param inputQuantity the number of shares sold
     * @return the total gain after shares are sold
     */
    public double partial(double inputPrice, int inputQuantity){
        double tempBookVal = 0.0;
        double payment = (inputQuantity * inputPrice) - REDEMPTION;
        double gain = payment - (this.bookValue * ((double)inputQuantity / this.quantity));
        tempBookVal = this.bookValue * (inputQuantity / this.quantity);
        this.bookValue -= tempBookVal;
        this.quantity -= inputQuantity;
        return gain;
    }

    /**
     * Computes the gain when all shares are sold for MutualFunds
     * @param inputPrice the price of each share 
     * @param inputQuantity the number of shares sold
     * @return the total gain after all shares are sold
     */
    public double full(double inputPrice, int inputQuantity){
        double payment = (inputPrice*inputQuantity) - REDEMPTION;
        double gain = payment - this.bookValue;
        return gain;
    }

    /**
     * Updates price of each investment
     * @param inputPrice the new price of the investment
     */
    public void updatePrice(Double inputPrice) throws NumberFormatException{
        this.price = inputPrice;
        this.bookValue = this.quantity*inputPrice;
    }

    /**
     * Sets the current BookValue of the Stock investment
     * @param inputBookValue the current price of each BookValue
     */
    public void setBookValue(double inputPrice, int inputQuantity) throws NumberFormatException{
        this.bookValue = inputPrice * inputQuantity;
    }

    /**
     * Checks to see if the current object equals the input Investment object
     * @param investment the input object to be compared
     * @return either true or false
     */
    @Override
    public boolean equals(Object investment){
        return (investment == null || this.getClass() != investment.getClass() || !this.symbol.equals(((MutualFund)investment).symbol) || !this.name.equals(((MutualFund)investment).name) || this.quantity != ((MutualFund)investment).quantity || this.price != ((MutualFund)investment).price || this.bookValue != ((MutualFund)investment).bookValue) ? false : true;
    }
}