/*
 * Copyright 2015-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.stream.app.load.generator.source;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests for LoadGeneratorSource.
 *
 * @author Glenn Renfro
 * @author Gary Russell
 * @author Thomas Risberg
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
		"load-generator.producers:1",
		"load-generator.messageSize:1000",
		"load-generator.messageCount:1",
		"load-generator.generateTimestamp:false",
		"spring.cloud.stream.bindings.output.contentType=application/octet-stream"})
public class LoadGeneratorSourceTests {

	@Autowired
	protected Source channels;

	@Autowired
	protected MessageCollector messageCollector;

	@Test
	public void testForOneMessage() {
		List<Message<?>> messages = new ArrayList<>();
		assertEquals(1, messageCollector.forChannel(channels.output()).size());
		messageCollector.forChannel(channels.output()).drainTo(messages, 1);
		assertEquals(1, messages.size());
		Message<?> message = messages.get(0);
		byte[] payload = (byte[]) message.getPayload();
		assertEquals(1000,payload.length);
	}

	@SpringBootApplication
	public static class LoadGeneratorSourceApplication {

	}

}
