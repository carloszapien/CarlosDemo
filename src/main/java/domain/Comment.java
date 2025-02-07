package domain;
//The following class is a data model that represents a comment in an application. It has:
//The fields: postId, id, name, email, and body to store the details of the comment.
//We define getter and setter methods to access and modify these fields.
//This class will be used to handle comments.
public class Comment {
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;

    // Getters and Setters
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}