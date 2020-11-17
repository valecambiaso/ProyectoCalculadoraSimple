package Logica;

public class Division implements PluginInterface {
	private int num1,num2;
	boolean error;
	
	@Override
	public void setParametros(int p1, int p2) {
		num1 = p1;
		num2 = p2;
		error = num2 == 0 ? true : false;
	}

	@Override
	public int getResultado() {
		int res = 0;
		if(!error) {
			res = num1 / num2;
		}
		return res;
	}

	@Override
	public String getNombrePlugin() {
		return "Division";
	}

	@Override
	public boolean hasError() {
		return error;
	}
}
