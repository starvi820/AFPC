package one.auditfinder.server.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import one.auditfinder.server.common.Pair;
import one.auditfinder.server.comps.file.Utils;

@Configuration
public class FileConfig {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("#{fileConf['fileConf.tempDir']}")
	private String uploadTempDir;
	
	@Value("#{fileConf['fileConf.defaultEncode']}")
	private String defaultEncode;
	
	@Value("#{fileConf['fileConf.maxInMemorySize']}")
	private String maxInMemorySize;
	
	@Value("#{fileConf['fileConf.maxUploadSize']}")
	private String maxUploadSize;
	
	@Value("#{fileConf['fileConf.upfileDir']}")
	private String upfileDir;
	
	
	public FileConfig(){
		uploadTempDir = null;
		defaultEncode = null;
		maxInMemorySize = null;
		maxUploadSize = null;
		upfileDir = null;
	}
	
	@Bean
	public Pair<Long, Integer> sizePair(){
		Pair<Long, Integer> sizePair = new Pair<Long, Integer>();
		
		sizePair.setFirst(Long.parseLong(maxUploadSize));
		sizePair.setSecond(Integer.parseInt(maxInMemorySize));
		return sizePair;
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver(){
		try{
			CommonsMultipartResolver resolver = new CommonsMultipartResolver();
			
			if(uploadTempDir != null && uploadTempDir.length() > 0){
				try{
					File f = new File(uploadTempDir);
					if(f.isDirectory() && f.canWrite()){
						
						
						resolver.setUploadTempDir(new FileSystemResource(f));
						
						if(log.isDebugEnabled())
							log.debug("File Upload Temp Dir Set : {}", uploadTempDir);
						
					}
				}catch(Exception e){
					log.error("uploadTempDir : {} -> error : {}", uploadTempDir, e);
				}
			}
			if(defaultEncode != null && defaultEncode.length() > 0){
				resolver.setDefaultEncoding(defaultEncode);
			}else{
				resolver.setDefaultEncoding("utf-8");
			}
			
			if(log.isDebugEnabled()){
				log.debug("MaxUploadSize : {}, MaxInMemorySize : {}", maxUploadSize, maxInMemorySize);
			}
			
			if(maxUploadSize != null && maxUploadSize.length() > 0){
				try{
					resolver.setMaxUploadSize(Long.parseLong(maxUploadSize));
				}catch(Exception e){
					log.error("maxUploadSize : {} -> error : {}", maxUploadSize, e);
				}
			}
			
			if(maxInMemorySize != null && maxInMemorySize.length() > 0){
				try{
					resolver.setMaxInMemorySize(Integer.parseInt(maxInMemorySize));
				}catch(Exception e){
					log.error("maxInMemorySize : {} -> error : {} ", maxInMemorySize, e);
				}
			}
			return resolver;
		}catch(Exception e){
			return new CommonsMultipartResolver();
		}
	}
	
	@Bean
	public Utils fileUtils(){
		Utils utils = new Utils();
		
		if(log.isDebugEnabled())
			log.debug("upFileDir : {}", upfileDir);
		
		utils.setDirName(upfileDir);
		return utils;
	}
}
