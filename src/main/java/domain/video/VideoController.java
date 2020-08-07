package domain.video;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoController {
    public ArrayList<Video> videoQueue = new ArrayList<Video>();
    private final String apikey = "AIzaSyDljtb4JLfnbEzn-LBP2ieWC_PFxwicgBg";
    final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
    final String[] videoIdRegex = { "\\?vi?=([^&]*)","watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};

    public Video QueueVideo(String url) {
        String videoID = extractVideoIdFromUrl(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.googleapis.com/youtube/v3/videos?id=" + videoID + "&part=contentDetails&part=snippet&key=" + apikey))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            Gson gson = new GsonBuilder().create();
            Video video = gson.fromJson(json.toString(), Video.class);
            videoQueue.add(video);
            System.out.println("Added: " + video.getItems().get(0).snippet.getTitle() + " to Video Queue");
            return video;
        } catch (NullPointerException | IOException | InterruptedException ex) {
            System.out.println("Couldn't find video\n" + ex);
            return null;
        }
    }

    // credit to jvanderwee at https://gist.github.com/jvanderwee/b30fdb496acff43aef8e
    public String extractVideoIdFromUrl(String url) {
        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);

        for(String regex : videoIdRegex) {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);

            if(matcher.find()){
                return matcher.group(1);
            }
        }

        return null;
    }

    // credit to jvanderwee at https://gist.github.com/jvanderwee/b30fdb496acff43aef8e
    private String youTubeLinkWithoutProtocolAndDomain(String url) {
        Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);
        Matcher matcher = compiledPattern.matcher(url);

        if(matcher.find()){
            return url.replace(matcher.group(), "");
        }
        return url;
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
