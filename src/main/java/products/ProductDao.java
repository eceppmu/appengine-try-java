package products;

import java.util.List;

public interface ProductDao {
	  public long createProduct(Product product);
	  public List<Product> listProductByName(String name);
}
