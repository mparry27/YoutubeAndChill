package domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.video.Video;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class domain{
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.googleapis.com/youtube/v3/videos?id=LDU_Txk06tM&part=contentDetails&part=snippet&key=AIzaSyDy841gJF7UwpBxHaUliw_qVwIDR9oZ6oI"))
                .build();
        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            Gson gson = new GsonBuilder().create();
            Video video = gson.fromJson(response.body(), Video.class);

            System.out.println(video.getItems().get(0).contentDetails.getDuration());
            System.out.println(durationToSeconds(video.getItems().get(0).contentDetails.getDuration()));
        } catch (NullPointerException | IOException | InterruptedException ex) {
            System.out.println(ex);
        }
    }

    private static double durationToSeconds(String duration) {
        double hour = 0;
        double minute = 0;
        double second = 0;
        Pattern pattern = Pattern.compile("^PT(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?$");
        Matcher matcher = pattern.matcher(duration);
        if(matcher.matches()) {
            if (matcher.group(1) != null)
                hour = Double.parseDouble(matcher.group(1));
            if (matcher.group(2) != null)
                minute = Double.parseDouble(matcher.group(2));
            if (matcher.group(3) != null)
                second = Double.parseDouble(matcher.group(3));
            return ((hour * 3600)+(minute * 60) + second);
        } else {
            return 0;
        }

    }
}
