package projekt;

import java.util.*; 
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Třída pro studenty telekomunikací
class StudentTelekomunikace extends Student {
 public StudentTelekomunikace(String jmeno, String prijmeni, int rokNarozeni) {
     super(jmeno, prijmeni, rokNarozeni);
 }

 public void spustDovednost() {
     System.out.println("Morseovka: " + naMorseovku(jmeno + " " + prijmeni));
 }

 private String naMorseovku(String text) {
     Map<Character, String> morseovkaMap = new HashMap<>();
     String[] morseKody = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---",
                           "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-",
                           "..-", "...-", ".--", "-..-", "-.--", "--.."};
     char znak = 'A';
     for (String kod : morseKody) {
         morseovkaMap.put(znak++, kod);
     }
     morseovkaMap.put(' ', " / ");
     
     StringBuilder morseovka = new StringBuilder();
     for (char c : text.toUpperCase().toCharArray()) {
         morseovka.append(morseovkaMap.getOrDefault(c, "")).append(" ");
     }
     return morseovka.toString().trim();
 }
}