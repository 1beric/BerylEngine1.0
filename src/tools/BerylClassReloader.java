package tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class BerylClassReloader extends ClassLoader {

	public BerylClassReloader(ClassLoader parent) {
		super(parent);
	}

    public Class<?> loadClass(String filePath, String name) throws ClassNotFoundException {
        if(!name.startsWith("game.behaviors.")) throw new ClassNotFoundException("IMPROPER FORMAT");
        try {
            InputStream inputStream = new File(filePath).toURI().toURL().openConnection().getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = inputStream.read();
            while(data != -1) {
                buffer.write(data);
                data = inputStream.read();
            }
            inputStream.close();
            byte[] classData = buffer.toByteArray();
            return defineClass(name, classData, 0, classData.length);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
