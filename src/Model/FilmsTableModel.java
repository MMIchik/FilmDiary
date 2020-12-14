package Model;

import db.DBWorker;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FilmsTableModel implements TableModel {
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>(); // слушатели событий
    private ArrayList<Film> films; // список фильмов для прогрузки

    //конструктор
    public FilmsTableModel(ArrayList<Film> films) {
        this.films = films;
    }

    //добавляем лисенеров (для добавления/изменения)
    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    // задаем для каждой колонки свой тип данных
    public Class<?> getColumnClass(int columnIndex){
        switch (columnIndex){
            case 0: return String.class;
            case 1: return String.class;
            case 2: return String.class;
            case 3: return Integer.class;
            default: return Boolean.class;
        }
    }

    // задаем кол-во колонок
    public int getColumnCount() {
        return 7;
    }

    // делаем заголовки для таблицы
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Название";
            case 1:
                return "Жанр";
            case 2:
                return "Режиссер";
            case 3:
                return "Год выхода";
            case 4:
                return "Избранное";
            case 5:
                return "Желаемое";
            case 6:
                return "Просмотрено";
        }
        return "";
    }

    // задаем кол-во строк (равное кол-ву элементов в списке)
    public int getRowCount() {
        return films.size();
    }

    // загружаем значения в таблицу
    public Object getValueAt(int rowIndex, int columnIndex){
        Film film = films.get(rowIndex);
        switch (columnIndex){
            case 0:
                return film.getName();
            case 1:
                try {
                    return DBWorker.getGenreName(film.getGenre_id());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            case 2:
                return film.getProducer();
            case 3:
                return film.getYear();
            case 4:
                return film.isFavorites();
            case 5:
                return film.isDesired();
            case 6:
                return film.isViewed();
        }
        return "";
    }

    // Разрешаем редактирование только в определенных ячейках (где чекбоксы)
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 4:
            case 5:
            case 6:
                return true;
        }
        return false;
    }

    // для сохранения изменений
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Film film = films.get(rowIndex);

        switch (columnIndex){
            case 4: film.setFavorites((Boolean)value); break;
            case 5: film.setDesired((Boolean)value); break;
            case 6: film.setViewed((Boolean)value); break;
        }

        try {
            DBWorker.updateChecks(rowIndex + 1, film);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Film> getFilms(){
        return films;
    }

    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }
}
