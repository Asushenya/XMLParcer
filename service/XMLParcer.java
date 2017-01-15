package service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import DAO.FileReader;

public class XMLParcer {

	
	private StringBuilder parceredString = new StringBuilder();
	
	String file;
	
	private  int index = 0;
	
	public void nextParce(){
		
		if(index >= file.length()-1 ){
			return ;
		}
		
		//while(index < file.length()-1){ // ������ ��������� ���� �� ������ �� �����
			
			if(file.charAt(index) == '<'){ // ��� ������ ��������� < ����� ��� ��� �����-�� ���
										   // ����� ������ ��� ���
				
				index++;
				String tagName = "";
				
				if(getTagName(index, file).equals("?xml")){ // ���� �� ���������� ?xml, �� ����� ��� ��� ��������
					
					String xmlInfo = file.substring(index+4,getNextSymbol(index, file, '>')-1);  // ���������� �� xml
					
					System.out.println("xml ����������: "+xmlInfo);
						parceredString.append("xml ����������: "+xmlInfo+"\n");
					return ;//continue; // �� ��� ����� ��� ��� �� ��� ������� ���� � ��������� ��������
				}			
				
				if (file.charAt(index) == '/') { // ���� ����� ������ < ���� / ������ ��� �������������
					
					System.out.print("\t������������� <");
								
					tagName = getTagName(index, file); 
					
					System.out.println(tagName + ">");
					
								parceredString.append("\t������������� <"+tagName + ">\n");
					return;//continue; // �� ��� ������ ��� ��� �� ���, ���� � ��������� ��������
				} else {				
					
					tagName = getTagName(index, file);
					
					if(isTagWithoutBody(index, file)){ // ��� ����� ���� � ��� ����				
						
						System.out.println("��� <"+tagName+"/> ��� ����");
						
						parceredString.append("��� <"+tagName+"/> ��� ����");
					}
					
					System.out.println("������������� <" + tagName + ">"); // ���� ���� ����� �� ���� �� ��� ������������� ���
								parceredString.append("������������� <" + tagName + ">\n");
								
					
								// ����� ������ �������� �� ������ ��� �������:
								// ���� ��������� ��� ����� ����� �� �������� ������ ��������
								// ���� ��� �� ������� ������ �� �����
								
					int nextIndex = getNextTagIndex(index, file);       // ������ ������ ���������� ����
					String nextTagName  = getTagName(nextIndex, file);
					StringBuilder tagWithoutSlash = new StringBuilder(nextTagName);
					tagWithoutSlash.deleteCharAt(0);
					nextTagName = tagWithoutSlash.toString();  		   // ��� ���������� ����
					
					if(nextTagName.equals(tagName)){				  // ���� ����� ������� �� ������ ������� ����
						
						
						int contentStart = getNextSymbol(index, file, '>'); // ������ ��������
						int contentEnd	 = nextIndex;						// �����  ��������
						
						String tagContext = file.substring(contentStart+1, contentEnd-1);
						
						System.out.println("\t\t���������� "+tagName+":"+tagContext);
									parceredString.append("\t\t���������� "+tagName+":"+tagContext+"\n");
					}				
				}							
			}
			index++;		
	
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
			this.file = FileReader.readFromFile("d://plant_catalog.xml", StandardCharsets.UTF_8);
			
		} catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	private static String getTagName(int index, String file){ // ���������� ��� ���� ������� ���������� � index
		StringBuilder tagName = new StringBuilder();
		int nameIndex = index;
		
		while(file.charAt(nameIndex) != ' ' && file.charAt(nameIndex) != '>' ){ // ���� �������� ������ ��� > ������ ��� ���� �����������
			tagName.append(file.charAt(nameIndex));
			nameIndex++;
		}			
		return new String(tagName);
	}
	
	private static int getNextTagIndex(int index, String file){ // ���� ��������� ���
		
		while(file.charAt(index) != '<') index++;
		
		return ++index;
	}
	
	private static int getNextSymbol(int index, String file, char symbol){ // ����� ������������� ������� � ������������� ����� � ������
		
		while(file.charAt(index) != symbol){
			index++;
		}		
		return index;
	}
	
	private static boolean isTagWithoutBody(int index, String file){ // �������� �� ��� ����� ��� ����
		int nextSymbol =  getNextSymbol(index, file, '>'); // ���� ����� �������� > ����� ������ / �� ��� ��� ����
		
		if(file.charAt(nextSymbol-1) == '/'){
			return true;
		}
		return false;
	}
}
