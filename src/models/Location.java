/**
 * @author Jackson Lee, Serena Zeng
 */
package models;

public enum Location {
    BRIDGEWATER("Bridgewater", "08807", "Somerset"),
    EDISON("Edison", "08837", "Middlesex"),
    FRANKLIN("Franklin", "08873", "Somerset"),
    PISCATAWAY("Piscataway","08854", "Middlesex"),
    SOMERVILLE("Somerville", "08876", "Somerset");

    private final String city;
    private final String county;
    private final String zipCode;

    Location(String city, String zipCode, String county){
        this.city = city;
        this.zipCode = zipCode;
        this.county = county;
    }

    public String getCounty() {
        return county;
    }

    public String getZipCode() {
        return zipCode;
    }

    public static Location toLocation(String loc){

        for (Location location : values()) {
            if (loc.equalsIgnoreCase(location.name())) {
                return location;
            }
        }

        return null;
    }

    @Override
    public String toString(){
        return city.toUpperCase() + ", " + zipCode +
                ", " + county.toUpperCase();
    }
}
