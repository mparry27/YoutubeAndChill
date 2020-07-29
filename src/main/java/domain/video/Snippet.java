package domain.video;

import java.util.ArrayList;

public class Snippet {
    private String title;
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    private String descrption;
    public String getDescrption() { return descrption; }
    public void setDescrption(String descrption) { this.descrption = descrption; }

    private Thumbnail thumbnails;
    public Thumbnail getThumbnails() { return thumbnails; }
    public void setThumbnails(Thumbnail thumbnails) { this.thumbnails = thumbnails; }
}
