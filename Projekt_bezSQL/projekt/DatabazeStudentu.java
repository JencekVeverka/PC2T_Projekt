package projekt;

import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Správa databáze studentů
class DatabazeStudentu {
 private List<Student> studenti = new ArrayList<>();

 public void pridatStudenta(Student student) {
     studenti.add(student);
 }

 public Student najitStudenta(int id) {
     return studenti.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
 }

 public void odstranitStudenta(int id) {
     studenti.removeIf(s -> s.getId() == id);
 }

 public void vypisVsechnyStudenty() {
     studenti.stream()
             .sorted(Comparator.comparing(Student::getPrijmeni))
             .forEach(s -> System.out.println(s.getId() + " " + s.getJmeno() + " " + s.getPrijmeni()));
 }
 
	 public void vypisStudenta(int id) {
	     Student s = najitStudenta(id);
	     if (s != null) {
	         System.out.println("ID: " + s.getId());
	         System.out.println("Jméno: " + s.getJmeno());
	         System.out.println("Příjmení: " + s.getPrijmeni());
	         System.out.println("Rok narození: " + s.getRokNarozeni());
	         System.out.println("Studijní průměr: " + s.getStudijniPrumer());
	     } else {
	         System.out.println("Student s ID " + id + " nenalezen.");
	     }
	 }

	 // Metoda pro spuštění dovednosti studenta podle ID
	 public void spustitDovednostStudenta(int id) {
	     Student s = najitStudenta(id);
	     if (s != null) {
	         s.spustDovednost();
	     } else {
	         System.out.println("Student s ID " + id + " nenalezen.");
	     }
	 }

	 // Metoda pro abecedně řazený výpis studentů podle oboru
	 public void vypisStudentyPodleOboru() {
	     List<Student> telekomunikace = new ArrayList<>();
	     List<Student> kyberbezpecnost = new ArrayList<>();
	
	     // Rozdělení studentů do oborů
	     for (Student s : studenti) {
	         if (s instanceof StudentTelekomunikace) {
	             telekomunikace.add(s);
	         } else if (s instanceof StudentKyberbezpecnost) {
	             kyberbezpecnost.add(s);
	         }
	     }
	
	     // Abecední výpis studentů podle příjmení v jednotlivých oborech
	     System.out.println("\nStudenti oboru Telekomunikace:");
	     telekomunikace.stream()
	             .sorted(Comparator.comparing(Student::getPrijmeni))
	             .forEach(s -> System.out.println(s.getId() + " " + s.getJmeno() + " " + s.getPrijmeni() + " " +
	                     s.getRokNarozeni() + " " + s.getStudijniPrumer()));
	
	     System.out.println("\nStudenti oboru Kyberbezpečnost:");
	     kyberbezpecnost.stream()
	             .sorted(Comparator.comparing(Student::getPrijmeni))
	             .forEach(s -> System.out.println(s.getId() + " " + s.getJmeno() + " " + s.getPrijmeni() + " " +
	                     s.getRokNarozeni() + " " + s.getStudijniPrumer()));
	 }
 
	 // Výpis průměru v oborech
	 public void vypisStudijniPrumeryOboru() {
		    List<Student> telekomunikace = studenti.stream()
		        .filter(s -> s instanceof StudentTelekomunikace)
		        .toList();
		    
		    List<Student> kyberbezpecnost = studenti.stream()
		        .filter(s -> s instanceof StudentKyberbezpecnost)
		        .toList();
	
		    double prumerTelekomunikace = telekomunikace.stream()
		        .mapToDouble(Student::getStudijniPrumer)
		        .average()
		        .orElse(0);
	
		    double prumerKyberbezpecnost = kyberbezpecnost.stream()
		        .mapToDouble(Student::getStudijniPrumer)
		        .average()
		        .orElse(0);
	
		    System.out.println("\n Telekomunikace:");
		    System.out.printf("Celkový průměr: %.2f\n", prumerTelekomunikace);
	
		    System.out.println("\n Kyberbezpečnost:");
		    System.out.printf("Celkový průměr: %.2f\n", prumerKyberbezpecnost);
		}
 
 	// výpis počtu studentu v oboru
	 public void vypisPoctuStudentuVeSkupinach() {
		    long pocetTelekomunikace = studenti.stream()
		        .filter(s -> s instanceof StudentTelekomunikace)
		        .count();
		    
		    long pocetKyberbezpecnost = studenti.stream()
		        .filter(s -> s instanceof StudentKyberbezpecnost)
		        .count();
	
		    System.out.println("\nPočet studentů v jednotlivých skupinách:");
		    System.out.println("Telekomunikace: " + pocetTelekomunikace);
		    System.out.println("Kyberbezpečnost: " + pocetKyberbezpecnost);
		}
	 
	 //metoda pro uložení do souboru
	 public void ulozitStudentaDoSouboru(int id) {
		    String fileName = "studenti.txt";
		    Student student = najitStudenta(id);
		    if (student != null) {
		        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
		        	
		        	String radek = String.format("ID: %d, Jméno: %s, Příjmení: %s, Rok narození: %d, Průměr: %.2f",
			                student.getId(),
			                student.getJmeno(),
			                student.getPrijmeni(),
			                student.getRokNarozeni(),
			                student.getStudijniPrumer());
			                
		        	
		            System.out.println("Student byl úspěšně uložen do souboru.");
		            
		            writer.write(radek);
			        writer.newLine();
		        } catch (IOException e) {
			        System.out.println("Chyba při ukládání do souboru: " + e.getMessage());
			    }
		    } else {
		        System.out.println("Student s ID " + id + " nebyl nalezen.");
		    }
		}
	 
	 //metoda pro načtení ze souboru
	 public void nacistStudentaZeSouboru(int hledaneId) {
		 String fileName = "studenti.txt";
		 
		 try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
		        String radek;
		        boolean nalezen = false;

		        while ((radek = reader.readLine()) != null) {
		            if (radek.startsWith("ID: ")) {
		                String[] casti = radek.split(", ");
		                int id = Integer.parseInt(casti[0].split(": ")[1]);

		                if (id == hledaneId) {
		                    System.out.println(radek);
		                    nalezen = true;
		                    break;
		                }
		            }
		        }

		        if (!nalezen) {
		            System.out.println("Student s ID " + hledaneId + " nebyl nalezen v souboru.");
		        }

		    } catch (IOException e) {
		        System.out.println("Chyba při čtení souboru: " + e.getMessage());
		    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
		        System.out.println("Chybný formát řádku v souboru.");
		    }
		}



	 
}
