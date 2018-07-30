/*
 * Copyright 2002-2017 the original author or authors.
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
package org.rbc.interview.petstore.repository.jdbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.rbc.interview.petstore.model.Pet;
import org.rbc.interview.petstore.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jdbc")
public class JdbcPetRepositoryImpl implements PetRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertPet;

    @Autowired
    public JdbcPetRepositoryImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        this.insertPet = new SimpleJdbcInsert(dataSource)
            .withTableName("pets")
            .usingGeneratedKeyColumns("id");
    }
    
    @Override
    public void save(Pet pet) throws DataAccessException {
        if (pet.isNew()) {
            Number newKey = this.insertPet.executeAndReturnKey(
                createPetParameterSource(pet));
            pet.setId(newKey.intValue());
        } else {
            this.namedParameterJdbcTemplate.update(
                "UPDATE pets SET name=:name, birth_date=:birth_date, type_id=:type_id, " +
                    "owner_id=:owner_id WHERE id=:id",
                createPetParameterSource(pet));
        }
    }

    /**
     * Creates a {@link MapSqlParameterSource} based on data values from the supplied {@link Pet} instance.
     */
    private MapSqlParameterSource createPetParameterSource(Pet pet) {
        return new MapSqlParameterSource()
            .addValue("id", pet.getId())
            .addValue("name", pet.getName())
            .addValue("birth_date", pet.getBirthDate());
    }
    
	@Override
	public Collection<Pet> findAll() throws DataAccessException {
		Map<String, Object> params = new HashMap<>();
		Collection<Pet> pets = new ArrayList<Pet>();
		Collection<JdbcPet> jdbcPets = new ArrayList<JdbcPet>();
		jdbcPets = this.namedParameterJdbcTemplate
				.query("SELECT pets.id as pets_id, name, birth_date, type_id, owner_id FROM pets",
				params,
				new JdbcPetRowMapper());
		for (JdbcPet jdbcPet : jdbcPets) {
			pets.add(jdbcPet);
		}
		return pets;
	}

	@Override
	public void delete(Pet pet) throws DataAccessException {
		Map<String, Object> pet_params = new HashMap<>();
		pet_params.put("id", pet.getId());
		this.namedParameterJdbcTemplate.update("DELETE FROM pets WHERE id=:id", pet_params);
	}

	@Override
	public Pet findById(int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
