package checkin;

import checkin.BizLogic.CheckinService;
import checkin.Adapters.OutgoingAdapters.ForRegisteringArrivals.MockLanesManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckinServiceTest {

    @Test
    void test_greetings_returns_greetings_from_checkin() {
        CheckinService service = new CheckinService();
        assertEquals("greetings from Checkin", service.greetings());
    }

    @Test
    void test_connectLanesManager_returns_greetings_from_mock_lanes_manager() {
        CheckinService service = new CheckinService();
        assertEquals("greetings from mock Lanes Manager", service.connectLanesManager(new MockLanesManager()));
    }
}
