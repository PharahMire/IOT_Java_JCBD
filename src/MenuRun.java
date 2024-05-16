import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuRun {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        EmployeDAOModele employeDAOModele = new EmployeDAOModele();
        boolean fin = false;
        while (!fin) {
            System.out.println("Faites votre choix :");
            System.out.println("---------------------------------------");
            System.out.println("(1) Montrer tous les employés");
            System.out.println("(2) Rechercher un employé par son ID");
            System.out.println("(3) Insérer un employé");
            System.out.println("(4) Vérfier login/mot de passe d'un employé");
            System.out.println("(5) Quitter");
            System.out.println("---------------------------------------");
            System.out.println("");
            // entrée clavier entier
            String choixString = scan.nextLine();
            int choix;
            try {
                choix = Integer.parseInt(choixString);
            } catch (NumberFormatException e) {
                choix = 0;
                System.out.println("Sélectionner un menu de 1 à 5");
            }
            switch (choix) {
                case 1:
                    System.out.println("1 - Les employées :");
                    ArrayList<EmployeBeanModele> listeEmploye = employeDAOModele.lireListe();
                    for (int i = 0; i < listeEmploye.size(); i++) {
                        EmployeBeanModele employe = listeEmploye.get(i);
                        System.out.println(
                                "Employé: " + employe.getId() + ", " + 
                                              employe.getNom() + ", " + 
                                              employe.getLogin() + ", " +
                                "date: "+ employe.getDatetimecreation().toString());
                    }
                    break;
                case 2:
                    System.out.println("2 - Rechercher un employé :");
                    System.out.print("id : ");
                    String idstring = scan.nextLine();
                    int id = Integer.parseInt(idstring);
                    EmployeBeanModele employeById = employeDAOModele.lire(id);
                    System.out.println(
                                "Employé: " + employeById.getId() + ", " + 
                                              employeById.getNom() + ", " + 
                                              employeById.getLogin() + ", " +
                                "date: "+ employeById.getDatetimecreation().toString());
                    break;
                case 3:
                    System.out.println("3 - Insérer un employé :");
                    System.out.print("Nom : ");
                    String nom = scan.nextLine();
                    System.out.print("login : ");
                    String login = scan.nextLine();
                    System.out.print("mdp : ");
                    String mdp = scan.nextLine();
                    System.out.print("Day of birth : ");
                    String day = scan.nextLine();
                    System.out.print("Month of birth : ");
                    String month = scan.nextLine();
                    System.out.print("Year of birth : ");
                    String year = scan.nextLine();
                    System.out.println("***");
                    String date = year + "-" + month + "-" + day;
                    LocalDate birthday = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
                    EmployeBeanModele employe = new EmployeBeanModele(nom, login, mdp, birthday);
                    employeDAOModele.creer(employe);
                    break;
                case 4:
                    System.out.println("4 - Vérification :");
                    // Demander à l'utilisateur de rentrer login et mot de passe
                    System.out.print("login : ");
                    String identifiant = scan.nextLine();
                    System.out.print("mdp : ");
                    String motdepasse = scan.nextLine();
                    // Vérifier si le login existe et si le mot de passe est le bon mot de passe
                    int idMdp = employeDAOModele.verifier(identifiant, motdepasse);
                    if ( idMdp > 0) {
                        System.out.println("La connexion est validé ppour l'identifiant n°: " + idMdp);
                    } else {
                        System.out.println("Mauvais login ou mdp");
                    }
                    break;
                case 5:
                    fin = true;
                    break;
            }
        }
        scan.close();
    }
}