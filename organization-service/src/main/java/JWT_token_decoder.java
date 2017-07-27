import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import static org.springframework.security.jwt.codec.Codecs.b64UrlDecode;

public class JWT_token_decoder {
	CharsetEncoder  utf8Encoder = Charset.forName("UTF-8").newEncoder();
	CharsetDecoder  utf8Decoder = Charset.forName("UTF-8").newDecoder();
	
	public static void main(String[] args) {
		//jwt_token = header_str . claims_str . crypto_str
		String jwt_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcmdhbml6YXRpb25JZCI6ImQxODU5ZjFmLTRiZDctNDU5My04NjU0LWVhNmQ5YTZhNjI2ZSIsInVzZXJfbmFtZSI6ImpvaG4uY2FybmVsbCIsInNjb3BlIjpbIndlYmNsaWVudCJdLCJleHAiOjE1MDA5NjExNjYsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJlNjExYTI5MC1lMDBkLTRlMzktOTllNC0xOWNhOTM2ZjdiM2QiLCJjbGllbnRfaWQiOiJlYWdsZWV5ZSJ9.1LWsTzGldoJbrji6Y5QnO-hgqpkaWjFHKYLnZg0eA1w";
		int firstPeriod = jwt_token.indexOf('.');
		int lastPeriod = jwt_token.lastIndexOf('.');

		byte[] header_b64 = jwt_token.substring(0, firstPeriod).getBytes();
		byte[] claims_b64 = jwt_token.substring(firstPeriod+1, lastPeriod).getBytes();
		byte[] crypto_b64 = jwt_token.substring(lastPeriod+1).getBytes();
		
		//Il token e' b64 encoded.Deve decodificarlo per leggere contenuto
		byte[] header_ascii = b64UrlDecode(header_b64);
		byte[] claims_ascii = b64UrlDecode(claims_b64);
		byte[] crypto_ascii = b64UrlDecode(crypto_b64);
				
		//Ora posso portare tutto a stringa
		String header_str = new String(header_ascii)  ;
		String claims_str = new String(claims_ascii)  ;
		String crypto_str = new String(crypto_ascii)  ;
		
		
		
		
		
	    //Base64.urlDecode(bytes)
	    //byte[] bytes = b64UrlDecode(header);
		

	}
	
}
