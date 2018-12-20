package mahendra;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;


public class InMemoryDbTest {
    private static final Logger _logger = LoggerFactory.getLogger(InMemoryDb.class);

    @Test
    public void test1() {
        InMemoryDb<Integer, Integer> db = new InMemoryDb<>("file");
        _logger.info("" + db);

        int key = 1;
        Integer value = 100;

        try (Transaction<Integer, Integer> t1 = db.beingTransaction()) {
            assertEquals("Missing key should be null!", null, t1.get(1));

            t1.set(key, value);
            assertEquals("Value in transaction should be visible to that transaction!",
                    value, t1.get(key));
            assertEquals("Value in the main db should remain unchanged till commit happens!",
                    null, db.get(key));

            key = 2;
            value = 200;
            t1.set(2, 200);
            assertEquals("Value in transaction should be visible to that transaction!",
                    value, t1.get(key));
            assertEquals("Value in the main db should remain unchanged till commit happens!",
                    null, db.get(key));
        } catch (Exception e) {
            e.printStackTrace();
            fail(String.format("Unexpected exception! %s", e));
        }
        assertEquals("After commit, main db should contain the values added in commited transaction!",
                value, db.get(key));

        try (Transaction<Integer, Integer> t2 = db.beingTransaction()) {
            Integer newValue = 20;
            t2.set(key, newValue);
            assertEquals("Value in transaction should be visible to that transaction!",
                    newValue, t2.get(key));
            assertEquals("Value in the main db should remain unchanged and point to old value!",
                    value, db.get(key));

            t2.rollback();
            assertEquals("After rollback the transaction value should match that of the last committed transaction",
                    value, t2.get(key));

            t2.set(key, newValue);
            try (Transaction<Integer, Integer> t3 = db.beingTransaction(t2)) {
                assertEquals("Child transaction should see all the changes that have happened in the parent thus far",
                        newValue, t3.get(key));
                assertEquals("Value in the main database should be at what it was as per the last committed transaction",
                        value, db.get(key));

                t3.set(key, 2000);
            }
            assertEquals("Changes made via committed child transaction should be preserved in the parent",
                    Integer.valueOf(2000), t2.get(key));
            t2.rollback();
            assertEquals("Rolling back a parent should also rollback any changes made by its committed child transactions",
                    value, t2.get(key));
        } catch (Exception e) {
            e.printStackTrace();
            fail(String.format("Unexpected exception! %s", e));
        }
    }

}