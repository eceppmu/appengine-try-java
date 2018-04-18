package products;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SQLProductDao implements ProductDao {
	Connection conn = null;
	final String createProductSql = "INSERT INTO Product (sku, name, type, price, image, url) VALUES (?,?,?,?,?,?);";

	public SQLProductDao() {
		try {
			conn = DriverManager.getConnection("jdbc:google:mysql://ppmutestapp:us-central1:products");
		} catch (Throwable th) {
			th.printStackTrace(System.out);
		}
	}

	@Override
	public long createProduct(Product product) {
		try {
			PreparedStatement statementCreateProduct = conn.prepareStatement(createProductSql);
			statementCreateProduct.setString(0, product.getSku());
			statementCreateProduct.setString(1, product.getName());
			statementCreateProduct.setString(2, product.getType());
			statementCreateProduct.setString(3, "" + product.getPrice());
			statementCreateProduct.setString(4, product.getImage());
			statementCreateProduct.setString(5, product.getUrl());
			
			statementCreateProduct.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return 0;
	}

}
