package es.dcb.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.asm.Opcodes;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

@Slf4j
public class ConfigProperties {
    private static final String PROPERTY_fILE = "config.properties";

    private Properties properties = null;
    private static ConfigProperties configProperties = null;

    public static ConfigProperties instance() {
        if (configProperties == null) {
            configProperties = new ConfigProperties();
        }
        return configProperties;
    }

    public Properties properties() {
        if(this.properties == null) {
            loadProperties();
        }
        return this.properties;
    }

    private void loadProperties() {
        Optional<URL> configFileURL = configFileURL();
        if (!configFileURL.isPresent()) {
            log.error("Config properties could not be loaded");
            this.properties = new Properties();
            return;
        }
        try (InputStream input = configFileURL.get().openStream()) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            log.error("Config properties could not be loaded", e);
            this.properties = new Properties();
        }
    }

    private Optional<URL> configFileURL() {
        return localConfigFilePath().isPresent()
                ? localConfigFilePath()
                : classPathConfigFilePath();
    }

    private Optional<URL> localConfigFilePath() {
        // look for the file in local path
        String localConfigFile = "./" + PROPERTY_fILE;
        log.debug("localConfigFile: {}", localConfigFile);
        if (new File(localConfigFile).exists()) {
            try {
                return Optional.of(new File(localConfigFile).toURI().toURL());
            } catch (MalformedURLException e) {
                log.error("Error getting localConfigFile");
                return Optional.empty();
            }
        }
        log.warn("Config property file not found in local path");
        return Optional.empty();
    }

    private Optional<URL> classPathConfigFilePath() {
        // look for the file in class path
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(PROPERTY_fILE);
        if (resource == null) {
            log.error("Error getting config property file in class path");
            return Optional.empty();
        }
        log.debug("localConfigFile: {}", resource.getFile());
        return Optional.of(resource);
    }
}
