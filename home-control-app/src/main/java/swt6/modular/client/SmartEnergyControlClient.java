package swt6.modular.client;

import swt6.modular.airCondition.impl.AirCondition;
import swt6.modular.beans.Timer;
import swt6.modular.beans.TimerFactory;
import swt6.modular.inverter.InverterFactory;
import swt6.modular.inverter.impl.Inverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SmartEnergyControlClient {

    private static final int MinACThreshold = 22;
    private static final int MaxACThreshold = 24;

    public static void main(String[] args) throws Exception {
        List<Measurement> measurementList = new ArrayList<>();

        AirCondition airCondition = new AirCondition();
        Timer timer = TimerFactory.createTimer(500, 30);
        Inverter inverter = InverterFactory.createInverter();

        System.out.println("---- SmartEnergyControl ----");

        timer.addTimerListener(e -> System.out.printf("\nNew Measurement (%d of %d):\n", e.getTickCount() + 1, e.getNumTicks()));
        timer.addTimerListener(e -> getCurrentMeasurementDataAndShowInfo(airCondition, inverter, measurementList));

        timer.addTimerListener(e -> {
            if (!measurementList.isEmpty()) {
                if (isTenthMeasurement(measurementList)) {
                    handleAirConditioner(measurementList, airCondition);
                }
            }
        });
        timer.start();
    }

    /**
     * handleAirConditioner
     * @param measurementList of type Measurement for instance containing temperature, current and timestamp
     * @param airCondition of type AirCondition for calling the method getRoomTemperature()
     * @description Checks the last ten measurements and turns the air conditioner off if the temperature is under 22°C
     * If the temperature is above 24°C and the average current is above 0.1 kWh, the air conditioner is turned on
     */
    private static void handleAirConditioner(List<Measurement> measurementList, AirCondition airCondition) {
        var subList = getLastTenMeasurementsAsList(measurementList);
        double averageTemperature = getAverageOfLastTenTemperatures(subList);
        double averageCurrent = getAverageOfLastTenCurrents(subList);
        if (averageTemperature < MinACThreshold) {
            airCondition.turnOff();
        } else if (averageTemperature > MaxACThreshold && (averageCurrent > 0.1)) {
            airCondition.turnOn();
        }
    }


    /**
     * getLastTenMeasurementsAsList
     * @param airCondition of type AirCondition for calling the method getRoomTemperature()
     * @param inverter of type Inverter for calling the method getActualCurrent()
     * @param measurementList of type Measurement for instance containing temperature, current and timestamp
     * Writes the current data to the console and saves the current data into the measurementList
     */
    private static void getCurrentMeasurementDataAndShowInfo(AirCondition airCondition, Inverter inverter, List<Measurement> measurementList) {
        Measurement measurement = new Measurement(airCondition.getRoomTemperature(), inverter.getActualCurrent(), new Date());
        measurementList.add(measurement);
        System.out.println("Actual room temperature: " + String.format("%.1f", measurement.getRoomTemperature()) + " °C");
        System.out.println("Actual current: " + String.format("%.3f", measurement.getCurrent()) + " kWh");
    }

    /**
     * getLastTenMeasurementsAsList
     * @param measurementList of type Measurement for instance containing temperature, current and timestamp
     * @return A list of the ten last measurements of measurementList
     */
    private static List<Measurement> getLastTenMeasurementsAsList(List<Measurement> measurementList) {
        return measurementList.subList(measurementList.size()-10, measurementList.size());
    }

    /**
     * isTenthMeasurement
     * @param measurementList of type Measurement for instance containing temperature, current and timestamp
     * @return True, if it`s the 10th measurement
     */
    private static boolean isTenthMeasurement(List<Measurement> measurementList) {
        return measurementList.size() % 10 == 0;
    }

    /**
     * getAverageOfLastTenCurrents
     * @param measurementList of type Measurement for instance containing temperature, current and timestamp
     * @return The average of the last ten temperatures
     */
    private static double getAverageOfLastTenTemperatures(List<Measurement> measurementList) {
        double sumTemperature = 0.0;
        for (double temperature : measurementList.stream().map(x -> x.getRoomTemperature()).collect(Collectors.toList())) {
            sumTemperature += temperature;
        }
        return sumTemperature / 10;
    }

    /**
     * getAverageOfLastTenCurrents
     * @param measurementList of type Measurement for instance containing temperature, current and timestamp
     * @return The average of the last ten currents
     */
    private static double getAverageOfLastTenCurrents(List<Measurement> measurementList) {
        double sumCurrent = 0.0;
        for (double current : measurementList.stream().map(x -> x.getCurrent()).collect(Collectors.toList())) {
            sumCurrent += current;
        }
        return sumCurrent / 10;
    }
}
