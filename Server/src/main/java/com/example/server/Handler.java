package com.example.server;


import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpData;
import io.netty.handler.codec.http.multipart.HttpPostMultipartRequestDecoder;
import io.netty.util.AsciiString;


public class Handler extends SimpleChannelInboundHandler<FullHttpMessage> {

    private static final AsciiString CONTENT_TYPE = new AsciiString("CONTENT_TYPE");
    private static final AsciiString CONTENT_LENGTH = new AsciiString("CONTENT_LENGTH");
    private static final AsciiString CONNECTION = new AsciiString("Connection");
    private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpMessage msg) throws Exception {

        if( msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
            if(HttpHeaders.is100ContinueExpected(req)) {
                ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
            }

            boolean keepAlive = HttpHeaders.isKeepAlive(req);

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, msg.content().copy());

          /*  System.out.println("type===> " + type);*/
            if(type.getHttpDataType().equals("text/plain")) {
                response.headers().set(CONTENT_TYPE, "text/plain");
            } else {
                response.headers().set(CONTENT_TYPE, "application/json");
            }
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());

            if(!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, KEEP_ALIVE);
                ctx.write(response);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
        ctx.close();
    }

}
