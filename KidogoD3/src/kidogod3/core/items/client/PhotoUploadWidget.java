package kidogod3.core.items.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;

public abstract class PhotoUploadWidget extends Composite {
	
	public static class UploadedPhoto extends JavaScriptObject{
		protected UploadedPhoto() {}
		
		public final native String getPhotoId()/*-{ return this.id; }-*/;
		public final native String getPhotoUrl()/*-{ return this.url; }-*/;
	}

	public static class UploadResult extends JsArray<UploadedPhoto>{
		protected UploadResult() {}
		
		public static native UploadResult fromString(String s)/*-{ return $wnd.eval(s); }-*/;
	}
	
	FlowPanel container = new FlowPanel();
	FormPanel form = new FormPanel(ItemPhotoUploadService.PHOTO_URL);
	FileUpload upload = new FileUpload();
	
	public PhotoUploadWidget() {
		initWidget(container);
		
		form.setMethod("POST");
		form.setEncoding("multipart/form-data");
		
		upload.setName("file");
		form.add(upload);		
		
		container.add(form);
		
		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				String resultString = event.getResults();
				UploadResult result = UploadResult.fromString(resultString);
				
			}
		});
		
		upload.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				form.submit();				
			}
		});
	}
	
	public void uploadForm(){
		
	}

	protected abstract void onUpload(UploadResult result);
}
