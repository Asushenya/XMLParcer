import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;



import service.XMLParcer;

public class Test {

	public static void main(String[] args) throws IOException {
			
		XMLParcer parcer = new XMLParcer();
		parcer.setFile("d://plant_catalog.xml");
		for(int i = 0;i<300;i++)
		parcer.nextParce();
	

	}
	
}
