/**
 * 
 * @author Luca Graziani
 *
 * This class prove that Java pass parameters by value
 */
public class PassByValueExample {

	private String name;
	
	public PassByValueExample(String name){
		this.name = name;
	}
	// The object a will still exist after the execution of this method
	public void passByValueProve(PassByValueExample a){
		a = new PassByValueExample("This Is the Prove");
		System.out.println(name);
		System.out.println(a.name);
	}
	
	 public static void main(String args[]){
		 PassByValueExample test = new PassByValueExample("String name will not change");
		 test.passByValueProve(test);
	 }
}
