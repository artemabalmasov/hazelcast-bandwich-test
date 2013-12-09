package com.abalmasov.hazelcast.actors;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.IMap;

/**
 * @author Artem_Abalmasov@epam.com
 * @since 1.0.3
 *        Date: 11/28/13
 *        Time: 6:07 PM
 */
public class ProcessorActor {
    private final IMap<String, Long> keys;

    public ProcessorActor(IMap<String, Long> keys) {
        this.keys = keys;
        EntryListener<String, Long> reqList = new ProcessorActor.MyEntryListener();
        keys.addEntryListener(reqList, true);

    }

    private class MyEntryListener implements EntryListener<String, Long> {
        @Override
        public void entryAdded(EntryEvent<String, Long> event) {
            keys.remove(event.getKey());
        }

        @Override
        public void entryRemoved(EntryEvent<String, Long> event) {
            //To change body of implemented methods use File | Settings | File Templates.
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
