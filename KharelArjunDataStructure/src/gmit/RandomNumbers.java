package gmit;
import java.util.HashSet;
import java.util.Random;

public class RandomNumbers {
	private static HashSet<Integer> uniques = new HashSet<Integer>();
	private static HashSet<Integer> uniques2 = new HashSet<Integer>();
	private static  Random rand = new Random(System.nanoTime());
	
	//This method will be called for the common words top 900
	public static int getRandom(){
		int randomValue=rand.nextInt(89000)+10000;
		while(isDuplicated(randomValue)){
			randomValue=rand.nextInt(89000)+10000;
		}
		uniques.add(randomValue);
		return randomValue;
	}
	//This method will be called for the words that were not found in the dictionary 
	public static void getRandom(int howMany){
		uniques2.clear();
		while(uniques2.size()<=howMany){
			int randomValue = rand.nextInt(800000)+100000;
			if((!EncodeDecodeTable.getDecodeMap().containsKey(randomValue))){
				uniques2.add(randomValue);
			}
		}
		
	}
	//I am returning the set of integers for the word that were not found
	public static HashSet<Integer> getUniques (){
		return new HashSet<Integer>(uniques2);
	}
	//making sure the random number generated is complete unique
	private static boolean isDuplicated(int randomValue) {
		return (uniques.contains(randomValue)||EncodeDecodeTable.getDecodeMap().containsKey(randomValue));
	}
	
}
