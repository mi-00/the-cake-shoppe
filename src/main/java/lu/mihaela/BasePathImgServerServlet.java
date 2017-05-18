package lu.mihaela;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BasePathImgServerServlet
 */
@WebServlet(description = "serves images from eclipse path", urlPatterns = { "/BasePathImgServerServlet" })
public class BasePathImgServerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	static String[] images = { "black_forest", "carrot", "cassata", "cheese", "chocolate_mousse_cheese",
			"chocolate-cheese", "chocolate", "cremeschnitte", "dark_chocolate", "dobos", "gingerbread", "lemon",
			"macaron", "millefeuille", "pancake", "red_velvet", "sacher", "savarina", "tarte_tatin", "tiramisu" };
			*/

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BasePathImgServerServlet() {
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
		
		String img = request.getParameter("image");

		String imgName = "upImages/" + img;

		String imgPath = "/home/mihaela/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MihaelasCakeShoppe/"
				+ imgName;

		response.setContentType("image/jpeg");

		BufferedInputStream in = new BufferedInputStream(new FileInputStream(imgPath));

		int size = in.available();

		response.setContentLength(size);

		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());

		int readByte;

		while ((readByte = in.read()) != -1)
			out.write(readByte);

		in.close();

		// Do not forget to flush the output stream - it is buffered.
		// You do not need to close it - the server will do it for you.
		// Important: the server closes the OutputStream - it does no know anything
		// about the buffering
		
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	// localFilePath:
	// /home/mihaela/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/WebShopJSTL/upImages/sacher_cake.jpg

}
