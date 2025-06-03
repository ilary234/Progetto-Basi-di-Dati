package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class TipoLinea {

    private final String nomeTipo;
    private final String durata;
    private final float percentualeDaPagare;
    private final String categoriaServizio;

    public TipoLinea(String nomeTipo, String durata, float percentualeDaPagare, String categoriaServizio) {
        this.nomeTipo = Objects.requireNonNull(nomeTipo);
        this.durata = Objects.requireNonNull(durata);
        this.percentualeDaPagare = Objects.requireNonNull(percentualeDaPagare);
        this.categoriaServizio = Objects.requireNonNull(categoriaServizio);
    }

    public static final class DAO {

        public static List<String> getLinee(Connection connection, int codice) {
            List<String> info = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_NAME_LINES, codice);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var durata = resultSet.getString("Durata");
                    info.add(durata);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return info;
        }

        public static String getImageLines(Connection connection, String codiceServizio) {
            var code = Integer.valueOf(codiceServizio);
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_IMAGE_LINES, code);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    String categoria = resultSet.getString("CategoriaServizio");
                    return categoria;
                } else {
                    return null;
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}