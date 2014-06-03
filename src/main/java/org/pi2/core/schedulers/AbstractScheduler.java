package org.pi2.core.schedulers;

import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * User: ikka
 * Date: 4/9/14
 * Time: 5:10 AM
 */
public abstract class AbstractScheduler {
  //logger
  private static final Logger log = Logger.getLogger(AbstractScheduler.class);
  protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  protected long initialDelay = 600;
  protected long period = 3600 * 3;
  private final String className = this.getClass().getSimpleName();

  /**
   * @param initialDelay in seconds
   * @param period       in seconds
   */
  public AbstractScheduler(long initialDelay, long period) {
    this.initialDelay = initialDelay;
    this.period = period;
  }

  public AbstractScheduler() {
  }

  public void start() {
    final Runnable task = getTask();

    scheduler.scheduleAtFixedRate(getCommand(task), initialDelay, period, SECONDS);
  }

  protected Runnable getCommand(final Runnable task) {
    return new Runnable() {
      private final ExecutorService executor = Executors.newSingleThreadExecutor();
      private Future<?> lastExecution;

      @Override
      public void run() {

        String message = "task";
        if (lastExecution != null && !lastExecution.isDone()) {
          log.info("Skipping " + message.toLowerCase());
          return;
        }
        log.info("Submitting task " + this.getClass().getName());
        lastExecution = executor.submit(task);
      }
    };
  }

  abstract protected Runnable getTask();
}
