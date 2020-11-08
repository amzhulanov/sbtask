package jam.example.sbtask.controllers;

import jam.example.sbtask.utils.Converter;
import jam.example.sbtask.services.GuideService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST-контроллер для работы со структурой справочника
 *
 * @author JAM
 */
@Controller
@RequestMapping("/data")
@Slf4j
public class GuideDataController {
    private Map<String, String> fieldsGuide;

    @Autowired
    private Converter converter;

    @Autowired
    private GuideService guideService;

//    public GuideDataController(Converter converter, GuideService guideService) {
//        this.converter = converter;
//        this.guideService = guideService;
//    }


    /**
     * Метод для добавления значений в справочник
     *
     * @param name Имя таблицы
     * @param json Текст запроса
     * @return Возвращает кол-во добавленых строк
     */
    @PutMapping("/{name}")
    public ResponseEntity<String> addDataGuide(@PathVariable String name, @RequestBody String json) {
        fieldsGuide = converter.convertJsonToMap(json);
        guideService.addRecords(fieldsGuide, name);
        return new ResponseEntity<>("Data added", HttpStatus.OK);
    }

    /**
     * Метод для изменений значений в справочнике
     *
     * @param name Имя таблицы
     * @param json Текст запроса
     * @return Возвращает кол-во измененных записей
     * @throws ParseException Исключение
     */
    @PostMapping("/{name}")
    public ResponseEntity<String> updateDataGuide(@PathVariable String name, @RequestBody String json) throws ParseException {
        Integer result = guideService.updateRecords(converter.convertJson(json), name);
        return new ResponseEntity<>("Update " + result + " records", HttpStatus.OK);
    }

    /**
     * Метод для поиска значений в справочнике. Если условия для поиска не указаны выгружается весь справочник
     *
     * @param name Имя справочника
     * @param json Текст запроса (опционально)
     * @return Список строк
     */
    @GetMapping("/{name}")
    public ResponseEntity<String> find(@PathVariable String name, @RequestBody String json) {
        fieldsGuide = converter.convertJsonToMap(json);
        log.info(json);
        return new ResponseEntity<>(converter.convertListToJson(guideService.find(fieldsGuide, name)), HttpStatus.OK);

    }

    /**
     * Метод для удаления записей из справочника
     *
     * @param name имя справочника
     * @param json список условий дял удаления
     * @return кол-во удалённых записей
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteGuide(@PathVariable String name, @RequestBody String json) {
        fieldsGuide = converter.convertJsonToMap(json);
        Integer result = guideService.deleteRecords(fieldsGuide, name);
        return new ResponseEntity<>("Delete " + result + " records", HttpStatus.OK);
    }

}
