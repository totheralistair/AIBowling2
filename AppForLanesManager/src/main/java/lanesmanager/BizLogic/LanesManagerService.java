package lanesmanager.BizLogic;

import lanesmanager.Ports.Incoming.ForConfiguring.ForConfiguring;

public class LanesManagerService implements ForConfiguring {

    @Override
    public String greetings() {
        return "greetings from Lanes Manager";
    }
}
