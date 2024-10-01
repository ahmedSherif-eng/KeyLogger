package org.Spring;

import org.Spring.Sniffing.SniffingService;

/**
 * aaa
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        SniffingService sniffingService = new SniffingService();
        sniffingService.startSniffing();
    }
}
