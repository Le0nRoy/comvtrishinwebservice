package com.vtrishin.webservice;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.*;

class getHttpHelloTest {
    @Test
    void shouldReturnHelloStringOnHttpRequestToServer() {
        try {
            String testedAddress = "http://localhost:9000/hello";
            String actual = makeRequest(testedAddress);
            String expected = "Hello!";
            assertEquals(expected, actual);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    private Server server = createServer();

    Server createServer() {
        Server s = new Server();
        s.startServer();
        return s;
    }

    private String makeRequest(String address) {
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
//            con.disconnect();
        }
        return null;
    }
}
