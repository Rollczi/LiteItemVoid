/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.scheduler;

public class SchedulerFullSynchronized implements Scheduler {

    @Override
    public void runTask(Runnable task) {
        task.run();
    }

    @Override
    public void runTaskAsynchronously(Runnable task) {
        task.run();
    }

    @Override
    public void runTaskLater(Runnable task, long after) {
        try {
            Thread.sleep(after * 50);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        task.run();
    }

    @Override
    public void runTaskLaterAsynchronously(Runnable task, long after) {
        try {
            Thread.sleep(after * 50);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        task.run();
    }

    @Override
    public void runTaskTimer(Runnable task, long delay, long period) {
        throw new UnsupportedOperationException("SchedulerSyncMain don't support TaskTimer!");
    }

    @Override
    public void runTaskTimerAsynchronously(Runnable task, long delay, long period) {
        throw new UnsupportedOperationException("SchedulerSyncMain don't support TaskTimerAsynchronously!");
    }

}
