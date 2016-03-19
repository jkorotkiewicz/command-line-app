package my.company;

import lombok.extern.slf4j.Slf4j;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Slf4j
public class App {

    @Option(name = "-in", usage = "path to load files")
    private String inPath = "IN";

    @Option(name = "-out", usage = "path to save files")
    private String outPath = "OUT";

    @Option(name = "-c", usage = "config file")
    private String configFile = "app.properties";

    public static void main(String[] args) {
        App app = new App();
        CmdLineParser parser = new CmdLineParser(app);
        try {
            parser.parseArgument(args);
            app.start();
        } catch (CmdLineException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Properties properties = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(configFile);;
        try {
            inputStream = Files.newInputStream(Paths.get(configFile));
        } catch (IOException e) {
            log.info("no config file specified, using the default one");
        } finally {
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.debug(properties.toString());
        log.debug(properties.get("db.version").toString());
        log.debug(inPath);
        log.debug(outPath);
//        log.debug(this.getClass().getResource().toString());
//        log.debug(this.getClass().getClassLoader().toString());
    }
}
