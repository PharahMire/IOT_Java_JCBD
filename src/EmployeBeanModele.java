import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeBeanModele {
    private int id;
    private String nom, login, mdp;
    private LocalDateTime datetimecreation;
    private LocalDate birthday;

    public EmployeBeanModele() {
        
    }

    public EmployeBeanModele(String nom, String login, String mdp, LocalDate birthday) {
        this.nom = nom;
        this.login = login;
        this.mdp = mdp;
        this.birthday = birthday;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return this.mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public LocalDateTime getDatetimecreation() {
        return this.datetimecreation;
    }

    public void setDatetimecreation(LocalDateTime datetimecreation) {
        this.datetimecreation = datetimecreation;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public void setDatetimecreation(LocalDate birthday) {
        this.birthday = birthday;
    }
}


