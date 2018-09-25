package alok.trials;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DirectExecutoServiceWithCallback {
    private static final Logger logger_ = LoggerFactory.getLogger(DirectExecutoServiceWithCallback.class);

    public static void main(String[] args) {
        boolean useGuava = true;

        final ListeningExecutorService directExecutorService;
        if (useGuava) {
            directExecutorService = MoreExecutors.newDirectExecutorService();
        } else {
            directExecutorService = MoreExecutors.listeningDecorator( new ThreadPoolExecutor(
                    0, 1, 0, TimeUnit.DAYS,
                    new SynchronousQueue<Runnable>(),
                    new ThreadPoolExecutor.CallerRunsPolicy()));
        }

        ListenableFuture<Boolean> future = directExecutorService.submit(new MyCallable());
        Futures.addCallback(future, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                logger_.info("In onSuccess!");
            }

            @Override
            public void onFailure(Throwable throwable) {
                logger_.info("In onFailure!");
            }
        });
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
            boolean result = _random.nextBoolean();
            if (result) {
                return result;
            } else {
                throw new Exception("Some exception!");
            }
        }
    }
}
