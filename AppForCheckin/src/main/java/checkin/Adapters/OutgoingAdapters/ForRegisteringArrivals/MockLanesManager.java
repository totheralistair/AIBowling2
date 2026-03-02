package checkin.Adapters.OutgoingAdapters.ForRegisteringArrivals;

import checkin.Ports.Outgoing.ForRegisteringArrivals.ForRegisteringArrivals;

public class MockLanesManager implements ForRegisteringArrivals {

    @Override
    public String greetings() {
        return "greetings from mock Lanes Manager";
    }
}
