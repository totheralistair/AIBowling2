package checkin.BizLogic;

import checkin.Ports.Incoming.ForConfiguring.ForConfiguring;
import checkin.Ports.Outgoing.ForRegisteringArrivals.ForRegisteringArrivals;

public class CheckinService implements ForConfiguring {

    private ForRegisteringArrivals lanesManager;

    @Override
    public String greetings() {
        return "greetings from Checkin";
    }

    @Override
    public String connectLanesManager(ForRegisteringArrivals lanesManager) {
        this.lanesManager = lanesManager;
        return this.lanesManager.greetings();
    }
}
