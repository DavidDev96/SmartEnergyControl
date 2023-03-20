package swt6.modular.client;

import java.util.Date;

public class Measurement {
    private double roomTemperature;
    private double current;
    private Date timeStamp;

    public Measurement(double roomTemperature, double current, Date date) {
        this.roomTemperature = roomTemperature;
        this.current = current;
        this.timeStamp = date;
    }

    public double getRoomTemperature() {
        return roomTemperature;
    }

    public double getCurrent() {
        return current;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
