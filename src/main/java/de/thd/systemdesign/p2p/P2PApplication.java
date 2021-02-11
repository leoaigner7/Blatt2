package de.thd.systemdesign.p2p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Random;

/**
 * This is a P2P Application providing a unstructured P2P Network.
 * It is using a lot of spring technology to be as simple as possible.
 * We therefore piggy-back everything on http, which makes the network even more efficient ;)
 *
 * An unstructured network is exchanging messages on a constant basis.
 * The Recurring Tasks are:
 *
 * ## Maintain Neighborhood:
 * - Cleanup clients that dropped of the network
 * - Retrieve new clients that are known to our neighbors
 *
 *
 */

@EnableScheduling
@SpringBootApplication
public class P2PApplication {
	private static Logger log = LogManager.getLogger(P2PApplication.class);

	public static void main(String[] args) {
		// The standard port of the master
		int port = 8080;
		Random rnd = new Random();

		boolean master = System.getenv("ISMASTER") != null;
		if (master) {
			log.info("I AM MASTER");
		} else {
			log.info("I AM NOT MASTER");
			port = rnd.nextInt(1000) + 9000;
		}
		System.setProperty("server.port", String.valueOf(port));
		SpringApplication.run(P2PApplication.class, args);
	}
}