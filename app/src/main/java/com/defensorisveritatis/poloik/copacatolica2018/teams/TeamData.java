package com.defensorisveritatis.poloik.copacatolica2018.teams;

/**
 * Created by poloi on 04/02/2018.
 */

public class TeamData {

    private int id;
    private String name, image_path;

    public TeamData(int id, String name, String image_path) {
        this.id = id;
        this.name = name;
        this.image_path = image_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
