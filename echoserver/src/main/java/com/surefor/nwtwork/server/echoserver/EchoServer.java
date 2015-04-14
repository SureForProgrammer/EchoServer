package com.surefor.nwtwork.server.echoserver;

import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

/**
 * Created by ethan on 10/04/15.
 */
public class EchoServer {
    private Integer port = 12334 ;
    public static final String OPTION_PORT = "port" ;
    public final static Logger logger = Logger.getLogger(EchoServer.class);
    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(EchoServer.OPTION_PORT, true, "listening port number.");

        EchoServer echoServer = new EchoServer() ;

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        if(cmd.hasOption(EchoServer.OPTION_PORT)) {
            String port = cmd.getOptionValue(EchoServer.OPTION_PORT) ;
            logger.info(port);
        } else {
            HelpFormatter help = new HelpFormatter();
            help.printHelp("EchoServer", options);
        }
    }
}
