package testIntrospection;

public class Vehicule {
	
	private String marque;

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	@Override
	public String toString() {
		return "marque : " + getMarque();
	}

}
