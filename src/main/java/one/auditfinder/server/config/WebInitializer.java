package one.auditfinder.server.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootConfig.class, FileConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/","*.do",  "/page/*", "/api/*" };
	} 
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.addListener(new SessionListener());
		CharacterEncodingFilter utf8Filter = new CharacterEncodingFilter();
		utf8Filter.setEncoding("UTF-8");
		utf8Filter.setForceEncoding(true);
		servletContext.addFilter("utf-8Filter", utf8Filter).addMappingForUrlPatterns(null, false, "*.do", "/page/*", "/api/*");
	}
}
