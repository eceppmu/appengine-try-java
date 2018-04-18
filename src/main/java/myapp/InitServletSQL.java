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
import products.SQLProductDao;
import utils.StorageUtils;

public class InitServletSQL extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// "INSERT INTO Product (sku, name, type, price, image, url) VALUES
		// (?,?,?,?,?,?);";

		try {
			ProductDao productDao = new SQLProductDao();

			Gson gson = new GsonBuilder().create();

			//for (int i = 1; i <= 11; i++) {

				InputStream productsStream = getServletContext().getResourceAsStream("/WEB-INF/json/products.json");

				JsonReader jsonReader = new JsonReader(new InputStreamReader(productsStream));

				jsonReader.beginArray();

				Product product = null;

				while (jsonReader.hasNext()) {
					product = gson.fromJson(jsonReader, Product.class);

					//long key = productDao.createProduct(product);

					System.out.println("INSERT INTO Product (sku, name, type, price, image, url) VALUES ('"
					+ product.getSku() + "','"
					+ product.getName() + "','"
					+ product.getType() + "','"
					+ product.getPrice() + "','"
					+ product.getImage() + "','"
					+ product.getUrl() + "');");
				}
				jsonReader.close();
				productsStream.close();
			//}
		} catch (Exception e) {
			e.printStackTrace(new PrintStream(resp.getOutputStream()));
			// throw new RuntimeException("failed to load Instance json file " +
			// resourcePath + ": " + e.getMessage(), e);
		} catch (Throwable th) {
			th.printStackTrace(new PrintStream(resp.getOutputStream()));
		}

	}

	public void doGet2(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		try {
			String number = req.getParameter("number");

			ProductDao productDao = new SQLProductDao();

			Gson gson = new GsonBuilder().create();

			InputStream productsStream = getServletContext()
					.getResourceAsStream("/WEB-INF/json/xaa" + number + ".json");

			JsonReader jsonReader = new JsonReader(new InputStreamReader(productsStream));

			jsonReader.beginArray();

			Product product = null;

			while (jsonReader.hasNext()) {
				product = gson.fromJson(jsonReader, Product.class);

				long key = productDao.createProduct(product);

				System.out.println(key + " : " + product);
			}

			jsonReader.close();
			productsStream.close();
		} catch (Exception e) {
			e.printStackTrace(new PrintStream(resp.getOutputStream()));
			// throw new RuntimeException("failed to load Instance json file " +
			// resourcePath + ": " + e.getMessage(), e);
		} catch (Throwable th) {
			th.printStackTrace(new PrintStream(resp.getOutputStream()));
		}

	}
}
