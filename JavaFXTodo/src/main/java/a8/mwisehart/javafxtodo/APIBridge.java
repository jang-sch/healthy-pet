package a8.mwisehart.javafxtodo;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import org.json.JSONObject;

public class APIBridge {

    private static final HttpClient client = HttpClient.newBuilder().build();
    private static final String baseURL = "https://javafxtodo-4bad6-default-rtdb.firebaseio.com/";
    private static final String apiKey = "6mpSaHXNVM2IrkxgIQRQCjcwXm2j7H6wY0KeeapR";

    public static void addItem(String selectedTab, Item item){
        String path = URLEncoder.encode(item.getDesc(), StandardCharsets.UTF_8);

        JSONObject obj = new JSONObject(item);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + selectedTab + "/" + path + ".json?auth=" + apiKey))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(obj.toString()))
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void getList(String selectedTab, ObservableList<Item> items) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + selectedTab + ".json?auth=" + apiKey))
                .header("Content-Type", "application/json?auth=")
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    JSONObject obj = new JSONObject(response.body());
                    Iterator<String> keys = obj.keys();

                    while(keys.hasNext()){
                        JSONObject jsonItem = obj.getJSONObject(keys.next());
                        Platform.runLater(()->{
                            items.add(new Item(jsonItem.getString("desc")));
                        });
                    }
                    return response;
                });

    }

    public static void deleteItem(String selectedTab, Item selectedItem) {
        String path = URLEncoder.encode(selectedItem.getDesc(), StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + selectedTab + "/" + path + ".json?auth=" + apiKey))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println("Item deleted from database");
                    return response;
                });

    }
}
