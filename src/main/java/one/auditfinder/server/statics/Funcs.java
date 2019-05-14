package one.auditfinder.server.statics;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import one.auditfinder.server.common.HttpReqObject;

public class Funcs {

	private static final char[] HEXCHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'd', 'd', 'e', 'f' };
	public static String getMD5Str(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bf = md.digest(input.getBytes("UTF-8"));
			StringBuilder stbf = new StringBuilder();
			for( int i = 0; i < bf.length ; i++) {
				stbf.append(HEXCHAR[((bf[i]>>4) & 0x0f)]);
				stbf.append(HEXCHAR[(bf[i] & 0x0f)]);
			}
			return stbf.toString();
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	public static String getSha512Str( String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] bf = md.digest(input.getBytes());
			StringBuilder stbf = new StringBuilder();
			for( int i = 0; i < bf.length ; i++) {
				stbf.append(HEXCHAR[((bf[i]>>4) & 0x0f)]);
				stbf.append(HEXCHAR[(bf[i] & 0x0f)]);
			}
			String stst = stbf.toString();
			return stbf.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}

	public static byte[] hexToByteArray(String input) {
		if (input == null || input.length() % 2 != 0) {
			return null;
		}
		byte[] bytes = new byte[input.length() >> 1];
		for (int i = 0; i < input.length(); i += 2) {
			byte value = (byte)Integer.parseInt(input.substring(i, i + 2), 16);
			bytes[(i>>1)] = value;
		}
		return bytes;
	}

	public static final void convtCommaStr( String str , List<String> lststr) {
		if( str == null || str.length() == 0) return;

		char[] buf = str.toCharArray();
		int pt, spt, cnt, stat,len;
		pt = spt = cnt = 0;
		len = buf.length;
		char nextfind;

		try {
			while( pt < len) {
				if( buf[pt] == '"') {
					nextfind = '"';
					stat = 2;
					pt++;
					spt = pt;
				} else {
					if( buf[pt] == ',') {
						lststr.add("");
						pt++;
						if( pt == len) {
							lststr.add("");
						}
						spt = pt;
						continue;
					} else {
						nextfind = ',';
						stat = 1;
						spt = pt;
						cnt++;
						pt++;
					}
				}
				while (pt < len ) {
					if( buf[pt] != nextfind) {
						pt++; cnt++; continue;
					}
					if( stat == 1) {
						lststr.add( new String(buf, spt, cnt));
						pt++;
						if(pt == len) {
							lststr.add("");
						}
						cnt = 0;
						break;
					}
					if(stat == 2) {
						pt++;
						if( buf[pt] != nextfind) {
							lststr.add( new String(buf, spt, cnt));
							cnt = 0;
							pt++;
							if( pt == len) {
								lststr.add("");
							}
							break;
						} else {
							int i = pt;
							while( i < len) {
								buf[i-1] = buf[i];
								i++;
							}
							len--;
							cnt++;
						}
					}
				}
			}
			if( cnt > 0) 
				lststr.add( new String( buf, spt, cnt));
		}
		catch( Exception e) {
			if( cnt > 0)
				lststr.add( new String(buf, spt, cnt));
		}
	}

	public static final List<String> convtCommaStr( String str ) {
		if( str == null || str.length() == 0) return null;

		char[] buf = str.toCharArray();
		int pt, spt, cnt, stat,len;
		pt = spt = cnt = 0;
		len = buf.length;
		char nextfind;
		List<String> lststr = new ArrayList<String>();

		try {
			while( pt < len) {
				if( buf[pt] == '"') {
					nextfind = '"';
					stat = 2;
					pt++;
					spt = pt;
				} else {
					if( buf[pt] == ',') {
						lststr.add("");
						pt++;
						if( pt == len) {
							lststr.add("");
						}
						spt = pt;
						continue;
					} else {
						nextfind = ',';
						stat = 1;
						spt = pt;
						cnt++;
						pt++;
					}
				}
				while (pt < len ) {
					if( buf[pt] != nextfind) {
						pt++; cnt++; continue;
					}
					if( stat == 1) {
						lststr.add( new String(buf, spt, cnt));
						pt++;
						if(pt == len) {
							lststr.add("");
						}
						cnt = 0;
						break;
					}
					if(stat == 2) {
						pt++;
						if( buf[pt] != nextfind) {
							lststr.add( new String(buf, spt, cnt));
							cnt = 0;
							pt++;
							if( pt == len) {
								lststr.add("");
							}
							break;
						} else {
							int i = pt;
							while( i < len) {
								buf[i-1] = buf[i];
								i++;
							}
							len--;
							cnt++;
						}
					}
				}
			}
			if( cnt > 0) 
				lststr.add( new String( buf, spt, cnt));
		}
		catch( Exception e) {
			if( cnt > 0)
				lststr.add( new String(buf, spt, cnt));
		}
		return lststr;
	}

	public static final String[] commaStrToStrs( String str , int count) {
		if( str == null || str.length() == 0) return null;

		String[] arstrs = new String[count];
		int numidx = 0;
		char[] buf = str.toCharArray();
		int pt, spt, cnt, stat,len;
		pt = spt = cnt = 0;
		len = buf.length;
		char nextfind;

		try {
			while( pt < len) {
				if( buf[pt] == '"') {
					nextfind = '"';
					stat = 2;
					pt++;
					spt = pt;
				} else {
					if( buf[pt] == ',') {
						if( numidx == count) return null;
						arstrs[numidx] = "";
						numidx++;
						pt++;
						if( pt == len) {
							if( numidx == count) return null;
							arstrs[numidx] = "";
							numidx++;
						}
						spt = pt;
						continue;
					} else {
						nextfind = ',';
						stat = 1;
						spt = pt;
						cnt++;
						pt++;
					}
				}
				while (pt < len ) {
					if( buf[pt] != nextfind) {
						pt++; cnt++; continue;
					}
					if( stat == 1) {
						if( numidx == count) return null;
						arstrs[numidx] = new String(buf, spt, cnt);
						numidx++;
						pt++;
						if(pt == len) {
							if( numidx == count) return null;
							arstrs[numidx] = "";
							numidx++;
						}
						cnt = 0;
						break;
					}
					if(stat == 2) {
						pt++;
						if( buf[pt] != nextfind) {
							if( numidx == count) return null;
							arstrs[numidx] =  new String(buf, spt, cnt);
							numidx++;
							pt++;
							if(  pt == len) {
								if( numidx == count) return null;
								arstrs[numidx] = "";
								numidx++;
							}
							cnt = 0;
							break;
						} else {
							int i = pt;
							while( i < len) {
								buf[i-1] = buf[i];
								i++;
							}
							len--;
							cnt++;
						}
					}
				}
			}
			if( cnt > 0) {
				if( numidx == count) return null;
				arstrs[numidx] =  new String( buf, spt, cnt);
				numidx++;
			}
		}
		catch( Exception e) {
			if( cnt > 0) {
				if( numidx == count) return null;
				arstrs[numidx] =  new String( buf, spt, cnt);
				numidx++;
			}
		}
		if( count == numidx ) return arstrs;
		else return null;
	}

	public static String toJson(Object obj){
		ObjectMapper mapper = new ObjectMapper();
		String json = "";

		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public static int getHttpStatus(String url){
		int result = 404;
	
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpReqObject httpObj = new HttpReqObject();
			httpObj.setURI(new URI(url));
					
			httpObj.SetMethod("GET");

			CloseableHttpResponse response = httpclient.execute(httpObj);
			result = response.getStatusLine().getStatusCode();

			response.close();

		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return result;
	}

	public static String sendHttp(String url, HttpEntity Params, String method) throws Exception{
		String result = null;
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpReqObject httpObj = new HttpReqObject();
			httpObj.setURI(new URI(url));
			
			if(Params != null){
				httpObj.setEntity(Params);
			}
			
			httpObj.SetMethod(method);

			CloseableHttpResponse response = httpclient.execute(httpObj);

			HttpEntity rEntry = response.getEntity();

			if(rEntry != null){
				result = EntityUtils.toString(rEntry);
				EntityUtils.consume(rEntry);
			}
			response.close();
		return result;
	}

}
