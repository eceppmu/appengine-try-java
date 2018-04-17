package myapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.com.google.api.client.json.JsonFactory;
import com.google.appengine.repackaged.com.google.api.client.json.JsonParser;
import com.google.appengine.repackaged.com.google.api.client.json.jackson2.JacksonFactory;

public class InitServlet extends HttpServlet {
	  @Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    //resp.setContentType("text/plain");
	    //resp.getWriter().println("Initializing...");
	    
	    
	    // clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new FileInputStream(new File("WEB-INF/client_secrets.json"))));
	    
	    try {
		    InputStream productsStream = getServletContext().getResourceAsStream("/WEB-INF/products.json");
		    		    
		    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

	        JsonParser parser = jsonFactory.createJsonParser(productsStream);
	        	        
	        while(parser.nextToken() != null){
	        	resp.getOutputStream().println("jsonToken = " + parser.getCurrentToken() + "<br>");	            
	        }	        
	        
	    } catch (Exception e) {
	    	e.printStackTrace(new PrintStream(resp.getOutputStream()));
	        //throw new RuntimeException("failed to load Instance json file " + resourcePath + ": " + e.getMessage(), e);
	    } catch (Throwable th) {
	    	th.printStackTrace(new PrintStream(resp.getOutputStream()));
	    }
	    

	  }
}
