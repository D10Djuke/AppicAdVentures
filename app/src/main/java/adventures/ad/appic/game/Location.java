package adventures.ad.appic.game;

/**
 * Created by vivid_000 on 2/10/2015.
 */
public class Location {
    private int id;
    private String name;
    private String coordx;
    private String coordy;
    private String address;
    private String photo;
    private  int eventId;

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCoordx() {
        return coordx;
    }

    public String getCoordy() {
        return coordy;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoto() {
        return photo;
    }

    public int getEventId() {
        return eventId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordx(String coordx) {
        this.coordx = coordx;
    }

    public void setCoordy(String coordy) {
        this.coordy = coordy;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
