package lanesmanager.BizLogic;

import lanesmanager.Ports.Incoming.ForConfiguring.ForConfiguring;
import lanesmanager.Ports.Outgoing.ForActivatingLanes.ForActivatingLanes;

public class LanesManagerService implements ForConfiguring {

    private ForActivatingLanes laneGovernor;

    @Override
    public String greetings() {
        return "greetings from Lanes Manager";
    }

    @Override
    public String connectLaneGovernor(ForActivatingLanes laneGovernor) {
        this.laneGovernor = laneGovernor;
        return this.laneGovernor.greetings();
    }
}
