/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.scheduler;

public interface Scheduler {

    void runTask(Runnable task);

    void runTaskAsynchronously(Runnable task);

    void runTaskLater(Runnable task, long after);

    void runTaskLaterAsynchronously(Runnable task, long after);

    void runTaskTimer(Runnable task, long delay, long period);

    void runTaskTimerAsynchronously(Runnable task, long delay, long period);

}
