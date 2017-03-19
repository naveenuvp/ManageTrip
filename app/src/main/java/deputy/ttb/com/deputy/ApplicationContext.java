package deputy.ttb.com.deputy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import deputy.ttb.com.deputy.GreenDaoDB.DaoMaster;
import deputy.ttb.com.deputy.GreenDaoDB.DaoSession;
import deputy.ttb.com.deputy.Utils.Utility;

/**
 * Created by naveenu on 19/03/2017.
 */

public class ApplicationContext {
    private Context appContext  =   null;
    private DaoMaster.DevOpenHelper helper  =   null;
    private DaoMaster daoMaster =   null;
    private DaoSession daoSession   ;

    private ApplicationContext(){}

    public void init(Context context){
        if(appContext == null){
            appContext              =   context;
            helper                  =   new DaoMaster.DevOpenHelper(appContext, Utility.GREEN_DAO_DB_NAME);
            SQLiteDatabase db       =   helper.getWritableDatabase();
            daoMaster               =   new DaoMaster(db);
            daoSession              =   daoMaster.newSession();
        }
    }

    private Context getContext(){
        return appContext;
    }

    public static Context get(){
        return getInstance().getContext();
    }

    private static ApplicationContext instance;

    public static ApplicationContext getInstance(){
        return instance == null ?
                (instance = new ApplicationContext()):
                instance;
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }
}
