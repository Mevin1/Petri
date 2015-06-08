package me.b3nw.dev;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import me.b3nw.dev.Handlers.SetupHandler;

@Slf4j
public class Petri {

    public static AttributeKey<Boolean> channelHasPlayer;

    public static void main(String[] args) {
        log.info("Starting Petri!");

        EventLoopGroup bossGrp = new EpollEventLoopGroup(1);
        EventLoopGroup workGrp = new EpollEventLoopGroup();

        channelHasPlayer = AttributeKey.newInstance("ChannelHasPlayer"); //New key to track if currently playing

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGrp, workGrp)
                    .channel(EpollServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<EpollSocketChannel>() {
                        @Override
                        protected void initChannel(EpollSocketChannel ch) throws Exception {
                            log.debug("Connection from [" + ch.remoteAddress() + "]");

                            ch.pipeline().addLast(
                                    new HttpServerCodec(),
                                    new HttpObjectAggregator(9999),
                                    new WebSocketServerProtocolHandler("/"),
                                    new SetupHandler());

                            ch.attr(channelHasPlayer).set(false); //No one is playing on this channel quite yet ;)
                        }
                    });

            ChannelFuture future = bootstrap.bind(9999).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGrp.shutdownGracefully();
            workGrp.shutdownGracefully();
        }
    }
}
