package model.amministratore;

import model.utility.DAOException;
import model.utility.DAOUtils;
import java.sql.SQLException;

public class AppAmministratore {

    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("AgenziaBus", "root", "");
        var model = new ModelAmm(connection);
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
