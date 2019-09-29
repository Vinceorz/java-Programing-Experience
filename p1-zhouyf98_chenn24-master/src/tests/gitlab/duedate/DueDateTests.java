package tests.gitlab.duedate;

import tests.GradingUtility;
import tests.gitlab.ckpt1.MinFourHeapTests;

public class DueDateTests extends GradingUtility {
    public static void main(String[] args) {
        new DueDateTests();
    }

    protected Class<?>[] getTests() {
        return new Class<?>[] {
                MinFourHeapTests.class, HashTrieMapTests.class
        };
    }
}
