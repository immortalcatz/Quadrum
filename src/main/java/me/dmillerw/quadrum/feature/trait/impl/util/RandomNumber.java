package me.dmillerw.quadrum.feature.trait.impl.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author dmillerw
 */
public class RandomNumber extends Number {

    public double[] value = new double[]{0};

    @Override
    public int intValue() {
        return value.length == 1 ? (int) value[0] : ThreadLocalRandom.current().nextInt((int) value[0], (int) value[1]);
    }

    @Override
    public long longValue() {
        return value.length == 1 ? (long) value[0] : ThreadLocalRandom.current().nextLong((long) value[0], (long) value[1]);
    }

    @Override
    public float floatValue() {
        return value.length == 1 ? (float) value[0] : (float) ThreadLocalRandom.current().nextDouble(value[0], value[1]);
    }

    @Override
    public double doubleValue() {
        return value.length == 1 ? value[0] : ThreadLocalRandom.current().nextDouble(value[0], value[1]);
    }

    @Override
    public String toString() {
        return "{" + value[0] + ", " + value[1] + "}";
    }
}
