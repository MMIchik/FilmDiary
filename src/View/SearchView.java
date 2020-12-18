package View;

import javax.swing.*;
import java.awt.*;

public class SearchView extends JDialog {
    private JTextField textFieldName; // текстфиелд для поиска
    private JButton button; // ОК

    public SearchView(Frame owner) {
        super(owner, "Что вы хотите найти?", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(250, 300);

        init(); //инициализируем

        JPanel panel = new JPanel(); // создаем панель и добавлям в нее текстфиелд и кнопку:
        panel.add(textFieldName);
        panel.add(button);

        add(panel); //добавляем в окно панель
        pack(); // задаем минимальные размеры для окна

        setLocationRelativeTo(owner); // центрируем относительно предшественника
    }

    private void init(){
        textFieldName = new JTextField(20);
        button = new JButton("OK");
    }

    public JTextField getTextFieldName() {
        return textFieldName;
    }

    public JButton getButton() {
        return button;
    }
}
