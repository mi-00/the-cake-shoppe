package lu.mihaela;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ProductsServlet
 */
@WebServlet(description = "implements CRUD", urlPatterns = { "/ProductsServlet" })
public class ProductsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String action;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		RequestDispatcher dispatcher = null;
		PrintWriter out = response.getWriter();
		action = request.getParameter("action");
		DBTools dbt = new DBTools();
		Cake cake = new Cake();
		HttpSession session;
		HashSet<Cake> setOfCakes;
		Map<Long, Integer> cart;
		int STEP = 1;//used in pagination
		int page = 1; //when app starts, we're on first page of results
		String next = "";
		String previous = "disabled";

		switch (action.toLowerCase()) {
		case "new":
			dispatcher = request.getRequestDispatcher("WEB-INF/cakeFactory.jsp");
			dispatcher.forward(request, response);
			return;
		case "show":
			int show_id = Integer.parseInt(request.getParameter("id"));
			cake = dbt.getCake(show_id);
			request.setAttribute("cake", cake);
			dispatcher = request.getRequestDispatcher("/WEB-INF/editCake.jsp");
			dispatcher.forward(request, response);
			return;
		case "delete":
			int id;
			try {
				id = Integer.parseInt(request.getParameter("id"));
			} catch (NumberFormatException e) {
				dispatcher = request.getRequestDispatcher("/WEB-INF/wrongParameterError.jsp");
				dispatcher.forward(request, response);
				return;
			}
			ArrayList<Integer> delete_list = new ArrayList<Integer>();
			delete_list.add(id);
			dbt.deleteCakes(delete_list);
			dispatcher = request.getRequestDispatcher("/ProductsServlet?action=all&page=1");
			dispatcher.forward(request, response);
			return;
		case "all":
			if(request.getParameter("page")!=null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			ArrayList<Cake> list = dbt.getCakesList(page, STEP);
			request.setAttribute("listOfCakes", list);
			if(list.size()<STEP){
				next = "disabled";
			}else{
				next = "";
			}
			if(page>1){
				previous = "";
			}else{
				previous = "disabled";
			}
			request.setAttribute("next", next);
			request.setAttribute("prev", previous);
			dispatcher = request.getRequestDispatcher("/WEB-INF/showAll.jsp");
			dispatcher.forward(request, response);
			return;
		case "getcart"://has to be lower case since I check value of action.toLowerCase()
			session = request.getSession();
			setOfCakes = new HashSet<Cake>();
			//setOfCakes = ((HashSet<Cake>) request.getAttribute("setOfCakes"))==null?new HashSet<Cake>():(HashSet<Cake>) request.getAttribute("setOfCakes");
			cart = (Map<Long, Integer>) session.getAttribute("cart");
			
			if(cart==null){
				dispatcher = request.getRequestDispatcher("/WEB-INF/cartEmpty.jsp");
				dispatcher.forward(request, response);
				return;
			}
			
			Iterator<Entry<Long, Integer>> it = cart.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        long cartCakeID = (long) pair.getKey();
		        Cake cartCake = dbt.getCake(cartCakeID);
		        setOfCakes.add(cartCake);
		        //it.remove(); // avoids a ConcurrentModificationException
		    }
			request.setAttribute("setOfCakes", setOfCakes);
			dispatcher = request.getRequestDispatcher("/WEB-INF/showCart.jsp");
			dispatcher.forward(request, response);
			return;
		case "buy":
			setOfCakes = new HashSet<Cake>();
			session = request.getSession();
			cart = (Map<Long, Integer>) session.getAttribute("cart");

			if (cart == null) {// id=>amount
				cart = new HashMap<Long, Integer>();
				session.setAttribute("cart", cart);
			}

			long buy_id;

			try {
				buy_id = Long.parseLong(request.getParameter("id"));
			} catch (NumberFormatException e) {
				dispatcher = request.getRequestDispatcher("/WEB-INF/wrongParameterError.jsp");
				dispatcher.forward(request, response);
				return;
			}

			cake = dbt.getCake(buy_id);

			if (cake != null) {
				if (cart.containsKey(buy_id)) {
					int quantity = cart.get(buy_id);
					cart.put(buy_id, quantity + 1);
				} else {
					cart.put(buy_id, 1);
				}
				setOfCakes.add(cake);
			} else {
				dispatcher = request.getRequestDispatcher("/WEB-INF/noFile.jsp");
				dispatcher.forward(request, response);
				return;
			}

			// save cake info in set to avoid duplicates - useful in jsp
			// presentation
			request.setAttribute("setOfCakes", setOfCakes);

			dispatcher = request.getRequestDispatcher("/ProductsServlet?action=all&page=1");
			dispatcher.forward(request, response);
			return;
		case "clearcart":
			session = request.getSession();
			session.invalidate();
			
			dispatcher = request.getRequestDispatcher("/WEB-INF/cartEmpty.jsp");
			dispatcher.forward(request, response);
			return;
		case "checkout":
			session = request.getSession();
			session.invalidate();
			
			dispatcher = request.getRequestDispatcher("/WEB-INF/thankYou.jsp");
			dispatcher.forward(request, response);
			return;
		default:
			dispatcher = request.getRequestDispatcher("/WEB-INF/notFound.jsp");
			dispatcher.forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		action = request.getParameter("action");
		DBTools dbt = new DBTools();
		Cake cake = new Cake();

		RequestDispatcher dispatcher = null;

		switch (action.toLowerCase()) {
		case "add":
			dispatcher = request.getRequestDispatcher("/DBImageUploaderServlet");
			dispatcher.forward(request, response);
			return;
		case "update":
			long id;
			double price;
			try {
				id = Long.parseLong(request.getParameter("id"));
				price = Double.parseDouble(request.getParameter("price"));
			} catch (NumberFormatException e) {
				dispatcher = request.getRequestDispatcher("/WEB-INF/wrongParameterError.jsp");
				dispatcher.forward(request, response);
				return;
			}
			dbt.setNewPrice(id, price);
			cake = dbt.getCake(id);
			request.setAttribute("cake", cake);

			dispatcher = request.getRequestDispatcher("/WEB-INF/editCake.jsp");
			dispatcher.forward(request, response);
			return;
		case "addremove"://has to be lower case since I check value of action.toLowerCase()
			HashSet<Cake> setOfCakes = new HashSet<Cake>();
			HttpSession session = request.getSession();
			Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");

			if (cart == null) {//
				// we shouldn't be here if cart is null. But if we are, redirect
				dispatcher = request.getRequestDispatcher("/WEB-INF/notFound.jsp");
				dispatcher.forward(request, response);
				return;
			}
			
			Iterator<Entry<Long, Integer>> it = cart.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        long cartCakeID = (long) pair.getKey();
		        Cake cartCake = dbt.getCake(cartCakeID);
		        setOfCakes.add(cartCake);
		        //it.remove(); // avoids a ConcurrentModificationException
		    }

			long cake_id = Long.parseLong(request.getParameter("id"));
			String plusMin = request.getParameter("submit");
			// System.out.println("cake_id is: " + cake_id);//printing here
			
			cake = dbt.getCake(cake_id);

			if (cake != null && cart.containsKey(cake_id)) {
				int quantity = cart.get(cake_id);
				if (plusMin != null && plusMin.equals("+")) {
					cart.put(cake_id, (quantity + 1));

				} else if (plusMin != null && plusMin.equals("-")) {
					if ((quantity - 1) > 0) {
						cart.put(cake_id, (quantity - 1));
					} else {
						cart.remove(cake_id);
						setOfCakes.remove(cake);
					}
				}
			} else {
				dispatcher = request.getRequestDispatcher("/WEB-INF/notFound.jsp");
				dispatcher.forward(request, response);
				return;
			}

			// save cake info in set to avoid duplicates - useful in jsp
			// presentation
			request.setAttribute("setOfCakes", setOfCakes);
			
			dispatcher = request.getRequestDispatcher("/WEB-INF/showCart.jsp");
			dispatcher.forward(request, response);
			return;
		default:
			dispatcher = request.getRequestDispatcher("/WEB-INF/doPostNotFound.jsp");
			dispatcher.forward(request, response);
			return;
		}
	}

}
