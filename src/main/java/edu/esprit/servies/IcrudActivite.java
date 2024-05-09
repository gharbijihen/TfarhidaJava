package edu.esprit.servies;
        import javafx.util.Pair;

        import java.sql.SQLException;
        import java.util.List;
        import java.util.Map;

public interface IcrudActivite <T>{
    void ajouter(T t,String imagePath);
    void ajouter1(T t);

    void modifier(T T) throws SQLException;
    void supprimer(T T) throws SQLException;
    List<T> afficher();

    public List<Integer> getAllCategorieIds() ;
}
