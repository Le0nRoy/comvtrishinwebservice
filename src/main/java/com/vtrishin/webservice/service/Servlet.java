package com.vtrishin.webservice.service;

import com.vtrishin.webservice.models.BaseModel;
import com.vtrishin.webservice.repositories.DatabaseAdvert;
import com.vtrishin.webservice.repositories.DatabaseUser;
import com.vtrishin.webservice.repositories.TableOperations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import static java.net.HttpURLConnection.*;

public class Servlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /// Just for future (or for lulz)
//        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/add.jsp");
//        requestDispatcher.forward(req, resp);

        PrintWriter out = resp.getWriter();
        String requestURI = req.getRequestURI();
        String[] requestURIs = requestURI.split("/");

        // requestURIs[0] - empty
        // requestURIs[1] - name of app
        // requestURIs[2] - name of servlet
        int uriNum = 3;
        if (requestURIs.length < uriNum + 1) {
            out.println(getError(HTTP_NOT_FOUND));
        } else {
            switch (requestURIs[uriNum++]) {
                case "all-customers": {
                    out.println("TODO Add returning off all customers list");
                    List<BaseModel> users;
                    try {
                        users = databaseUser.getAll(-1);
                    } catch (SQLException e) {
                        getError(HTTP_BAD_REQUEST);
                    }
                    break;
                }
                case "customer": {
                    int userId = 0;
                    try {
                        userId = Integer.parseInt(requestURIs[uriNum++]);
                    } catch (NumberFormatException e) {
                        out.println(getError(HTTP_BAD_REQUEST));
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        out.println(getError(HTTP_NOT_FOUND));
                        break;
                    }

                    out.println("You requested user with id " + userId);
                    BaseModel user = null;
                    try {
                        user = databaseUser.find(userId, -1);
                    } catch (SQLException e) {
                        getError(HTTP_BAD_REQUEST);
                    }

                    if (user != null) {
                        String advertRequest = requestURIs[uriNum];
                        switch (advertRequest) {
                            case "adverts": {
                                List<BaseModel> adverts;
                                try {
                                    adverts = databaseAdvert.getAll(userId);
                                } catch (SQLException e) {
                                    getError(HTTP_BAD_REQUEST);
                                }

                                out.println("TODO return all adverts for user");
                                break;
                            }
                            default: {
                                int advertId = 0;
                                try {
                                    advertId = Integer.parseInt(advertRequest);
                                } catch (NumberFormatException e) {
                                    out.println(getError(HTTP_BAD_REQUEST));
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    out.println(getError(HTTP_NOT_FOUND));
                                    break;
                                }

                                BaseModel advert = null;
                                try {
                                    advert = databaseAdvert.find(advertId, userId);
                                } catch (SQLException e) {
                                    out.println(getError(HTTP_BAD_REQUEST));
                                }

                                if (advert != null) {
                                    out.println("TODO return advert with id" + advertId);
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
                default: {
                    out.println(getError(HTTP_NOT_FOUND));
                    break;
                }
            }
        }

        out.println("\n" + requestURI);
        out.close();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        out.println(this.getGreeting() + "Here we have post method!");
        out.close();
    }

    private TableOperations databaseAdvert;
    private TableOperations databaseUser;
    {
        try {
            databaseAdvert = new DatabaseAdvert();
            databaseUser = new DatabaseUser();
        } catch (SQLException e) {

        }
    }

    private String getGreeting() {

        return "Hello Tomcat! Now I'm in game!";
    }

    private String getError(int errorId) {

        String strError;
        switch (errorId) {
            case HTTP_BAD_REQUEST:
                strError = "TODO 400 wrong command passed";
                break;
            case HTTP_NOT_FOUND:
                strError = "TODO 404 not found";
                break;
            default:
                strError = "TODO some error";
                break;
        }
        return strError;
    }

}
