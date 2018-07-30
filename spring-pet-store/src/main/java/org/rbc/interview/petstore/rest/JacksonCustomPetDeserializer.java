/*
 * Copyright 2016 the original author or authors.
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

package org.rbc.interview.petstore.rest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.rbc.interview.petstore.model.Pet;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * 
 *
 */

public class JacksonCustomPetDeserializer extends StdDeserializer<Pet> {

	public JacksonCustomPetDeserializer() {
		this(null);
	}

	public JacksonCustomPetDeserializer(Class<Pet> t) {
		super(t);
	}

	@Override
	public Pet deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Pet pet = new Pet();
		ObjectMapper mapper = new ObjectMapper();
		Date birthDate = null;
		JsonNode node = parser.getCodec().readTree(parser);
		JsonNode petIdNode = node.get("id");
		Integer petId = null;
		if (petIdNode != null) {
			petId = petIdNode.asInt();
		}
		String name = node.get("name").asText(null);
		String birthDateStr = node.get("birthDate").asText(null);
		Boolean deleted = node.get("deleted").asBoolean(false);
		String race = node.get("race").asText(null);
		String gender = node.get("gender").asText(null);
		try {
			birthDate = formatter.parse(birthDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		pet.setId(petId);
		pet.setName(name);
		pet.setBirthDate(birthDate);
		pet.setDeleted(deleted);
		pet.setRace(race);
		pet.setGender(gender);
		return pet;
	}

}
