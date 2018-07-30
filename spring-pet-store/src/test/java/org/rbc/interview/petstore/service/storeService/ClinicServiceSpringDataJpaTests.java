package org.rbc.interview.petstore.service.storeService;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p> Integration test using the 'Spring Data' profile.
 *
 * 
 * @see AbstractStoreServiceTests AbstractStoreServiceTests for more details. </p>
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("spring-data-jpa, hsqldb")
public class ClinicServiceSpringDataJpaTests extends AbstractStoreServiceTests {

}
