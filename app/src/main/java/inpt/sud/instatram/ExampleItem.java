package inpt.sud.instatram;

public class ExampleItem {

    private String mNameStation;
    private Double longitude;
    private Double latitude;

    public ExampleItem(String mNameStation, Double longitude, Double latitude) {
        this.mNameStation = mNameStation;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public ExampleItem(String name){
        this.mNameStation = name;
    }

    public String getname(){
        return mNameStation;
    }

}
