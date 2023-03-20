package swt6.modular.inverter;

import swt6.modular.inverter.impl.Inverter;

public class InverterFactory {

    public static Inverter createInverter() {
        return new Inverter();
    }
}
