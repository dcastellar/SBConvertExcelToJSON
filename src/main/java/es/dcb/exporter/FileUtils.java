package es.dcb.exporter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import es.dcb.config.ConfigProperties;
import es.dcb.model.DataDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@AllArgsConstructor
@Component
public class FileUtils {

    private static final String DATA_PATH = "data.path";

    private ExcelParser excelParser;

    public void saveDataToFile(ObjectWriter ow, DataDTO dataDTO) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.dir")
             + ConfigProperties.instance().properties().getProperty(DATA_PATH)
             + dataDTO.getFriendlyName() + ".json")) {

             String json = ow.writeValueAsString(dataDTO);
             fileOutputStream.write(json.getBytes());

             fileOutputStream.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFilePath(String name) {
        try {
            Path path = Paths.get(System.getProperty("user.dir")
            + ConfigProperties.instance().properties().getProperty(name));
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
