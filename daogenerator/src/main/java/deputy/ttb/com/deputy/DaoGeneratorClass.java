package deputy.ttb.com.deputy;

import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class DaoGeneratorClass {
    private static final String PROJECT_DIR = System.getProperty("user.dir");

    public static void main(String[] args) {
        Schema schema = new Schema(1, "deputy.ttb.com.deputy.GreenDaoDB");
        schema.enableKeepSectionsByDefault();

        // Business Info schema creation
        businessInfo(schema);

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
    private static Entity businessInfo(final Schema schema){
        Entity entity   =   schema.addEntity("BusinessInfo");
        entity.addIdProperty().primaryKey();
        entity.addStringProperty("name").notNull().unique();
        entity.addStringProperty("logoUrl").notNull();
        return entity;
    }



}
