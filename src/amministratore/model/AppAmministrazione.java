package src.amministratore.model;

import src.DAOException;
import src.DAOUtils;
import java.sql.SQLException;

public class AppAmministrazione {

    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("AgenziaBus", "root", "");
        //var model = Model.fromConnection(connection);
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
