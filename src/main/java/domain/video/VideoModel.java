package domain.video;

public class VideoModel {
    public Snippet snippet;
    public Snippet getSnippet() { return snippet; }
    public void setSnippet(Snippet snippet) { this.snippet = snippet; }

    public String id;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public ContentDetails contentDetails;
    public ContentDetails getContentDetails() { return contentDetails; }
    public void setContentDetails(ContentDetails contentDetails) { this.contentDetails = contentDetails; }
}
