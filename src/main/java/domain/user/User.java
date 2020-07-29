package domain.user;

import domain.video.Video;
import java.util.ArrayList;

public class User {
    private String username;
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    private ArrayList<Video> videos;
    public ArrayList<Video> getVideos() { return videos; }
    public void setVideos(ArrayList<Video> videos) { this.videos = videos; }
}
