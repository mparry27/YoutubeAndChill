package domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.video.Video;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class domain{
    public static void main(String[] args) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.googleapis.com/youtube/v3/videos?id=LDU_Txk06tM&part=contentDetails&part=snippet&key=AIzaSyDy841gJF7UwpBxHaUliw_qVwIDR9oZ6oI"))
                .build();
        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());
            Video video = new Video();

            Gson gson = new GsonBuilder().create();
            video = gson.fromJson(json.toString(), Video.class);

            System.out.println(video.getItems().get(0).snippet.getTitle());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
