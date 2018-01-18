/*
 * Copyright 2018 the original author or authors.
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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for {@ContentTypeEnvironmentPostProcessor}.
 *
 * @author Chris Schaefer
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class ContentTypeEnvironmentPostProcessorTests {
	@Autowired
	ConfigurableEnvironment configurableEnvironment;

	public static class PostProcessorContentTypeOverrideTests extends ContentTypeEnvironmentPostProcessorTests {
		@Test
		public void testPostProcessorContentType() {
			PropertySource propertySource = configurableEnvironment.getPropertySources()
					.get(ContentTypeEnvironmentPostProcessor.PROPERTY_SOURCE_KEY_NAME);

			assertNotNull("Property source " + ContentTypeEnvironmentPostProcessor.PROPERTY_SOURCE_KEY_NAME + " is null",
					propertySource);

			assertTrue("Unexpected content type", propertySource.getProperty(ContentTypeEnvironmentPostProcessor.CONTENT_TYPE_PROPERTY_KEY)
					.equals(ContentTypeEnvironmentPostProcessor.CONTENT_TYPE_PROPERTY_VALUE));

		}
	}

	@TestPropertySource(properties = {
			"spring.cloud.stream.bindings.output.contentType=application/json"
	})
	public static class UserDefinedContentTypeOverrideTests extends ContentTypeEnvironmentPostProcessorTests {
		@Test
		public void testUserDefinedContentType() {
			assertTrue("contentType property key not found",
					configurableEnvironment.containsProperty(ContentTypeEnvironmentPostProcessor.CONTENT_TYPE_PROPERTY_KEY));

			assertTrue("Unexpected content type", configurableEnvironment.getProperty(ContentTypeEnvironmentPostProcessor.CONTENT_TYPE_PROPERTY_KEY)
					.equals("application/json"));
		}
	}
}
