package adventures.ad.appic.game;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jory on 25/01/2015.
 */
public class Character implements Parcelable{

    public Character(){

    }

    String characterName = "default";
    int hitPoints = 0;
    int maxHitPoints = 0;
    int atk = 0;
    int def = 0;
    int stam = 0;
    int lvl = 1;

    protected void takeDamage(int damage){
        hitPoints = hitPoints - damage;
    }

    public void setCharacterName(String name){
        this.characterName = name;
    }

    public void setHitPoints(int hp){
        this.hitPoints = hp;
    }

    public void setMaxHitPoints(int hp) { this.maxHitPoints = hp; }

    public void setAtk(int atk){
        this.atk = atk;
    }

    public void setDef(int def){
        this.def = def;
    }

    public void setStam(int stam){
        this.stam = stam;
    }

    public void setLvl(int lvl){
        this.lvl = lvl;
    }

    public void levelUp(){
        lvl++;
    }

    public String getCharacterName(){
        return characterName;
    }

    public int getAtk(){
        return atk;
    }

    public int getDef(){
        return def;
    }

    public int getStam(){
        return stam;
    }

    public int getLvl(){
        return lvl;
    }

    public int getHitPoints(){
        return hitPoints;
    }

    public int getMaxHitpoints() { return maxHitPoints;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stam);
        dest.writeInt(atk);
        dest.writeInt(def);
        dest.writeInt(hitPoints);
        dest.writeInt(maxHitPoints);
        dest.writeInt(lvl);
        dest.writeString(characterName);
    }

    protected void readFromParcel(Parcel in) {
        stam = in.readInt();
        atk = in.readInt();
        def = in.readInt();
        hitPoints = in.readInt();
        maxHitPoints = in.readInt();
        lvl = in.readInt();
        characterName = in.readString();
    }
}
