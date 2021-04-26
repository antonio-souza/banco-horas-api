package br.com.aaas.bancohoras.domain.entity;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Senha {
  
  private String hash;

  public Senha(String plainText) {
    super();
    
    MessageDigest algorithm = null;;
    try {
      algorithm = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    byte messageDigest[] = null;
    try {
      messageDigest = algorithm.digest(plainText.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }  
    
    StringBuilder hexString = new StringBuilder();
    for (byte b : messageDigest) {
      hexString.append(String.format("%02X", 0xFF & b));
    }
    this.hash = hexString.toString();
  }

  public String getHash() {
    return hash;
  } 
}
