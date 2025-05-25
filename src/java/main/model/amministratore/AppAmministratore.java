package model.amministratore;

import model.utility.DAOUtils;
import view.amministratore.api.ViewAmm;

import java.sql.SQLException;

import controller.amministratore.ControllerAmm;

public class AppAmministratore {

    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("AgenziaBus", "root", "");
        var model = new ModelAmm(connection);
        var view = new ViewAmm(() -> {
            try {
                connection.close();
            } catch (Exception ignored) {}
        });
        var controller = new ControllerAmm(view, model);
        view.setController(controller);
    }

}
