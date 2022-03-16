package com.wenote.alipay.utils;




import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


public final class RSA {

	private static String RSA = "RSA";


	public static KeyPair generateRSAKeyPair() {
		return generateRSAKeyPair(1024);
	}


	public static KeyPair generateRSAKeyPair(int keyLength) {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
			kpg.initialize(keyLength);
			return kpg.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] encryptPasswd(byte[] data, PublicKey publicKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] encryptSign(byte[] data, PrivateKey privateKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public static byte[] decryptPasswd(byte[] encryptedData, PrivateKey privateKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(encryptedData);
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] decryptSign(byte[] encryptedData, PublicKey publicKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			return cipher.doFinal(encryptedData);
		} catch (Exception e) {
			return null;
		}
	}


	public static PublicKey getPublicKey(byte[] keyBytes)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}


	public static PrivateKey getPrivateKey(byte[] keyBytes)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}


	public static PublicKey getPublicKey(String modulus, String publicExponent)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		BigInteger bigIntModulus = new BigInteger(modulus);
		BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus,
				bigIntPrivateExponent);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}


	public static PrivateKey getPrivateKey(String modulus,
										   String privateExponent) throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		BigInteger bigIntModulus = new BigInteger(modulus);
		BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus,
				bigIntPrivateExponent);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}


	public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
		try
		{
			byte[] buffer = Base64.decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e)
		{
			throw new Exception("");
		} catch (InvalidKeySpecException e)
		{
			throw new Exception("");
		} catch (NullPointerException e)
		{
			throw new Exception("");
		}
	}

//	public static PublicKey loadPublicKeyFromcs1(String in) throws Exception {
//
//		CertificateFactory cf = null;
//		try {
//			cf = CertificateFactory.getInstance("X.509");
//			X509Certificate encryptCert = (X509Certificate)cf.generateCertificate(GlobalAppliction.instance.getAssets().open("umpsapi-staging.crt"));
//			return encryptCert.getPublicKey();
//			//Utils.print(HexStr.bufferToHex(this.publicKey.getEncoded()));
//		} catch (CertificateException e) {
//			throw new Exception("");
//		}
//	}


	public static PrivateKey loadPrivateKey(String privateKeyStr)
			throws Exception {
		try {

			byte[] buffer = Base64.decode(privateKeyStr);
			RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure(fromByteArray(buffer));
			RSAPrivateKeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent());
			/*priPKCS8 = new PKCS8EncodedKeySpec(
				     new BASE64Decoder().decodeBuffer(privateKeyStr));*/

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(rsaPrivKeySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("");
		} catch (InvalidKeySpecException e) {
			throw new Exception("");
		} catch (NullPointerException e) {
			throw new Exception("");
		}
	}
	public static ASN1Sequence fromByteArray(byte[] paramArrayOfByte)
			throws IOException
	{
		ASN1InputStream localASN1InputStream = new ASN1InputStream(paramArrayOfByte);
		return (ASN1Sequence)localASN1InputStream.readObject();
	}

	public static PublicKey loadPublicKey(InputStream in) throws Exception {
		try {
			return loadPublicKey(readKey(in));
		} catch (IOException e) {
			throw new Exception("");
		} catch (NullPointerException e) {
			throw new Exception("");
		}
	}


	public static PrivateKey loadPrivateKey(InputStream in) throws Exception {
		try {
			return loadPrivateKey(readKey(in));
		} catch (IOException e) {
			throw new Exception("");
		} catch (NullPointerException e) {
			throw new Exception("");
		}
	}


	private static String readKey(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String readLine = null;
		StringBuilder sb = new StringBuilder();
		while ((readLine = br.readLine()) != null) {
			if (readLine.charAt(0) == '-') {
				continue;
			} else {
				sb.append(readLine);
				sb.append('\r');
			}
		}

		return sb.toString();
	}

//	public static void printPublicKeyInfo(PublicKey publicKey) {
//		RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
//		Utils.print("----------RSAPublicKey----------");
//		Utils.print("Modulus.length="
//				+ rsaPublicKey.getModulus().bitLength());
//		Utils.print("Modulus=" + rsaPublicKey.getModulus().toString());
//		Utils.print("PublicExponent.length="
//				+ rsaPublicKey.getPublicExponent().bitLength());
//		Utils.print("PublicExponent="
//				+ rsaPublicKey.getPublicExponent().toString());
//	}
//
//	public static void printPrivateKeyInfo(PrivateKey privateKey) {
//		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
//		Utils.print("----------RSAPrivateKey ----------");
//		Utils.print("Modulus.length="
//				+ rsaPrivateKey.getModulus().bitLength());
//		Utils.print("Modulus=" + rsaPrivateKey.getModulus().toString());
//		Utils.print("PrivateExponent.length="
//				+ rsaPrivateKey.getPrivateExponent().bitLength());
//		Utils.print("PrivatecExponent="
//				+ rsaPrivateKey.getPrivateExponent().toString());
//
//	}

}
