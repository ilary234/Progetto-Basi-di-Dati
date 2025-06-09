package model.utente;

import java.sql.SQLException;

import controller.utente.ControllerUtente;
import model.utility.DAOUtils;
import view.utente.ViewUtente;

public class AppUtente {

    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("AgenziaBus", "root", "");
        var model = new ModelUtente(connection);
        var view = new ViewUtente(() -> {
            try {
                connection.close();
            } catch (Exception ignored) {}
        });
        var controller = new ControllerUtente(model);
        view.setController(controller);
    }

}
