package jam.example.sbtask.repositories;

import jam.example.sbtask.config.ConstantSQLTest;
import jam.example.sbtask.utils.PrepareSQL;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты методов GuideDataRepository
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class GuideDataRepositoryTest extends ConstantSQLTest {
    private final List<String> fieldList = new ArrayList<>();
    private final List<String> dataList = new ArrayList<>();
    private final Map<String,String> fieldData=new HashMap<>();
    private final Map<String,String> fieldGuide=new HashMap<>();
    private List<Map<String, Object>> result=new ArrayList<>();
    private String nameTable;
    private final StringBuilder sql=new StringBuilder();

    private final GuideDataRepository guideDataRepository;
    private final JdbcTemplate jdbcTemplate;
    private final PrepareSQL prepareSQL;


    @Autowired
    GuideDataRepositoryTest(GuideDataRepository guideDataRepository, JdbcTemplate jdbcTemplate, PrepareSQL prepareSQL) {
        this.guideDataRepository = guideDataRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.prepareSQL = prepareSQL;
    }

    @BeforeEach
    void setUp()  {
        jdbcTemplate.execute(dropTable);
        jdbcTemplate.execute(createTable);
        jdbcTemplate.execute(insertFirstRecord);
        nameTable = "person";
        fieldList.add("firstName");
        fieldList.add("lastname");
        dataList.add("mike");
        dataList.add("2");
        for (int i=0;i<fieldList.size();i++){
            fieldData.put(fieldList.get(i),dataList.get(i));
        }
        fieldGuide.put("firstName","varchar");
        fieldGuide.put("secondName","integer");
    }
    @AfterEach
    void closeParam(){
        jdbcTemplate.execute(dropTable);
    }

    @Test
    void addRecordTest() {
        String query=prepareSQL.addRecords(fieldList,dataList,nameTable);
        guideDataRepository.addRecord(query);
        query=prepareSQL.find(new HashMap<String,String>(),nameTable);
        List<Map<String,Object>> result=jdbcTemplate.query(query,new ColumnMapRowMapper());
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void updateByFieldsTest() {
        jdbcTemplate.execute("INSERT INTO person (firstName,secondname) VALUES('nike','5')");
        Map<String,String> updList = new HashMap<>();
        Map<String,String> searchList = new HashMap<>();
        updList.put("firstName","john");
        searchList.put("firstName","nike");
        searchList.put("secondname","5");
        String query=prepareSQL.updateData(searchList,updList,nameTable);
        Integer count=guideDataRepository.updateByFields(query);
        assertThat(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM person WHERE firstname='john';",Integer.class)).isGreaterThan(0);

    }

    @Test
    void findTest() {
        addRecords(2);
        result=guideDataRepository.find(prepareSQL.find(new HashMap<>(),nameTable));
        assertThat(result.size()).isPositive();
        result=guideDataRepository.find(prepareSQL.find(fieldData,nameTable));
        assertThat(result.size()).isPositive();

    }

    @Test
    void deleteRecordsTest() {
        addRecords(2);
        Integer result=guideDataRepository.deleteRecords(prepareSQL.deleteRecords(new HashMap<>(),nameTable));
        assertThat(result).isPositive();
        addRecords(2);
        assertThat(guideDataRepository.deleteRecords(prepareSQL.deleteRecords(new HashMap<>(),nameTable))).isEqualTo(2);
    }

    private void addRecords(Integer count){
        String query =prepareSQL.addRecords(fieldList,dataList,nameTable);
        for (int i = 0; i < count; i++) {
            guideDataRepository.addRecord(query);
        }

    }
}