package jam.example.sbtask.repositories;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
class GuideStructureRepositoryTest {

    private final Map<String,String> guideFields =new HashMap<>();
    private String nameTable;


    private JdbcTemplate jdbcTemplate;

    private GuideStructureRepository guideStructureRepository;


    @Value("${guide.structure.CHECK_GUIDE}")
    private String CHECK_GUIDE;

    @Autowired
    GuideStructureRepositoryTest(JdbcTemplate jdbcTemplate, GuideStructureRepository guideStructureRepository) {
        this.jdbcTemplate = jdbcTemplate;
        //  this.jdbcTemplate = jdbcTemplate;
        this.guideStructureRepository = guideStructureRepository;
    }
    @BeforeEach
    void initialParam (){
        guideFields.put("name","varchar");
        guideFields.put("lastname","varchar");
        nameTable="guide";
    }
    @AfterEach
    void deleteParam(){
        jdbcTemplate.execute("DROP TABLE "+nameTable);
    }

    @Test
    void createGuideTest() {
      // guideStructureRepository.createGuide(guideFields,nameTable);
       //assertThat(jdbcTemplate.queryForObject(String.valueOf(CHECK_GUIDE + nameTable + "';"), String.class)).isEqualTo(nameTable);

    }

//    @Test
//    void editTypeFields() {
//    }
//
//    @Test
//    void editNameFields() {
//    }
//
//    @Test
//    void checkGuide() {
//    }
}


//        Mockito.when(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM EMPLOYEE", Integer.class))
//                .thenReturn(4);
//        Mockito.when(jdbcTemplate.queryForObject(String.valueOf(CHECK_GUIDE + nameTable + "';"), String.class))
//                .thenReturn(nameTable);