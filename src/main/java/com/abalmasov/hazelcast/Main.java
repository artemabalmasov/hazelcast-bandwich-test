package com.abalmasov.hazelcast;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.apache.commons.cli.*;

import java.io.FileNotFoundException;

/**
 * @author Artem_Abalmasov@epam.com
 * @since 1.0.3
 *        Date: 11/28/13
 *        Time: 5:31 PM
 */
public class Main {
    public static final String DEFAULT_TYPE = NodeRole.Processor.name();
    public static final String CONFIG = "hazelcast.xml";

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("t", "type", true, "Type of action, default to  " + DEFAULT_TYPE);
        options.addOption("h", "help", false, "Print help message");
        try {
            CommandLineParser parser = new BasicParser();
            /**/
            CommandLine commandLine = parser.parse(options, args);
            /**/
            if (commandLine.hasOption("h")) {
                printHelp(options);
                return;
            }

            initHazelcast();
            final String type = commandLine.getOptionValue("t", DEFAULT_TYPE);
            NodeRole role = NodeRole.valueOf(type);
            role.action();

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return;
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar ***.jar -t <type> com.abalmasov.hazelcast.Main", options);
    }

    private static HazelcastInstance initHazelcast() throws FileNotFoundException {
        Config cfg = new ClasspathXmlConfig(CONFIG);
        cfg.setInstanceName(CONFIG);
        return Hazelcast.newHazelcastInstance(cfg);

    }
}

