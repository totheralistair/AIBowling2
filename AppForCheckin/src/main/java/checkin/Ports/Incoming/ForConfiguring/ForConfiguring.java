package checkin.Ports.Incoming.ForConfiguring;

public interface ForConfiguring {
    String greetings();
    String connectLanesManager(checkin.Ports.Outgoing.ForRegisteringArrivals.ForRegisteringArrivals lanesManager);
}
