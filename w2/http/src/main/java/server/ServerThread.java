package server;

import framework.controller.DIEngine;
import framework.controller.HttpRequest;
import framework.response.JsonResponse;
import framework.response.Response;
import framework.request.enums.HttpMethod;
import framework.request.Header;
import framework.request.Helper;
import framework.request.Request;
import framework.request.exceptions.RequestNotValidException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerThread implements Runnable{

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Map<HttpRequest, Method> httpMap;
    private Map<Class<?>, Object> controllers;

    public ServerThread(Socket socket){
        this.socket = socket;
        this.httpMap = DIEngine.getHttpMap();
        this.controllers = DIEngine.getControllers();

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {

            Request request = this.generateRequest();
            if(request == null) {
                in.close();
                out.close();
                socket.close();
                return;
            }

            // Response example
            HttpRequest req = new HttpRequest(request.getLocation(), request.getMethod());

            if(!httpMap.containsKey(req)){
                throw new RequestNotValidException("");
            }
            Method method = httpMap.get(req);
            Map<String, Object> responseMap = new HashMap<>();
            Response response = new JsonResponse(responseMap);
            responseMap.put("response", method.invoke(controllers.get(method.getDeclaringClass())));
            out.println(response.render());

            in.close();
            out.close();
            socket.close();

        } catch (IOException | RequestNotValidException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Request generateRequest() throws IOException, RequestNotValidException {
        String command = in.readLine();
        if(command == null) {
            return null;
        }

        String[] actionRow = command.split(" ");
        HttpMethod httpMethod = HttpMethod.valueOf(actionRow[0]);
        String route = actionRow[1];
        Header header = new Header();
        HashMap<String, String> parameters = Helper.getParametersFromRoute(route);

        do {
            command = in.readLine();
            String[] headerRow = command.split(": ");
            if(headerRow.length == 2) {
                header.add(headerRow[0], headerRow[1]);
            }
        } while(!command.trim().equals(""));

        if(httpMethod.equals(HttpMethod.POST)) {
            int contentLength = Integer.parseInt(header.get("content-length"));
            char[] buff = new char[contentLength];
            in.read(buff, 0, contentLength);
            String parametersString = new String(buff);

            HashMap<String, String> postParameters = Helper.getParametersFromString(parametersString);
            for (String parameterName : postParameters.keySet()) {
                parameters.put(parameterName, postParameters.get(parameterName));
            }
        }

        Request request = new Request(httpMethod, route, header, parameters);

        return request;
    }
}
