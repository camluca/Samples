import java.util.StringTokenizer;


public class ShortestDistance {

	public static String WORD1 = "hello";
	public static String WORD2 = "world";
	
	public static String TEXT = "hello text! this is a test to find a world, the word is hello but not sure if the hello is the right world";
	
	/**
	 * Given 2 words and a text it will return the minimum distance between the 2 words
	 * if the text is empty of doesn't contain both the word it will return -1
	 * @param word1
	 * @param word2
	 * @param text
	 * @return
	 */
	public int getShortestDistance(String word1, String word2, String text)
	{
	// initialized at the length of the full text, every distance found will be less
		int minDinstance = text.length();
		if(minDinstance == 0){
			// text is empty
			return -1;
		}
		String tempWord = "";
		String lastFoundWord = "";
		int counter = 0;

		StringTokenizer st = new StringTokenizer(text);
		while (st.hasMoreTokens()){
			counter++;
			// ignore special characters
			tempWord =  st.nextToken().replaceAll("[!?.:,]","");
			if(!tempWord.equalsIgnoreCase(word1)  && !tempWord.equalsIgnoreCase(word2))
			{
				//this words are between the 2 that we want
				continue;
			}
	
			// if we are here the current token is either word1 or word2
			
			
			if(!lastFoundWord.equalsIgnoreCase("") && !lastFoundWord.equalsIgnoreCase(tempWord) && counter < minDinstance)
			{
				// we found the 2 words and the distance is shorter than the current stored one, 
				// we updated it
				minDinstance = counter;
			}
	
			lastFoundWord = tempWord;
			counter = 0;
			

		}
		
		// if we found the 2 words we return the minimum distance otherwise we return -1
		return minDinstance != text.length() ? minDinstance : -1;

	}
	
	 public static void main(String args[]){
		 ShortestDistance sd = new ShortestDistance();
			System.out.print(sd.getShortestDistance(WORD1, WORD2, TEXT));
	 }
}
