package org.terasology.world.time;

import org.terasology.engine.Time;
import org.terasology.entitySystem.systems.In;
import org.terasology.entitySystem.systems.UpdateSubscriberSystem;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Immortius
 */
public class WorldTimeImpl implements WorldTime, UpdateSubscriberSystem {
    private static final float WORLD_TIME_MULTIPLIER = 48f;
    private static final long DAYS_TO_MS = (DAY_LENGTH);
    private static final float MS_TO_DAYS = 1.f / (DAYS_TO_MS);

    private AtomicLong worldTime = new AtomicLong(0);

    @In
    private Time time;

    @Override
    public void initialise() {
    }

    @Override
    public void shutdown() {
    }


    @Override
    public long getMilliseconds() {
        return worldTime.get();
    }

    @Override
    public float getSeconds() {
        return worldTime.get() / 1000f;
    }

    @Override
    public float getDays() {
        return MS_TO_DAYS * worldTime.get();
    }

    @Override
    public float getTimeRate() {
        return WORLD_TIME_MULTIPLIER;
    }

    @Override
    public void setMilliseconds(long time) {
        // TODO: Send network event to update
        this.worldTime.getAndSet(time);
    }

    @Override
    public void setDays(float timeInDays) {
        setMilliseconds((long) ((double) timeInDays * DAYS_TO_MS));
    }

    @Override
    public void update(float delta) {
        worldTime.getAndAdd((long)(time.getDeltaInMs() * WORLD_TIME_MULTIPLIER));
    }
}