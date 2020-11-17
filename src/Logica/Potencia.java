package Logica;

public class Potencia implements PluginInterface{
	private int num1,num2;
	
	@Override
	public void setParametros(int p1, int p2) {
		num1 = p1;
		num2 = p2;
	}

	@Override
	public int getResultado() {
		return (int) Math.pow(num1,num2);
	}

	@Override
	public String getNombrePlugin() {
		return "Potencia";
	}

	@Override
	public boolean hasError() {
		return false; //La potencia nunca tiene errores.
	}
}
