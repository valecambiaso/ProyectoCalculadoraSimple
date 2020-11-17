package Logica;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Clase que se ocupa de buscar los plugins en la carpeta bin.
 */
public class PluginClassLoader extends ClassLoader {
    File directory; //Directorio donde se encuentran los plugins.
    
    /**
     * Constructor que inicializa el directorio.
     * @param dir Direccion del directorio de los plugins.
     */
    public PluginClassLoader (File dir) {
		directory = dir;
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
	public Class loadClass (String name) throws ClassNotFoundException { 
      return loadClass(name, true); 
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
	public Class loadClass (String classname, boolean resolve) throws ClassNotFoundException {
      try {

        Class c = findLoadedClass(classname);

        if (c == null) {
          try { 
        	  c = findSystemClass(classname); 
          }catch (Exception ex) {}
        }

        if (c == null) {
          //Armamos el nombre.
          String filename = classname.replace('.',File.separatorChar)+".class";

          //Creamos un archivo File.
          File f = new File(directory, filename);

          int length = (int) f.length();
          byte[] classbytes = new byte[length];
          DataInputStream in = new DataInputStream(new FileInputStream(f));
          in.readFully(classbytes);
          in.close();

          //Llamamos al método que convierte los bytes en una clase.
          c = defineClass(classname, classbytes, 0, length);
        }

        if (resolve) { 
        	resolveClass(c);
        }

        //Devolvemos la clase que cargamos.
        return c;
      }
      //Avisamos que la clase no se encontró.
      catch (Exception ex) { throw new ClassNotFoundException(ex.toString()); }
    }
}