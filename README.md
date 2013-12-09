hazelcast-bandwich-test
=======================

Tests for number of requests for hazelcast node-to-node solution via TCP\Multicast discovery

1. Update hazelcast.xml (src/main/resources) with correct settings
2. Build with maven
mvn install 
3. Rename target\bandwich-test-1.0-SNAPSHOT.jar to bt.jar and copy it to hosts under test

4. Run one who process entries (removes added entries)
java -jar bt.jar -t Processor
5. Run another who make additions to map
java -jar bt.jar -t Requestor
6. Wait till stats to show up.

Detailed instruction follows.
