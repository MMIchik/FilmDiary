package View;

import Model.Film;

import javax.swing.*;
import java.awt.*;

public class AddFilmView extends JDialog {
    private JButton button; //ok
    private JTextField textFieldName; //имя
    private JTextField textFieldProducer; //ркжиссер
    private JTextField textFieldYear;//год
    private JComboBox comboBoxGenres; //комбобокс со всеми жанрами

    private JCheckBox checkBox1; // избранное
    private JCheckBox checkBox2;//просмотренр
    private JCheckBox checkBox3; //желаемое

    //конструктор
    public AddFilmView(Frame owner) {
        super(owner, "Добавить фильм", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(250, 300);
        init(); //инициализируем
        add(addComponents()); //добавляем в окно панель с компонентами
        setLocationRelativeTo(owner); // центрируем относительно предшественника
    }

    //функция для добавления всех компонентов в панель
    private JPanel addComponents(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 1 , 10, 5));

        mainPanel.add(textFieldName);
        mainPanel.add(textFieldProducer);
        mainPanel.add(textFieldYear);
        mainPanel.add(comboBoxGenres);

        mainPanel.add(checkBox1);
        mainPanel.add(checkBox2);
        mainPanel.add(checkBox3);

        mainPanel.add(button);

        return mainPanel;
    }

    private void init(){
        textFieldName = new JTextField();
        textFieldName.setText("Название");
        textFieldProducer = new JTextField();
        textFieldProducer.setText("Режиссер");
        textFieldYear = new JTextField();
        textFieldYear.setText("Год выпуска");

        comboBoxGenres = new JComboBox();
        //Заполняем комбобокс жанрами:
        for (Film.Genre el:
             Film.Genre.values()) {
            comboBoxGenres.addItem(el.toString());
        }

        checkBox1 = new JCheckBox("Избранное");
        checkBox2 = new JCheckBox("Желаемое");
        checkBox3 = new JCheckBox("Просмотрено");

        button = new JButton("OK");
    }

    //геттеры и сеттеры:
    public JButton getButton() {
        return button;
    }

    public JTextField getTextFieldYear() {
        return textFieldYear;
    }

    public JTextField getTextFieldName() {
        return textFieldName;
    }

    public JTextField getTextFieldProducer() {
        return textFieldProducer;
    }

    public JComboBox getComboBoxGenres() {
        return comboBoxGenres;
    }

    public JCheckBox getCheckBox1() {
        return checkBox1;
    }

    public JCheckBox getCheckBox2() {
        return checkBox2;
    }

    public JCheckBox getCheckBox3() {
        return checkBox3;
    }
}
