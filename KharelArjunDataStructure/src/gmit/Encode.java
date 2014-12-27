package gmit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Encode {
	private static StringBuilder encodedIntegers = new StringBuilder();
	private static StringBuilder encodedWordsWithTheirValues = new StringBuilder();
	private static StringBuilder wordNotFound  = new StringBuilder();
	private static int wordFrequency = 0 ;
	// 25 unique number to be assigned for the words that were not found
	private static final int NOT_FOUND_WORD_RANGE = 25; 
	private static final int MAX_RANGE = 75000;
	
	//Declaration of constants
	private static final int TOP_25_WORDS = 25;
	private static final int TOP_100_Words = 100;
	private static final int TOP_300_Words = 300;
	
	/*
	 * This method execute whenever user wish to encode their messages
	 *I am splitting the string that was passed as tokens and processed accordingly
	 */
	public static void encodeText(String words) throws IOException{
		long startTime = System.currentTimeMillis();
		StringTokenizer st = new StringTokenizer(words," ");
		
		int newLineTrigger=0;
		while(st.hasMoreTokens()){
			String word = st.nextToken().toString().toLowerCase().trim();
			newLineTrigger++;
			//checking if my HashMap contains the current token (word)
			//tableHasWord returns boolean value
			if(tableHasWord(word)) {
				encodedIntegers.append(pickOneValue(word)+" ");
			}//if
			/*
			 * if the word is not found, which means the word is strange or not in
			 *  the rank of top 900 i am adding it automatically into the decodeBook.txt
			 *  and assigned 25 unique integers for it
			 */
			else{
				EncodeDecodeTable.getCopyOfHashMap().put(word, new ArrayList<Integer>());
				RandomNumbers.getRandom(NOT_FOUND_WORD_RANGE);
				//taking care of the words that were not found for later 
				//adding into the decodeBook.txt
				appendNotFoundWord(word);
				encodedIntegers.append(pickOneValue(word)+" ");	
				
			}//else
			//New line trigger
			if(newLineTrigger%10==0){
				encodedIntegers.append("\n");
			}
		}//while
		/*While there are no for token to process now i am adding all the 
		  not found words with their corresponding unique integers to the file
		  This method is really fast because I am appending all the words that were
		  not found and built the long string and sent that big chunk of String to
		  be written into the decodeBook.txt
		 */
		System.out.println("Finished Encoding " +(System.currentTimeMillis() - startTime) + " ms" +
				"\nPlese check integersToDecode.txt");
		System.out.println("Appending all the words that were not found into the decodebook(Writing)");
		startTime= System.currentTimeMillis();
		EncodeDecodeTable.addNotFoundWordToTheTable(wordNotFound.toString());
		System.out.println("Finished adding the words that were not found into the decodebook: " + (System.currentTimeMillis()-startTime) +" ms");
		System.out.println("\nNow saving all the encoded values (Integers) into the file \n" +
				"so you can decode your original string by reading that file which contains integers values");
		startTime = System.currentTimeMillis();
		EncodeFile.saveEncodedValuesToFile(encodedIntegers.toString());
		
		System.out.println("Finished writing all the encoded values(Integers) to the file: " + (System.currentTimeMillis()-startTime) + " ms");
		wordNotFound.setLength(0); //clearing the StringBuilder once i have finished using it
		encodedIntegers.setLength(0);
	}	
	
	/*
	 * This method add the notFoundWord word and build the long String with its
	 *  corresponding integers. 
	 *  This method iterate over the set of unique integers and append to the 
	 *  StringBuilder so I can update notFoundWord into the decodeBook 
	 */
	public static void appendNotFoundWord(String word) throws IOException {
		wordNotFound.append("\n" + word + "\t"); //appending the word that were not found.
		for(Integer uniques: RandomNumbers.getUniques()){
			EncodeDecodeTable.getCopyOfHashMap().get(word).add(uniques);
			EncodeDecodeTable.getDecodeMap().put(uniques, word);
			wordNotFound.append(uniques+"\t"); //appending the unique for that specific word
		}	
	}
	/*This method picks one value from the list of integers 
	 * Eg. HashMap<String,List<integer>>;
	 * It picks one value from the list randomly while encoding
	 */
	public static int pickOneValue(String key){
		int value = 0 + (int)(Math.random() * ((EncodeDecodeTable.getCopyOfHashMap().get(key).size())));
		return EncodeDecodeTable.getCopyOfHashMap().get(key).get(value);
	}
	public static boolean  tableHasWord(String word){
		return EncodeDecodeTable.getCopyOfHashMap().containsKey(word.trim());
	}
	//This method only execute when the program is running for the fist time 
	//and the decodeBook.txt was not created at that point
	//Building one long string using StringBuilder so I can write the entire
	//table that contains 900 words initially with 75000 integers at one go using
	//BufferBytes classes. please see EncodeDecode Table for more details.
	public static void populateEncodeDecodeTable(String word){
		encodedWordsWithTheirValues.append(word + "\t");
		int randomVal = 0;
		for(int i = 0 ; i < computeFreq() ; i++){
			randomVal = RandomNumbers.getRandom();
			encodedWordsWithTheirValues.append(randomVal + "\t");
			}
			encodedWordsWithTheirValues.append("\n");	
	}
	//returning each words corresponding integers
	//This method is called when i am writing the Integers value/ encoded values
	//in the file for decoding purposes.
	public static  String valuesOfKey(){
		return encodedIntegers.toString();
	}
	//returning words + encoded integers
	public static String getWordsWithEncodedValues(){
		return encodedWordsWithTheirValues.toString();
	}
	public static void incrementFreq(){
		wordFrequency++;
	}
	//computing the frequency
	public static int computeFreq(){
		int freq = 0;
		//// first 25 words get 1 third of the numbers
		if(wordFrequency < TOP_25_WORDS){	
			freq = (MAX_RANGE / 3) / 25;
		}
		// next 75 words gets 17% third of the numbers (half of a third)
		else if(wordFrequency < TOP_100_Words){	
			freq = MAX_RANGE / 3 / 2 / 75;
		}
		// next 200 words gets 15% of the numbers
		else if(wordFrequency < TOP_300_Words){	
			freq = (int) (MAX_RANGE * 0.15) / 200 ;
		}
		// remainder (600 words) gets 35% of numbers
		else{					
			freq = (int) (MAX_RANGE * .35) / 600;
		}
		
		return freq;
	}
}
