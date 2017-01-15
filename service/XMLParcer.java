package service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DAO.FileReader;

public class XMLParcer {

	
	private StringBuilder parceredString = new StringBuilder();
	
	String file;
	
	private  int index = 0;
	
	public void nextParce(){
		
		if(index >= file.length()-1 ){
			return ;
		}
		
		//while(index < file.length()-1){ // читаем документо пока не дойдем до конца
			
			if(file.charAt(index) == '<'){ // как только встречаем < знаем что это какой-то тэг
										   // нужно узнать его тип
				
				index++;
				String tagName = "";
				
				if(getTagName(index, file).equals("?xml")){ // если он называется ?xml, то знаем что это опмсание
					
					String xmlInfo = file.substring(index+4,getNextSymbol(index, file, '>')-1);  // информация об xml
					
					System.out.println("xml объявление: "+xmlInfo);
						parceredString.append("xml объявление: "+xmlInfo+"\n");
					return ;//continue; // мы уже знаем что это за тег поэтому идем к следующей итерации
				}			
				
				if (file.charAt(index) == '/') { // если сразу полсле < идет / значит тэг закрывающийся
					
					System.out.print("\tзакрывающийся <");
								
					tagName = getTagName(index, file); 
					
					System.out.println(tagName + ">");
					
								parceredString.append("\tзакрывающийся <"+tagName + ">\n");
					return;//continue; // мы уже узнали что это за тэг, идем к следующей итерации
				} else {				
					
					tagName = getTagName(index, file);
					
					if(isTagWithoutBody(index, file)){ // тэг может быть и без тела				
						
						System.out.println("тег <"+tagName+"/> без тела");
						
						parceredString.append("тег <"+tagName+"/> без тела");
					}
					
					System.out.println("открывающийся <" + tagName + ">"); // если дело дошло до сюда то это открывающийся тэг
								parceredString.append("открывающийся <" + tagName + ">\n");
								
					
								// нужно узнать содержит ли данный тэг контент:
								// если следующий тэг имеет такое же название значит содержит
								// если нет то контент читать не нужно
								
					int nextIndex = getNextTagIndex(index, file);       // узнаем индекс следующего тэга
					String nextTagName  = getTagName(nextIndex, file);
					StringBuilder tagWithoutSlash = new StringBuilder(nextTagName);
					tagWithoutSlash.deleteCharAt(0);
					nextTagName = tagWithoutSlash.toString();  		   // имя следующего тэга
					
					if(nextTagName.equals(tagName)){				  // если имена совпали то читаем контент тэга
						
						
						int contentStart = getNextSymbol(index, file, '>'); // начало контента
						int contentEnd	 = nextIndex;						// конец  контента
						
						String tagContext = file.substring(contentStart+1, contentEnd-1);
						
						System.out.println("\t\tСодержимое "+tagName+":"+tagContext);
									parceredString.append("\t\tСодержимое "+tagName+":"+tagContext+"\n");
					}				
				}							
			}
			index = getNextTagIndex(index, file)-1;		
	
			try{
				
				PrintStream ps = new PrintStream(new FileOutputStream("d://parceredXML.txt"));		
				ps.print(parceredString);
				
			} catch(FileNotFoundException e){
				System.out.println(e.getMessage());
			}		
		//}		
	}
	
	public void setFile(String fileName){
		try{
			this.file = FileReader.readFromFile("d://notes.xml", StandardCharsets.UTF_8);
			
		} catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	public int getTagQuantity(){
		Pattern p = Pattern.compile("<");
		Matcher m = p.matcher(file);
		
		int countOfTags = 0;
		while(m.find()){
			countOfTags++;
		}
		return countOfTags;
	}
	
	private static String getTagName(int index, String file){ // возвращает имя тэга которое зачитается с index
		StringBuilder tagName = new StringBuilder();
		int nameIndex = index;
		
		while(file.charAt(nameIndex) != ' ' && file.charAt(nameIndex) != '>' ){ // если встречам пробел или > значит имя тэга закончилось
			tagName.append(file.charAt(nameIndex));
			nameIndex++;
		}			
		return new String(tagName);
	}
	
	private static int getNextTagIndex(int index, String file){ // ищем следующий тэг
		
		try{
			while(file.charAt(index) != '<') index++;
			
		} catch(StringIndexOutOfBoundsException e){
			System.out.println("Конец документа");
		}
		
		
		return ++index;
	}
	
	private static int getNextSymbol(int index, String file, char symbol){ // поиск определенного символа с определенного места в строке
		
		while(file.charAt(index) != symbol){
			index++;
		}		
		return index;
	}
	
	private static boolean isTagWithoutBody(int index, String file){ // является ли тэг тэгом без тела
		int nextSymbol =  getNextSymbol(index, file, '>'); // если перед символом > стоит символ / то тэг без тела
		
		if(file.charAt(nextSymbol-1) == '/'){
			return true;
		}
		return false;
	}
}
