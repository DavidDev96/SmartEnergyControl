package swt6.modular.airCondition.impl;

import swt6.modular.airCondition.AirConditionApi;

import java.util.Date;
import java.util.Random;

public class AirCondition implements AirConditionApi {
    Random r = new Random();
    private static final double MinTemperature = 19.0;
    private static final double MaxTemperature = 30.0;
    private boolean isRunning = false;
    @Override
    public void turnOn() {
        if (!isRunning) {
            this.isRunning = true;
            System.out.println("AirCondition: Turned on at " + new Date());
        }
    }

    @Override
    public void turnOff() {
        if (isRunning) {
            this.isRunning = false;
            System.out.println("AirCondition: Turned off at " + new Date());
        }
    }

    @Override
    public double getRoomTemperature() {
        return r.nextDouble(MaxTemperature-MinTemperature) + MinTemperature;
    }
}
