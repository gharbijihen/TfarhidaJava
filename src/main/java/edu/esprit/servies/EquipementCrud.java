    package edu.esprit.servies;

    import edu.esprit.entites.Equipement;
    import edu.esprit.entites.Logement;
    import edu.esprit.tools.MyConnection;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;

    import java.sql.*;
    import java.util.List;

    public class EquipementCrud implements IcrudL<Equipement> {
        @Override
        public boolean ajouter(Equipement equipement) {
            String req1 = "INSERT INTO equipement (parking, internet, climatisation, nbr_chambre, description, types_de_chambre) VALUES (?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1);
                pst.setBoolean(1, equipement.isParking());
                pst.setBoolean(2, equipement.isInetrnet());
                pst.setBoolean(3, equipement.isClimatisation());
                pst.setInt(4, equipement.getNbr_chambre());
                pst.setString(5, equipement.getDescription());
                pst.setString(6, equipement.getType_chambre());
                pst.executeUpdate();
                System.out.println("Equipement ajouté!");
                return true; // Retourne true si l'ajout est réussi
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false; // Retourne false si une exception est levée
            }
        }
        public int ajouterreturnsID(Equipement reponse) {
            String req1 = "INSERT INTO reponse (parking, internet, climatisation, nbr_chambre, description, types_de_chambre) VALUES (?, ?, ?, ?, ?, ?)";
            int generatedId = -1;
            try {
                Connection connection = MyConnection.getInstance().getCnx();
                PreparedStatement pst = connection.prepareStatement(req1, Statement.RETURN_GENERATED_KEYS);
                pst.setBoolean(1, reponse.isParking());
                pst.setBoolean(2, reponse.isInetrnet());
                pst.setBoolean(3, reponse.isClimatisation());
                pst.setInt(4, reponse.getNbr_chambre());
                pst.setString(5, reponse.getDescription());
                pst.setString(6, reponse.getType_chambre());

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected == 1) {
                    ResultSet generatedKeys = pst.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    }
                    System.out.println("Equipement ajoutée!");
                } else {
                    System.out.println("Erreur lors de l'ajout de la equipement.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur SQL: " + e.getMessage());
            }
            return generatedId;
        }
        // Autres méthodes de la classe
        public Equipement getById(int id) throws SQLException {
            String query = "SELECT * FROM reponse WHERE id = ?";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    Equipement rec = new Equipement();
                    rec.setId(rs.getInt("id"));
                    rec.setDescription(rs.getString("description"));
                    rec.setDescription(rs.getString("parking"));
                    rec.setDescription(rs.getString("intertnet"));
                    rec.setDescription(rs.getString("climatisation"));
                    rec.setDescription(rs.getString("type_de_chambre"));
                    rec.setDescription(rs.getString("nbr_chambre"));
                    return rec;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        }


        @Override
        public void modifier(Equipement T) throws SQLException {

        }

        @Override
        public void supprimer(Equipement equipement) throws SQLException {
            final String query = "DELETE FROM equipement WHERE id=? ";
            try(PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);){
                pst.setInt(1,equipement.getId());
                pst.executeUpdate();
            }

        }


        @Override
        public List<Equipement>afficher() {
            ObservableList<Equipement> equipements = FXCollections.observableArrayList();

            String req3 = "SELECT * FROM equipement";
            try {
                Statement st = MyConnection.getInstance().getCnx().createStatement();
                ResultSet rs = st.executeQuery(req3);
                while (rs.next()) {
                    Equipement equipement = new Equipement();
                    equipement.setId(rs.getInt("id"));
                    equipement.setParking(rs.getBoolean("parking"));
                    equipement.setInetrnet(rs.getBoolean("internet"));
                    equipement.setClimatisation(rs.getBoolean("climatisation"));
                    equipement.setDescription(rs.getString("description"));
                    equipement.setNbr_chambre(rs.getInt("nbr_chambre"));
                    equipement.setType_chambre(rs.getString("types_de_chambre"));
                    equipements.add(equipement);
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération des données : " + e.getMessage());
            }
            return equipements;
        }
        }

