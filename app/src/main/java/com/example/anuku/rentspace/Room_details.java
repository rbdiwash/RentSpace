package com.example.anuku.rentspace;

public class Room_details
{
    String price;
    String area;
    String description;
    String address;
    String userid;

    public Room_details()
    {
    }

    public Room_details(String address, String price, String area, String description, String userid)
    {
        this.address = address;
        this.price = price;
        this.area = area;
        this.description = description;
        this.userid = userid;
    }

    public String getUserid() { return userid; }

    public String getAddress()
    {
        return address;
    }

    public String getPrice()
    {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getArea() {
        return area;
    }

}
