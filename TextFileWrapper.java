import java.io.*;
import java.net.URL;
import java.util.StringTokenizer;

/**
 * @author Luca Graziani
 * 
 * Java program that performs the following:
 * Read the following file: https://staging.chromeriver.com/eval/file.txt
 * From the file.txt file, create a new file with the following requirements:
 * Each line in the file should not have more than 50 characters
 * Each line should be word wrapped (i.e. there should be no half words on a line)
 * A word is delimited by 1 or more spaces or a linefeed.
 * The paragraphs of the original file should be left intact.
 *
 */
public class TextFileWrapper {
	
	/**
	 * 
	 * @param urlInput url of the text file
	 * @param fileOutput 
	 */
	public void convert(String urlInput, String fileOutput){
		final int LINE_CHAR_LIMIT = 50;
		try{
			File file = new File(fileOutput);
		    URL url = new URL(urlInput);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			String line = null;
			int counter = 0;
			String limitedLine = "";
			String word = "";
			while ((line = reader.readLine()) != null)
			{
				if(line.length() == 0)
				{
					// new paragraph
					writer.write(limitedLine);
			        writer.newLine();
			        writer.newLine();
			        limitedLine = "";
			        counter = 0;
					continue;
				}
				
				StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreTokens()){
					word = st.nextToken();
					counter = counter + word.length();
					if(counter > LINE_CHAR_LIMIT){
						// We reached the limit of 50 characters
						writer.write(limitedLine);
				        writer.newLine();
				        limitedLine = "";
				        counter = 0;
					}
					limitedLine = limitedLine + " " + word;
				}
			}
			
			reader.close();
			writer.close();
		}
		catch(Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
		
	}
	
	 public static void main(String args[]){
			TextFileWrapper tfw = new TextFileWrapper();
			tfw.convert("https://staging.chromeriver.com/eval/file.txt", "./output.txt");
	 }
}
