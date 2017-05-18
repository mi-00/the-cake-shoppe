package lu.mihaela;

import java.io.File;
import java.io.IOException;
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

import java.util.*;

/**
 * Servlet implementation class ImageUploaderServlet
 */
@WebServlet(description = "uploads cake images on server", urlPatterns = { "/ImageUploaderServlet" })
public class ImageUploaderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int SIZE_THRESHOLD = 1024;// threshold size

	private static final long FILE_SIZE_MAX = 1024 * 1024;
	private static final long SIZE_MAX = 1024 * 1024 + 1024;
	private static final String UPLOAD_DIRECTORY = "upImages";
	
	String action, imgName, name, ingredients, method;
	double price;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageUploaderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub  // I do nothing with GET, only with POST
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cake cake = new Cake();
		//check if form has right enctype attribute
		if(!ServletFileUpload.isMultipartContent(request)){
			getServletContext().getRequestDispatcher("/noFile.html").forward(request, response);
			return;//important to return after forwarding
		}
		
		//it means i don't serve images, but return text
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//the temp location of the file
		File repository = new File(System.getProperty("java.io.tmpdir"));
		
		//above size_threshold, files will be written to disk
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(SIZE_THRESHOLD, repository);
		ServletFileUpload fileUploadHandler = new ServletFileUpload(fileItemFactory);
		
		fileUploadHandler.setFileSizeMax(FILE_SIZE_MAX);
		fileUploadHandler.setSizeMax(SIZE_MAX);
		
		String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
		
		File uploadDir = new File(uploadPath);
		if(!uploadDir.exists()){
			uploadDir.mkdir();
		}
		
		try{
			List<FileItem> itemsFromForm = fileUploadHandler.parseRequest(new ServletRequestContext(request));
			if(itemsFromForm!=null && itemsFromForm.size()>0){
				for(FileItem item: itemsFromForm){
					if(!item.isFormField()){
						String fileName = item.getName();
						imgName = fileName;
						String filePath = uploadPath + File.separator + fileName;
						
						File fileToBeStored = new File(filePath);
						
						item.write(fileToBeStored);
						request.setAttribute("localFile", filePath);
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
				dbt.addCake(imgName, name, ingredients, method, price);
				long lastID = dbt.getlastInsertedRowID();
				//I should instantiate a cake obj, fill the results and set attr of request
				cake.setId(lastID);
				cake.setImgName(imgName);
				cake.setName(name);
				cake.setIngredients(ingredients);
				cake.setMethod(method);
				cake.setPrice(price);
				request.setAttribute("cake", cake);
			}
		}catch(Exception e){
			request.setAttribute("Error message: ", "The following error has occured in saving the image: " + e.getMessage());
			getServletContext().getRequestDispatcher("/WEB-INF/writingError.jsp").forward(request, response);
			return;
		}
		getServletContext().getRequestDispatcher("/WEB-INF/displayCake.jsp").forward(request, response);
	}
}

//localFilePath: /home/mihaela/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/WebShopJSTL/upImages/sacher_cake.jpg
