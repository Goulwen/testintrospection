package testIntrospection;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;

public class Main {

	public static void main(String[] args) throws Exception {
		Voiture voiture = new Voiture();
//		voiture.setMarque("Renault");
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
		String json = mapper.writeValueAsString(voiture);
		System.out.println(json);

	}

}
