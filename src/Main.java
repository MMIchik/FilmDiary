import Controller.Controller;
import db.DBWorker;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        // возникшие проблемы:
        // 1) нет типа boolean в SQlite
        // 2) Вылетала ошибка, что нет столбца. Починилось после пересоздания БД

        try {
            DBWorker.initDB();
            new Controller();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
