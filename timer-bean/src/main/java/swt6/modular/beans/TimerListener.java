package swt6.modular.beans;


// (Marker)Annotation: Hinweis für entwickler/Tools, dass es sich um ein Interface handelt dass wie ein Function zu behandeln ist (nur eine Methode)
// Wenn jemand eine weitere Methode hinzufügt, wird eine Fehlermeldung ausgegeben
@FunctionalInterface
public interface TimerListener {
    public void expired(TimerEvent event);
}
