package swt6.modular.beans;

public interface Timer {
    void start();

    // lock vom this (ist dasselbe wie synchronzed(this) { ... }. untere is kurzform
    void stop();

    boolean isRunning();

    int getInterval();

    int getNumTicks();

    void addTimerListener(TimerListener listener);

    void removeTimerListener(TimerListener listener);
}
