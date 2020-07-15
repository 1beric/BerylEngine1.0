package settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import meshCreation.OBJFileLoader;
import models.data.ModelData;
import renderEngine.Loader;
import renderEngine.models.LookupTable;
import renderEngine.models.RawModel;

public class PreloadLookupTable {
	
	public static void preload(String filename) {
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
		String[] splitLine = line.split(",");
		switch (splitLine[0]) {
		case "obj":
			ModelData data = OBJFileLoader.loadOBJ(splitLine[1]);
			RawModel model = Loader.loadToVAO(data);
			LookupTable.putRawModel(splitLine[1], model);
			LookupTable.putRawModel(data, model);	
			break;
		case "png":
			LookupTable.putTexture(splitLine[1], Loader.loadTexture(splitLine[1]));
			break;
		case "sky":
			LookupTable.putTexture(splitLine[1].split(";"), Loader.loadCubeMap(splitLine[1].split(";")));
			break;
		default:
			break;
		}
	}

}
