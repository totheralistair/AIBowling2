package lanesmanager;

import lanesmanager.BizLogic.LanesManagerService;
import lanesmanager.Adapters.OutgoingAdapters.ForActivatingLanes.MockLaneGovernor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LanesManagerServiceTest {

    @Test
    void test_greetings_returns_greetings_from_lanes_manager() {
        LanesManagerService service = new LanesManagerService();
        assertEquals("greetings from Lanes Manager", service.greetings());
    }

    @Test
    void test_connectLaneGovernor_returns_greetings_from_mock_lane_governor() {
        LanesManagerService service = new LanesManagerService();
        assertEquals("greetings from mock Lane Governor", service.connectLaneGovernor(new MockLaneGovernor()));
    }
}
