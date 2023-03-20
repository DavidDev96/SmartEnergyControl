import swt6.modular.beans.TimerProvider;
import swt6.modular.beans.impl.SimpleTimerProvider;

module swt6.modular.beans {
    // java package name
    exports swt6.modular.beans;
    provides TimerProvider with SimpleTimerProvider;
}