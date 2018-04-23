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
import utils.StorageUtils;

public class InitServlet extends HttpServlet {
	  @Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    //resp.setContentType("text/plain");
	    //resp.getWriter().println("Initializing...");
	    
	    
	    // clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new FileInputStream(new File("WEB-INF/client_secrets.json"))));
	    
	    try {
	    	DatastoreProductDao productDao = new DatastoreProductDao();
	    		    	
	    	//InputStream productsStream = getServletContext().getResourceAsStream("/WEB-INF/products.json.zip");
	    	//InputStream productStream = new URL("https://storage.googleapis.com/ppmutestapp.appspot.com/products.json").openStream();

	    	Gson gson = new GsonBuilder().create();
	    	
	    	for( int i = 1; i <= 11; i++ ) {
		    	InputStream productsStream = getServletContext().getResourceAsStream("/WEB-INF/json/xaa"+i+".json");

			    JsonReader jsonReader = new JsonReader(new InputStreamReader(productsStream));
		    	
			    //JsonReader jsonReader = new JsonReader(StorageUtils.readFile("ppmutestapp.appspot.com", "products.json"));
		    	jsonReader.beginArray();
		    	
		    	Product product = null;
	
			    while(jsonReader.hasNext()) {		    	
			    	product = gson.fromJson(jsonReader, Product.class);
			    	
			    	long key = productDao.createProduct(product);
			   
			    	System.out.println(key + " : " + product);
			    }
	
			    jsonReader.close();
			    productsStream.close();
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace(new PrintStream(resp.getOutputStream()));
	        //throw new RuntimeException("failed to load Instance json file " + resourcePath + ": " + e.getMessage(), e);
	    } catch (Throwable th) {
	    	th.printStackTrace(new PrintStream(resp.getOutputStream()));
	    }
	    

	  }
}
