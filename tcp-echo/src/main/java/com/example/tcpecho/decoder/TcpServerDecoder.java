package com.example.tcpecho.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TcpServerDecoder extends ByteToMessageDecoder {

    private int DATA_LENGTH = 10;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() < DATA_LENGTH) {
            return;
        }

        list.add(byteBuf.readBytes(DATA_LENGTH));
    }
}
