package checkin.BizLogic;

import checkin.Ports.Incoming.ForConfiguring.ForConfiguring;

public class CheckinService implements ForConfiguring {

    @Override
    public String greetings() {
        return "greetings from Checkin";
    }
}
