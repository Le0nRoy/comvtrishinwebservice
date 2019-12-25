package com.vtrishin.webservice;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

import static java.net.HttpURLConnection.HTTP_OK;

public class Server {

    Server() {
        context.setHandler(this::handleHttpRequest);
    }

    void startServer() {
        server.start();
    }

    // TODO create database

    private Logger logger = Logger.getLogger(Server.class.getName());

    private String hostAddress = "localhost";
    private String contextAddress = "/";
    private int port = 9000;
    private int backlog = 0;

    void handleHttpRequest(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestURI().toString()) {
            case "/hello":
                String response = "Hello!";
                OutputStream os = exchange.getResponseBody();
                exchange.sendResponseHeaders(HTTP_OK, response.getBytes().length);
                os.write(response.getBytes());
                os.close();
                break;
            case "":
                break;
            default:
                break;
        }
    }

    // FIXME: Logger
    private HttpServer server = createServer();
    private HttpServer createServer() {
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(hostAddress, port);
            return HttpServer.create(socketAddress, backlog);
        } catch (Exception e) {
            logger.info("ERROR: Unsuccesfull creation of HTTP server!\n" + e.getMessage());
            System.exit(-127);
        }
        return null;
    }
    private HttpContext context = server.createContext(contextAddress);


    Connection databaseConnection = createDatabaseConnection();
    private Connection createDatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager
                    .getConnection("jdbc:postgresql:/E:/Java_Workspace/comvtrishinwebservicedb/DataBase/",
                            "postgres", "123");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }

}
