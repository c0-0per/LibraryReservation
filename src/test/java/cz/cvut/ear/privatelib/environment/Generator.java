package cz.cvut.ear.privatelib.environment;

import cz.cvut.ear.privatelib.model.Title;
import cz.cvut.ear.privatelib.model.User;

import java.util.Random;

public class Generator {
    private static final Random RAND = new Random();

    public static int randomInt() {
        return RAND.nextInt();
    }

    public static int randomInt(int max) {
        return RAND.nextInt(max);
    }

    public static int randomInt(int min, int max) {
        assert min >= 0;
        assert min < max;

        int result;
        do {
            result = randomInt(max);
        } while (result < min);
        return result;
    }

    public static boolean randomBoolean() {
        return RAND.nextBoolean();
    }

    public static User generateUser() {
        final User user = new User();
        user.setUsername("username" + randomInt() + "@kbss.felk.cvut.cz");
        user.setPassword(Integer.toString(randomInt()));
        return user;
    }

    public static Title generateTitle() {
        final Title title = new Title();
        title.setNameOfBook("name" + randomInt());
        title.setDescriptionOfBook(Integer.toString(randomInt()));
        title.setPublicationYear(randomInt());
        return title;
    }


}
