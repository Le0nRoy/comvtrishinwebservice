package com.vtrishin.webservice.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vtrishin.webservice.models.Advert;
import com.vtrishin.webservice.models.BaseModel;
import com.vtrishin.webservice.models.User;
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
import java.util.Arrays;
import java.util.List;

import static java.net.HttpURLConnection.*;

public class Servlet extends HttpServlet {

    /**
     *
     * @param request
     *  /all-customers
     *  /customer/id/adverts
     *  /customer/id/adId
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /// Just for future (or for lulz)
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/add.jsp");
//        requestDispatcher.forward(request, response);

        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        StringBuffer responseJsonString = new StringBuffer();

        // FIXME исправить под чтение параметров
        String requestURI = request.getRequestURI();
        String[] requestURIs = requestURI.split("/");

        // FIXME еще раз подумать над возрващаемыми ошибками
        // requestURIs[0] - empty
        // requestURIs[1] - name of servlet
        int uriNum = 2;
        if (requestURIs.length < uriNum + 1) {
            responseJsonString.append(getError(HTTP_NOT_FOUND, null));
        } else {
            switch (requestURIs[uriNum++]) {
                case "all-customers": {
                    logger.log(logger.INFO, "All users list was requested.");
                    try {
                        List<BaseModel> users = databaseUser.getAll(-1);
                        if (users == null) {
                            throw new Exception("Returned null as list of users..\n");
                        }
                        if (users.size() < 1) {
                            throw new Exception("No users in database.\n");
                        }
                        if (!(users.get(0) instanceof User)){
                            throw new Exception("Wrong type returned.\n");
                        }
                        responseJsonString.append(gson.toJson(users));
                    } catch (Exception e) {
                        responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                        break;
                    }
                    break;
                }
                case "customer": {
                    int userId = -1;
                    try {
                        userId = Integer.parseInt(requestURIs[uriNum++]);
                        if (userId < 0 ) {
                            throw new Exception(negativeIdException);
                        }
                    } catch (Exception e) {
                        responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                        break;
                    }
                    logger.log(logger.INFO, "User with id = " + userId + " was requested.");

                    User user = null;
                    try {
                        user = (User) databaseUser.find(userId, -1);
                    } catch (Exception e) {
                        responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                        break;
                    }
                    if (user == null) {
                        responseJsonString.append(getError(HTTP_BAD_REQUEST, null));
                        responseJsonString.append("Returned null as user.");
                        break;
                    }

                    String advertRequest = requestURIs[uriNum];
                    if (advertRequest.equals("adverts")) {
                        logger.log(logger.INFO, "All adverts list was requested.");
                        try {
                            responseJsonString.append(gson.toJson(databaseAdvert.getAll(userId)));
                        } catch (Exception e) {
                            responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                            break;
                        }
                    } else {
                        int advertId = -1;

                        try {
                            advertId = Integer.parseInt(advertRequest);
                            if (advertId < 0) {
                                throw new Exception(negativeIdException);
                            }
                        } catch (Exception e) {
                            responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                            break;
                        }
                        logger.log(logger.INFO, "Advert with id = " + advertId + " was requested.");

                        BaseModel advert = null;
                        try {
                            advert = databaseAdvert.find(advertId, userId);
                            if (advert != null) {
                                responseJsonString.append(gson.toJson(advert));
                            } else {
                                throw new Exception("Returned null as user.");
                            }
                        } catch (Exception e) {
                            responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                            break;
                        }
                    }
                    break;
                }
                default: {
                    responseJsonString.append(getError(HTTP_NOT_FOUND, null));
                    break;
                }
            }
        }

        if (!responseJsonString.toString().contains("[ERROR]")) {

            response.setContentType("application/json");
        }
        out.println(responseJsonString);
        out.flush();
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setCharacterEncoding("UTF-8");
        StringBuffer responseJsonString = new StringBuffer();

        String[] requestParams = request.getQueryString().split("&");
        logger.log(logger.INFO, "Params: " + request.getQueryString());

        String[] paramPair = requestParams[0].split("=");
        switch(paramPair[0]) {
            case "customer": {
                int id = -1;
                boolean isEntity = false;
                try {
                    id = Integer.parseInt(paramPair[1]);
                    if (id < 0) {
                        throw new Exception(negativeIdException);
                    }
                    paramPair = requestParams[4].split("=");
                    isEntity = Boolean.getBoolean(paramPair[1]);
                } catch (Exception e) {
                    responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                    break;
                }

                // FIXME add checks of parameter names
                paramPair = requestParams[1].split("=");
                String name = paramPair[1];
                paramPair = requestParams[2].split("=");
                String secondName = paramPair[1];
                paramPair = requestParams[3].split("=");
                String email = paramPair[1];
                User user = new User(id, name, secondName, email, isEntity);
                logger.log(logger.INFO, user.toString());

                try {
                    if (!databaseUser.add(user)) {
                        throw new Exception("Failed to add user!");
                    }
                    responseJsonString.append("\nUser with id = ").append(id).
                            append(" successfully added.");
                } catch (Exception e) {
                    responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                    responseJsonString.append("\nUser with id = ").append(id).
                            append(" already exists.");
                }
                break;
            }
            case "advert": {
                int id = -1;
                int userId = -1;
                try {
                    id = Integer.parseInt(paramPair[1]);
                    paramPair = requestParams[1].split("=");
                    userId = Integer.parseInt(paramPair[1]);
                    if (id < 0 || userId < 0) {
                        throw new Exception(negativeIdException);
                    }
                } catch (Exception e) {
                    responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                    break;
                }

                paramPair = requestParams[2].split("=");
                String header = paramPair[1];
                paramPair = requestParams[3].split("=");
                String category = paramPair[1];
                paramPair = requestParams[4].split("=");
                String phoneNumber = paramPair[1];
                Advert advert = new Advert(id, userId, header, category, phoneNumber);

                try {

                    databaseUser.add(advert);
                } catch (Exception e) {
                    responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                    responseJsonString.append("\nAdvert with id = ").append(id).
                            append(" already exists.");
                    responseJsonString.append("\nUser id = ").append(userId);
                }

                responseJsonString.append("\nAdvert with id = ").append(id).
                        append(" successfully added.");
                responseJsonString.append("\nUser id = ").append(userId);
                break;
            }
            default: {
                responseJsonString.append(getError(HTTP_BAD_REQUEST, null));
                responseJsonString.append("\nFirst parameter must be 'customer' or 'advert'.");
                break;
            }
        }

        out.println(responseJsonString);
        out.flush();
        out.close();
    }

    // FIXME put this mistake in all occurrences
    private String negativeIdException = "Id must be positive!";

    private ServletLogger logger = ServletLogger.getInstance();
    private Gson gson = new GsonBuilder().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).
            setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    private TableOperations databaseAdvert;
    private TableOperations databaseUser;
    {
        try {
            databaseAdvert = new DatabaseAdvert();
            databaseUser = new DatabaseUser();
        } catch (SQLException e) {
            logger.log(logger.WARNING, "Failed to connect to database!");
        }
    }

    private StringBuffer getError(int errorId, Exception e) {

        StringBuffer strError = new StringBuffer();
        switch (errorId) {
            case HTTP_BAD_REQUEST:
                strError.append("[ERROR] 400 wrong command passed.\n");
                break;
            case HTTP_NOT_FOUND:
                strError.append("[ERROR] 404 not found.\n");
                break;
            default:
                strError.append("[ERROR] some error.\n");
                break;
        }
        if (e != null) {

            strError.append("\n");
            strError.append(e.getMessage());
            strError.append("\n");
        }
        logger.log(logger.WARNING, strError.toString());
        return strError;
    }
}
