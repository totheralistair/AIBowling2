package checkin;

import checkin.BizLogic.CheckinService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckinServiceTest {

    @Test
    void test_greetings_returns_greetings_from_checkin() {
        CheckinService service = new CheckinService();
        assertEquals("greetings from Checkin", service.greetings());
    }
}
