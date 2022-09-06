package com.example.stadymasca;

public class publicc {
    private  String pic;
    private  String datte;
    private  String transmitter,receiver;
    private  String title;
    private  String textt;
    private  String id_t;
    private  String id_p1;
    private  String id1;



    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setDatte(String datte) {
        this.datte = datte;
    }

    public void setTransmitter(String transmitter) {
        this.transmitter = transmitter;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTextt(String textt) {
        this.textt = textt;
    }


    public  String getTitle() {
        return title;
    }

    public  String getDatte() {
        return datte;
    }

    public  String getTransmitter() {
        return transmitter;
    }

    public  String getPic() {
        return pic;
    }

    public  String getTextt() {
        return textt;
    }
    private String id_pub, imageurl;



    public String getImageurl() {

        return imageurl;
    }

    public void setImageurl(String imageurl) {

        this.imageurl = imageurl;
    }

    public String getId_pub() {
        return id_pub;
    }

    public void setId_pub(String id_pub) {
        this.id_pub = id_pub;
    }

    public String getId_t() {
        return id_t;
    }

    public void setId_t(String id_t) {
        this.id_t = id_t;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId_p1() {
        return id_p1;
    }

    public void setId_p1(String id_p1) {
        this.id_p1 = id_p1;
    }
}
