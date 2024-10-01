package org.Spring.Uploading;

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
import java.net.InetAddress;
import java.time.LocalDate;

public class ServerUploader {
    //need to properly set well to your server endpoint
    private final String URI = "http://localhost:8080/upload";
    public ServerUploader(){
        final String pathName = "log" + getSystemName() + " " + LocalDate.now() + ".txt";
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Program is being forced to stop.");
            File file = new File(pathName);
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost uploadFile = new HttpPost(URI);
                HttpEntity multipartEntity = MultipartEntityBuilder.create()
                        .addBinaryBody("file", file)  // "file" is the form field name expected by the server
                        .build();
                uploadFile.setEntity(multipartEntity);
                // Execute the request
                try (CloseableHttpResponse response = httpClient.execute(uploadFile)) {
                    System.out.println("Response code: " + response.getCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
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
