package projekt;

import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//MAIN
public class Univerzita {
 public static void main(String[] args) {
     Scanner scanner = new Scanner(System.in);
     DatabazeStudentu db = new DatabazeStudentu();
     boolean running = true;

     while (running) { // menu
         try {
             System.out.println("\nVyberte akci:");
             System.out.println("1. Přidat studenta");
             System.out.println("2. Vyhledat studenta podle ID");
             System.out.println("3. Přidat známku studentovi");
             System.out.println("4. Propuštění studenta");
             System.out.println("5. Spustit dovednost studenta dle ID");
             System.out.println("6. Zobrazit studenty podle oboru");
             System.out.println("7. Výpis studijního průměru pro každý obor zvlášť");
             System.out.println("8. Výpis celkového počtu studentů v jednotlivých skupinách");
             System.out.println("9. Konec");
             System.out.println("10. Uložit studenta do souboru");
             System.out.println("11. Načíst studenta ze souboru");

             int volba = scanner.nextInt();
             scanner.nextLine();  

             switch (volba) {
                 case 1:
                     try {
                         System.out.print("Zadejte jméno studenta: ");
                         String jmeno = scanner.nextLine();
                         System.out.print("Zadejte příjmení studenta: ");
                         String prijmeni = scanner.nextLine();
                         System.out.print("Zadejte rok narození studenta: ");
                         int rokNarozeni = scanner.nextInt();
                         scanner.nextLine();  
                         System.out.println("Vyberte obor studenta (1 - Telekomunikace, 2 - Kyberbezpečnost): ");
                         int obor = scanner.nextInt();
                         scanner.nextLine();  

                         if (obor == 1) {
                             db.pridatStudenta(new StudentTelekomunikace(jmeno, prijmeni, rokNarozeni));
                         } else if (obor == 2) {
                             db.pridatStudenta(new StudentKyberbezpecnost(jmeno, prijmeni, rokNarozeni));
                         } else {
                             System.out.println("Neplatný obor.");
                         }
                     } catch (InputMismatchException e) {
                         System.out.println("Neplatný vstup. Zkuste to znovu.");
                         scanner.nextLine();
                     }
                     break;
                 case 2:
                     try {
                         System.out.print("Zadejte ID studenta: ");
                         int id = scanner.nextInt();
                         scanner.nextLine();  
                         db.vypisStudenta(id);
                     } catch (InputMismatchException e) {
                         System.out.println("Neplatné ID.");
                         scanner.nextLine();
                     }
                     break;
                 case 3:
                     try {
                         System.out.print("Zadejte ID studenta: ");
                         int studentId = scanner.nextInt();
                         System.out.print("Zadejte známku (1 - 5): ");
                         int znamka = scanner.nextInt();
                         scanner.nextLine(); 
                         if (znamka < 1 || znamka > 5) {
                        	 System.out.println("Známka mimo rozsah české normy");
                        	 break;
                         }
                         Student student = db.najitStudenta(studentId);
                         if (student != null) {
                             student.pridatZnamku(znamka);
                             System.out.println("Známka přidána.");
                         } else {
                             System.out.println("Student s tímto ID nenalezen.");
                         }
                     } catch (InputMismatchException e) {
                         System.out.println("Neplatný vstup.");
                         scanner.nextLine();
                     }
                     break;
                 case 4:
                     try {
                         System.out.print("Zadejte ID studenta k propuštění: ");
                         int idKPropuštění = scanner.nextInt();
                         scanner.nextLine();
                         db.odstranitStudenta(idKPropuštění);
                         System.out.println("Student propuštěn.");
                     } catch (InputMismatchException e) {
                         System.out.println("Neplatné ID.");
                         scanner.nextLine();
                     }
                     break;
                 case 5:
                     try {
                         System.out.print("Zadejte ID studenta pro spuštění dovednosti: ");
                         int idProDovednost = scanner.nextInt();
                         scanner.nextLine();  
                         db.spustitDovednostStudenta(idProDovednost);
                     } catch (InputMismatchException e) {
                         System.out.println("Neplatné ID.");
                         scanner.nextLine();
                     }
                     break;
                 case 6:
                     db.vypisStudentyPodleOboru();
                     break;
                 case 7:
                     db.vypisStudijniPrumeryOboru();
                     break;
                 case 8:
                     db.vypisPoctuStudentuVeSkupinach();
                     break;
                 case 9:
                     running = false;
                     System.out.println("Program končí.");
                     break;
                 case 10:
                	 try {
                	    System.out.print("Zadejte ID studenta k uložení: ");
                	    int idUlozit = scanner.nextInt();
                	    scanner.nextLine();
                	    db.ulozitStudentaDoSouboru(idUlozit);
	             	} catch (InputMismatchException e) {
	             		System.out.println("Neplatný vstup. Zkuste to znovu.");
	             		scanner.nextLine();
	             	} catch (Exception e) {
	                 System.out.println("Chyba při ukládání: " + e.getMessage());
	             	}
                	    break;                	    
                 case 11:
                     try {
                         System.out.print("Zadejte ID studenta: ");
                         int searchID = scanner.nextInt();
                 	     db.nacistStudentaZeSouboru(searchID);
                     } catch (Exception e) {
                         System.out.println("Chyba při načítání: " + e.getMessage());
                     }
                     break;

                 default:
                     System.out.println("Neplatná volba. Zkuste to znovu.");
             }
         } catch (InputMismatchException e) {
             System.out.println("Neplatný vstup. Zkuste to znovu.");
             scanner.nextLine();
         } catch (Exception e) {
             System.out.println("Neočekávaná chyba: " + e.getMessage());
         }
     }

     scanner.close();
 }
}