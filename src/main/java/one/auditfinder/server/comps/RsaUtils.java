package one.auditfinder.server.comps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import one.auditfinder.server.statics.Funcs;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

@Component
public class RsaUtils {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	private String publicKeyModulus;
	private String publicKeyExponent;
	
	public RsaUtils() {
		publicKey = null;
		privateKey = null;
		
		publicKeyModulus = null;
		publicKeyExponent = null;
		
		KeyPairGenerator generator;
		try {
			generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(1024);
			KeyPair keys = generator.genKeyPair();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keys.getPublic();
			privateKey = keys.getPrivate();
			RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
			publicKeyModulus = publicSpec.getModulus().toString(16);
			publicKeyExponent = publicSpec.getPublicExponent().toString(16);
			
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error("Rsa Key Init Error : " , e);
			
			publicKey = null;
			privateKey = null;
			
			publicKeyModulus = null;
			publicKeyExponent = null;
		}
	}

	public final PublicKey getPublicKey() {
		return publicKey;
	}

	public final void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public final String getPublicKeyModulus() {
		return publicKeyModulus;
	}

	public final void setPublicKeyModulus(String publicKeyModulus) {
		this.publicKeyModulus = publicKeyModulus;
	}

	public final String getPublicKeyExponent() {
		return publicKeyExponent;
	}

	public final void setPublicKeyExponent(String publicKeyExponent) {
		this.publicKeyExponent = publicKeyExponent;
	}
	
	public String decrypt( String securedValue) {
		 String decryptedValue = "";
		 try{
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		   /**
			* 암호화 된 값은 byte 배열이다.
			* 이를 문자열 폼으로 전송하기 위해 16진 문자열(hex)로 변경한다. 
			* 서버측에서도 값을 받을 때 hex 문자열을 받아서 이를 다시 byte 배열로 바꾼 뒤에 복호화 과정을 수행한다.
			*/
			byte[] encryptedBytes = Funcs.hexToByteArray(securedValue);
			if( encryptedBytes == null) return null;
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
			decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.
			return decryptedValue;
		 }catch(Exception e)
		 {
			 log.error("decrypt Error : " , e);
			 return null;
		 }		
	}

}
