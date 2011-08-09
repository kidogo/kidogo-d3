package kidogod3.core.items;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kidogod3.core.items.client.ItemPhotoUploadService;
import kidogod3.core.items.dao.ItemPhoto;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.repackaged.com.google.common.base.Throwables;
import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Servlet that handle picture uploads and downloads.
 * 
 *  Http post handles picture uploads and returns a json array of photos,
 *  each of which contains the id of the photo, and a url to the photo
 *  
 *  e.g. [{"id"="1", "/photo?id=1"}, {{"id"="2", "/photo?id=2"}}]
 * 
 * @author Arnold P. Minde
 *
 */
@Singleton
public class ItemPictureServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6615542922851383240L;
	
	public static final String PHOTO_URL = ItemPhotoUploadService.PHOTO_URL;
	
	@Inject Provider<PersistenceManager> pm;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(ServletFileUpload.isMultipartContent(req)){
			ServletFileUpload upload = new ServletFileUpload();
			List<Long> photoIds = new ArrayList<Long>();
			
			try {
				FileItemIterator iterator = upload.getItemIterator(req);
				while(iterator.hasNext()){
					FileItemStream s = iterator.next();
					InputStream imgStream = s.openStream();
					
					Blob imageBlob = new Blob(IOUtils.toByteArray(imgStream));
					
					ItemPhoto image = new ItemPhoto();
					image.setName(s.getName());
					image.setPhoto(imageBlob);
				    
					PersistenceManager pm = this.pm.get();
					pm.currentTransaction().begin();
					try{
						image = pm.makePersistent(image);
						
						photoIds.add(image.getPhotoId());
						
						pm.currentTransaction().commit();
					}finally{
						if(pm.currentTransaction().isActive()){
							pm.currentTransaction().rollback();
						}
					}
				}
				
				JSONArray list = new JSONArray();
				for(Long photoId:photoIds){
					JSONObject photo = new JSONObject();
					photo.put("id", photoId.toString());
					photo.put("url", PHOTO_URL+"?id="+photoId.toString());
					list.put(photo);
				}
				resp.getWriter().print(list.toString());
			} catch (FileUploadException e) {
				Throwables.propagate(e);
			} catch (JSONException e) {
				Throwables.propagate(e);
			}			
		}else{
			throw new ServletException("Request not multipart: use enctype=\"multipart/form-data\" ");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long pictureId = Long.parseLong(req.getParameter("id"));
		ItemPhoto photo = pm.get().getObjectById(ItemPhoto.class, pictureId);
		Blob data = photo.getPhoto();
		
		OutputStream out = resp.getOutputStream();
		
		IOUtils.copy(new ByteArrayInputStream(data.getBytes()), out);
		
		out.flush();
		out.close();
	}
}
