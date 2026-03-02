package lanesmanager.Adapters.OutgoingAdapters.ForActivatingLanes;

import lanesmanager.Ports.Outgoing.ForActivatingLanes.ForActivatingLanes;

public class MockLaneGovernor implements ForActivatingLanes {

    @Override
    public String greetings() {
        return "greetings from mock Lane Governor";
    }
}
