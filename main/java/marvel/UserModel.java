package marvel;

import com.opencsv.bean.CsvBindByName;

/**
 * This is the Java representation of a User
 */
public class UserModel {

    /**
     * the character
     */
    @CsvBindByName
    private String hero;

    /**
     * the comic book that appear character
     */
    @CsvBindByName
    private String book;

    /**
     * get the character
     *
     * @return name of the character
     */
    public String getHero() {
        return hero;
    }

    /**
     * set the name of the character
     *
     * @param hero name of the character
     */
    public void setHero(String hero) {
        this.hero = hero;
    }

    /**
     * get the email of the user
     *
     * @return email of the user
     */
    public String getBook() {
        return book;
    }

    /**
     * set the email of the user
     *
     * @param book comic book that appear character
     */
    public void setBook(String book) {
        this.book = book;
    }
}
