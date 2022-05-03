package com.example.tcpecho.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TcpServerDecoder extends MessageToMessageDecoder {

    private String DATA_END = "end";


    @Override
    protected void decode(ChannelHandlerContext ctx, Object o, List list) throws Exception {
        System.out.println("o==>" + o);
        ByteBuf
        if(o.toString() == DATA_END) {

            list.add(o);
        } else {
            return;
        }
    }
}
