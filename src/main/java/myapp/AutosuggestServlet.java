package myapp;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import products.Product;
import products.SQLProductDao;

/**
 * Servlet implementation class AutosuggestServlet
 */
public class AutosuggestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");

		SQLProductDao productDao = new SQLProductDao();
		
		List<Product> productList = productDao.listProductByName(req.getParameter("startsWith"));
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("[");
		
		
		for(int i = 0; i < productList.size(); i++) {
			Product product = productList.get(i);
			sb.append("{\"sku\":" + product.getSku() + ",\"name\":\"" + product.getName() + "\"}");
			if(i < productList.size() -1 ) {
				sb.append(",");
			}
		}
		
		sb.append("]");
		try {
			resp.getOutputStream().println(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
