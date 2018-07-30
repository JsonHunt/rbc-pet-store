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
import java.text.Format;
import java.text.SimpleDateFormat;

import org.rbc.interview.petstore.model.Pet;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * 
 *
 */

public class JacksonCustomPetSerializer extends StdSerializer<Pet> {

	public JacksonCustomPetSerializer() {
		this(null);
	}

	protected JacksonCustomPetSerializer(Class<Pet> t) {
		super(t);
	}

	@Override
	public void serialize(Pet pet, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		Format formatter = new SimpleDateFormat("yyyy/MM/dd");
		jgen.writeStartObject(); // pet
		if (pet.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", pet.getId());
		}
		jgen.writeStringField("name", pet.getName());
		jgen.writeStringField("birthDate", formatter.format(pet.getBirthDate()));
		jgen.writeBooleanField("deleted", pet.getDeleted());
		jgen.writeStringField("gender", pet.getGender());
		jgen.writeStringField("race", pet.getRace());
		jgen.writeEndObject(); // pet
	}

}
