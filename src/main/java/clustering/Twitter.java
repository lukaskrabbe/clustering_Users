
/**
 * Copyright 2013 Twitter, Inc.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package clustering;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Twitter {

	public static void run(String consumerKey, String consumerSecret, String token, String secret)
			throws InterruptedException {

		// Create an appropriately sized blocking queue
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

		// Define our endpoint: By default, delimited=length is set (we need
		// this for our processor)
		// and stall warnings are on.
		StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();
		endpoint.stallWarnings(false);

		Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);
		// Authentication auth = new
		// com.twitter.hbc.httpclient.auth.BasicAuth(username, password);

		// Create a new BasicClient. By default gzip is enabled.
		BasicClient client = new ClientBuilder().name("sampleExampleClient").hosts(Constants.STREAM_HOST)
				.endpoint(endpoint).authentication(auth).processor(new StringDelimitedProcessor(queue)).build();

		// Establish a connection
		client.connect();

		File file ;
		int numFiles = 10;
		int numMessages = 1000;
		for (int createdFiles = 0; createdFiles < numFiles; createdFiles++) {
			// Create File
			file = new File();
			for (int msgRead = 0; msgRead < numMessages; msgRead++) {
				if (client.isDone()) {
					System.out.println("Client connection closed unexpectedly: " + client.getExitEvent().getMessage());
					break;
				}

				String msg = queue.poll(5, TimeUnit.SECONDS);
				if (msg == null) {
					System.out.println("Did not receive a message in 5 seconds");
				} else {
					// Write Input in File
					file.addVal("\n");
					file.addVal(msg);
				}
			}
			
			file.toFile();
			System.out.println("File " + createdFiles + "/" + numFiles + " created");
		}

		client.stop();

		// Print some stats
		System.out.printf("The client read %d messages!\n", client.getStatsTracker().getNumMessages());
	}

	public static void main(String[] args) {

		try {
			Twitter.run("fr4W67JN6lwN0dgWdIhqda4xB", "dOesm2TjVrWWwVh0LS4cQxJeWcsX3qD0H4t5geHCtkAwRM6TVY",
					"2375599923-C9NEX5mBxnGdlT8LURFUeHDKwuGQYdFdNByoMrQ",
					"AxEqy4bqrhmf5caaO1sRskoWW3nTsyVo63sYlqTCHiTng");
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
}