package lu.mihaela;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import java.sql.Blob;

import java.util.*;

/**
 * Servlet implementation class ImageUploaderServlet
 */
@WebServlet(description = "uploads cake images in the DB", urlPatterns = { "/DBImageUploaderServlet" })
public class DBImageUploaderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int SIZE_THRESHOLD = 1024;

	private static final long FILE_SIZE_MAX = 1024 * 1024;
	private static final long SIZE_MAX = 1024 * 1024 + 1024;
	
	String action, imgName, name, ingredients, method;
	
	double price;
	
	File tempFile = null;//the temporary file directory
	
	InputStream inputStream = null;
	       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBImageUploaderServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cake cake = new Cake();
		//check if form has right enctype attribute
		if(!ServletFileUpload.isMultipartContent(request)){
			getServletContext().getRequestDispatcher("/noFile.html").forward(request, response);
			return;//important to return after forwarding
		}
		
		// the temp location of the file
		tempFile = new File(System.getProperty("java.io.tmpdir"));//tempFile is: / tmp
		
		
		//it means i don't serve images, but return text
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//above size_threshold, files will be written to disk
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(SIZE_THRESHOLD, tempFile);
		ServletFileUpload fileUploadHandler = new ServletFileUpload(fileItemFactory);
		
		fileUploadHandler.setFileSizeMax(FILE_SIZE_MAX);
		fileUploadHandler.setSizeMax(SIZE_MAX);
		
		try{
			List<FileItem> itemsFromForm = fileUploadHandler.parseRequest(new ServletRequestContext(request));
			if(itemsFromForm!=null && itemsFromForm.size()>0){
				for(FileItem item: itemsFromForm){
					if(!item.isFormField()){
						imgName = item.getName();
						String fieldName = item.getFieldName();
					    String contentType = item.getContentType();
					    boolean isInMemory = item.isInMemory();
					    long sizeInBytes = item.getSize();

	                    if (sizeInBytes > SIZE_MAX || !contentType.equals("image/jpeg")) {
			                getServletContext().getRequestDispatcher("/WEB-INF/wrongFileSize.jsp").forward(request, response);
			                return;// important to return after forwarding
		                }

					    inputStream = item.getInputStream();
					}else{
					    String fieldName = item.getFieldName();
					    String value = item.getString();
					    switch(fieldName.toLowerCase()){
					    case "name":
					    	name=value;
					    	break;
					    case "ingredients":
					    	ingredients=value;
					    	break;
					    case "method":
					    	method=value;
					    	break;
					    case "price":
					    	price=Double.parseDouble(value);
					    	break;
					    default:
					    	return;
					    }
					}
				}
				DBTools dbt = new DBTools();
				dbt.addCake(imgName, name, ingredients, method, price, inputStream);
				
				long lastID = dbt.getlastInsertedRowID();
				
				cake = dbt.getCake(lastID);
				request.setAttribute("cake", cake);
			    
			    inputStream.close();
			}
		}catch(Exception e){
			request.setAttribute("Error message: ", "The following error has occured in saving the image: " + e.getMessage());
			getServletContext().getRequestDispatcher("/WEB-INF/writingError.jsp").forward(request, response);
			return;
		}
		getServletContext().getRequestDispatcher("/WEB-INF/displayCake.jsp").forward(request, response);
	}
}

