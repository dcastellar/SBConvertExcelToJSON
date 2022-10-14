package es.dcb;

import es.dcb.model.DataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@SpringBootApplication
public class ConfigGeneratorApp implements CommandLineRunner {

    @Autowired
    private JsonGenerator jsonGenerator;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ConfigGeneratorApp.class);
        //disable webserver
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting config....");

        List<DataDTO> dataDtos = jsonGenerator.generateData(System.getProperty("user.dir") + "/data.xlsx");

        log.info("Configutation finished!");
    }
}