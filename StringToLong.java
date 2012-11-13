
public class StringToLong {
	
	 private long stringToLong(String input) {
		 long maxValue = Long.MAX_VALUE;
		 // The maximum number stored in a Long is 19 digits
		 if(input.length()>19){
			 System.out.println("The number is too big, The maximum number is " + maxValue);
			 return -1;
		 }
		 long result = 0;
		 String digits = "0123456789";
		 for (int i = 0; i <= input.length()-1; i++) {
			 char c = (char) input.charAt(i);
			 int lastValue = digits.indexOf(c);
			 // In the string there is a characters that is not a number
			 if(lastValue < 0){
				 System.out.println("Invalid charater in the string" + lastValue);
				 return -1;
			 }
			 if(result*10 + lastValue <= maxValue){
				 result = result*10 + lastValue;
			 }else{
				 // The number is bigger than the maximum supported by a Long
				 System.out.println("The number is too big, The maximum number is " + maxValue);
				 return -1;
			 }
			 
		 	}
		 return result;
	 }
	
	 public static void main(String args[]){
		StringToLong test = new StringToLong();
		System.out.print(test.stringToLong("23425356436"));
	}
}
