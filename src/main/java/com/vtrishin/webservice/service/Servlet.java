package com.vtrishin.webservice.service;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

import static java.net.HttpURLConnection.HTTP_OK;

public class Servlet extends HttpServlet {

    public String getGreeting() {

        return "Hello Tomcat! Now I'm in game!";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /// Just for future (or for lulz)
//        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/add.jsp");
//        requestDispatcher.forward(req, resp);


        PrintWriter out = resp.getWriter();
        out.println(this.getGreeting());

        String requestURI = req.getRequestURI();
        int index = requestURI.indexOf('/');
        index = requestURI.indexOf('/', index);
        out.println(index);
        requestURI = requestURI.substring(index);
//        requestURI = req.getRemoteUser();

        out.println(requestURI);
        out.close();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        out.println(this.getGreeting() + "Here we have post method!");
        out.close();
    }
}
