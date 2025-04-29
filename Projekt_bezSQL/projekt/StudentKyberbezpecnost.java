package projekt;

import java.util.*; 
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Třída pro studenty kyberbezpečnosti
class StudentKyberbezpecnost extends Student {
 public StudentKyberbezpecnost(String jmeno, String prijmeni, int rokNarozeni) {
     super(jmeno, prijmeni, rokNarozeni);
 }

 public void spustDovednost() {
     System.out.println("Hash: " + vytvorHash(jmeno + " " + prijmeni));
 }

 private String vytvorHash(String text) {
     try {
         MessageDigest md = MessageDigest.getInstance("SHA-256");
         byte[] hash = md.digest(text.getBytes());
         StringBuilder hexString = new StringBuilder();
         for (byte b : hash) {
             hexString.append(String.format("%02x", b));
         }
         return hexString.toString();
     } catch (NoSuchAlgorithmException e) {
         return "Chyba hashování";
     }
 }
}
