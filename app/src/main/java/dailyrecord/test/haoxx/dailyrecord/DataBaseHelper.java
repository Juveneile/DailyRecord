package dailyrecord.test.haoxx.dailyrecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String COST_TITLE = "cost_title";
    public static final String COST_DATE = "cost_date";
    public static final String COST_MONEY = "cost_money";
    public static final String Table_Name = "mycost";
    private String Creat_Table_MyCost = "create table if not exists " + Table_Name + "(" +
            "id integer primary key ," +
            "cost_title varchar ," +
            "cost_date varchar ," +
            "cost_money varchar )";
    //自定义构造器

    /**
     * name 数据库名称
     *
     * @param context
     */
    public DataBaseHelper(Context context) {
        super(context, "MyDaily", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Creat_Table_MyCost);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
