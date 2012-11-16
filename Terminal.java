
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
 
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.HashMap;
 

/**
 * @author Luca Graziani
 * This class represent a Terminal that accepts an arbitrary ordering of products 
 * (similar to what would happen at a checkout line) and then returns the 
 * correct total price for an entire shopping cart based on the per unit 
 * prices or the volume prices as applicable.
 *
 */
public class Terminal extends JPanel implements ActionListener {
	final static long serialVersionUID = 1;
    protected JButton b1, b2, b3, b4, b5;
    protected JTextField t1,t2,t3;
    protected HashMap<String, Product> productsMap = new HashMap<>();
    /*Key: Product, Value:Quantity*/
    protected HashMap<Product, Integer> cart = new HashMap<>();
    protected BigDecimal total = new BigDecimal("0.00");
    protected BigDecimal savingToday = new BigDecimal("0.00");
    
    public Terminal() {
 
        b1 = new JButton("A");
        b1.setMnemonic(KeyEvent.VK_A);
 
        b2 = new JButton("B");
        b2.setMnemonic(KeyEvent.VK_B);
 
        b3 = new JButton("C");
        b3.setMnemonic(KeyEvent.VK_C);
        
        b4 = new JButton("D");
        b4.setMnemonic(KeyEvent.VK_D);
        
        b5 = new JButton("Clear");
        b5.setMnemonic(KeyEvent.VK_DELETE);
       
        t1 = new JTextField(10);
        t2 =  new JTextField(10);
        t3 = new JTextField(10);
 
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
 
        b1.setToolTipText("$2.00 each or 4 for $7.00");
        b2.setToolTipText("$12.00");
        b3.setToolTipText("$1.25 or $6 for a six pack");
        b4.setToolTipText("$0.15");
       
        add(b1);
        add(b2);
        add(b3);
        add(b4);
        
        JPanel inFieldPane = new JPanel();
		inFieldPane.setLayout(new GridLayout(2,3));
		inFieldPane.add(new JLabel("Scanned Product"));
		inFieldPane.add(new JLabel("Subtotal"));
		inFieldPane.add(new JLabel("Saving Today"));
		inFieldPane.add(t1);
		inFieldPane.add(t2);
		inFieldPane.add(t3);
		add(inFieldPane);
		add(b5);
    }
    
    /**
     * Set the price for a product
     * If a product is not already stored it creates a new one,
     * otherwise update the price
     * If the product is on special, updates the related fields
     * @param productName unique, is used as "barcode" for this product
     * @param productPrice
     * @param isOnSpecial
     * @param quantityNeededForSpecial
     * @param productSpecialPrice
     */
    public void setPricing(String productName, String productPrice,boolean isOnSpecial, int quantityNeededForSpecial, String productSpecialPrice){
    	Product product = this.productsMap.get(productName);
    	BigDecimal price = new BigDecimal(productPrice);
    	if(product == null){
    		product = new Product(productName,price);
    		this.productsMap.put(productName, product);
    	}else{
    		// update the price
    		product.price = price;
    	} 
    	product.isOnSpecial = isOnSpecial;
    	if(isOnSpecial){
    		BigDecimal specialPrice = new BigDecimal(productSpecialPrice);
    		
    		BigDecimal quantity = new BigDecimal(quantityNeededForSpecial);
    		product.quantityNeededForSpecial = quantityNeededForSpecial;
    		product.specialPrice = specialPrice;
    		product.retailPrice = quantity.multiply(price);
    		product.saving = product.retailPrice.subtract(specialPrice);
    	}
    }
    
    /**
     * Scan the product and update the total
     * @param productName
     * @throws Exception
     */
    private void scan(String productName) throws Exception{
    	Product product = this.productsMap.get(productName);
    	if(product == null){
    		throw(new Exception("Invalid Product"));
    	}
    	int quantityInCart=0;
    	if(cart.containsKey(product)){
    		quantityInCart = this.cart.get(product);
    	}
    	quantityInCart++;
    	total = total.add(product.price);
    	if(product.isOnSpecial && quantityInCart%product.quantityNeededForSpecial==0){
    		total = total.subtract(product.saving);
    		savingToday = savingToday.add(product.saving);
    	}
    	cart.put(product, quantityInCart);
    	t1.setText(productName);
    	t2.setText("$"+total);
    	t3.setText("$"+savingToday);
    }
 
    public void actionPerformed(ActionEvent e) {
    	String item = e.getActionCommand();
    	switch (item){
	    	case "Clear":
	    	this.cart.clear();
	    	t1.setText(""); 
	    	t2.setText(""); 
	    	t3.setText(""); 
	    	// set to 0 total and saving
	    	total = total.subtract(total);
	    	savingToday = savingToday.subtract(savingToday);
	    	break;
	    	
	    	default:
	    	try {
				scan(item);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	    	break;
    	}
    }
 
    /**
     * @author Luca Graziani
     * Define a product to be scanned from the Scanner Class
     * Name and Price are required for every product
     * 
     */
     class Product {

    	private String name;
    	public BigDecimal price;
    	public boolean isOnSpecial = false;
    	public int quantityNeededForSpecial;
    	public BigDecimal specialPrice;
    	/** the retail price for the quantity in the special */
    	public BigDecimal retailPrice;
    	/** The amount on $ saved with the offer*/
    	public BigDecimal saving;
    	
    	public String getName() {
    		return name;
    	}

    	public void setName(String name) {
    		this.name = name;
    	}

    	/**
    	 * @param name
    	 * @param price
    	 */
    	public Product(String name, BigDecimal price){
    		this.name = name;
    		this.price = price;
    	}
    	
    }

    public static void main(String[] args) throws Exception {
    	/**
    	 * Change this number to run the program with different inputs
    	 * 0 Display a UI with buttons to select input and field to displays output
    	 * 1 Scan these items in this order: ABCDABAA
    	 * 2 Scan these items in this order: CCCCCCC
    	 * 3 Scan these items in this order: ABCD
    	 * */
    	int selection = 0;
    	
    	
    	 Terminal terminal= new Terminal();
    	 terminal.setPricing("A", "2.00", true, 4, "7.00");
    	 terminal.setPricing("B", "12.00", false, 0, "0.00");
    	 terminal.setPricing("C", "1.25", true, 6, "6.00");
    	 terminal.setPricing("D", "0.15", false, 0, "0.00");
    	  
    	 
    	 switch(selection){
	     	case 0:
	     		  JFrame frame = new JFrame("Terminal");
	              frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	             
	              frame.setContentPane(terminal);
	              frame.pack();
	              frame.setVisible(true);
	     		break;
	     		
	     	case 1:

	     		terminal.scan("A");
	     		terminal.scan("B");
	     		terminal.scan("C");
	     		terminal.scan("D");
	     		terminal.scan("A");
	     		terminal.scan("B");
	     		terminal.scan("A");
	     		terminal.scan("A");
	     		
	     		break;
	     		
	     	case 2:
	     		terminal.scan("C");
	     		terminal.scan("C");
	     		terminal.scan("C");
	     		terminal.scan("C");
	     		terminal.scan("C");
	     		terminal.scan("C");
	     		terminal.scan("C");
	 	        break;
	 	
	     	case 3:
	     		terminal.scan("A");
	     		terminal.scan("B");
	     		terminal.scan("C");
	     		terminal.scan("D");
	 	        break;
     	}
    	System.out.print(terminal.total);
       
    }
}