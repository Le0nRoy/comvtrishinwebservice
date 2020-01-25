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
import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;

import static java.net.HttpURLConnection.*;

/**
 * JSON type
 * {
 * "method": "methodName",
 * "param": "paramValue"
 * }
 * TODO create enum for this
 * methodName is oe of: remove, add, getAll, find
 */
public class Servlet extends HttpServlet {

    final String advertQuery = "advert";
    final String userQuery = "user";
    final String objNameParamQuery = "objName";
    final String idParamQuery = "id";
    final String userIdParamQuery = "userId";
    /**
     * @param request  /all-customers
     *                 /customer/id/adverts
     *                 /customer/id/adId
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
        String[] requestParams = request.getQueryString().split("&");
        String[] paramPair = requestParams[0].split("=");
        Map<String, String> query_pairs = new LinkedHashMap<>();
        for (String pair : requestParams) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                    URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }

        String objNameString = query_pairs.get(objNameParamQuery);
        String userIdString = null;
        String idString = null;
        if (objNameString.equals(advertQuery) &&
                query_pairs.containsKey(userIdParamQuery)) {
            userIdString = query_pairs.get(userIdParamQuery);
        }
        if (query_pairs.containsKey(idParamQuery)) {
            idString = query_pairs.get(idParamQuery);
        }

        switch (objNameString) {
            case userQuery: {
                if (idString == null) {
                    logger.log(logger.INFO, "All users list was requested.");
                    try {
                        List<BaseModel> users = databaseUser.getAll(-1);
                        if (users == null) {
                            throw new Exception("Returned null as list of users.\n");
                        }
                        if (users.size() < 1) {
                            throw new Exception("No users in database.\n");
                        }
                        if (!(users.get(0) instanceof User)) {
                            throw new Exception("Wrong type returned.\n");
                        }
                        responseJsonString.append(gson.toJson(users));
                    } catch (Exception e) {
                        responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                        if (e instanceof SQLException) {
                            logger.log(logger.WARNING,
                                    "All users: got SQLException: " + e.getMessage());
                        }
                        break;
                    }
                } else {
                    int id = -1;
                    try {
                        id = Integer.parseInt(idString);
                        if (id < 0) {
                            throw new Exception(negativeIdException);
                        }
                    } catch (Exception e) {
                        responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                        break;
                    }
                    logger.log(logger.INFO, "User with id = " + id + " was requested.");

                    User user = null;
                    try {
                        user = (User) databaseUser.find(id, -1);
                    } catch (Exception e) {
                        responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                        break;
                    }
                    if (user == null) {
                        responseJsonString.append(getError(HTTP_BAD_REQUEST, null));
                        responseJsonString.append("Returned null as user.");
                    } else {
                        responseJsonString.append(gson.toJson(user));
                    }
                    break;
                }
                break;
            }
            case advertQuery: {
                int userId = -1;
                try {
                    userId = Integer.parseInt(userIdString);
                    if (userId < 0) {
                        throw new Exception(negativeIdException);
                    }
                } catch (Exception e) {
                    responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                    break;
                }

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
                }

                if (idString == null) {
                    logger.log(logger.INFO, "All adverts list for user with id = " +
                            userId + " was requested.");
                    try {
                        responseJsonString.append(gson.toJson(databaseAdvert.getAll(userId)));
                    } catch (Exception e) {
                        responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                        break;
                    }
                } else {
                    int advertId = -1;
                    try {
                        advertId = Integer.parseInt(idString);
                        if (advertId < 0) {
                            throw new Exception(negativeIdException);
                        }
                    } catch (Exception e) {
                        responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                        break;
                    }
                    logger.log(logger.INFO, "Advert with id = " + advertId + " for user with id = " +
                                    userId + " was requested.");

                    Advert advert = null;
                    try {
                        advert = (Advert) databaseAdvert.find(advertId, userId);
                        if (advert != null) {
                            responseJsonString.append(gson.toJson(advert));
                        } else {
                            throw new Exception("Returned null as advert.");
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
        // FIXME change for JSON reading
        String[] requestParams = request.getQueryString().split("&");

//        ////////
//        StringBuffer jb = new StringBuffer();
//        String line = null;
//        try {
//            BufferedReader reader = request.getReader();
//            while ((line = reader.readLine()) != null) {
//                jb.append(line);
//            }
//        } catch (Exception e) { /*report an error*/ }
//
//        try {
//            JSONObject jsonObject =  HTTP.toJSONObject(jb.toString());
//        } catch (JSONException e) {
//            // crash and burn
//            throw new IOException("Error parsing JSON request string");
//        }
//
//        User usr;
//        Gson gson = new Gson();
//        usr = gson.fromJson(jb.toString(), User.class);
//        logger.log(logger.INFO, "User from JSON " + usr);
//        /////////////
        String[] paramPair = requestParams[0].split("=");
        switch (paramPair[0]) {
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
                // FIXME make it in cycle
                paramPair = requestParams[1].split("=");
                String name = paramPair[1];
                paramPair = requestParams[2].split("=");
                String secondName = paramPair[1];
                paramPair = requestParams[3].split("=");
                String email = paramPair[1];
                User user = new User(id, name, secondName, email, isEntity);

                try {
                    databaseUser.add(user);
                    responseJsonString.append("\nUser with id = ").append(id).
                            append(" successfully added.");
                } catch (Exception e) {
                    responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                    responseJsonString.append("\nUser with id = ").append(id).
                            append(" already exists.").append(e.getMessage());
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

                    databaseAdvert.add(advert);
                } catch (Exception e) {
                    responseJsonString.append(getError(HTTP_BAD_REQUEST, e));
                    responseJsonString.append("\nAdvert with id = ").append(id).
                            append(" failed to be added.");
                    responseJsonString.append("\nUser id = ").append(userId);
                }
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
