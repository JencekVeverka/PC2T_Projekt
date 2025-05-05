package projekt;

import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//Rozhraní pro dovednosti studentů
interface Dovednost {
 void spustDovednost();
}

//Abstraktní třída pro studenty
abstract class Student implements Dovednost, Comparable<Student> {
 protected static int idCounter = 1;
 protected int id;
 protected String jmeno;
 protected String prijmeni;
 protected int rokNarozeni;
 protected List<Integer> znamky = new ArrayList<>();

 public Student(String jmeno, String prijmeni, int rokNarozeni) {
     this.id = idCounter++;
     this.jmeno = jmeno;
     this.prijmeni = prijmeni;
     this.rokNarozeni = rokNarozeni;
 }

 public void pridatZnamku(int znamka) {
     if (znamka >= 1 && znamka <= 5) {
         znamky.add(znamka);
     }
 }

 public double getStudijniPrumer() {
     return znamky.stream().mapToInt(Integer::intValue).average().orElse(0);
 }

 public int getId() {
     return id;
 }

 public String getJmeno() {
     return jmeno;
 }

 public String getPrijmeni() {
     return prijmeni;
 }

 public int getRokNarozeni() {
     return rokNarozeni;
 }

 // Metoda pro spuštění dovednosti studenta
 public abstract void spustDovednost();
}