package DAO;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {
	public static String readFromFile(String fileName, Charset encoding) throws IOException{
		byte[] encoded = Files.readAllBytes(Paths.get(fileName));
		return new String(encoded, encoding);
	}
}
