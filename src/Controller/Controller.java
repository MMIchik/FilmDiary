package Controller;

import Model.Film;
import Model.FilmsTableModel;
import Model.GroupClass;
import View.*;
import db.DBWorker;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс - контроллер.
 * Осуществляет связь между видом и моделью.
 * Обрабатывает все события, все действия пользователя
 */
public class Controller {
    private View view; // Вид
    private TableModel model; // Модель
    private ArrayList<Film> films = new ArrayList<>(); // Список всех фильмов

    public Controller() throws SQLException {
     //   GroupClass groupClass = new GroupClass(); // создаем объект класса групкласс, чтобы получить список фильмов
        films = GroupClass.updateList();

 //       films = groupClass.getFilms(); // записываем полученный список
        // инициализируем модель, записываем в нее этот список фильмов

        System.out.println("fur:");
        for (Film f:
             films) {
            System.out.println(f.toString());
        }

        model = new FilmsTableModel(films);

        // запускаем вид. Помещаем модель в вид
        view = new View(model);
       // DBWorker.editFilm(1, new Film("Фильм2", 2, "Режиссер", 2019, false, false, false));

        // добавляем слушатель на закрытие, чтобы считать изменения
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ArrayList<Film> tempFilms = ((FilmsTableModel)model).getFilms();

                for (Film f:
                        tempFilms) {
                    System.out.println(f.toString());
                }

            }
        });



        // добавляем слушатель для добавления
        view.getMenuOptionsItem1().addActionListener(new AddListener());
        // добавляем слушатель для поиска
        view.getMenuOptionsItem2().addActionListener(new SearchListener());

        // добавляем для каждого пункта меню "Списки" слушатель нажатия
        for (Component item: //проходим по каждому пункту меню
                view.getMenuLists().getMenuComponents()) {
            JMenuItem menuItem = (JMenuItem) item; // ссылка на пункт меню
            menuItem.addActionListener(new ListsListener()); // добавляем слушатель
        }

        // добавляем для каждого пункта меню "Жанры" слушатель нажатия
        for (Component item: //проходим по каждому пункту меню
                view.getMenuGenres().getMenuComponents()) {
            JMenuItem menuItem = (JMenuItem) item; // ссылка на пункт меню
            menuItem.addActionListener(new GenresListener()); // добавляем слушатель
        }
    }

    // Слушатель для пункта меню "Поиск"
    public class SearchListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            SearchView searchView = new SearchView(view); // открываем модульное окно для поиска
            //Обработка нажатия кнопки
            JButton button = searchView.getButton();
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    String search = searchView.getTextFieldName().getText(); // строка, в которой пользователь написал свой запрос (то что он хочет найти)

                    if (search.equals("")){ // если поле оказалось пустым
                        JOptionPane.showMessageDialog(null, "Заполните поле!");//показываем окно с ошибкой
                        return;
                    }

                    ArrayList<Film> resultOfSearch = new ArrayList<>(); // список, который хранит в себе результат поиска,
                    //т.е. все фильммы, которые содержат в себе search (то что ввел юзер)

                    //проходим по каждомы фильму
                    for (Film el:
                         films) {
                        // если фильм содержит в названии/режиссере/году search
                        if (el.getName().contains(search)
                                || String.valueOf(el.getYear()).contains(search)
                                || el.getProducer().contains(search))
                            resultOfSearch.add(el); // то добавляем в результирующий список
                    }

                    if (resultOfSearch.size() == 0){ // если по итогу в список ничего не записанр, то:
                        JOptionPane.showMessageDialog(null, "Ничего не найдено"); //показываем окно с ошибкой
                        return;
                    }
                    view.getTable().setModel(new FilmsTableModel(resultOfSearch)); // обновляем; в таблицу записываем
                    //новую модель, которая отображает рузультат поиска

                    searchView.setVisible(false); // закрываем окно
                }
            });
            searchView.setVisible(true);
        }
    }

    // Слушатель для пункта меню "Добавление"
    public class AddListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            AddFilmView addView = new AddFilmView(view); // открываем модульное окно для добавления
            //Обработка нажатия кнопки
            JButton button = addView.getButton();
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    //проверка на коррекнтость ввода
                    if (!checkCorrectInput(addView)){ // если что-то неправильно введено
                        JOptionPane.showMessageDialog(null, "Некорректный ввод");
                        return;
                    } else {
                        Film film = null; // получаем введенный фильм
                        try {
                            film = getFilmFromAddView(addView);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        // проверяем, есть ли такой в списке
                        for (Film el:
                             films) {
                            if (el.getName().equals(film.getName())
                                    && (el.getProducer().equals(film.getProducer()))
                                    && (el.getYear() == film.getYear())){
                                JOptionPane.showMessageDialog(null, "Такой фильм уже есть в списке!");
                                return;
                            }
                        }

                        //films.add(film); // вносим в список
                        try {
                            DBWorker.addFilm(film); // Добавляем фильм в БД
                            films = GroupClass.updateList(); // обновляем данные
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        view.getTable().setModel(new FilmsTableModel(films)); // обновляем
                    }
                    addView.setVisible(false); //закрываем окно
                }
            });
            addView.setVisible(true);
        }
    }

    // Функция для получения введенных пользователем значений
    // возвращает объект класса Film
    public static Film getFilmFromAddView(AddFilmView addView) throws SQLException {
        // считывает с текстфиелдов:
        String name = addView.getTextFieldName().getText(); // имя
        String producer = addView.getTextFieldProducer().getText(); // режиссер
        int year = Integer.parseInt(addView.getTextFieldYear().getText()); // год
        // с комбобокса считываем выбранный итем:
        //Film.Genre genre = Film.Genre.valueOf(addView.getComboBoxGenres().getSelectedItem().toString()); // жанр
        int genreId = DBWorker.getGenreId(addView.getComboBoxGenres().getSelectedItem().toString());

        //дальше смотрим, какие чекбоксы нажаты:
        // записываем везде false
        boolean favorite = false;
        boolean viewed = false;
        boolean desired = false;

        // проверяем каждый чекбокс. Если он выбран, то записываем true
        if (addView.getCheckBox1().isSelected())
            favorite = true;
        if (addView.getCheckBox2().isSelected())
            desired = true;
        if (addView.getCheckBox3().isSelected())
            viewed = true;

        // создаем объект со всеми введенными данными
        Film resultFilm = new Film(name, genreId, producer, year, favorite, viewed, desired);
        return resultFilm;
    }

    // функция для проверки на возможность перевода строки в значение типа int
    // true - возможно
    public static boolean isNumeric(String str)
    {
        try
        {
            int d = Integer.parseInt(str); //преобразовываем
        }
        catch(NumberFormatException nfe) //если возникает исключение
        {
            return false;
        }
        return true;
    }

    // функция для проверки текстфиелдов на коррекность ввода
    // true - корректный
    private static boolean checkCorrectInput(AddFilmView view){
        for (Component comp: // по каждому компоненту
             view.getComponents()) {
            String yearStr = view.getTextFieldYear().getText(); // строка с годом выхода
            if (comp instanceof JTextField // если это текстфилд
                    && ((JTextField) comp).getText().equals("")) // и он пустой
                return false;
            else if (!isNumeric(yearStr)) // проверка на ввод целого числа
                return false;
            else if (Integer.parseInt(yearStr) < 1800
                    || Integer.parseInt(yearStr) > 2060 ) // проверка на корректность ввода года
                return false;
        }
        return true;
    }

    // Слушатель для пунктов "Списки"
    public class ListsListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            JMenuItem clickedMenuItem = (JMenuItem) e.getSource(); // записываем кликнутый пункт

            ArrayList<Film> newFilms = new ArrayList<>(); // список с результатом выбора жанра

            // если нажат пункт "Все", то выгружаем полный список
            if (clickedMenuItem.getText().equals("Все"))
                newFilms = films;
            else {

                if (clickedMenuItem.getText().equals("Избранное")){
                    for (Film el:
                         films) {
                        if (el.isFavorites())
                            newFilms.add(el);
                    }
                } else if (clickedMenuItem.getText().equals("Просмотрено")){
                    for (Film el:
                            films) {
                        if (el.isViewed())
                            newFilms.add(el);
                    }
                } else {
                    for (Film el:
                            films) {
                        if (el.isDesired())
                            newFilms.add(el);
                    }
                }
            }
            // загружаем новый список в таблицу
            view.getTable().setModel(new FilmsTableModel(newFilms)); //обновляем
        }
    }

    // ЖАНРЫ
    //слушатель для выбора типа жанра
    public class GenresListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            JMenuItem clickedMenuItem = (JMenuItem) e.getSource();

            ArrayList<Film> newFilms = new ArrayList<>();

            // если нажат пункт "Все", то выгружаем полный список
            if (clickedMenuItem.getText().equals("Все"))
                newFilms = films;
            else {
                // проходим по каждому элементу списка и проверяем, совпадает ли текст нажатого
                // пункта с жанром текущего элемента.
                // Если да, то вносим в новый список
                for (Film el :
                        films) {
                    try {
                        if (DBWorker.getGenreName(el.getGenre_id()).equals(clickedMenuItem.getText()))
                            newFilms.add(el);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
            // загружаем новый список в таблицу
            view.getTable().setModel(new FilmsTableModel(newFilms)); //обновляем
        }
    }

}
