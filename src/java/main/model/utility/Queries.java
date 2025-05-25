package model.utility;

public final class Queries {

    public static final String TAGS_FOR_PRODUCT =
        """
            select tag_name
            from TAGGED
            where product_code = ?
        """;

    public static final String LIST_PRODUCTS =
        """
            select code, name
            from PRODUCT
        """;

    public static final String PRODUCT_COMPOSITION =
        """
            select M.code, M.description, C.percent
            from MATERIAL as M, COMPOSITION as C
            where M.code = C.material_code
            and product_code = ?
        """;

    public static final String FIND_IMPIEGATO =
        """
            select *
            from impiegati as i
            where i.CodImpiegato = ?
            and i.Password = ?
        """;
}
