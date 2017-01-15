package bean;
import java.util.Map;

public class Element {
	private String name;
	private Map<String, String> attributes;
	private String [] insertedElements;
	
	public Element(){}
	public Element(String name, Map<String, String> attributes, String[] insertedElements){
		this.name = name;
		this.attributes = attributes;
		this.insertedElements = insertedElements;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public String [] getInsertedElements() {
		return insertedElements;
	}
	public void setInsertedElements(String [] insertedElements) {
		this.insertedElements = insertedElements;
	}
	
	public void showElement(){
		System.out.println("name: "+this.name);
		System.out.println("attributes:");
		for(Map.Entry<String, String> item : attributes.entrySet()){
			System.out.println("key:"+item.getKey()+" value:"+item.getValue());
		}
		System.out.print("inserted: ");
		for(String item:this.insertedElements){
			System.out.print(item+", ");
		}
	}
}
