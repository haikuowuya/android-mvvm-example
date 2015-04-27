package utils.retrofit;

import java.util.concurrent.Executor;

public class ImmediateTestExecutor implements Executor {

    // Executor

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
