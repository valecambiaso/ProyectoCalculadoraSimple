package Logica;

public interface PluginInterface {
	
	/**
	 * Obtiene los parametros de la operación.
	 * @param p1 Primer parámetro numérico.
	 * @param p2 Segundo parámetro numérico.
	 */
	public void setParametros(int p1,int p2);

	/**
	 * Retorna el resultado de la operación.
	 * @return Resultado de la operación.
	 */
	public int getResultado();
	
	/**
	 * Retorna el nombre del plugin.
	 * @return Nombre del plugin.
	 */
	public String getNombrePlugin();

	/**
	 * Verifica que los parámetros no sean inválidos.
	 * @return True en caso de que haya error, falso en caso contrario.
	 */
	public boolean hasError();
}
