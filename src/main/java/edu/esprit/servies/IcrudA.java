package edu.esprit.servies;
import java.sql.SQLException;
import java.util.List;
public interface IcrudA <T>{
    void ajouter(T t);
    void modifier(T T) throws SQLException;
    void supprimer(T T) throws SQLException;
    List<T> afficher();
}
