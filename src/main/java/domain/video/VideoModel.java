package domain.video;

public class VideoModel {
    public Snippet snippet;
    public Snippet getSnippet() { return snippet; }
    public void setSnippet(Snippet snippet) { this.snippet = snippet; }

    public ContentDetails contentDetails;
    public ContentDetails getContentDetails() { return contentDetails; }
    public void setContentDetails(ContentDetails contentDetails) { this.contentDetails = contentDetails; }
}
