package swt6.modular.beans.impl;

import swt6.modular.beans.Timer;
import swt6.modular.beans.TimerProvider;

public class HighResTimerProvider implements TimerProvider {
    @Override
    public double getResolution() {
        return 0;
    }

    @Override
    public Timer createTimer(int interval, int numTicks) {
        return null;
    }
}
