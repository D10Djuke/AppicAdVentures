package adventures.ad.appic.game;

/**
 * Created by Jory on 25/01/2015.
 */
public interface Character {

    String characterName = "default";
    int baseHitPoints = 0;
    int baseManaPoits = 0;

    int baseStrength = 10;
    int baseIntelligence = 10;
    int baseWillPower = 10;
    int baseWisdom = 10;
    int baseSpirit = 10;
    int baseSpeed = 10;
    int baseAgility = 10;
    int baseEvasion = 10;
    int baseAccuracy = 10;
    int baseLuck = 10;

    abstract void takeDamage();
    abstract void dealDamage();

}
