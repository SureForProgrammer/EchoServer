package com.surefor.nwtwork.server.echoserver;

import com.surefor.utils.properties.ConfigNotFondException;
import com.surefor.utils.properties.ProperitesExistException;
import com.surefor.utils.properties.ProperitesNotFondException;
import com.surefor.utils.properties.Properties;
import org.apache.commons.cli.*;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;

/**
 * Created by ethan on 10/04/15.
 */
public class EchoServer {
    private Integer port = 12334 ;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public static final String OPTION_PORT = "port" ;
    public static final Logger logger = Logger.getLogger(EchoServer.class);
    public static final Integer DEFAULT_ECHO_LISTENING_PORT = 7 ;

    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(EchoServer.OPTION_PORT, true, "listening port number.");

        EchoServer echoServer = new EchoServer() ;

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        if(cmd.hasOption(EchoServer.OPTION_PORT)) {
            echoServer.setPort(Integer.valueOf(cmd.getOptionValue(EchoServer.OPTION_PORT))) ;
        } else {
            try {
                echoServer.setPort(ServerConfig.getPort()) ;
            } catch (ConfigurationException e) {
                echoServer.setPort(DEFAULT_ECHO_LISTENING_PORT) ;
            }
        }

        logger.debug(echoServer.getPort());
    }
}
