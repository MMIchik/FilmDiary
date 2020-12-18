package View;

import Model.Film;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class View extends JFrame {
    private JMenu menuLists; //1й пункт меню "Списки"
    private JMenuItem menuListsItem1; // Избранное
    private JMenuItem menuListsItem2; // Просмотрено
    private JMenuItem menuListsItem3; // Желаемое

    private JMenu menuGenres; //2й пункт меню "Жанр"

    private JMenu menuOptions; //3й пункт меню "Действия"
    private JMenuItem menuOptionsItem1; // добавить
    private JMenuItem menuOptionsItem2; // поиск

    private JTable table; // таблица для хранения всех объектов

    //конструктор
    public View(TableModel tableModel) {
        super("Кинодневник");
        setSize(750, 300);
        setLocationRelativeTo(null); // размещаем по центру
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        init();//инициализируем компоненты
        addMenuBar();//добавляем меню

        addTable(tableModel); // добавляем таблицу

        setVisible(true); // отображаем окно
    }

    // функция для добавления таблицы в окно
    public void addTable(TableModel model){
        // добавляем таблицу
        setTable(new JTable(model));
        // помещаем таблицу в скрол
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane); //добавляем скрол
    }

    //инициализируем
    private void init() {
        menuLists = new JMenu("Списки");
        menuListsItem1 = new JMenuItem("Избранное");
        menuListsItem2 = new JMenuItem("Желаемое");
        menuListsItem3 = new JMenuItem("Просмотрено");

        menuGenres = new JMenu("Жанры");

        menuOptions = new JMenu("Действия");
        menuOptionsItem1 = new JMenuItem("Добавить");
        menuOptionsItem2 = new JMenuItem("Поиск");
    }

    // функция для моздания и добавления меню в окно
    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();// Создание строки главного меню
        // Формируем "Списки"
        menuLists.add(new JMenuItem("Все"));
        menuLists.add(menuListsItem1);
        menuLists.add(menuListsItem2);
        menuLists.add(menuListsItem3);
        menuBar.add(menuLists);

        menuGenres.add(new JMenuItem("Все"));
        // добавляем в меню все жанры из типа "Жанры" в классе Film
        for (Film.Genre el:
                Film.Genre.values()) {
            menuGenres.add(new JMenuItem(el.toString()));
        }
        menuBar.add(menuGenres);

        //Формируем "Опции"
        menuOptions.add((menuOptionsItem1));
        menuOptions.add(menuOptionsItem2);
        menuBar.add(menuOptions);

        setJMenuBar(menuBar);// Подключаем меню к интерфейсу приложения
    }

    //геттреы и сеттеры:
    public void setTable(JTable table) {
        this.table = table;
    }

    public JMenu getMenuGenres() {
        return menuGenres;
    }

    public JMenuItem getMenuOptionsItem1() {
        return menuOptionsItem1;
    }

    public JTable getTable() {
        return table;
    }

    public JMenu getMenuLists() {
        return menuLists;
    }

    public JMenuItem getMenuOptionsItem2() {
        return menuOptionsItem2;
    }
}
