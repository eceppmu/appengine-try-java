package myapp;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import products.Product;
import products.SQLProductDao;
import utils.MemcacheUtils;

/**
 * Servlet implementation class AutosuggestServlet
 */
public class AutosuggestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");

		String key = req.getParameter("startsWith");
		
		String value = MemcacheUtils.getCacheKey(key);
		
		if(value == null) {
		
			SQLProductDao productDao = new SQLProductDao();
		
			List<Product> productList = productDao.listProductByName(key);
		
			StringBuffer sb = new StringBuffer();
		
			sb.append("[");
			
			
			for(int i = 0; i < productList.size(); i++) {
				Product product = productList.get(i);
				//sb.append("{\"sku\":\"" + product.getSku() + "\",\"name\":\"" + product.getName() + "\"}");
				sb.append("{\"name\":\"" + product.getName() + "\"}");
	
				if(i < productList.size() -1 ) {
					sb.append(",");
				}
			}
			
			sb.append("]");
			value = sb.toString();
			
			MemcacheUtils.addCacheKey(key, value);
		}
		try {
			resp.getOutputStream().println(value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
