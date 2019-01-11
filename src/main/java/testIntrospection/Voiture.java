package testIntrospection;

public class Voiture extends Vehicule {

	@Getter(nom = "positionMotorisation")
	public String motorisation;
	
	@Override
	public String toString() {
		return "Voiture " + super.toString() + ", motorisation : " + motorisation;
	}
	
	public void positionMotorisation(String motorisation) {
		this.motorisation = motorisation.toUpperCase();
	}
}
