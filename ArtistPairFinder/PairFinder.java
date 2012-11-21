import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;


public class PairFinder {
	
	/** 
	 * key = concatenation of the 2 artist names
	 *  Value = Pair object
	 *  
	 *  */
	HashMap<String, Pair> pairMap = new HashMap<>();
	
	private static BufferedWriter writer;
	
	public HashMap<Pair, Pair> result = new HashMap<>();
	
	
	/**
	 * read the txt file in input and create a list of lists of artists
	 * @param file
	 * @return
	 */
	public List<List<String>> readInput(String file){
		/**List of lists containing the favorite artists*/
		List<List<String>> matrix = new ArrayList<>();
		try{
			FileInputStream fis = new FileInputStream(file); 
			InputStreamReader in = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = new BufferedReader(in);
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
				matrix.add(items);
				
			}
			reader.close();
		}catch(Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
		return matrix;
	}
	
	/**
	 * @param matrix
	 * @throws IOException
	 */
	/**
	 * @param matrix
	 * @throws IOException
	 */
	public void checkEveryCombination(List<List<String>> matrix) throws IOException{
		List<String> listA;
		for(int i=0;i<matrix.size();i++){
			listA = (List<String>) matrix.get(i);
			this.findPairs(listA);
		}
	}
	
	/**
	 * Find all pairs in a list of artists
	 * @param a
	 */
	public void findPairs(List<String> a){
		String artist1;
		String artist2;
		for(int i=0;i<a.size()-1;i++){
			for(int j=i+1;j<a.size();j++){
				artist1 = a.get(i).toString();
				artist2 = a.get(j).toString();
				this.checkPair(artist1, artist2);
			}
		}
	}
	
	/**
	 * Given the 2 artist name, check if the pair is already present in the hashmap
	 * the key is the concatenation of the 2 artist name
	 * since this concatenation could be both ways (i.e. A1A2 and A2A1)
	 * but we only want to have 1 entry in the map, we check for both if they are present
	 * the first (and only) Pair object returned gets the occurrence field updated
	 * if no Pair object is found it mean that this pair is still not in the map
	 * so we create a new pair object and add it in the map
	 * @param artist1
	 * @param artist2
	 * @throws IOException 
	 */
	public void checkPair(String artist1, String artist2){
		String concat12 = artist1.concat(artist2);
		String concat21 = artist2.concat(artist1);
		Pair found;
		if(this.pairMap.containsKey(concat12)){
			found = this.pairMap.get(concat12);
			if(found.occurences<50){
				found.occurences++;
			}else{
				if(!result.containsKey(found)){
					result.put(found, found);
					try {
						writer.write(found.getArtist1() + "," + found.getArtist2());
						writer.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			return;
		}
		
		if(this.pairMap.containsKey(concat21)){
			found = this.pairMap.get(concat21);
			if(found.occurences<50){
				found.occurences++;
			}else{
				if(!result.containsKey(found)){
					result.put(found, found);
					try {
						writer.write(found.getArtist1() + "," + found.getArtist2());
						writer.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return;
		}
		
		found = new Pair(artist1, artist2);
		this.pairMap.put(concat12, found);
	}
	
	 public static void main(String args[]) throws IOException{
		    File file = new File("./artistsPairs.txt");
		    writer = new BufferedWriter(new FileWriter(file));
			PairFinder pf = new PairFinder();
			List<List<String>> matrix = pf.readInput("./Artist_lists_small.txt");
			pf.checkEveryCombination(matrix);
			writer.close();
			JOptionPane.showMessageDialog(null,"All Done, check result on artistsPairs.txt");
	 }
}

class Pair{
	private String artist1, artist2;
	public int occurences;
	public String getArtist1() {
		return artist1;
	}
	public String getArtist2() {
		return artist2;
	}
	public int getOccurence() {
		return occurences;
	}
	
	public Pair(String artist1, String artist2){
		this.artist1 = artist1;
		this.artist2 = artist2;
		occurences = 1;
	}
	
	
}