package one.auditfinder.server.comps.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import javax.activation.MimetypesFileTypeMap;

import org.springframework.web.multipart.MultipartFile;

import one.auditfinder.server.vo.files.FileItem;

public class Utils {
	
	private File dirFile;
	
	public Utils() {
		dirFile = null;
	}

	public final void setDirName(String dirName){
		this.dirFile = new File(dirName);
	}

	public FileItem getUploadMultipartFile(MultipartFile file) throws Exception{
		if(file.isEmpty()) return null;
		
		FileItem fitm = new FileItem();
		
		if(dirFile != null){
			if(!dirFile.exists()) dirFile.mkdir();
			
			String fname = file.getOriginalFilename();
			fitm.setMime(file.getContentType());
			fitm.setForginame(fname);
			
			File upFile = new File(dirFile.getAbsolutePath() + File.separator + fname);
			
			if(upFile.exists()) upFile= File.createTempFile("no_", fname, dirFile);
			
			file.transferTo(upFile);
			fitm.setFsize(file.getSize());
			fitm.setTm(System.currentTimeMillis());
			fitm.setFname(upFile.getAbsolutePath());
			
			return fitm;
		}
		return null;
	}
	
	public FileItem getUploadFile(File file) throws Exception{
		if(!file.exists()) return null;
		
		FileItem fitm = new FileItem();
		
		if(dirFile != null){
			if(!dirFile.exists()) dirFile.mkdir();
			
			String fname = file.getName();
			fitm.setMime(new MimetypesFileTypeMap().getContentType(file));
			fitm.setForginame(fname);
			
			File upFile = new File(dirFile.getAbsolutePath() + File.separator + fname);
			
			if(upFile.exists()) upFile = File.createTempFile("no_", fname, dirFile);
			
			try{
				FileInputStream is = new FileInputStream(file);
				FileOutputStream os = new FileOutputStream(upFile);
				FileChannel fcis = is.getChannel();
				FileChannel fcos = os.getChannel();
				
				long size = fcis.size();
				fcis.transferTo(0, size, fcos);
				
				fcos.close();
				fcis.close();
				os.close();
				is.close();
				
			}catch(Exception e){
				
			}
			
			fitm.setFsize(file.length());
			fitm.setTm(System.currentTimeMillis());
			fitm.setFname(upFile.getAbsolutePath());
			
			file.delete();
			
			return fitm;
		}
		return null;
	}
	
	public void deleteUploadFile(String path){
		try{
			File f = new File(path);
			
			if(f.exists()){
				f.delete();
			} 
		}catch(Exception e){
			
		}
	}
	
	public String getFilePath(){
		return dirFile.getAbsolutePath() + File.separator;
	}
	
}
