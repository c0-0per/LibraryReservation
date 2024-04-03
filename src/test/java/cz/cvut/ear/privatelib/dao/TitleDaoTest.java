package cz.cvut.ear.privatelib.dao;

import cz.cvut.ear.privatelib.PrivateLibraryApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static cz.cvut.ear.privatelib.environment.Generator.randomInt;
//*/
//@DataJpaTest
//@ComponentScan(basePackageClasses = PrivateLibraryApplication.class, excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class)})
//@ActiveProfiles("test")
//public class TitleDaoTest {
//    @Autowired
//    private TestEntityManager em;
//
//    @Autowired
//    private TitleDao sut;
//
//    @Test
//    public void findAllReturnsAllUsers() {
//        Title title;
//        for(int i = 0; i < 5; i++) {
//            title = Generator.generateTitle();
//            em.persist(title);
//        }

//        final List<Title> result = sut.getAll();
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(5, result.size());
//    }
//
//    @Test
//    public void findAllByGenreReturnsProductsInSpecifiedGenre() {
//        final Genre genre = generateGenre("testCategory");
//
//        final List<Title> titles = generateTitles(genre);
//        final List<Title> result = sut.findAllByGenre(genre);
//        assertEquals(titles.size(), result.size());
//        titles.sort(Comparator.comparing(Title::getNameOfBook));
//        result.sort(Comparator.comparing(Title::getNameOfBook));
//        for (int i = 0; i < titles.size(); i++) {
//            assertEquals(titles.get(i).getBookId(), result.get(i).getBookId());
//        }
//    }
//
//    private Genre generateGenre(String name) {
//        final Genre genre = new Genre();
//        genre.setNameOfGenre(name);
//        em.persist(genre);
//        return genre;
//    }
//
//    private List<Title> generateTitles(Genre genre) {
//        final List<Title> inCategory = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            final Title p = Generator.generateTitle();
//            p.setGenre(genre);
//            em.persist(p);
//        }
//        return inCategory;
//    }
//}