# GUI_Project
Created a GUI using a Java's built-in Swing library to help users maintain an investment portfolio.
The InvestmentGUI program is used to create investments using a Graphical User Interface (GUI). Responsible for creating Stocks and MutualFunds, this program will help the user perform many tasks involving Stocks and MutualFunds via a user-friendly interface. Said commands include:
    - buying
    - selling 
    - updating 
    - computing the total gain of the investments 
    - searching for existing investments

The only assumption made is that the user will not increase/decrase the size of the window when running the GUI. The GUI is properly formatted for the size is was intialized to (600 pixels x 600 pixels). The size of the components in the GUI will not increase when the window is manually increased by the user.

## To Compile and Run:
To compile the program, first make sure the classes are in the same folder. Then, use the 'javac' command, followed by the filenames (Portfolio, InvestmentGUI). Then, to test the program, run the InvestmentGUI.java file and a new window will appear. From there, just follow the prompts.
## Test Plan:
The program allows the user to perform many tasks in managing investments in their portfolio (the commands are listed above). For buying/selling, you can buy/sell as many or as little shares as you want. For update, the user is prompted to update the price of each investment by looping through all investments. The program also computes the gain of the portfolio, which is displayed to the user when they choose. A HashMap is also implemented to make search through and accurate.

When testing the functionality, there were many conditions tested, trying to break the code, fixing any issues and/or bugs that were encountered.
The major conditions tested were:
#### GUI Related:
- empty textfields
- incorrect datatypes in textfields
    ie:
    - inputting string when integer is expected
    - inputting string when double is expected
    - inputting integer when string is expected
    - inputting integer when double is expected
    - inputting double when integer is expected
    - inputting double when string is expected
- negative price
- negative quantity
- properly displayed error messages in textarea
- properly formatted/sized textfields, textareas and buttons
- clearing the editable textfields when the reset, prev, next, save, buy, sell and search buttons are clicked
#### Code functionality related:
- trying to buy a MutualFund that exists as a Stock
- trying to sell more shares than available
- removing investment after all shares were sold
- making sure all the ArrayList data is correct after selling shares/adding shares to an investment
- making sure the HashMap indices are correct for keywords
- making sure the math was correct for getGain, sell, and bookValue

#### Future Improvements:
Given more time, I would try and make each panel a different colour, by experimenting with the RGB scale and creating custom panel colours rather than relying on the built-in colours in the Swing library. Another improvement I would like to implement is to is make the font bigger for the text that appears in the textarea for the user.
