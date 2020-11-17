package Logica;

public class Resta implements PluginInterface{
	private int num1,num2;
	
	@Override
	public void setParametros(int p1, int p2) {
		num1 = p1;
		num2 = p2;
	}

	@Override
	public int getResultado() {
		return num1 - num2;
	}

	@Override
	public String getNombrePlugin() {
		return "Resta";
	}

	@Override
	public boolean hasError() {
		return false; //La resta nunca tiene errores.
	}
}
