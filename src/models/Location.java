package models;

public enum Location {
    BRIDGEWATER("08807", "Somerset County"),
    EDISON("08837", "Middlesex County"),
    FRANKLIN("08873", "Somerset County"),
    PISCATAWAY("08854", "Middlesex County"),
    SOMERVILLE("08876", "Somerset County");

    private final String county;
    private final String zipCode;

    Location(String zipCode, String county){
        this.zipCode = zipCode;
        this.county = county;
    }
}
