package com.gzl0ng.nio.other.chat.client;

import java.io.IOException;

/**
 * @author:郭正龙
 * @data:2022/12/15
 */
public class BClient {
    public static void main(String[] args) {
        try {
            new ChatClient().startClient("mary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
