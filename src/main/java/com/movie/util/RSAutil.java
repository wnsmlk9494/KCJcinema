package com.movie.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

@Repository
public class RSAutil {
	/****************************************************************************
	    		[RSA] 복호화 / 16진문자열 byte배열로 변환 / 공개키,비밀키 생성
	*****************************************************************************
    * rsa 공개키, 비밀키 생성, @param request
    *****************************************************************************/
	public String RSA_WEB_KEY = "_RSA_WEB_Key_"; // 개인키 session key
    public String RSA_INSTANCE = "RSA"; // rsa transformation
    
    public void initRsa(HttpServletRequest request) {
        HttpSession session = request.getSession();
 
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance(RSA_INSTANCE);
            generator.initialize(1024);
 
            KeyPair keyPair = generator.genKeyPair();
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_INSTANCE);
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            
            //session에 RSA 비밀키를 세션에 저장
            session.setAttribute(RSA_WEB_KEY, privateKey);
 
            RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            String publicKeyModulus = publicSpec.getModulus().toString(16);
            String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
            
            //rsa modulus 를 request 에 추가
            //rsa exponent 를 request 에 추가
            request.setAttribute("RSAModulus", publicKeyModulus);
            request.setAttribute("RSAExponent", publicKeyExponent);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /*****************************************************************************
	* 복호화___ @param privateKey, @param securedValue, @return, @throws Exception
	*****************************************************************************/
	public String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
		Cipher cipher = Cipher.getInstance(RSA_INSTANCE);
		byte[] encryptedBytes = hexToByteArray(securedValue);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		String decryptedValue = new String(decryptedBytes, "utf-8"); //문자 인코딩에 주의가 필요
		
		return decryptedValue;
	}
	
	/************************************************** 
	* 16진 문자열을 byte 배열로 변환한다, @param hex, @return *
	***************************************************/
	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() % 2 != 0) { return new byte[] {}; }
		
		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length(); i += 2) {
			byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
			bytes[(int) Math.floor(i / 2)] = value;
		}
		
		return bytes;
	}
}
