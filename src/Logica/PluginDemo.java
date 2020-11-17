package Logica;


import java.io.File;

import java.util.*;

/**
 * Clase que busca y arma una lista con los plugins.
 */
public class PluginDemo {
	private final String rutaAbsoluta = "./bin/Logica"; //Ruta de la carpeta donde están los plugins.
	private final String nombreDirectorio = "Logica"; //El directorio en el que se buscan los plugins.
	private List<PluginInterface> plugins; //La lista donde se guardan los plugins.
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	/**
	 * Arma una lista con todos los plugins que extiendan a la interface PluginInterface.
	 * @return Lista de plugins.
	 */
	public List<PluginInterface> getPlugins() {
		this.plugins = new ArrayList<PluginInterface>();
		File dir = new File(rutaAbsoluta);
		ClassLoader cl = new PluginClassLoader(dir); //Llama al classloader con la direccion de la carpeta.
		String rutaPlugin; //Arma el nombre del plugin.
		String[] files; //Arreglo de archivos del directorio dir.
		Class c;
		Class [] intf;
		PluginInterface pf;
		
		if (dir.exists() && dir.isDirectory()) { //Si el archivo/directorio existe y ES un directorio.
			files = dir.list(); //Hago una lista con los archivos del directorio.
			for (int i=0; i<files.length; i++) { //Recorro todos los archivos del directorio.
				try {
					if (files[i].endsWith(".class")) { //Solo considera archivos finalizados en .class
						rutaPlugin = this.nombreDirectorio + "." + files[i].substring(0,files[i].indexOf(".")); 
						c = cl.loadClass(rutaPlugin); //Carga la clase con el nombre rutaPlugin.
						intf = c.getInterfaces(); //Obtiene las interfaces de la clase.
						for (int j = 0; j < intf.length; j++) { //Recorro las interfaces de la clase.
							if (intf[j].getName().equals(this.nombreDirectorio + ".PluginInterface")) {
								pf = (PluginInterface) c.getDeclaredConstructor().newInstance(); //Creo una nueva instancia de la clase.
								plugins.add(pf); //Agrego a los plugins.
								continue;
							}
						}
					}
				} catch (Exception ex) {
					System.err.println("El archivo " + files[i] + " no contiene una clase PluginInterface válida.");
				}
			}
		}
		return plugins; //Devuelvo la lista de plugins.
	}	
}