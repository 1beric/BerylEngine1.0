package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import models.components.BerylComponent;
import models.components.renderable.BerylRC;
import models.components.updatable.BerylUC;
import models.data.Entity;

public class BerylTools {

	public static Entity[] recompileGroups(String sceneFile) {
		Map<String,List<String>> lines = new HashMap<>();
		try {
			Scanner scanner = new Scanner(new File(sceneFile));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.startsWith("#") || line.length()==0) continue;
				String[] arr = line.split(",");
				if (!lines.containsKey(arr[0])) lines.put(arr[0], new ArrayList<>());
				lines.get(arr[0]).add(arr[1]);
				
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found: src/game/groups.txt");
			e.printStackTrace();
		}
		Entity[] entities = new Entity[lines.keySet().size()];
		int i = 0;
		for (String name : lines.keySet()) {
			entities[i] = new Entity(name);
			for (String behaviorClass : lines.get(name)) {
				Class<?> c = compile(
						"src/game/behaviors/" + behaviorClass + ".java",
						"bin/game/behaviors/" + behaviorClass + ".class",
						"game.behaviors." + behaviorClass);
				BerylComponent component = null;
				try {
					component = (BerylComponent) c.getConstructor(Entity.class).newInstance(entities[i]);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				if (component != null) {
					if (component instanceof BerylRC) entities[i].addComponent((BerylRC)component);
					if (component instanceof BerylUC) entities[i].addComponent((BerylUC)component);
				}
			}
			i++;
		}
		return entities;
	}
	
	public static Class<?> compile(String srcFilePath, String compFilePath, String className) {
		// compile
		File sourceFile = new File(srcFilePath);
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> compUnits =  fileManager.getJavaFileObjects(sourceFile);
		try {
			compiler.getTask(null, fileManager, null, Arrays.asList(new String[] { "-d", new File("bin/game/behaviors").getCanonicalPath()}), null, compUnits).call();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// reload
		return reload(compFilePath, className);
	}

	private static Class<?> reload(String dotClassPath, String name) {
	    ClassLoader parentClassLoader = BerylClassReloader.class.getClassLoader();
	    BerylClassReloader classLoader = new BerylClassReloader(parentClassLoader);
		try {
			return classLoader.loadClass(dotClassPath, name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
