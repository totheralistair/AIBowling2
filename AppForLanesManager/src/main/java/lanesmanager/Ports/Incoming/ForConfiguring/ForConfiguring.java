package lanesmanager.Ports.Incoming.ForConfiguring;

public interface ForConfiguring {
    String greetings();
    String connectLaneGovernor(lanesmanager.Ports.Outgoing.ForActivatingLanes.ForActivatingLanes laneGovernor);
}
