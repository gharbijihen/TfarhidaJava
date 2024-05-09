package edu.esprit.servies;

import Utils.Datasource;
import edu.esprit.entites.Reponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseCrud implements IcrudL<Reponse> {
    @Override
    public void ajouter(Reponse reponse) {
        String req1 = "INSERT INTO reponse(description,date) VALUES (?,?)";
        try {
            PreparedStatement pst = Datasource.getConn().prepareStatement(req1);
            pst.setString(1, reponse.getDescription());
            pst.setDate(2, reponse.getDate());


            pst.executeUpdate();

            System.out.println("Reponse ajoutée!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int ajouterreturnsID(Reponse reponse) {
        String req1 = "INSERT INTO reponse(description, date) VALUES (?, ?)";
        int generatedId = -1;
        try {
            Connection connection = Datasource.getConn();
            PreparedStatement pst = connection.prepareStatement(req1, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, reponse.getDescription());
            pst.setDate(2, reponse.getDate());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
                System.out.println("Reponse ajoutée!");
            } else {
                System.out.println("Erreur lors de l'ajout de la réponse.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL: " + e.getMessage());
        }
        return generatedId;
    }

    @Override
    public void modifier(Reponse reponse) throws SQLException {
        final String query="UPDATE reponse SET description = ?, date = ? WHERE id= ?";
        try( PreparedStatement pst = Datasource.getConn().prepareStatement(query);){

            pst.setString(1,reponse.getDescription());
            pst.setDate(2, reponse.getDate());




            pst.executeUpdate();
        }
    }

    @Override
    public void supprimer(Reponse reponse) throws SQLException {
        final String query = "DELETE FROM reponse WHERE id=? ";
        try(PreparedStatement pst = Datasource.getConn().prepareStatement(query);){
            pst.setInt(1,reponse.getId());
            pst.executeUpdate();
        }

    }
    public Reponse getById(int id) throws SQLException {
        String query = "SELECT * FROM reponse WHERE id = ?";
        try {
            PreparedStatement pst = Datasource.getConn().prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Reponse rec = new Reponse();
                rec.setId(rs.getInt("id"));
                rec.setDescription(rs.getString("description"));
                return rec;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    @Override
    public List<Reponse> afficher() {
        List<Reponse>reponses=new ArrayList<>();
        String req3="SELECT * FROM reponse";
        try {
            Statement st=Datasource.getConn().createStatement();
            ResultSet rs= st.executeQuery(req3);
            while (rs.next())
            {
                Reponse rep=new Reponse();
                rep.setId(rs.getInt("id"));
                rep.setDescription(rs.getString("Description"));
                rep.setDate(rs.getDate("Date"));

                reponses.add(rep);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  reponses;
    }

//    public Reponse displayById(int id) {
//        String req="select * from reponse where id ="+id;
//        Reponse rep=new Reponse();
//        try {
//            Statement st=MyConnection.getInstance().getCnx().createStatement();
//            ResultSet rs=st.executeQuery(req);
//
//            rs.next();
//            rep.setId(rs.getInt("id"));
//            rep.setDescription(rs.getString("Description"));
//            rep.setDate(rs.getString("Date"));
//
//        } catch (SQLException ex) {
//            Logger.getLogger(ReponseCrud.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return rep;
//
//    }
}

