package gmit;
/*EncoderDecoder application - Developed by Arjun Kharel (Software Development GMIT)
 
 * This is a Runner class.
 * This class is responsible for the followings:
 * Provide the menu for the user
 * They can choose to Encode or Decode the text
 * Depending on the option selected by the user switch statement calls 
 * another class to carry the appropriate tasks
 */

import java.io.IOException;
import java.util.Scanner;

public class EncoderDecoder {
	//Scanner for getting the input from the user
	static Scanner console = new Scanner(System.in);
	
	public static void run() {
	try {
		 /*Calling the runEncodeDecodeTable method
		  * This method checks if the decodeBook/EncodeDecode Table is created or not
		  * The fileName of the table is "decodeBook.txt"
		  * I AM REFEARING THE FILENAME "decodeBook.txt" AS A TABLE
		  */
		EncodeDecodeTable.runEncodeDecodeTable();
		int choice=menu();
		while(choice!=3)
		{
			switch(choice)
			{
			case 1:
				/* If user select option 1 then I called the Encode class
				 * which contains the method encodeText --> which parses the
				 * fileName "messageToEncode.txt";
				 * In the method encodeText() I am sending the chunk of String to encode.
				 * The parseFile method in class EncodeFile reads the whole file 
				 * append it to the string builder and returns as a String for 
					encoding
				*/
				Encode.encodeText(EncodeFile.parseFile().toString());
				
				break;
			case 2:
				/*
				 * If user select the option to decode the string then the method
				 * readIntegers() on DecodeFile class reads all the encoded values
				 * from the file called "integersToDecode.txt",process all the encoded
				 * values (Decode one by one) and THE METHOD saveDecodedStringToFile 
				 * PRODUCES THE NEW FILE CALLED "decodedString.txt"
				 * which now contains the decoded String
				 */
				DecodeFile.readIntegers();
				DecodeFile.saveDecodedStringsToFile();
				break;
			case 3:
				System.out.println("Bye");
				break;
				
			}
			choice=menu();
		}
		
	} catch (IOException e) {
		//System.out.println("File Not Found");
	}
	}
	public static int  menu(){
		int option;
		System.out.println("\nPlease select the option bellow");
		System.out.println("1: Encode your String/texts");
		System.out.println("2: Decode the encoded String/texts");
		System.out.println("3: Exist");
		return option=console.nextInt();	
	}
}
