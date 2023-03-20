package swt6.modular.beans.impl;

import swt6.modular.beans.Timer;
import swt6.modular.beans.TimerEvent;
import swt6.modular.beans.TimerListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimpleTimer implements Timer {

    private final int numTicks;
    private final int interval;
    private final List<TimerListener> listeners = new CopyOnWriteArrayList<>();
    private Thread tickerThread;

    // add doc here
    public SimpleTimer(int interval, int numTicks) {
        this.interval = interval;
        this.numTicks = numTicks;
    }
    
    @Override
    public void start() {
        if (isRunning()) {
            return;
        }

        // Möglichkeit für persistenz
        final int numTicks = this.getNumTicks();
        final int interval = this.getInterval();

        // wird bereitgestellt um Tasks nebenläufig laufen zu lassen
        tickerThread = new Thread((/* kein Argument */) -> {
            // implementierung dieser Lamba Expression
            // Thread Start

            int tickCount = 0;
            // numTicks MUSS in diesem Fall readonly sein - sonst könnte es während loop geändert werden
            while(tickCount < numTicks && !Thread.currentThread().isInterrupted()) {
                // TODO

                // Sleep kann immer interrupten -> Exception
                try {
                    Thread.sleep(interval);
                    fireEvent(new TimerEvent(this, tickCount, numTicks));
                    tickCount++;
                } catch (InterruptedException e) {
                    // restore interrupted flag
                    Thread.currentThread().interrupt();
                }
            }
            // versucht einen Lock auf dieses Objekt zu bekommen
            synchronized (this) {
                tickerThread = null;
            }
            // Thread End
        });
        tickerThread.start();
    }

    private void fireEvent(TimerEvent event) {
        listeners.forEach(listener -> {
            listener.expired(event);
        });
    }

    // lock vom this (ist dasselbe wie synchronzed(this) { ... }. untere is kurzform
    @Override
    public synchronized void stop() {
        if (tickerThread != null /* oder isRunning() */) {
            tickerThread.interrupt();
            tickerThread = null;
        }
    }

    @Override
    public synchronized boolean isRunning() {
        return tickerThread != null;
    }

    @Override
    public int getInterval() {
        return interval;
    }

    @Override
    public int getNumTicks() {
        return numTicks;
    }

    @Override
    public void addTimerListener(TimerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeTimerListener(TimerListener listener) {
        listeners.remove(listener);
    }
}
