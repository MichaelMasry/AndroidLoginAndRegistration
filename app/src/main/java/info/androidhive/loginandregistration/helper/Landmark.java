package info.androidhive.loginandregistration.helper;

import android.media.Image;

public class Landmark {

    private String name;
    private double distance;
    private double rate;
    private Image image;
    private String imageS;
    private String PhoneNumber;

    public Landmark(String name,double distance,double rate,Image image,String Phone){

        this.name=name;
        this.distance=distance;
        this.rate=rate;
        this.image=image;
        this.PhoneNumber = Phone;

    }
    public Landmark(String name,double distance,double rate,String image,String Phone){

        this.name=name;
        this.distance=distance;
        this.rate=rate;
        this.imageS=image;
        this.PhoneNumber=Phone;

    }
    public Landmark(String name,double distance,double rate,String Phone){

        this.name=name;
        this.distance=distance;
        this.rate=rate;
        this.PhoneNumber=Phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}