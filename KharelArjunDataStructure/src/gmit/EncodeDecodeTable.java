package gmit;
/*
 * This class is responsible for the followings:
 * 1)CREATING decodeBook/EncodeDecode
 * 2)READING all the contents of the decodeBook Textfile (decodeBook.txt)
 * 3)ADDING the word to the decodeBook with unique integers automatically 
 * IF THE WORD IS NOT FOUND
 * 4)User can encode ANY text and decode successfully.
 * 5)DecodeBook will be expand automatically if the word, the user wants to encode
 * is not found . So from user perspective, they can encode any word regardless
 * if its common or not.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EncodeDecodeTable {
	private static HashMap<String,List<Integer>> encodeTable = new HashMap<String,List<Integer>>();
	private static HashMap<Integer,String>   decodeTable = new HashMap<Integer,String>();
	private static StringBuilder sb = new StringBuilder();
	private static int SPECIAL_CHARS_RANGE = 47;
	
	public static void runEncodeDecodeTable() throws IOException
	{
		/*checking if the decodeBook exists or not
		if it does I am reading all the data from the decodeBook.txt 
		and adding them into the encode HashMap
		*/
		boolean fileExist = false;
		@SuppressWarnings("unused")
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader("decodeBook.txt"));
			fileExist=true;
		} catch (Exception e) {
			fileExist=false;
			System.out.println("Decodebook doesn't exist, creating new decodeBook.txt");
		} 
		/*
		 * If the table is not created then I am reading 900 words from the dictonary
		 * and I have generated the random numbers for each word depending on their
		 * frequency
		 */
		if(fileExist==false){
			readAllWords();
			createDecodeTable();
		}
		 //If the table is created already then I am simply reading those values 
		//from the decodeBook.txt to the hashMap
		readFromTable();
	}
	/*
	 * readAllWords()
	 * This method is responsible for reading 900 words from the dictionary and 
	 * creating a decodeBook. 
	 * For every word i read from the commonEnglishWords.txt I am sending that 
	 * word for encoding with certain numbers based on their frequency.
	 * Eg. for word "the" it will create 1000 integers.
	 */
	private static void readAllWords() throws IOException {
		RandomAccessFile aFile = new RandomAccessFile("commonEnglishWords.txt", "r");
	    FileChannel inChannel = aFile.getChannel();
	    MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
	    buffer.load();    
	    //Reading as an array of bytes
	    while(buffer.hasRemaining()){
	    	char charRead = (char) buffer.get(); //checking if the character read is > 47. Eliminated
	    	//most of the special characters
	    	if(charRead>SPECIAL_CHARS_RANGE){
	    		sb.append(charRead);
	    	}
	    	else { 
	    		//If the char read was not something else. Eg. space(' ') or \n
	    		//then I know for sure that it's a complete word so now I can pass that
	    		//word for encoding process
	    		Encode.incrementFreq(); 
	    		/*once I have a valid word to encode that 
	    		 Doesn't contain the special characters I am Sending the word to be 
	    		 inserted in the table.
	    		 */
	    		Encode.populateEncodeDecodeTable(sb.toString());
	    		sb.setLength(0);
	    	}
	    }
	    buffer.clear(); // do something with the data and clear/compact it   
	    inChannel.close();
	    aFile.close();//closing the file and the file channel
	}
	/*
	 * addNotFoundWordToTheTable() execute if the doesn't rank the top 900
	 * I am adding the word into the decodeBook,assigned the random numbers
	 * so encoded message will be 100% clear without missing the words. It will be
	 * 100% Precise except the special characters.
	 * I chose to use RandomAccessFile because not only it lets me to read the file 
	 * contents as a bytes array but also, it gives me the ability to write at any 
	 * position in the file. In my case I am appending the word to the decodeBook
	 * if it's not found THEREFORE I chose to use the combination of java.io and 
	 * java.nio packages for this application
	 */
	public static void addNotFoundWordToTheTable(String word) throws IOException{
		File f = new File("decodeBook.txt");
		long fileLength = f.length();
		RandomAccessFile raf = new RandomAccessFile(f,"rw");
		raf.seek(fileLength);//moving the file pointer and the end of the file for appending
		raf.write(word.getBytes());
		raf.close();
	}
	/*
	 * I have used java.nio
	 * The biggest advantage is I can write the chunk of BIG string at one go.
	 * It very very fast than traditional java.io
	 */
	private static void createDecodeTable() throws IOException{
		@SuppressWarnings("resource")
		FileChannel rwChannel = new RandomAccessFile("decodeBook.txt", "rw").getChannel();
		ByteBuffer wrBuf = rwChannel.map(FileChannel.MapMode.READ_WRITE, 0, Encode.getWordsWithEncodedValues().toString().length());
		wrBuf.put(Encode.getWordsWithEncodedValues().toString().getBytes());
		rwChannel.close();
		rwChannel.close();
	}
	/*
	 * This methods execute the table was created already..
	 * Here i am reading the data from the file decodeBook.txt and putting 
	 * them into the file
	 */
	private static void readFromTable() throws IOException {
		System.out.println("DecodeBook / lookup table already exists (decodeBook.txt)\n" +
				"Reading data from the file and putting them into hashMap on the same time");
		long startTime = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("decodeBook.txt")));
		String line= null;
		while((line=br.readLine())!=null){
			String [] words = line.split("\t");
			String key = words[0];
			encodeTable.put(key, new ArrayList<Integer>());
			for(int i = 1 ; i <words.length;i++){
				String word= words[i];
				encodeTable.get(key).add(Integer.parseInt(word));
				decodeTable.put(Integer.parseInt(word), key);
			}
		}
		br.close();
		System.out.println("\nFinished Reading the decodeBook and HashMaps has been populated.." +
				"\nTime taken : " +(System.currentTimeMillis()-startTime) +" ms");
	}

	public static HashMap<String, List<Integer>> getCopyOfHashMap()
	{
		return encodeTable;
	}
	public static HashMap<Integer,String> getDecodeMap()
	{
		return decodeTable;
	}
}
