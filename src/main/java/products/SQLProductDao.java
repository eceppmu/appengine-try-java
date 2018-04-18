package products;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public List listProductByName(String name) {
		ArrayList<Product> productList = new ArrayList();

		try {
			PreparedStatement productQuery = conn.prepareStatement("select * from Product where name like '%" + name + "' limit 5;");
			
			ResultSet rs = productQuery.executeQuery();
			
			
			while(rs.next()) {
				Product product = new Product();
				product.setSku(rs.getString("sku"));
				product.setName(rs.getString("name"));
				product.setType(rs.getString("type"));
				//product.setPrice(rs.getString("sku"));
				product.setImage(rs.getString("image"));
				product.setUrl(rs.getString("url"));
				
				productList.add(product);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}
		
		return productList;
	}

}
