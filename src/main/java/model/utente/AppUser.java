package model.utente;

import java.sql.SQLException;
import model.utility.DAOException;
import model.utility.DAOUtils;

public class AppUser {

    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("AgenziaBus", "root", "");
        var model = new ModelUser(connection);
        /*var view = new View(() -> {
            try {
                connection.close();
            } catch (Exception ignored) {}
        });*/
        //var controller = new Controller(model, view);
        //view.setController(controller);
        //controller.userRequestedInitialPage();
    }

}
