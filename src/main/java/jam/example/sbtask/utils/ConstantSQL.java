package jam.example.sbtask.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Класс содержит список констант, используемых при формировании SQL-запросов
 *
 * @author JAM
 */
@Component
public class ConstantSQL {

    @Value("${guide.data.INSERT}")
    public String INSERT;

    @Value("${guide.data.SELECT_ALL}")
    public String SELECT_ALL;

    @Value("${guide.data.SET}")
    public String SET;

    @Value("${guide.data.UPDATE}")
    public String UPDATE;

    @Value("${guide.data.WHERE}")
    public String WHERE;

    @Value("${guide.data.AND}")
    public String AND;

    @Value("${guide.structure.CREATE_GUIDE}")
    public String CREATE_GUIDE;

    @Value("${guide.structure.ALTER_TABLE}")
    public String ALTER_TABLE;

    @Value("${guide.structure.DROP_COLUMN}")
    public String DROP_COLUMN;

    @Value("${guide.structure.DROP_TABLE}")
    public String DROP_TABLE;

    @Value("${guide.structure.ADD_COLUMN}")
    public String ADD_COLUMN;

    @Value("${guide.structure.UPDATE_TYPE_FIELDS}")
    public String UPDATE_TYPE_FIELDS;

    @Value("${guide.structure.UPDATE_NAME_FIELDS}")
    public String UPDATE_NAME_FIELDS;

    @Value("${guide.structure.CHECK_GUIDE}")
    public String CHECK_GUIDE;

    @Value("${guide.SPACE}")
    public String SPACE;

    @Value("${guide.COMMA}")
    public String COMMA;

    @Value("${guide.SEMICOLON}")
    public String SEMICOLON;

    @Value("${guide.CLOSING_BRACKET}")
    public String CLOSING_BRACKET;

    @Value("${guide.OPENING_BRACKET}")
    public String OPENING_BRACKET;

    @Value("${guide.CONSTRAINT}")
    public String CONSTRAINT;

    @Value("${guide.EQUALLY}")
    public String EQUALLY;

    @Value("${guide.VALUES}")
    public String VALUES;

    @Value("${guide.data.DELETE}")
    public String DELETE;

    @Value("${guide.TO}")
    public String TO;

    @Value("${guide.USING}")
    public String USING;

    @Value("${guide.COLON}")
    public String COLON;

    @Value("${guide.TYPE}")
    public String TYPE;
}
