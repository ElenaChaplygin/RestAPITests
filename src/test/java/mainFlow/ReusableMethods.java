package mainFlow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReusableMethods {
	
	public static JsonPath dataToJson(Response r){
		String rsp = r.asString();
		JsonPath json = new JsonPath(rsp);
		return json;	
	}
	
	public static String generateStringFromResource(String path) throws IOException {
		String body = new String(Files.readAllBytes(Paths.get(path)));
	    return body;

	}

}
