package myapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class InitServlet extends HttpServlet {
	  @Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    //resp.setContentType("text/plain");
	    //resp.getWriter().println("Initializing...");
	    
	    
	    // clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new FileInputStream(new File("WEB-INF/client_secrets.json"))));
	    
	    try {
		    InputStream productsStream = getServletContext().getResourceAsStream("/WEB-INF/products.json");
		    
		    JsonReader jsonReader = new JsonReader(new InputStreamReader(productsStream));
		    while(jsonReader.hasNext()){
		        JsonToken nextToken = jsonReader.peek();
		        System.out.println(nextToken);

		        if(JsonToken.BEGIN_OBJECT.equals(nextToken)){

		            jsonReader.beginObject();

		        } else if(JsonToken.NAME.equals(nextToken)){

		            String name  =  jsonReader.nextName();
		            System.out.println(name);

		        } else if(JsonToken.STRING.equals(nextToken)){

		            String value =  jsonReader.nextString();
		            System.out.println(value);

		        } else if(JsonToken.NUMBER.equals(nextToken)){

		            long value =  jsonReader.nextLong();
		            System.out.println(value);

		        }
		    }
		    jsonReader.close();
		    productsStream.close();
	    } catch (Exception e) {
	    	e.printStackTrace(new PrintStream(resp.getOutputStream()));
	        //throw new RuntimeException("failed to load Instance json file " + resourcePath + ": " + e.getMessage(), e);
	    } catch (Throwable th) {
	    	th.printStackTrace(new PrintStream(resp.getOutputStream()));
	    }
	    

	  }
}
