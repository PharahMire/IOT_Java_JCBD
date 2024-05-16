import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// DAO pour CRUD (create, read, update, delete)
public class EmployeDAOModele {
    // read all
    public ArrayList<EmployeBeanModele> lireListe() {
        ConnexionBDDModele connexionBDDModele = new ConnexionBDDModele();
        Connection connexion = connexionBDDModele.getConnexion();
        ArrayList<EmployeBeanModele> listeEmploye = new ArrayList<EmployeBeanModele>();
        try {
            String requete = new String("SELECT id, nom, login, mdp, birthday, datetimecreation FROM employe;");
            Statement statement = connexion.createStatement();
            ResultSet rs = statement.executeQuery(requete);
            while (rs.next()) {
                EmployeBeanModele employe = new EmployeBeanModele();
                employe.setId(rs.getInt("id"));
                employe.setNom(rs.getString("nom"));
                employe.setLogin(rs.getString("login"));
                employe.setMdp(rs.getString("mdp"));
                employe.setDatetimecreation(rs.getDate("birthday").toLocalDate());
                employe.setDatetimecreation(rs.getTimestamp("datetimecreation").toLocalDateTime());
                listeEmploye.add(employe);
            }
        } catch (SQLException ex3) {
            while (ex3 != null) {
                System.out.println(ex3.getSQLState());
                System.out.println(ex3.getMessage());
                System.out.println(ex3.getErrorCode());
                ex3 = ex3.getNextException();
            }
        } finally {
            connexionBDDModele.fermerConnexion();
        }
        return listeEmploye;
    }

    // CRUD: obj = read(id)
    public EmployeBeanModele lire(int id) {
        ConnexionBDDModele connexionBDDModele = new ConnexionBDDModele();
        Connection connexion = connexionBDDModele.getConnexion();
        EmployeBeanModele employe = new EmployeBeanModele();
        try {
            String requete = new String("SELECT id, nom, login, mdp, birthday, datetimecreation FROM employe WHERE id=?;");

            PreparedStatement statement = connexion.prepareStatement(requete,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                employe = new EmployeBeanModele();
                employe.setId(id);
                employe.setNom(rs.getString("nom"));
                employe.setLogin(rs.getString("login"));
                employe.setMdp(rs.getString("mdp"));
                employe.setDatetimecreation(rs.getDate("birthday").toLocalDate());
                employe.setDatetimecreation(rs.getTimestamp("datetimecreation").toLocalDateTime());
            }
        } catch (SQLException ex3) {
            while (ex3 != null) {
                System.out.println("here");
                System.out.println(ex3.getSQLState());
                System.out.println(ex3.getMessage());
                System.out.println(ex3.getErrorCode());
                ex3 = ex3.getNextException();
            }
        } finally {
            connexionBDDModele.fermerConnexion();
        }
        return employe;
    }

    // CRUD: create(obj)
    public void creer(EmployeBeanModele employe) {
        ConnexionBDDModele connexionBDDModele = new ConnexionBDDModele();
        Connection connexion = connexionBDDModele.getConnexion();
        try {
            String requete = new String("INSERT INTO employe (nom, login, mdp, birthday, datetimecreation) VALUES (?, ?, MD5(?), ?, NOW());");
            PreparedStatement statement = connexion.prepareStatement(requete);
            statement.setString(1, employe.getNom());
            statement.setString(2, employe.getLogin());
            statement.setString(3, employe.getMdp());
            statement.setDate(4, Date.valueOf(employe.getBirthday()));
            statement.executeUpdate();
        } catch (SQLException ex3) {
            while (ex3 != null) {
                System.out.println(ex3.getSQLState());
                System.out.println(ex3.getMessage());
                System.out.println(ex3.getErrorCode());
                ex3 = ex3.getNextException();
            }
        } finally {
            connexionBDDModele.fermerConnexion();
        }
    }

    // CRUD: obj = read(login, mdp)
    public int verifier(String login, String mdp) {
        // fonction à remplir : vérifier l'existance de l'utilisateur/mdp, retourner
        ConnexionBDDModele connexionBDDModele = new ConnexionBDDModele();
        Connection connexion = connexionBDDModele.getConnexion();
        try {
            String requete = new String("SELECT id, mdp FROM employe WHERE login=?;"); // créer la requete

            PreparedStatement statement = connexion.prepareStatement(requete,
                    Statement.RETURN_GENERATED_KEYS); // prépare la requete
            statement.setString(1, login); // implémente les variables dans la requete (?)
            ResultSet rs = statement.executeQuery(); // envoie la requete
            if (rs.next()) {
                if (rs.getString("mdp").equals(hashMdp(mdp))) { // == doesn t work for strings
                    int id = rs.getInt("id");
                    return id;
                }   
            }
        } catch (SQLException ex3) {
            while (ex3 != null) {
                System.out.println("here");
                System.out.println(ex3.getSQLState());
                System.out.println(ex3.getMessage());
                System.out.println(ex3.getErrorCode());
                ex3 = ex3.getNextException();
            }
        } finally {
            connexionBDDModele.fermerConnexion();
        }
        // soit son id, soit -1.
        return -1;
    }


    //Copied from internet
    public String hashMdp(String mdp) {
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
 
            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(mdp.getBytes());
 
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
 
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
}
