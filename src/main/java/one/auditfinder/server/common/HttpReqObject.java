package one.auditfinder.server.common;

import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.CloneUtils;
import org.apache.http.protocol.HTTP;

@NotThreadSafe
public class HttpReqObject extends HttpRequestBase implements HttpEntityEnclosingRequest {
	
	private HttpEntity entity;
	
	 public String METHOD_NAME;

	    public HttpReqObject() {
	        super();
	    }

	    public HttpReqObject(final URI uri) {
	        super();
	        setURI(uri);
	    }
	    
	    /**
	     * @throws IllegalArgumentException if the uri is invalid.
	     */
	    public HttpReqObject(final String uri) {
	        super();
	        setURI(URI.create(uri));
	    }

	    @Override
	    public String getMethod() {
	        return METHOD_NAME;
	    }
	    
	    public void SetMethod(String method){
	    	this.METHOD_NAME = method;
	    }
	    
	    public HttpEntity getEntity() {
	        return this.entity;
	    }

	    public void setEntity(final HttpEntity entity) {
	        this.entity = entity;
	    }

	    public boolean expectContinue() {
	        final Header expect = getFirstHeader(HTTP.EXPECT_DIRECTIVE);
	        return expect != null && HTTP.EXPECT_CONTINUE.equalsIgnoreCase(expect.getValue());
	    }

	    @Override
	    public Object clone() throws CloneNotSupportedException {
	        final HttpEntityEnclosingRequestBase clone =
	            (HttpEntityEnclosingRequestBase) super.clone();
	        if (this.entity != null) {
	            clone.setEntity(CloneUtils.cloneObject(this.entity));
	        }
	        return clone;
	    }
	    
}
