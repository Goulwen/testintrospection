package testIntrospection;

public class Moto extends Vehicule {

	private String type;
	
	@Override
	public String toString() {
		return "Moto " + super.toString() + ", type : " + type;
	}
}
