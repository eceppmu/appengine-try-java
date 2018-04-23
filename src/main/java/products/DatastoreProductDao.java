package products;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class DatastoreProductDao {
	  private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); // A;uthorized Datastore service
	  private static String ENTITY_NAME = "Product";
	  private static String FIELD_SKU = "sku";
	  private static String FIELD_NAME = "name";
	  private static String FIELD_PRICE = "price";
	  private static String FIELD_URL = "url";
	  private static String FIELD_IMAGE = "image"; 

	  public DatastoreProductDao() {
		  datastore = DatastoreServiceFactory.getDatastoreService();
	  }

	  public long createProduct(Product product) {
	    Entity productEntity = new Entity(ENTITY_NAME);  // Key will be assigned once written
	    productEntity.setProperty(FIELD_SKU, product.getSku());
	    productEntity.setProperty(FIELD_NAME, product.getName());
	    productEntity.setProperty(FIELD_PRICE, product.getPrice());
	    productEntity.setProperty(FIELD_URL, product.getUrl());
	    productEntity.setProperty(FIELD_IMAGE, product.getImage());


	    Key productKey = datastore.put(productEntity); // Save the Entity
	    return productKey.getId();                     // The ID of the Key
	  }
}
