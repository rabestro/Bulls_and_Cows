import bullscows.Main;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

import java.io.IOException;

public class BullsCowsScript extends StageTest<String> {

    public BullsCowsScript() {
        super(Main.class);
    }

    @DynamicTestingMethod
    CheckResult testScript() throws IOException {
        final var result = new Scenario(Main.class, "BullsCows").check();
        return result;
    }

}