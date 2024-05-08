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
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1, Statement.RETURN_GENERATED_KEYS);
                pst.setBoolean(1, equipement.isParking());
                pst.setBoolean(2, equipement.isInternet());
                pst.setBoolean(3, equipement.isClimatisation());
                pst.setInt(4, equipement.getNbr_chambre());
                pst.setString(5, equipement.getDescription());
                pst.setString(6, equipement.getTypes_de_chambre());

                int rowsAffected = pst.executeUpdate();

                if (rowsAffected == 1) {
                    // Retrieve the generated id
                    ResultSet rs = pst.getGeneratedKeys();
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        equipement.setId(generatedId);
                        System.out.println("Equipement ajouté avec succès ! ID généré : " + generatedId);
                        return true;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout de l'équipement : " + e.getMessage());
            }
            return false;
        }
        /*
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
         */
        // Autres méthodes de la classe
        public Equipement getById(int id) {
            String query = "SELECT * FROM equipement WHERE id = ?";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    Equipement rec = new Equipement();
                    rec.setId(rs.getInt("id"));
                    rec.setDescription(rs.getString("description"));
                    rec.setParking(rs.getBoolean("parking"));
                    rec.setInetrnet(rs.getBoolean("internet"));
                    rec.setClimatisation(rs.getBoolean("climatisation"));
                    rec.setTypes_de_chambre(rs.getString("types_de_chambre"));
                    rec.setNbr_chambre(rs.getInt("nbr_chambre"));
                    System.out.println("Equipement found: " + rec);
                    return rec;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        }


        @Override
        public void modifier(Equipement equipement) {
            final String query = "UPDATE equipement SET parking=?, internet=?, climatisation=?, nbr_chambre=?, description=?, types_de_chambre=? WHERE id=?";
            try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
                pst.setBoolean(1, equipement.isParking());
                pst.setBoolean(2, equipement.isInternet());
                pst.setBoolean(3, equipement.isClimatisation());
                pst.setInt(4, equipement.getNbr_chambre());
                pst.setString(5, equipement.getDescription());
                pst.setString(6, equipement.getTypes_de_chambre());
                pst.setInt(7, equipement.getId());
                pst.executeUpdate();
                System.out.println("Equipement mis à jour avec succès !");
            } catch (SQLException e) {
                System.out.println("Erreur lors de la modification de l'équipement : " + e.getMessage());
                e.printStackTrace();
            }
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
                    equipement.setTypes_de_chambre(rs.getString("types_de_chambre"));
                    equipements.add(equipement);
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération des données : " + e.getMessage());
            }
            return equipements;
        }
        public int ajouterReturnsID(Equipement equipement) {
            String req1 = "INSERT INTO equipement (parking, internet, climatisation, nbr_chambre, description, types_de_chambre) VALUES (?, ?, ?, ?, ?, ?)" ;
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req1, Statement.RETURN_GENERATED_KEYS);
                pst.setBoolean(1, equipement.isParking());
                pst.setBoolean(2, equipement.isInternet());
                pst.setBoolean(3, equipement.isClimatisation());
                pst.setInt(4, equipement.getNbr_chambre());
                pst.setString(5, equipement.getDescription());
                pst.setString(6, equipement.getTypes_de_chambre());
                pst.executeUpdate();
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int insertedId = rs.getInt(1);
                    System.out.println("Equipement ajouté avec l'ID : " + insertedId);
                    return insertedId; // Returns the ID of the inserted equipment
                } else {
                    throw new SQLException("No ID obtained.");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return -1; // Returns -1 if an exception is thrown
            }
        }}

