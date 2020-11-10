package jam.example.sbtask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:test.properties")
public class ConstantDataRepositoryTest extends ConstantSQLTest{

    @Value("${guide.data.FIRST_FIELD}")
    public String FIRST_FIELD;

    @Value("${guide.data.SECOND_FIELD}")
    public String SECOND_FIELD;

    @Value("${guide.data.FIRST_FIELD_NEW_VALUE}")
    public String FIRST_FIELD_NEW_VALUE;

    @Value("${guide.data.FIRST_FIELD_OLD_VALUE}")
    public String FIRST_FIELD_OLD_VALUE;

    @Value("${guide.data.SECOND_FIELD_VALUE}")
    public String SECOND_FIELD_VALUE;

    @Value("${guide.data.NAME_TABLE}")
    public String NAME_TABLE;



}
