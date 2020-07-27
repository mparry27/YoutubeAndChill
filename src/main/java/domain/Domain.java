package domain;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class domain{
    public static void main(String[] args) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.googleapis.com/youtube/v3/videos?id=5aJ9U5t9oD4&part=snippet&key=AIzaSyDy841gJF7UwpBxHaUliw_qVwIDR9oZ6oI"))
                .build();

        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            //System.out.println(json.get("kind"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
