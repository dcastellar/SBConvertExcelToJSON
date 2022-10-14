package es.dcb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import es.dcb.exporter.ExcelParser;
import es.dcb.exporter.FileUtils;
import es.dcb.model.DataDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class JsonGenerator {

    private ExcelParser excelParser;
    private FileUtils fileUtils;

    private static final String DATA_PATH = "data.path";

    public List<DataDTO> generateData(String excelFilePath) {
        log.info("Generate data from excel file {}", excelFilePath);
        excelParser.readExcel(excelFilePath);
        List<DataDTO> data = excelParser.getdata();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        fileUtils.getFilePath(DATA_PATH);
        data.forEach(tempData -> {
            fileUtils.saveDataToFile(ow, tempData);
        });
        return data;
    }
}
