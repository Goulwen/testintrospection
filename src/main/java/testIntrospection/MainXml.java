package testIntrospection;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class MainXml {

	public static HashMap<String, String> mapper = new HashMap<>();

	static {
		mapper.put("voiture", "testIntrospection.Voiture");
		mapper.put("moto", "testIntrospection.Moto");
	}

	public static void main(String[] args) throws Exception {
		List<Vehicule> parc = new ArrayList<>();
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("C:\\Users\\admin\\eclipse-workspace\\testIntrospection\\src\\main\\resources\\parc.xml");
		Document document = (Document) builder.build(xmlFile);
		Element rootNode = document.getRootElement();
		List<Element> list = rootNode.getChildren();
		// On met dans une liste tous les noeuds enfants que contient le fichier xml
		// notre fichier c'est voiture/voiture/moto
		// et on lui spécifie que ce sont des véhicules
		for (Element element : list) {
			String name = element.getName();
			String className = mapper.get(name);
			Class clazz = Class.forName(className);
			Vehicule newInstance = (Vehicule) clazz.newInstance();

			// On met dans une liste tous les attributs et valeurs que contient le fichier xml
			// notre fichier c'est marque/motorisation avec renault/electrique
			List<Attribute> attributes = element.getAttributes();
			for (Attribute attribute : attributes) {
				String attributeName = attribute.getName();
				try {
					// je mets set et je concatène, je sélectionne le "m" de marque
					// ou le "m" de motorisation
					// je mets en majuscule "M" puis ajoute "arque" ou "otorisation"
					// conclusion je fais : setMarque ou setMotorisation
					String methodName = "set"+attributeName.substring(0,1).toUpperCase()+attributeName.substring(1);
					// Methode = voiture avec methode du nom trouvé (setMarque ou setMotorisation)
					// qui prend en paramètre une String
					Method method = newInstance.getClass().getMethod(methodName, String.class);
					// J'appelle la méthode et j'attribut sa valeur (Renault/Peugeot/Yamaha ou
					// electrique/diesel/routiere)
					method.invoke(newInstance, attribute.getValue());
				} catch (NoSuchMethodException e) {
					// Si la méthode n'existe pas dans la classe on la capture
					try {
						Field field = newInstance.getClass().getField(attributeName);
						Getter annotation = field.getAnnotation(Getter.class);
						if (annotation != null) {
							String methodName = annotation.nom();
							Method method = newInstance.getClass().getMethod(methodName, String.class);
							method.invoke(newInstance, attribute.getValue());
						}
						else {
							field.set(newInstance, attribute.getValue());
						}
					}
					catch (NoSuchFieldException e2) {
						// Aucun champs public n'est trouvé
						Field field = newInstance.getClass().getDeclaredField(attributeName);
						// Il regarde les autres champs (private)
						field.setAccessible(true);
						field.set(newInstance, attribute.getValue());
						field.setAccessible(false);
					}
				}
			}

			parc.add(newInstance);
		}

		for (Vehicule vehicule : parc) {
			System.out.println(vehicule);
		}

		//		String nom = "Dupont";
		//		Method method = String.class.getMethod("toUpperCase");
		//		String resultat = (String) method.invoke(nom);
		//		System.out.println(resultat);

	}

}
