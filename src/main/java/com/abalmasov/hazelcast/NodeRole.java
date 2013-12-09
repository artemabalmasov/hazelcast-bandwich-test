package com.abalmasov.hazelcast;

import com.abalmasov.hazelcast.actors.ProcessorActor;
import com.abalmasov.hazelcast.actors.RequestorActor;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;

/**
 * @author Artem_Abalmasov@epam.com
 * @since 1.0.3
 *        Date: 11/28/13
 *        Time: 5:45 PM
 */
public enum NodeRole {
    Requestor(new Runnable() {

        @Override
        public void run() {
            IMap<String, Long> keys =  Hazelcast.getHazelcastInstanceByName(Main.CONFIG).getMap("keys");
            RequestorActor ra = new RequestorActor(keys);
            ra.send();
        }
    }),

    Processor(new Runnable() {

        @Override
        public void run() {
            IMap<String, Long> keys =  Hazelcast.getHazelcastInstanceByName(Main.CONFIG).getMap("keys");
            ProcessorActor pa = new ProcessorActor(keys);
        }
    });
    private Runnable runnable;

    NodeRole(Runnable runnable) {


        this.runnable = runnable;
    }

    public void action() {
        runnable.run();
    }
}
