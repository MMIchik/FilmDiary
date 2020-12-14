package Model;

public class Film {
    public enum Genre { // создаем тип данных, в котором храним жанры
        Триллер,
        Ужасы,
        Комедия,
        Мультипликация,
        Драма,
        Документальный
    }

    private int id;

    private String name; //название
    private Genre genre; //жанр //FIXME
    private int genre_id;
    private String producer; //режиссер
    private int year; //год выхода

    private boolean favorites; // избранное
    private boolean viewed; // просмотренный
    private boolean desired; //желаемое

    //конструктор
    /*public Film(String name, Genre genre, String producer, int year,
                boolean favorites, boolean viewed, boolean desired) {
        this.name = name;
        this.genre = genre;
        this.producer = producer;
        this.year = year;
        this.favorites = favorites;
        this.viewed = viewed;
        this.desired = desired;
    }*/

    public Film(int id, String name, int genre_id, String producer, int year,
                boolean favorites, boolean viewed, boolean desired) {
        this.id = id;
        this.name = name;
        this.genre_id = genre_id;
        this.producer = producer;
        this.year = year;
        this.favorites = favorites;
        this.viewed = viewed;
        this.desired = desired;
    }

    public Film(String name, int genre_id, String producer, int year,
                boolean favorites, boolean viewed, boolean desired) {
        this.name = name;
        this.genre_id = genre_id;
        this.producer = producer;
        this.year = year;
        this.favorites = favorites;
        this.viewed = viewed;
        this.desired = desired;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre_id=" + genre_id +
                ", producer='" + producer + '\'' +
                ", year=" + year +
                ", favorites=" + favorites +
                ", viewed=" + viewed +
                ", desired=" + desired +
                '}';
    }

    // дальше геттеры и сеттеры
    public String getName() {
        return name;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getProducer() {
        return producer;
    }

    public int getYear() {
        return year;
    }

    public boolean isFavorites() {
        return favorites;
    }

    public boolean isViewed() {
        return viewed;
    }

    public boolean isDesired() {
        return desired;
    }

    public void setFavorites(boolean favorites) {
        this.favorites = favorites;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public void setDesired(boolean desired) {
        this.desired = desired;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
