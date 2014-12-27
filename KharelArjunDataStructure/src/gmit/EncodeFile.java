package gmit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class EncodeFile {
	static StringBuilder messageToEncode = new StringBuilder();
	private static int SPECIAL_CHAR_RANGE = 47;
	/*
	 * parseFile() methods reads the file that user wants to encode -> user should enter
	 * their message in this file if they wish to encode the message
	 */
	public static String parseFile() throws IOException {
		System.out.println("parsing file - Reading the data from file to encode");
		long startTime = System.currentTimeMillis();
		RandomAccessFile aFile = new RandomAccessFile("messageToEncode.txt", "r");
	    FileChannel inChannel = aFile.getChannel();
	    MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
	    buffer.load();    
	    while(buffer.hasRemaining()){
	    	char charRead = (char) buffer.get();
	    	if((charRead>SPECIAL_CHAR_RANGE)){
	    		messageToEncode.append(charRead);
	    	}
	    	//if I encounter space or newline character then I know for sure for im either at
	    	//the new line or end of word. 
	    	else if(charRead==' ' || charRead=='\n'){	
	    		messageToEncode.append(" ");	
	    	}
	    	else{
	    		messageToEncode.append(" ");
	    	}
	    	
	    }
	buffer.clear(); // do something with the data and clear/compact it   
	inChannel.close();
	aFile.close();
	System.out.println("\nFinished Parsing:  " + (System.currentTimeMillis()-startTime) +" ms");
	return messageToEncode.toString();
	    
	}
	/*
	 * saveEncodedValuesToFile() methods saves the encoded string values (Integers) 
	 * to the file so we can read this file for decoding process
	 */
	public static void saveEncodedValuesToFile(String str) throws IOException {
		System.out.println("Saving encoded values (Integers) to the file so you can decode by reading that file (integersTodecode.txt)");
		long startTime = System.currentTimeMillis();
		FileWriter writerHelper ;
		BufferedWriter bufferWriter;
		writerHelper = new FileWriter("integersToDecode.txt",false);
		bufferWriter=new BufferedWriter(writerHelper);
		bufferWriter.write(Encode.valuesOfKey().toString());
		bufferWriter.close();
		writerHelper.close();
		System.out.println("Finised saving encoded values: " + (System.currentTimeMillis()-startTime));
	}
}
