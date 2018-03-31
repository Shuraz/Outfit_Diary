package edu.csu.surajpokhrel.outfit_diary.utility;

/**
 * Created by NgocTri on 3/4/2017.
 */

public class ImageUpload {
    public String imageId;
    public String name;
    public String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public ImageUpload(String imageId, String name, String url) {
        this.imageId=imageId;
        this.name = name;
        this.url = url;
    }

    public String getImageId(){
        return imageId;
    }
    public ImageUpload(){}
}
