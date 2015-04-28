package com.surefor.network.server.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.cli.*;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;

/**
 * Created by ethan on 10/04/15.
 */
public class EchoServer {
    public static final Logger logger = Logger.getLogger(EchoServer.class);
    public static final Integer DEFAULT_ECHO_LISTENING_PORT = 7 ;
    private static final Integer UNDEFINED_PORT = -1 ;
    private Integer port = UNDEFINED_PORT ;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        assert(getPort() != UNDEFINED_PORT) ;

        EventLoopGroup group = new NioEventLoopGroup() ;
        try {
            ServerBootstrap b = new ServerBootstrap() ;
            b.group(group)
             .channel(NioServerSocketChannel.class)
             .localAddress(new InetSocketAddress((getPort())))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel socketChannel) throws Exception {
                     socketChannel.pipeline().addLast(new EchoServerHandler());
                 }
             }) ;

            ChannelFuture f = b.bind().sync() ;
            System.out.println(EchoServer.class.getName() + " started and listen on " + f.channel().localAddress()) ;
            logger.debug(EchoServer.class.getName() + " started and listen on " + f.channel().localAddress());

            f.channel().closeFuture().sync() ;
        } finally {
            group.shutdownGracefully().sync() ;
        }
    }

    public static final String OPTION_PORT = "port" ;

    public static void main(String[] args) throws ParseException, InterruptedException {
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
        echoServer.run();
    }
}
