package deputy.ttb.com.deputy;

import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

/**
 * Created by naveenu on 17/03/2017.
 */

public class DaoGeneratorClass {
    private static final String PROJECT_DIR = System.getProperty("user.dir");

    public static void main(String[] args) {
        Schema schema = new Schema(1, "deputy.ttb.com.deputy.GreenDaoDB");
        schema.enableKeepSectionsByDefault();

        // Shift details schema creation
        shiftDetails(schema);
        try {
            new org.greenrobot.greendao.generator.DaoGenerator().generateAll(schema, PROJECT_DIR + "/app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function creates and Saves Business Info JSON Data
     *
     * @param schema
     * @return
     */
    private static Entity shiftDetails(final Schema schema){
        Entity entity   =   schema.addEntity("ShiftDetails");
        entity.addIdProperty().primaryKey();
        entity.addStringProperty("shiftDetails").notNull().unique();
        return entity;
    }
}
