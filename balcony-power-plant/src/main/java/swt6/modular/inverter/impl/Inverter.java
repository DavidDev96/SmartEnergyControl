package swt6.modular.inverter.impl;

import swt6.modular.inverter.InverterApi;

import java.util.Random;

public class Inverter implements InverterApi {
    Random r = new Random();
    private static final double MinCurrent = 0.000;
    private static final double MaxCurrent = 0.800;

    public Inverter() {

    }
    @Override
    public double getActualCurrent() {
        return r.nextDouble(MaxCurrent-MinCurrent) + MinCurrent;
    }
}
