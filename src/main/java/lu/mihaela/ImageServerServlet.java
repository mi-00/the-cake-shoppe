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
 * Servlet implementation class ImageServerServlet
 */
@WebServlet(description = "Displays images on front page, concealing the image path", urlPatterns = { "/ImageServerServlet" })
public class ImageServerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//read the img name
		String img = request.getParameter("image");
		
		//set the path to it in a variable
		String imgName = "images/" + img;
		
		//get the path to the image (app path, from root)
		String imgPath = getServletContext().getRealPath(imgName);
		
		//set response type to image, as we're serving images
		response.setContentType("image/jpeg"); //what if I want to serve info(i.e. text) as well??
		
		//response.addHeader("Content-Disposition","attachment; filename=myImage.jpg");
		
		//read the image with FileInputStream
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(imgPath));

		int size = in.available();

		// Set the size of the output (it should work in any browser without
		// this. Try what happens when you omit this)
		
		response.setContentLength(size);

		BufferedOutputStream out = new BufferedOutputStream(
				response.getOutputStream());

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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
