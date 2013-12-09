package com.abalmasov.hazelcast.actors;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.IMap;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Artem_Abalmasov@epam.com
 * @since 1.0.3
 *        Date: 11/28/13
 *        Time: 5:53 PM
 */
public class RequestorActor {
    private final IMap<String, Long> keys;
    private final int num = 100000;
    private final int threads = 10;
    private final int times = 5;
    private final ExecutorService exe = Executors.newFixedThreadPool(threads);
    private final DescriptiveStatistics stats = new DescriptiveStatistics();

    public RequestorActor(IMap<String, Long> keys) {
        this.keys = keys;
        EntryListener<String, Long> reqList = new RequestorActor.MyEntryListener();
        keys.addEntryListener(reqList, true);
    }

    public void send() {
        for (int i = 0; i < times; i++) {
            for (int j = 0; j < num; j++) {
                final String z = i + "." + j + ".value";
                exe.submit(new Runnable() {
                    @Override
                    public void run() {
                        keys.put(z, System.currentTimeMillis());
                    }
                });
            }
        }
    }

    private void printStats() {
        // Compute some statistics
//        double max = stats.getMax();
//        double min = stats.getMin();
//        double mean = stats.getMean();
//        double std = stats.getStandardDeviation();
//        double p50 = stats.getPercentile(50);
//        double p90 = stats.getPercentile(90);
        System.out.print(stats.toString());
    }

    private class MyEntryListener implements EntryListener<String, Long> {
        private volatile AtomicInteger recieved = new AtomicInteger();

        @Override
        public void entryAdded(EntryEvent<String, Long> event) {

        }

        @Override
        public void entryRemoved(EntryEvent<String, Long> event) {
            stats.addValue(System.currentTimeMillis() - event.getValue());
            if (recieved.incrementAndGet() % num == 0) printStats();

        }

        @Override
        public void entryUpdated(EntryEvent<String, Long> event) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void entryEvicted(EntryEvent<String, Long> event) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

}
