# GnuTHDella: Unstructured P2P Network in Spring using HTTP

This is an example implementation for the System Design course at TH Deggendorf (www.th-deg.de). 
It demonstrates the complexity, yet beauty of P2P systems. 
The project is based on the fahrenbuch (yes really!) project started roughly 2005 and changed over the years.

### How it works

The system checks at startup whether it is a special "master" node, that needs to always be there, or a client. If it is
the master it will listen on port 8080 and wait for connections. Otherwise, it will generate a random high port and
register at the master url drawn from application.properties using the p2p.master.url key.

Keeping the neighbors list fresh is done using Scheduled Tasks (de.thd.systemdesign.p2p.scheduler package), which check
the liveliness of all neighbors in the systems cache.

Generally they exchange two types of messages: P2PMessages for point-to-point messages and ForwardedMessages, which is a
container for other messages. This container is capable of recording all intermediate hops between source and target.
Everything is transmitted as JSON on the wire and serialized using Jacksons. That machinery can be found in the
P2PMessage Class.

## Future
- Create a structured topology using a chord like overlay?
- Ride some bitcoin like protocol on top of that
 - Goship transactions on the network
 - Compute something hash and transaction like
 - Announce the new Block to as many nodes as possible
 - create an algorithm for chain management (what happens if I get a new block)