import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import DAO.FileReader;

public class Test {

	public static void main(String[] args) throws IOException {
		
	StringBuilder parceredString = new StringBuilder();
		 
	String file = FileReader.readFromFile("d://notes.xml", StandardCharsets.UTF_8);
	
	int index = 0;
	
	while(index < file.length()-1){
		
		if(file.charAt(index) == '<'){
			
			index++;
			String tagName = "";
			
			if(getTagName(index, file).equals("?xml")){
				String xmlInfo = file.substring(index+4,getNextSumbol(index, file, '>')-1);
				System.out.println("xml объ€вление: "+xmlInfo);
					parceredString.append("xml объ€вление: "+xmlInfo+"\n");
				continue;
			}			
			
			if (file.charAt(index) == '/') {
				System.out.print("\tзакрывающийс€ <");
							
				tagName = getTagName(index, file);
				
				System.out.println(tagName + ">");
				
							parceredString.append("\tзакрывающийс€ <"+tagName + ">\n");
				continue;
			} else {				
				
				tagName = getTagName(index, file);
				
				if(isTagWithoutBody(index, file)){
					System.out.println("тег <"+tagName+"/> без тела");
					
					parceredString.append("тег <"+tagName+"/> без тела");
				}
				
				System.out.println("открывающийс€ <" + tagName + ">");
							parceredString.append("открывающийс€ <" + tagName + ">\n");
							
				
				int nextIndex = getNextTagIndex(index, file);
				String nextTagName  = getTagName(nextIndex, file);
				StringBuilder tagWithoutSlash = new StringBuilder(nextTagName);
				tagWithoutSlash.deleteCharAt(0);
				nextTagName = tagWithoutSlash.toString();
				
				if(nextTagName.equals(tagName)){
					
					
					int contentStart = getNextSumbol(index, file, '>');
					int contentEnd	 = nextIndex;
					
					String tagContext = file.substring(contentStart+1, contentEnd-1);
					
					System.out.println("\t\t—одержимое "+tagName+":"+tagContext);
								parceredString.append("\t\t—одержимое "+tagName+":"+tagContext+"\n");
				}				
			}							
		}
		index++;
		
		PrintStream ps = new PrintStream(new FileOutputStream("d://parceredXML.txt"));
		
		ps.print(parceredString);
	}		
	
	}
	
	public static String getTagName(int index, String file){
		StringBuilder tagName = new StringBuilder();
		int nameIndex = index;
		
		while(file.charAt(nameIndex) != ' ' && file.charAt(nameIndex) != '>' ){
			tagName.append(file.charAt(nameIndex));
			nameIndex++;
		}			
		return new String(tagName);
	}
	
	public static int getNextTagIndex(int index, String file){
		
		while(file.charAt(index) != '<') index++;
		
		return ++index;
	}
	
	public static int getNextSumbol(int index, String file, char symbol){
		
		while(file.charAt(index) != symbol){
			index++;
		}		
		return index;
	}
	
	public static boolean isTagWithoutBody(int index, String file){
		int nextSymbol =  getNextSumbol(index, file, '>');
		
		if(file.charAt(nextSymbol-1) == '/'){
			return true;
		}
		return false;
	}
}
