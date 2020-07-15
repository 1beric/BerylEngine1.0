package settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import tools.math.BerylMath;
import tools.math.BerylVector;

/**
 * Supported classes:
 * 	int
 * 	float
 * 	Vector2f
 * 	BerylVector
 *  String
 *  String[]
 * File format:
 * 	# this is a comment line
 * 	class,name,data[,...]
 * @author Brandon Erickson
 *
 */
public class Constants {

	private static Map<String, Object> map = new HashMap<String, Object>();
	
	public static void loadConstants(String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(filename)))) {
			String line = reader.readLine();
			while (line != null) {
				processLine(line);
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist: " + filename);
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.out.println("Error reading file: " + filename);
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private static void processLine(String line) {
		if (line.startsWith("#")) return;
		String[] lineArray = line.split(",");
		String name = lineArray[1];
		switch (lineArray[0]) {
		case "String":
			map.put(name, lineArray[2]);
			break;
		case "String[]":
			String[] finalArray = new String[lineArray.length-2];
			for (int i=0; i<finalArray.length; i++) {
				finalArray[i] = lineArray[i+2];
			}
			map.put(name,finalArray);
			break;
		case "int":
			map.put(name,Integer.parseInt(lineArray[2]));
			break;
		case "float":
			map.put(name,Float.parseFloat(lineArray[2]));
			break;
		case "Vector2f":
			map.put(name, new BerylVector(Float.parseFloat(lineArray[2]),Float.parseFloat(lineArray[3])));
			break;
		case "Vector3f":
			if (lineArray.length==5) map.put(name, new BerylVector(Float.parseFloat(lineArray[2]),Float.parseFloat(lineArray[3]),Float.parseFloat(lineArray[4])));
			else				  map.put(name,BerylMath.hexToRGB(lineArray[2]));
			break;
		}
	}

	public static Object get(String key) {
		if (map.containsKey(key)) return map.get(key);
		return null;
	}

}
