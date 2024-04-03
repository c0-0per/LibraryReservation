package cz.cvut.ear.privatelib.dao;


import cz.cvut.ear.privatelib.PrivateLibraryApplication;
import cz.cvut.ear.privatelib.environment.Generator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import cz.cvut.ear.privatelib.model.User;
import cz.cvut.ear.privatelib.dao.UserDao;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrivateLibraryApplication.class)
@AutoConfigureTestDatabase
@DataJpaTest
public class UserDaoTest {


    @Autowired
    private TestEntityManager em;


    @Autowired
    private UserDao sut;



    @Test
    public void whenFindByUsername_thenReturnUser() {
        User user = Generator.generateUser();
        em.persist(user);
        em.flush();

        User found = sut.findByUsername(user.getUsername());

        assertThat(found.getUsername())
                .isEqualTo(user.getUsername());
    }



    @Test
    public void findByUsernameReturnsPersonWithMatchingUsername1() throws Exception{


         int b  =sut.sumNumber(1,2);
        assertEquals(b,3);
    }
    @Test
    public void findByUsernameReturnsNullForUnknownUsername() {
        assertNull(sut.findByUsername("unknownUsername"));
    }

}

