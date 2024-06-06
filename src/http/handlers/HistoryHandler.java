package http.handlers;

import com.sun.net.httpserver.HttpExchange;
import service.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler {

    public HistoryHandler(TaskManager taskManager) {
        super(taskManager);
    }

    private void handleGet(HttpExchange httpExchange, String[] path) throws IOException {
        response = gson.toJson(taskManager.getHistory());
        sendText(httpExchange, response, 200);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String[] path = httpExchange.getRequestURI().getPath().split("/");


        switch (method) {
            case "GET" -> handleGet(httpExchange, path);
            default -> sendNotFound(httpExchange);
        }
    }
}