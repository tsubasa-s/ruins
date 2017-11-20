package ac.ruins.asuka.myapplication;

public class RuinEntity{
    private int id;
    private double longtitude;
    private double latitude;
    private String name;
    private int prefecture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(int prefecture) {
        this.prefecture = prefecture;
    }
}