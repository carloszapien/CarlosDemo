package domain;

//This Post class is a data model (also known as a POJO - Plain Old Java Object) that holds information about a post. It has:
//Four fields: userId, id, title, and body.
//Setting getter and setter methods that will allow us to retrieve and modify the values of these fields.

public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;


    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}