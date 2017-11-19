package com.wuhulala.netty.time.v2;

import com.wuhulala.netty.time.Constants;
import com.wuhulala.util.SystemUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wuhulala
 * @version 1.0
 * @date 2017/11/19
 * @description 作甚的
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(TimeClientHandler.class);

    private byte[] req;

    private int count;

    public TimeClientHandler(){

        req = (Constants.DEFAULT_CLIENT_REQ + SystemUtils.lineSeparator()).getBytes();
        count = 0;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Unexpected exception from downstream : " + cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf firstMessage = null;
        for (int i = 0; i < 100; i++) {
            firstMessage = Unpooled.buffer(req.length);
            firstMessage.writeBytes(req);
            ctx.writeAndFlush(firstMessage);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String body = (String) msg;
        logger.info("now is : " + body + "," + "the counter is " + ++count);

    }

}
