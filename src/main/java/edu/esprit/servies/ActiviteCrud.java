package edu.esprit.servies;

import java.sql.*;
import java.util.*;

import edu.esprit.entites.Categorie;
import edu.esprit.tools.MyConnection;
import edu.esprit.entites.Activite;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActiviteCrud implements IcrudA<Activite> {

    @Override
    public void ajouter1(Activite activite) {
        String req1 = "INSERT INTO activitee(categorie_id,nom,prix,localisation,nb_P,etat,description_act,image) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setInt(1,activite.getCategorie_id()); //tab3a ? eli fel values
            pst.setString(2, activite.getNom()); //tab3a ? eli fel values
            pst.setInt(3, activite.getPrix());
            pst.setString(4, activite.getLocalisation());
            pst.setInt(5, activite.getNb_P()); // Set nb_P
            pst.setString(6, activite.getEtat());
            pst.setString(7, activite.getDescription_act());
            pst.setString(8, activite.getImage());

            pst.executeUpdate();
            System.out.println("activite ajoutéé!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ajouter(Activite activite, String imagePath) {
        String req1 = "INSERT INTO activitee(categorie_id, nom, prix, localisation, nb_P, etat, image, description_act) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection connection = MyConnection.getInstance().getCnx()) {
            // Vérifier si la connexion est ouverte
            if (!connection.isClosed()) {
                // Vérifier si l'ID de catégorie existe dans la table categorie
                if (!categoryExists(activite.getCategorie_id(), connection)) {
                    System.out.println("Invalid category ID");
                    return;
                }

                try (PreparedStatement pst = connection.prepareStatement(req1)) {
                    // Paramétrer les valeurs dans la requête PreparedStatement
                    pst.setInt(1, activite.getCategorie_id());
                    pst.setString(2, activite.getNom());
                    pst.setInt(3, activite.getPrix());
                    pst.setString(4, activite.getLocalisation());
                    pst.setInt(5, activite.getNb_P());
                    pst.setString(6, activite.getEtat());
                    pst.setString(7, imagePath);
                    pst.setString(8, activite.getDescription_act());

                    // Exécuter la requête
                    pst.executeUpdate();
                    System.out.println("Activite ajoutée!");
                } catch (SQLException e) {
                    System.out.println("Error executing SQL query: " + e.getMessage());
                }
            } else {
                System.out.println("Connection to database is closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error establishing connection: " + e.getMessage());
        }
    }

    // Check if the category ID exists in the categorie table
    private boolean categoryExists(int categoryId, Connection connection) throws SQLException {
        String query = "SELECT id FROM categorie WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Si next() retourne true, l'ID de catégorie existe
            }
        }
    }



    @Override
    public void modifier(Activite activite) throws SQLException {
        final String query="UPDATE activitee SET nom = ?, prix = ?, localisation = ?,nb_P = ?,etat= ?,image = ?,description_act= ?,categorie_id= ?  WHERE id= ?";
        try( PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);){

            pst.setString(1,activite.getNom());
            pst.setInt(2,activite.getPrix());
            pst.setString(3,activite.getLocalisation());
            pst.setInt(4,activite.getNb_P());
            pst.setString(5,activite.getEtat());
            pst.setString(6,activite.getImage());
            pst.setString(7,activite.getDescription_act());
            pst.setInt(8,activite.getCategorie_id());
            pst.setInt(9,activite.getId());
            pst.executeUpdate();
        }
    }




    @Override
    public void supprimer(Activite activite) throws SQLException {
        final String query = "DELETE FROM activitee WHERE id=? ";
        try(PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);){
            pst.setInt(1,activite.getId());
            pst.executeUpdate();
        }
    }


    @Override
    public List<Activite> afficher() {
        List<Activite>activites=new ArrayList<>();
        String req3="SELECT * FROM activitee";
        try {
            Statement st=MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs= st.executeQuery(req3);
            while (rs.next())
            {
                Activite p;
                p = new Activite();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom")); //avec label
                p.setPrix(rs.getInt("prix"));
                p.setLocalisation(rs.getString("localisation"));
                p.setNb_P(rs.getInt("nb_P"));
                p.setEtat(rs.getString("etat"));
                p.setImage(rs.getString("image"));
                p.setDescription_act(rs.getString("description_act"));
                p.setCategorie_id(rs.getInt("categorie_id"));
                activites.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  activites;
    }
    public List<Activite> trierParPrix(ObservableList<Activite> activiteslist)throws SQLException {
        List<Activite> activites = new ArrayList<>();
        String req = "SELECT * FROM activitee ORDER BY prix";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Activite p;
                p = new Activite();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom")); //avec label
                p.setPrix(rs.getInt("prix"));
                p.setLocalisation(rs.getString("localisation"));
                p.setNb_P(rs.getInt("nb_P"));
                p.setEtat(rs.getString("etat"));
                p.setImage(rs.getString("image"));
                p.setDescription_act(rs.getString("description_act"));
                p.setCategorie_id(rs.getInt("categorie_id"));
                activites.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return activites;
    }



    public List<Integer> getAllCategorieIds() {
        List<Integer> categorieIds = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Obtenir une connexion à la base de données
            connection = MyConnection.getInstance().getCnx();

            // Préparer la requête SQL
            statement = connection.prepareStatement("SELECT id FROM Categorie");

            // Exécuter la requête SQL et obtenir le résultat
            resultSet = statement.executeQuery();

            // Parcourir le résultat et ajouter chaque ID de catégorie à la liste
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                categorieIds.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions si nécessaire
        } finally {
            // Fermer les ressources
            try {
                // Fermer le ResultSet en premier
                if (resultSet != null) resultSet.close();
                // Puis le PreparedStatement
                if (statement != null) statement.close();
                // Enfin, fermer la connexion
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Retourner la liste des IDs de catégorie
        return categorieIds;
    }




    public static Activite getActiviteParId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Activite activite = null;

        try {
            conn = MyConnection.getInstance().getCnx();
            if (conn == null) {
                throw new SQLException("La connexion à la base de données est nulle.");
            }

            String query = "SELECT * FROM activitee WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int categorie_id = rs.getInt("categorie_id");
                String nom = rs.getString("nom");
                int prix = rs.getInt("prix");
                String localisation = rs.getString("localisation");
                int nb_P = rs.getInt("nb_P");
                String etat = rs.getString("etat");
                String image = rs.getString("image");
                String description_act = rs.getString("description_act");

                activite = new Activite(id, categorie_id, nom, prix, localisation, nb_P, etat, description_act,image);
            } else {
                System.out.println("Aucune activité trouvée avec l'ID : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Vous pouvez gérer l'exception en renvoyant une valeur par défaut ou en lançant une nouvelle exception personnalisée
        } finally {
            // Fermer les ressources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return activite;
    }




    public int getIdCategorieByType(String typeCategorie) {
        int categoryId = -1; // Valeur par défaut si aucune catégorie correspondante n'est trouvée

        try (Connection connection = MyConnection.getInstance().getCnx()) {
            // Préparation de la requête SQL
            String query = "SELECT id FROM categorie WHERE type_categorie = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Définir le type de catégorie dans la requête
                statement.setString(1, typeCategorie);

                // Exécution de la requête
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Vérifier s'il y a un résultat
                    if (resultSet.next()) {
                        // Récupération de l'ID de la catégorie correspondante
                        categoryId = resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryId;
    }
    public static Map<String, Integer> getAllTypesCategories() {
        Map<String, Integer> typesCategories = new HashMap<>();

        try (Connection connection = MyConnection.getInstance().getCnx()) {
            String query = "SELECT type_categorie, id FROM categorie";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String typeCategorie = resultSet.getString("type_categorie");
                    int categoryId = resultSet.getInt("id");
                    typesCategories.put(typeCategorie, categoryId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return typesCategories;
    }
    public Map<String, Integer> getActivitebyEtat() {
        Map<String, Integer> activiteByetat = new HashMap<>();

        try  (Connection connection = MyConnection.getInstance().getCnx()) {
            String query = "SELECT etat, COUNT(*) AS count FROM activitee GROUP BY etat";
           try(PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();) {
               while (resultSet.next()) {
                   String etat = resultSet.getString("etat");
                   int count = resultSet.getInt("count");
                   activiteByetat.put(etat, count);
               }
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activiteByetat;
    }

}







