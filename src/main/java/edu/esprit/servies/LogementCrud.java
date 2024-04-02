package edu.esprit.servies;

import edu.esprit.entites.Logement;
import edu.esprit.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LogementCrud implements IcrudL<Logement>{
    @Override
    public void ajouter(Logement logement) {
        String req1 = "INSERT INTO logement(nom,prix,localisation,num,image,note_moyenne,type_log) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
            pst.setString(1, logement.getNom()); //tab3a ? eli fel values
            pst.setInt(2, logement.getPrix());
            pst.setString(3, logement.getLocalisation());
            pst.setInt(4, logement.getNum());
            pst.setString(5, logement.getImage());
            //pst.setString(6, logement.getEtat());
            pst.setInt(7, logement.getNote_moyenne());

            pst.setString(8, logement.getType_log());

           // pst.setInt(9, logement.getUser_id());
           // pst.setInt(10, logement.getEquipement_id());


            pst.executeUpdate();
            System.out.println("logement ajoutéé!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Logement logement) throws SQLException {
        final String query="UPDATE logement SET nom = ?, prix = ?, localisation = ?,note_moyenne = ?,etat= ?,image = ?,num = ?,type_log= ? WHERE id= ?";
        try( PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);){

            pst.setString(1,logement.getNom());
            pst.setInt(2,logement.getPrix());
            pst.setString(3,logement.getLocalisation());
            pst.setInt(4,logement.getNote_moyenne());
            pst.setString(5,logement.getEtat());
            pst.setString(6,logement.getImage());
            pst.setString(7,logement.getNom());
            pst.setString(8,logement.getType_log());
            pst.setInt(9,logement.getId());



            pst.executeUpdate();
        }
    }

    @Override
    public void supprimer(Logement logement) throws SQLException {
        final String query = "DELETE FROM logement WHERE id=? ";
        try(PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);){
            pst.setInt(1,logement.getId());
            pst.executeUpdate();
        }

    }


    @Override
    public List<Logement> afficher() {
        List<Logement>logements=new ArrayList<>();
        String req3="SELECT * FROM logement";
        try {
            Statement st=MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs= st.executeQuery(req3);
            while (rs.next())
            {
                Logement p=new Logement();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom")); //avec label
                p.setPrix(rs.getInt("prix"));
                p.setLocalisation(rs.getString("localisation"));
                p.setNote_moyenne(rs.getInt("note"));
                p.setEtat(rs.getString("etat"));
                p.setImage(rs.getString("image"));
                p.setNum(rs.getInt("numéro"));




                logements.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  logements;
    }
}

