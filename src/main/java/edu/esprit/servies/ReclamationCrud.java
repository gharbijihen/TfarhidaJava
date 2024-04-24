package edu.esprit.servies;

import edu.esprit.tools.MyConnection;
import edu.esprit.entites.Reclamation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamationCrud implements IcrudL<Reclamation> {
    @Override
    public void ajouter(Reclamation reclamation) {
        String req1 = "INSERT INTO reclamation(titre,type,description_reclamation,date,etat,image) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setString(1, reclamation.getTitre());
            pst.setString(2, reclamation.getType());
            pst.setString(3, reclamation.getDescription_reclamation());
            pst.setDate(4, reclamation.getDate());
            pst.setBoolean(5, reclamation.getEtat());
            pst.setString(6, reclamation.getImage());



            pst.executeUpdate();
            System.out.println("Reclamation ajoutée!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void modifier(Reclamation reclamation) throws SQLException {
        final String query="UPDATE reclamation SET titre= ?,type= ?,description_reclamation= ?,date= ?,etat = ?,image = ?,reponse_id= ? WHERE id= ?";
        try( PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);){
            pst.setString(1, reclamation.getTitre());
            pst.setString(2, reclamation.getType());
            pst.setString(3, reclamation.getDescription_reclamation());
            pst.setDate(4, reclamation.getDate());
            pst.setBoolean(5, reclamation.getEtat());
            pst.setString(6, reclamation.getImage());
            pst.setInt(7,reclamation.getReponseid());
            pst.setInt(8,reclamation.getId());

            pst.executeUpdate();
        }
    }

    @Override
    public void supprimer(Reclamation reclamation) throws SQLException {
        final String query = "DELETE FROM reclamation WHERE id=? ";
        try(PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);){
            pst.setInt(1,reclamation.getId());
            pst.executeUpdate();
        }

    }

    @Override
    public List<Reclamation> afficher() {
        List<Reclamation>reclamations=new ArrayList<>();
        String req3="SELECT * FROM reclamation";
        try {
            Statement st=MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs= st.executeQuery(req3);
            while (rs.next())
            {
                System.out.println(rs.getBoolean("etat"));

                Reclamation rec=new Reclamation();
                rec.setId(rs.getInt("id"));
                rec.setTitre(rs.getString("titre"));
                rec.setType(rs.getString("type"));
                rec.setDescription_reclamation(rs.getString("description_reclamation"));
                rec.setDate(rs.getDate("date"));
                rec.setEtat(rs.getBoolean("etat"));
                rec.setImage(rs.getString("image"));
                rec.setReponseid(rs.getInt("reponse_id"));
                ReponseCrud reponseservice=new ReponseCrud();
                rec.setReponse(reponseservice.getById(rec.getReponseid()));
                System.out.println(rec);
                reclamations.add(rec);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  reclamations;
    }

//    public Reclamation displayById(int id) {
//        String req="select * from reclamation where id ="+id;
//        Reclamation rec=new Reclamation();
//        try {
//            Statement st=MyConnection.getInstance().getCnx().createStatement();
//            ResultSet rs=st.executeQuery(req);
//
//            rs.next();
//            rec.setId(rs.getInt("id"));
//            rec.setTitre(rs.getString("Titre"));
//            rec.setType(rs.getString("Type"));
//            rec.setDescription_reclamation(rs.getString("Description"));
//            rec.setDate(rs.getDate("Date"));
//            rec.setEtat(rs.getBoolean("etat"));
//            rec.setImage(rs.getString("image"));
//
//
//
//        } catch (SQLException ex) {
//            Logger.getLogger(ReclamationCrud.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return rec;
//
//    }
}

