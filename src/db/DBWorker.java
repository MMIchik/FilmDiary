package db;

import Model.Film;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBWorker {

    public static final String PATH_TO_DB_FILE = "films.db";
    public static final String URL = "jdbc:sqlite:" + PATH_TO_DB_FILE;
    public static Connection conn;

    public static void initDB(){
        try {
            conn = DriverManager.getConnection(URL);
            if (conn != null) {
                createDB();

                // вписываем жанры
                if (getAllGenres().size() == 0){
                    addGenre("Триллер");
                    addGenre("Ужасы");
                    addGenre("Комедия");
                    addGenre("Мультипликация");
                    addGenre("Драма");
                    addGenre("Документальный");
                }

                DatabaseMetaData metaData = conn.getMetaData();
                System.out.println("Драйвер: " + metaData.getDriverName());


                List<String> genresStr = getAllGenres();
                for (String el:
                     genresStr) {
                    System.out.println(el);
                }

            }
            //conn.close();
        } catch (SQLException ex) {
            System.out.println("Ошибка подключения к БД: " + ex);
        }
    }

    public static void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public static void createDB() throws SQLException {
        Statement statmt = conn.createStatement();
        // передаем SQL-запросы:
        // создать таблицу если не существует 'genres' с полями id (ключевое инт с автоинкрементом) и текстом титл
        statmt.execute("CREATE TABLE if not exists 'genres' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT, " + // id
                "'title' text);" // название
        );
        System.out.println("Таблица 'genres' создана или уже существует.");

        // создать таблицу если не существует 'films' с полями: id (ключевое инт с автоинкрементом), name
        statmt.execute("CREATE TABLE if not exists 'films' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT, " + // id
                "'name' text," + // имя
                "'producer' text," + // режиссер
                "'year' INTEGER NOT NULL, " + // год
                "'isFavorite' INTEGER NOT NULL, " + // в избарнном (т.к. нет типа bool, то INTEGER)
                "'isViewed' INTEGER NOT NULL, " + // просмотрен
                "'isDesired' INTEGER NOT NULL, " + // в желаемом
                "'genres_id' INTEGER NOT NULL, FOREIGN KEY (genres_id) REFERENCES genres (id));"
        );
        System.out.println("Таблица 'films' создана или уже существует.");
    }

    public static void addGenre(String data) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO genres(`title`) " +
                        "VALUES(?)");

        statement.setObject(1, data);
        statement.execute();
        statement.close();
    }

    public static void addFilm(Film film) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO films(" +
                        "'name'," +
                        "'producer'," +
                        "'year'," +
                        "'isFavorite'," +
                        "'isViewed'," +
                        "'isDesired'," +
                        "'genres_id') " +
                        "VALUES(?,?,?,?,?,?,?)");

        statement.setObject(1, film.getName());
        statement.setObject(2, film.getProducer());
        statement.setObject(3, film.getYear());
        statement.setObject(4, film.isFavorites());
        statement.setObject(5, film.isViewed());
        statement.setObject(6, film.isDesired());
        statement.setObject(7, film.getGenre_id());

        statement.execute();
        statement.close();
    }

    public static int getGenreId(String genreName) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT id FROM genres WHERE genres.title ='" + genreName + "'");

        int genreId = -1;
        genreId = resultSet.getInt(1);

        return genreId;
    }

    public static String getGenreName(int genreId) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT title FROM genres WHERE genres.id =" + genreId);

        String genrename = "";
        genrename = resultSet.getString(1);

        resultSet.close();
        statement.close();

        return genrename;
    }

    public static ArrayList<String> getAllGenres() throws SQLException {
        Statement statement = conn.createStatement();
        ArrayList<String> list = new ArrayList<String>();

        ResultSet resultSet = statement.executeQuery("SELECT id, title FROM genres");

        while (resultSet.next()) {
            list.add(resultSet.getInt("id") + " "
                    + resultSet.getString("title"));
        }

        resultSet.close();
        statement.close();

        return list;
    }

    public static ArrayList<Film> getAllFilms() throws SQLException {
        Statement statement = conn.createStatement();
        ArrayList<Film> list = new ArrayList<Film>();

        ResultSet resultSet = statement.executeQuery(
                "SELECT films.id, " +
                        "films.name, " +
                        "films.producer, " +
                        "films.year, " +
                        "films.isFavorite, " +
                        "films.isViewed, " +
                        "films.isDesired, " +
                        "films.genres_id, genres.title FROM films JOIN genres ON genres.id = films.genres_id");

        while (resultSet.next()) {
            list.add(new Film(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("genres_id"),
                    resultSet.getString("producer"),
                    resultSet.getInt("year"),
                    resultSet.getBoolean("isFavorite"),
                    resultSet.getBoolean("isViewed"),
                    resultSet.getBoolean("isDesired")
                    )
            );
        }

        resultSet.close();
        statement.close();

        return list;
    }

    public static void deleteFilm(int id) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("DELETE FROM films WHERE films.id =" + id);
        System.out.println("Фильм удален.");
        statement.close();
    }

    public static void editFilm(int id, Film newFilm) throws SQLException {
        Statement statement = conn.createStatement();
        int rows = statement.executeUpdate("UPDATE films SET " +
                "isFavorite = " + newFilm.isFavorites() +
                ", isViewed = " + newFilm.isViewed() +
                ", isDesired = " + newFilm.isDesired() +
                ", name = " + newFilm.getName() +
                ", producer = " + newFilm.getProducer() +
                ", year = " + newFilm.getYear() +
                " WHERE id = " + id
                );
        System.out.println("Фильм изменен.");
        System.out.println(rows);
        statement.close();
    }

    // обновляет только булевы переменные (чекбоксы)
    public static void updateChecks(int id, Film newFilm) throws SQLException {
        Statement statement = conn.createStatement();
        int rows = statement.executeUpdate("UPDATE films SET " +
                "isFavorite = " + newFilm.isFavorites() +
                ", isViewed = " + newFilm.isViewed() +
                ", isDesired = " + newFilm.isDesired() +
                " WHERE id = " + id
        );
        System.out.println("Фильм изменен.");
        System.out.println(rows);
        statement.close();
    }


}
