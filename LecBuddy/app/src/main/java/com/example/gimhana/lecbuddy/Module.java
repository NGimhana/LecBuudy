package com.example.gimhana.lecbuddy;

/**
 * Created by Gimhana on 10/26/2017.
 */

public class Module {

    private String image;
    private String moduleDiscription;

    public Module(String image, String moduleDiscription) {
        this.setImage(image);
        this.setModuleDiscription(moduleDiscription);
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getModuleDiscription() {
        return moduleDiscription;
    }

    public void setModuleDiscription(String moduleDiscription) {
        this.moduleDiscription = moduleDiscription;
    }
}
