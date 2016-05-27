package com.swiftpot.android.teletrosky.model;/**
 * Created by Rodney on 27-May-16.
 */

/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 27-May-16
 * 8:51 PM
 */
public class ClientDestinationRequest {

    private int startingPointLatitude;
    private int startingPointLongitude;

    private int endPointLatitude;
    private int endPointLongitude;

    private String startingPointFull;
    private String endPointFull;

    private String endPointName;

    public int getStartingPointLatitude() {
        return startingPointLatitude;
    }

    public void setStartingPointLatitude(int startingPointLatitude) {
        this.startingPointLatitude = startingPointLatitude;
    }

    public int getStartingPointLongitude() {
        return startingPointLongitude;
    }

    public void setStartingPointLongitude(int startingPointLongitude) {
        this.startingPointLongitude = startingPointLongitude;
    }

    public int getEndPointLatitude() {
        return endPointLatitude;
    }

    public void setEndPointLatitude(int endPointLatitude) {
        this.endPointLatitude = endPointLatitude;
    }

    public int getEndPointLongitude() {
        return endPointLongitude;
    }

    public void setEndPointLongitude(int endPointLongitude) {
        this.endPointLongitude = endPointLongitude;
    }

    public String getStartingPointFull() {
        return startingPointFull;
    }

    public void setStartingPointFull(String startingPointFull) {
        this.startingPointFull = startingPointFull;
    }

    public String getEndPointFull() {
        return endPointFull;
    }

    public void setEndPointFull(String endPointFull) {
        this.endPointFull = endPointFull;
    }

    public String getEndPointName() {
        return endPointName;
    }

    public void setEndPointName(String endPointName) {
        this.endPointName = endPointName;
    }
}
