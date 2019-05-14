package one.auditfinder.server.webcomps.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

@Component
public class ImageShowView extends AbstractView{
	
private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		File f =  (File)model.get("downloadFile");

		
		response.setContentType("image/jpeg");
		response.setContentLength((int)f.length());
        
        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;
        
        try {
            fis = new FileInputStream(f);
            FileCopyUtils.copy(fis, out);             
        } catch(Exception e){
            log.error("File Download Error : ", e); 
        }finally{
            if(fis != null){
                try{
                    fis.close();
                }catch(Exception e){}
            }
        }
        out.flush();
	}
	
}
