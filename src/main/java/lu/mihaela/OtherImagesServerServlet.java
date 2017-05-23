package lu.mihaela;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OtherImagesServerServlet
 */
@WebServlet(description = "Serves images from other_images db table", urlPatterns = { "/OtherImagesServerServlet" })
public class OtherImagesServerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int DEFAULT_BUFFER_SIZE = 1024*1024+1024; // 10KB.
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OtherImagesServerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();

		int id = Integer.parseInt(request.getParameter("imageId"));

		Blob image = null;

		byte[] imgData = null;

		try {
			DBTools dbt = new DBTools();

			Image binaryImage = dbt.getImage(id);

			image = binaryImage.getImage();// it's binary data now; can't put it as
			// value of src param
			int imgLength = (int) image.length();
			byte[] imgAsBytes = new byte[imgLength];
			InputStream readImg = image.getBinaryStream();
			int index=readImg.read(imgAsBytes, 0, imgLength);
					
					//image.getBytes(1, imgLength);
			// display the image
			
			// Init servlet response.
	        response.reset();
	        response.setBufferSize(DEFAULT_BUFFER_SIZE);
	        
			response.setContentType("image/jpg");
			
			/*response.setHeader("Content-Length", String.valueOf(file.getLength()));
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");*/
			
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(imgAsBytes, 0, imgLength);
			outputStream.flush();
			outputStream.close();

		} catch (Exception e) {
			out.println("Unable to display this image");
			out.println("Error=" + e.getMessage());
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
