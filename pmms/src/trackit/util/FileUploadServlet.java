/*
 * JCatalog Project
 */
package trackit.util;

import java.util.List;
import java.util.Iterator;
import java.io.File;
//
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
//
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
//
import trackit.backingbeans.ApplicationBean;
import trackit.backingbeans.BeanNames;
import trackit.backingbeans.SessionBean;

/**
 * The file upload utility.
 * <p>
 * This is a simple file upload utility based on Commons FileUpload.
 * 
 * @author <a href="mailto:derek_shen@hotmail.com">Derek Y. Shen</a>
 * @see <a href="http://jakarta.apache.org/commons/fileupload/">Commons FileUpload</a>
 */
public class FileUploadServlet extends HttpServlet {
	//the logger object
	private Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Standard servlet doGet method.
	 * 
	 * @param req the servlet request
	 * @param res the servlet response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doPost(req, res);
	}

	/**
	 * Standard servlet doPost method.
	 * 
	 * @param req the servlet request
	 * @param res the servlet response
	 * @throws ServletException
	 * @throws IOException
	 */	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.logger.debug("upload image");
		
		try {
			//Create a new file upload handler
	 		DiskFileUpload upload = new DiskFileUpload();

			//Parse the request
	 		List items = upload.parseRequest(req);
	 		
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				if (item.isFormField()) {

				} else {
					SessionBean sessionBean = (SessionBean)req.getSession().getAttribute(BeanNames.SESSION_BEAN);
					
					String fileName = "temp.jpg";
					
					if (sessionBean != null) {
						//fileName = sessionBean.getCurrentProductId() + ".jpg";
					}
					
					fileName = ApplicationBean.getProductImageDirUri() + fileName;
					this.logger.debug("imageFileUri=" + fileName);
					
					File file = new File(fileName);
					item.write(file);
				}
			}
			
			req.getRequestDispatcher(ApplicationBean.getImageUploadResultPage()).forward(req, res);
		} catch (Exception e) {
			this.logger.error("Could not upload file.", e);
			throw new ServletException("Could not upload file." + e.toString());
		}
	}
}
