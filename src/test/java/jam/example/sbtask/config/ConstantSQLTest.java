package jam.example.sbtask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

//todo вынести константы в разные файлы для разных классов
@PropertySource("classpath:test.properties")
public class ConstantSQLTest {

    @Value("${guide.data.drop}")
    public String dropTable;

    @Value("${guide.data.create}")
    public String createTable;

    @Value("${guide.data.insert1}")
    public String insertFirstRecord;

    @Value("${guide.data.insert2}")
    public String insertSecondRecord;

    @Value("${guide.data.select_count}")
    public String selectCount;


    @Value("${guide.data.json_add2}")
    public String json_addData2;

    @Value("${guide.data.json_update}")
    public String json_updateData;

    @Value("${guide.data.json_find}")
    public String json_find;

    @Value("${guide.lifecycle.json_add}")
    public String lc_json_addData;

    @Value("${guide.lifecycle.json_update}")
    public String lc_json_updateData;

    @Value("${guide.lifecycle.json_find}")
    public String lc_json_find;

    @Value("${guide.lifecycle.createGuide}")
    public String lc_json_createGuide;


    @Value("${guide.structure.json_editNameField}")
    public String lc_json_editNameField;

    @Value("${guide.lifecycle.json_delete}")
    public String lc_json_delete;

    @Value("${guide.structure.jsonEmpty}")
    public String json_jsonEmpty;

}
