package jam.example.sbtask.repositories;

import jam.example.sbtask.config.ConstantSQLTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
class GuideStructureRepositoryTest extends ConstantSQLTest {

    private final Map<String,String> guideFields =new HashMap<>();

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    GuideStructureRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void initialParam (){

    }
    @AfterEach
    void deleteParam(){
        jdbcTemplate.execute(dropTable);
    }

    //todo добавить тесты для репозитория
}
