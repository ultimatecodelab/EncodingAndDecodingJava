Special Features:
1)Number 100% random & able to encode  any words. If the word is not found it will automatically add to the decodeBook.
2)Used java.nio for writing/reading large file
3)After user has encoded the Text file, the unique five digits integers
numbers will be issued
4)You can close the program any number of times and still be able to decode your String with original Text
5) No missing words. 
6)Decoded String very accurate except special characters eg "',"",!" etc...
7) Very fast to encode and decode

-------------------------------IMPORTANT ---------------------------------

1) This application is capable of encoding all the words that exists in the textfile called "messageToEncode.txt"
If the word is not found then it will have to generate the random number for each words that were not found and I have
specified to generate 44 unique integers for each NOT_FOUND_WORD and append them to the decodeBook

2) You cannot delete the filename "messageToEncode.txt" because the application will try to read the content from it.
messageToEncode.txt is the file where you can paste in your text to encode

3)Once the text file is encoded it will produce another file called "integersToDecode.txt" AND when you enter the decoding
process, program will look for the file "integersToDecode.txt"

4) You can delete decodeBook.txt but if the decodeBook.txt is not found it will automatically create in the runtime
5) You if select an option to decode it will read the text file "integersToDecode.txt" 

I have extended the range of random number to 900,000 because my program is capable of encoding large text files 
SO i can't stick with the range of 75000. If i don't extend the range of random number than it will be looping to 
generate random number but it will not be able to find.



---------------------------------------------------------------------------
		Running Times Results 


1)Full war and peace Encode and decode in less than second
You will be able to see the running time in the console when you execute the program.
---->>>>If you delete the decodeBook.txt and try to encode WarAndPeace it will take about 2 second for the following:
1) Reading whole war and peace
2) Encoding --> generating random number for all the words.
3) Saving all the encoded values to the textFile integersToDecode.txt
If you encode the textFile twice it will be approx. 2x faster because now it has got all the words into the decodeBook
So it doesn't have to go through the process of generating random number/encoding/saving them back etc...
Initially I have not sent you the decodeBook.txt, you will see decodeBook.txt once you have encoded the textFile...





