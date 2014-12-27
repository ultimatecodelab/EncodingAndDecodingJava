package gmit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class DecodeFile {
	
	private static ArrayList<Integer> listOfNumbersToDecode = new ArrayList<Integer>();
	private static StringBuilder sb = new StringBuilder();
	
	static StringBuilder encodeBook = new StringBuilder();
	
	/*
	 * readIntegers() methods reads all the encoded values that were stored
	 * in the text file and sends to decode one by one.
	 */
	public static void readIntegers() throws IOException{
		long startTime = System.currentTimeMillis();
		System.out.println("Reading from file and decoding on the same time: ");
		RandomAccessFile aFile = new RandomAccessFile("integersToDecode.txt", "r");
	    FileChannel inChannel = aFile.getChannel();
	    MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
	    buffer.load();    
	    //Reading as an array of bytes
	    while(buffer.hasRemaining()){
	    	char charRead = (char) buffer.get(); //checking if the character read is > 47. Eliminated
	    	//most of the special characters
	    	if(charRead!=' ')
	    	{
	    		sb.append(charRead);
	    	}
	    	else if (charRead==' ' || charRead=='\n'){
	    		Decode.decode(Integer.parseInt(sb.toString().trim()));
	    		sb.setLength(0);
	    	}
	    	
	    }//
	    System.out.println("Finished reading from file and decoding process: " + (System.currentTimeMillis()-startTime) +" ms");
	    buffer.clear();
	    aFile.close();
	    inChannel.close();
	}
	public static String decodeString(){
		return sb.toString();
	}
	public static ArrayList<Integer> getIntegers(){
		return listOfNumbersToDecode;
	}
	/*
	 * saveDecodedStringToFile() methods saves the decoded string to the file
	 * I am writing the chunk of string at one GO. because it is a faster process than 
	 * Writing one by one
	 */
	public static void saveDecodedStringsToFile() throws IOException{
		System.out.println("Saving the decoded String to the file");
		long startTime = System.currentTimeMillis();
		FileWriter writerHelper ;
		BufferedWriter bufferWriter;
		writerHelper = new FileWriter("decodedString.txt",false);
		bufferWriter=new BufferedWriter(writerHelper);
		bufferWriter.write(Decode.getDecodedString().toString());
		bufferWriter.close();
		writerHelper.close();	
		System.out.println("Successfully finished saving the entire decoded String to the file" +
				"Took: " +(System.currentTimeMillis()-startTime) +" ms \n Please check the file called integersToDecode.txt and decodedString.txt to view your decoded text files");
		System.out.println("REFRESH THE PROJECT TO SEE THE FILES CREATED IN THE RUNTIME--");
	}

}
