package com.defensorisveritatis.poloik.copacatolica2018.news;

class NewsData {

    private int id;
    private String title;
    private String description;
    private String image_path;
    private String text;

    public NewsData(int id, String title, String description, String image_path, String text) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image_path = image_path;
        this.text = text;
    }

    //getters
    public int getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_path() { return image_path; }

    public String getText() { return text; }

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setText(String text) {
        this.text = text;
    }
}
