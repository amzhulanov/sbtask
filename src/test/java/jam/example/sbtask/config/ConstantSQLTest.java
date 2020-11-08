package jam.example.sbtask.config;

import org.springframework.beans.factory.annotation.Value;

public class ConstantSQLTest {
    @Value("${guide.data.json_add}")
    public String json_add;

    @Value("${guide.data.json_update}")
    public String json_update;

    @Value("${guide.data.json_find}")
    public String json_find;

    @Value("${guide.data.json_delete}")
    public String json_delete;

    @Value("${test.before.drop}")
    public String dropTable;

    @Value("${test.before.create}")
    public String createTable;

    @Value("${test.before.insert1}")
    public String insertFirstRecord;

    @Value("${test.before.insert2}")
    public String insertSecondRecord;


}
