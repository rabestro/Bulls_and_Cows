import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static java.text.MessageFormat.format;
import static java.util.function.Predicate.not;
import static org.hyperskill.hstest.testcase.CheckResult.correct;
import static org.hyperskill.hstest.testcase.CheckResult.wrong;

public class Scenario {
    private final Object[][] data;
    private final String[][] script;
    private final Class<?> testedClass;
    private TestedProgram main;
    private String output = "";

    Scenario(Class<?> testedClass, String name) throws IOException {
        this.testedClass = testedClass;
        data = new YAMLMapper().readValue(new File("test/" + name + ".data.yaml"), String[][].class);
        script = new YAMLMapper().readValue(new File("test/" + name + ".script.yaml"), String[][].class);
    }

    CheckResult check() {
        for (var values : data) {
            for (var action : script) {
                final var command = action[0];
                switch (command) {
                    case "start":
                        main = new TestedProgram(testedClass);
                        output = action.length == 1 ? main.start()
                                : main.start(format(action[1], values).split(" "));
                        continue;
                    case "input":
                        output = main.execute(format(action[1], values));
                        continue;
                    case "finish":
                        if (main.isFinished()) continue;
                        return wrong(format(action[1], values));
                    default:
                        final Map<String, Predicate<String>> validation = Map.of(
                                "contains", getOutput()::contains,
                                "not contains", not(getOutput()::contains),
                                "file exists", file -> new File(file).exists(),
                                "file delete", file -> new File(file).delete(),
                                "find", pattern -> Pattern.compile(pattern).matcher(getOutput()).find(),
                                "matches", getOutput()::matches);

                        if (validation.get(command).test(format(action[1], values))) continue;
                        return wrong(format(action[2], values));
                }
            }
        }
        return correct();
    }

    String getOutput() {
        return output;
    }


}