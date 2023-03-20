package swt6.modular.beans;

public interface TimerProvider {
    double getResolution();
    Timer createTimer(int interval, int numTicks);
}
