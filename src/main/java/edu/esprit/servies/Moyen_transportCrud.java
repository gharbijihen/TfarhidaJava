package edu.esprit.servies;

import edu.esprit.entites.Moyen_transport;
import edu.esprit.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Moyen_transportCrud implements IcrudL<Moyen_transport> {


    @Override
    public void ajouter(Moyen_transport moyenTransport) {
        String req1 = "INSERT INTO moyen_transport(type,capacite,lieu,image,etat,valide) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setString(1,moyenTransport.getType());
            pst.setInt(2,moyenTransport.getCapacite());
            pst.setString(3,moyenTransport.getLieu());
            pst.setString(4,moyenTransport.getImage());
            pst.setBoolean(5,moyenTransport.isEtat());
            pst.setBoolean(6,moyenTransport.isValide());
            pst.executeUpdate();
            System.out.println("moyenTransport ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Moyen_transport moyenTransport) throws SQLException {
        final String query = "UPDATE moyen_transport SET type = ?, capacite = ?, lieu = ?, image = ?, etat = ?, valide = ? WHERE id = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, moyenTransport.getType());
            pst.setInt(2, moyenTransport.getCapacite());
            pst.setString(3, moyenTransport.getLieu());
            pst.setString(4, moyenTransport.getImage());
            pst.setBoolean(5, moyenTransport.isEtat());
            pst.setBoolean(6, moyenTransport.isValide());
            pst.setInt(7, moyenTransport.getId());

            pst.executeUpdate();
        }
    }


    @Override
    public void supprimer(Moyen_transport moyenTransport ) throws SQLException {

        final String query = "DELETE FROM moyen_transport WHERE id=? ";
        try(PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);){
            pst.setInt(1,moyenTransport.getId());
            pst.executeUpdate();
        }

    }

    @Override
    public List<Moyen_transport> afficher() {
        List<Moyen_transport>moyens=new ArrayList<>();
        String req3="SELECT * FROM  moyen_transport";
        try {
            Statement st=MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs= st.executeQuery(req3);
            while (rs.next())
            {
                Moyen_transport p;
                p=new Moyen_transport();
                p.setId(rs.getInt("id"));
                p.setType(rs.getString("type")); //avec label
                p.setCapacite(rs.getInt("capacite"));
                p.setLieu(rs.getString("lieu"));
                p.setImage(rs.getString("image"));
                p.setEtat(rs.getBoolean("etat"));
                p.setValide(rs.getBoolean("valide"));



                moyens.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  moyens;
       }
    public List<Integer> getAlltrajetIds() {
        List<Integer> categorieIds = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Obtenir une connexion à la base de données
            connection = MyConnection.getInstance().getCnx();

            // Préparer la requête SQL
            statement = connection.prepareStatement("SELECT id FROM Trajet");

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
    public List<Moyen_transport> getAllMoyensTransport() {
        List<Moyen_transport> moyens = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Obtain a connection to the database
            connection = MyConnection.getInstance().getCnx();

            // Prepare the SQL query
            statement = connection.prepareStatement("SELECT * FROM moyen_transport");

            // Execute the SQL query and get the result
            resultSet = statement.executeQuery();

            // Iterate through the result and add each moyen de transport to the list
            while (resultSet.next()) {
                Moyen_transport moyen = new Moyen_transport();
                moyen.setId(resultSet.getInt("id"));
                moyen.setType(resultSet.getString("type"));
                moyen.setCapacite(resultSet.getInt("capacite"));
                moyen.setLieu(resultSet.getString("lieu"));
                moyen.setImage(resultSet.getString("image"));
                moyen.setEtat(resultSet.getBoolean("etat"));
                moyen.setValide(resultSet.getBoolean("valide"));

                moyens.add(moyen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions if necessary

        }

        // Return the list of moyens de transport
        return moyens;
    }

}
