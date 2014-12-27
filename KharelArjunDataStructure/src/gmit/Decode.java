package gmit;
public class Decode {
	private static StringBuilder decodedStr =new StringBuilder();
	private static int indexTracker=0;
	
	//returns the decoded String
	public static String getDecodedString(){
		return decodedStr.toString();
	}
	
	//get the string and append it to the stringBuilder
	public static void decode(int parseInt) {
		indexTracker++;
		if(indexTracker%10==0){
			decodedStr.append("\n");
			decodedStr.append(" ");
		}
		decodedStr.append(getString(parseInt)+ " ");	
	}
	//decode the integer and return the string
		private static String getString(int number){
			if(EncodeDecodeTable.getDecodeMap().containsKey(number)){
				return EncodeDecodeTable.getDecodeMap().get(number);
			}
			return "NotFound";
		}//end of getString
}
