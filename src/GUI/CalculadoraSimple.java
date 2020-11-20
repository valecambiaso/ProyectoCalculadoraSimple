package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;
import Logica.PluginDemo;
import Logica.PluginInterface;

/**
 * Clase que modela una calculadora simple con plugins.
 */
public class CalculadoraSimple {

	private JFrame frame;
	private JTextField variable1;
	private JTextField variable2;
	private JTextField resultado;
	PluginDemo pg = new PluginDemo();
	private List<PluginInterface> listaPlugins = null;
	
	/**
	 * Ejecuta la aplicación.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculadoraSimple window = new CalculadoraSimple();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea la aplicación.
	 */
	public CalculadoraSimple() {
		initialize();
	}

	/**
	 * Inicializo el contenido del frame.
	 */
	private void initialize() {
		frame = new JFrame("Calculadora Simple");
		frame.setBounds(100, 100, 320, 145);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/img/calculator.png"));
		frame.setIconImage(imageIcon.getImage());
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(3, 2, 3, 3));
		
		//Etiquetas con los nombres de las entradas.
		variable1 = new JTextField();
		panel.add(variable1);
		variable1.setColumns(10);
		
		variable2 = new JTextField();
		panel.add(variable2);
		variable2.setColumns(10);
		
		//Caja desplegable donde se muestran los nombres de los plugins disponibles.
		JComboBox<String> pluginsBox = new JComboBox<String>();
		panel.add(pluginsBox);
		
		//Caja de texto donde se muestra el resultado de la operación.
		resultado = new JTextField();
		panel.add(resultado);
		resultado.setColumns(10);
		resultado.setEnabled(false);
		
		//Botón que encarga que se ejecute la operación del plugin.
		JButton calcular = new JButton("Calcular");
		calcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				calcular(pluginsBox);
			}
		});
		panel.add(calcular);
		
		//Botón que actualiza los Plugins.
		JButton actualizar = new JButton("Actualizar");
		actualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarComboBox(pluginsBox); //Actualizo el combobox.
			}
		});
		panel.add(actualizar);
	}
	
	/**
	 * Actualizo los plugins de la combobox.
	 * @param pluginsBox Combobox en la que actualizo los plugins.
	 */
	private void actualizarComboBox(JComboBox<String> pluginsBox) {
		pluginsBox.removeAllItems(); //Elimino los items que ya estaban para que no se me dupliquen.
		listaPlugins = pg.getPlugins();
		limpiar();
		if(listaPlugins != null && !listaPlugins.isEmpty()) {
			Iterator<PluginInterface> iter = listaPlugins.iterator();
			while (iter.hasNext()) {
				PluginInterface pf = (PluginInterface) iter.next();
				pluginsBox.addItem(pf.getNombrePlugin());
				
			}
		}else {
			resultado.setText("No hay operaciones.");
		}
	}
	
	/**
	 * Elimina el texto en las etiquetas de la pantalla.
	 */
	private void limpiar() {
		variable1.setText("");
		variable2.setText("");
		resultado.setText("");
	}
	
	/**
	 * Lleva a cabo la operación.
	 * @param pluginsBox ComboBox con los nombres de los plugins.
	 */
	private void calcular(JComboBox<String> pluginsBox) {
		if(listaPlugins != null && !listaPlugins.isEmpty()) { //Si la lista de plugins no está vacía, llevamos a cabo la operación.
			try{
				PluginInterface pf = null;
				//Obtenemos los números ingresados por el usuario.
				int v1 = Integer.parseInt(variable1.getText()); 
				int v2 = Integer.parseInt(variable2.getText());
				//Obtenemos el nombre del plugin que seleccionó.
				String nombre = pluginsBox.getItemAt(pluginsBox.getSelectedIndex());
				Iterator<PluginInterface> iter = listaPlugins.iterator(); //Iterador de plugins.
				boolean encontre = false;
				while(iter.hasNext() && !encontre) { //Mientras me queden plugins que buscar y no haya encontrado el que busco.
					pf = (PluginInterface)iter.next();
					encontre = pf.getNombrePlugin().equals(nombre) ? true : false;					
				}
				if(encontre) { //Si encontré el plugin,
					pf.setParametros(v1,v2); //Mando los parámetros.
					if(!pf.hasError()) { //Si los parámetros eran válidos,
						resultado.setText(String.valueOf(pf.getResultado())); //Obtengo el resultado.
					}else {
						resultado.setText("ERROR"); //Hubo un error en la operación.
					}
				}
			}catch(NumberFormatException e1) { //Si el usuario no ingresa nada o ingresa cualquier otra cosa que no sean números, aviso.
				resultado.setText("No ingresó numeros.");
			}
		}else { //Si la lista de plugins está vacía, aviso al usuario.
			resultado.setText("No hay operaciones.");
		}
	}
}
