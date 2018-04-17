package myapp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import products.DatastoreProductDao;
import products.Product;
import products.ProductDao;

public class InitServlet extends HttpServlet {
	  @Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    //resp.setContentType("text/plain");
	    //resp.getWriter().println("Initializing...");
	    
	    
	    // clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new FileInputStream(new File("WEB-INF/client_secrets.json"))));
	    
	    try {
	    	ProductDao productDao = new DatastoreProductDao();
	    		    	
	    	//InputStream productsStream = getServletContext().getResourceAsStream("/WEB-INF/products.json.zip");
	    	InputStream productStream = new URL("https://storage.googleapis.com/ppmutestapp.appspot.com/products.json").openStream();

	    	Gson gson = new GsonBuilder().create();
		    
		    JsonReader jsonReader = new JsonReader(new InputStreamReader(productStream));
	    	jsonReader.beginArray();

		    while(jsonReader.hasNext()) {
		    //for(int i = 0; i < 10; i++ ) {
		    	
		    	Product product = gson.fromJson(jsonReader, Product.class);
		    	
		    	long key = productDao.createProduct(product);
		   
		    	System.out.println(key + " : " + product);
		    }

		    jsonReader.close();
		    //productsStream.close();
	    } catch (Exception e) {
	    	e.printStackTrace(new PrintStream(resp.getOutputStream()));
	        //throw new RuntimeException("failed to load Instance json file " + resourcePath + ": " + e.getMessage(), e);
	    } catch (Throwable th) {
	    	th.printStackTrace(new PrintStream(resp.getOutputStream()));
	    }
	    

	  }
}
