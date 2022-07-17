package sg.edu.rp.c346.id21021436.p05_ndpsongsenhanced;

import java.io.Serializable;

public class Song implements Serializable {

    private int _id;
    private String title;
    private String singers;
    private int year;
    private int stars;

    //constructor
    public Song(int _id, String title, String singers, int year, int stars){

        this._id = _id;
        this.title = title;
        this.singers = singers;
        this.year = year;
        this.stars = stars;

    }

    //getters
    public int get_id() {
        return _id;
    }
    public String getTitle() {
        return title;
    }
    public String getSingers() {
        return singers;
    }
    public int getYear() {
        return year;
    }
    public int getStars() {
        return stars;
    }

    //setters
    public void set_id(int _id) {
        this._id = _id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setSingers(String singers) {
        this.singers = singers;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setStars(int stars) {
        this.stars = stars;
    }

    //toString()
    @Override
    public String toString() { return "ID: " + _id + ", " + "Title: " + title + ", " + "Singer(s): " + singers + ", " + "Year: " + year + ", " + "Rating: " + stars;  }
}
