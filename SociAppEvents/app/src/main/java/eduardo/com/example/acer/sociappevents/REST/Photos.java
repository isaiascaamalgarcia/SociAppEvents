package eduardo.com.example.acer.sociappevents.Rest;

import android.graphics.Bitmap;

/**
 * Created by ACER on 29/07/2015.
 */
public class Photos {

    private int id;
    private String title;
    private String description;
    private String datePhoto;
    private String type;
    private String base64;
    private Bitmap photo;

    public Bitmap getPhoto() {return photo;}

    public void setPhoto(Bitmap photo) {this.photo = photo;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}

    public void setDescription(String description) { this.description = description;}

    public String getDatePhoto() {return datePhoto;}

    public void setDatePhoto(String datePhoto) {this.datePhoto = datePhoto;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public String getBase64() {return base64;}

    public void setBase64(String base64) {this.base64 = base64;}
}
