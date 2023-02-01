package com.gzl0ng.netty.protocoltcp;

/**
 * @author:郭正龙
 * @data:2023/1/7
 */

//协议包
public class MessageProtocol {
    private int len;//关键
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
