package com.surefor.network.server.echoserver;

import com.surefor.utils.properties.ConfigNotFondException;
import com.surefor.utils.properties.ProperitesExistException;
import com.surefor.utils.properties.ProperitesNotFondException;
import com.surefor.utils.properties.Properties;
import org.apache.commons.configuration.ConfigurationException;

/**
 * Created by ethan on 20/04/15.
 */
public class ServerConfig {
    public static final String PROPERTIES = "server.properties" ;
    public static final String PORT = "server.port" ;

    private static Properties properties = Properties.instance() ;

    static {
        try {
            properties.loadProperties(PROPERTIES);
        } catch (ProperitesExistException e) {
            /* TODO Ignore */
        } catch (ConfigurationException e) {
            /* TODO Create a properites with default values */
        }
    }

    public static Integer getPort() throws ConfigurationException {
        if(properties.isLoaded(PROPERTIES)) {
            try {
                return Integer.valueOf(properties.get(PROPERTIES, PORT)) ;
            } catch (ProperitesNotFondException e) {
                throw new ConfigurationException() ;
            } catch (ConfigNotFondException e) {
                throw new ConfigurationException() ;
            }
        } else {
            throw new ConfigurationException() ;
        }
    }
}
