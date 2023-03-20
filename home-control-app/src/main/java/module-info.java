import swt6.modular.beans.TimerProvider;

module home.control.app {
    requires swt6.modular.beans;
    requires balcony.power.plant;
    requires ac.control;
    uses TimerProvider;
}