package jam.example.sbtask.repositories;

import jam.example.sbtask.config.ConstantDataRepositoryTest;
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
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GuideDataRepositoryTest extends ConstantDataRepositoryTest {

    private final List<String> fieldList = new ArrayList<>();
    private final List<String> dataList = new ArrayList<>();
    private final Map<String,String> fieldData=new HashMap<>();

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

    @BeforeAll
    void initParam(){
        jdbcTemplate.execute(dropTable);
        jdbcTemplate.execute(createTable);
        fieldList.add(FIRST_FIELD);
        fieldList.add(SECOND_FIELD);
        dataList.add(FIRST_FIELD_OLD_VALUE);
        dataList.add(SECOND_FIELD_VALUE);
    }

    @Test
    @Order(1)
    void addRecordTest() {
        String query=prepareSQL.addRecords(fieldList,dataList,NAME_TABLE);
        guideDataRepository.addRecord(query);
        query=prepareSQL.find(new HashMap<String,String>(),NAME_TABLE);
        List<Map<String,Object>> result=jdbcTemplate.query(query,new ColumnMapRowMapper());
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @Order(2)
    void updateByFieldsTest() {
        Map<String,String> updList = new HashMap<>();
        Map<String,String> searchList = new HashMap<>();
        updList.put(FIRST_FIELD, FIRST_FIELD_NEW_VALUE);
        searchList.put(SECOND_FIELD, SECOND_FIELD_VALUE);
        String query=prepareSQL.updateData(searchList,updList,NAME_TABLE);
        Integer count=guideDataRepository.updateByFields(query);
        assertThat(jdbcTemplate.queryForObject(selectCount.concat(FIRST_FIELD).concat("=").concat("'").concat(FIRST_FIELD_NEW_VALUE).concat("'"),Integer.class)).isPositive();
    }

    @Test
    @Order(3)
    void findTest() {
        List<Map<String, Object>> result = guideDataRepository.find(prepareSQL.find(new HashMap<>(), NAME_TABLE));
        assertThat(result.size()).isPositive();
        fieldData.put(FIRST_FIELD,FIRST_FIELD_NEW_VALUE);
        fieldData.put(SECOND_FIELD,SECOND_FIELD_VALUE);
        result =guideDataRepository.find(prepareSQL.find(fieldData,NAME_TABLE));
        assertThat(result.size()).isPositive();
    }

    @Test
    @Order(4)
    void deleteRecordsTest() {
        Integer result=guideDataRepository.deleteRecords(prepareSQL.deleteRecords(new HashMap<>(),NAME_TABLE));
        assertThat(result).isPositive();
    }

}