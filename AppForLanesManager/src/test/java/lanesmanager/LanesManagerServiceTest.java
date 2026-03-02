package lanesmanager;

import lanesmanager.BizLogic.LanesManagerService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LanesManagerServiceTest {

    @Test
    void test_greetings_returns_greetings_from_lanes_manager() {
        LanesManagerService service = new LanesManagerService();
        assertEquals("greetings from Lanes Manager", service.greetings());
    }
}
