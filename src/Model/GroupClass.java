package Model;

import db.DBWorker;

import java.sql.SQLException;
import java.util.ArrayList;



//хранит список фильмов
public class GroupClass {
    private static ArrayList<Film> films;


    public static ArrayList<Film> updateList() throws SQLException {
        films = DBWorker.getAllFilms(); // считываем фильмы из БД
        return films;
    }

}
