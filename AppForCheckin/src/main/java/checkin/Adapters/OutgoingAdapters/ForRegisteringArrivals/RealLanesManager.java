package checkin.Adapters.OutgoingAdapters.ForRegisteringArrivals;

import checkin.Ports.Outgoing.ForRegisteringArrivals.ForRegisteringArrivals;
import lanesmanager.BizLogic.LanesManagerService;

public class RealLanesManager implements ForRegisteringArrivals {

    private final LanesManagerService lanesManagerService;

    public RealLanesManager(LanesManagerService lanesManagerService) {
        this.lanesManagerService = lanesManagerService;
    }

    @Override
    public String greetings() {
        return lanesManagerService.greetings();
    }
}
