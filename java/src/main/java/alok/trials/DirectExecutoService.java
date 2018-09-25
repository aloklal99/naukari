package alok.trials;

import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.*;

public class DirectExecutoService {
    private static final Logger logger_ = LoggerFactory.getLogger(DirectExecutoService.class);

    public static void main(String[] args) {
        boolean useGuava = false;

        final ExecutorService directExecutorService;
        if (useGuava) {
            directExecutorService = MoreExecutors.newDirectExecutorService();
        } else {
            directExecutorService = new ThreadPoolExecutor(
                    0, 1, 0, TimeUnit.DAYS,
                    new SynchronousQueue<Runnable>(),
                    new ThreadPoolExecutor.CallerRunsPolicy());
            directExecutorService.submit(new BlockingCallable());
        }

        Future<Boolean> future = directExecutorService.submit(new MyCallable());
        try {
            logger_.info("Result: {}", future.get());
        } catch (InterruptedException e) {
            logger_.error("Unexpected: Interrupted!", e);
        } catch (ExecutionException e) {
            logger_.error("Unexpected: Execution exception!", e);
        }
        logger_.info("Exiting...");
    }

    static class MyCallable implements Callable<Boolean> {
        static final Random _random = new Random();
        @Override
        public Boolean call() throws Exception {
            logger_.info("In call()");
            return _random.nextBoolean();
        }
    }

    static class BlockingCallable implements Callable<Boolean> {

        Semaphore semaphore = new Semaphore(0);
        @Override
        public Boolean call() throws Exception {
            semaphore.acquire(); // this will never succeed.
            return true;
        }
    }
}
