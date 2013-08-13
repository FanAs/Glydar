package org.glydar.glydar;

import io.netty.bootstrap.ChannelFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.glydar.api.config.Configuration;
import org.glydar.api.plugin.PluginLoader;
import org.glydar.glydar.netty.CubeWorldServerInitializer;

import java.io.File;
import java.net.InetSocketAddress;

public class Glydar {

    private static GServer s; //TODO command line arg for debug
    private static Thread serverThread = new Thread(s);
    private static final PluginLoader loader = new PluginLoader();
    private static ChannelFuture chan;
    private static ServerBootstrap serverBootstrap;
    private static boolean serverDebug = false;

    public static Configuration serverConfiguration;

    public static boolean ignorePacketErrors = false;

    private static final String CONFIGURATION_FILE = "server.cfg";
    private static final int DEFAULT_PORT = 12345;
    public static final int DEFAULT_SEED = 69;

    public static int port;

    public static void main(String[] args) {
        if(args.length > 0) {
            for(String s : args)
            {
                if(s.equalsIgnoreCase("-ignorepacketerrors"))
                    ignorePacketErrors = true;
                else if(s.equalsIgnoreCase("-debug"))
                    serverDebug = true;
            }
        }

        loadConfiguration();

        s = new GServer(serverDebug);
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.childHandler(new CubeWorldServerInitializer());
        serverBootstrap.option(ChannelOption.TCP_NODELAY, true);
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        //serverBootstrap.setOption("child.keepAlive", true);
        serverBootstrap.group(new NioEventLoopGroup());
        serverBootstrap.channelFactory(new ChannelFactory<ServerChannel>() {
            @Override
            public ServerChannel newChannel() {
                return new NioServerSocketChannel();
            }
        });

        try {
            loader.loadPlugins();
        } catch (Exception e) {
            e.printStackTrace();
        }

        chan = serverBootstrap.bind(new InetSocketAddress(port));

        s.getLogger().info("Server ready on port " + port);
        serverThread.start();
    }

    public static GServer getServer() {
        return s;
    }

    private static void loadConfiguration() {
        serverConfiguration = new Configuration();
        File configurationFile = new File(CONFIGURATION_FILE);

        boolean save = false;
        try {
            if (!serverConfiguration.load(configurationFile)) {
                serverConfiguration.set("port", DEFAULT_PORT).set("seed", DEFAULT_SEED);
                save = true;
            }
            if (!serverConfiguration.contains("port")) {
                serverConfiguration.set("port", DEFAULT_PORT);
                save = true;
            }
            if (!serverConfiguration.contains("seed")) {
                serverConfiguration.set("seed", DEFAULT_SEED);
                save = true;
            }
        } finally {
            if (save) {
                serverConfiguration.save(configurationFile);
            }
        }


        try {
            port = Integer.parseInt(serverConfiguration.getString("port"));
        } catch (Exception e) {
            port = DEFAULT_PORT;
        }
    }

    public static void shutdown() {
        getServer().shutdown();
        serverThread.interrupt();
        chan.channel().close();
        serverBootstrap.childGroup().shutdownGracefully();
        serverBootstrap.group().shutdownGracefully();
    }

}
