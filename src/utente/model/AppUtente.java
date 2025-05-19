package src.utente.model;

import src.DAOException;
import src.DAOUtils;
import java.sql.SQLException;

public class AppUtente {
    
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
