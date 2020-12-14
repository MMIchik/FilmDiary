package Model;

import db.DBWorker;

import java.sql.SQLException;
import java.util.ArrayList;

import static Model.Film.Genre.*;

//хранит список фильмов
public class GroupClass {
    private static ArrayList<Film> films;

    public GroupClass() throws SQLException {
        /*films.add(new Film("Фильм1", Документальный, "Режиссер", 2020, false, true, true));
        films.add(new Film("Фильм2", Мультипликация, "Режиссер", 2019, false, false, false));
        films.add(new Film("Фильм3", Триллер, "Режиссер", 2001, true, false, true));
        films.add(new Film("Фильм4", Ужасы, "Режиссер", 2020, true, false, false));
        films.add(new Film("Фильм1", Документальный, "Режиссер", 2020, false, true, true));
        films.add(new Film("Фильм2", Мультипликация, "Режиссер", 2020, false, false, false));
        films.add(new Film("Фильм3", Триллер, "Режиссер", 2020, true, false, true));
        films.add(new Film("Фильм4", Ужасы, "Режиссер", 2020, true, false, false));*/

        //films = DBWorker.getAllFilms(); // считываем фильмы из БД
    }

    public static ArrayList<Film> updateList() throws SQLException {
        films = DBWorker.getAllFilms(); // считываем фильмы из БД
        return films;
    }

    //для получения списка фильмов
    public static ArrayList<Film> getFilms() {
        return films;
    }
}
