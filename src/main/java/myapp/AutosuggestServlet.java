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
		SQLProductDao productDao = new SQLProductDao();
		
		List productList = productDao.listProductByName(req.getParameter("startsWith"));
		
		for(int i = 0; i < productList.size(); i++) {
			try {
				resp.getOutputStream().println(((Product)productList.get(i)).getName() + "<br>");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
