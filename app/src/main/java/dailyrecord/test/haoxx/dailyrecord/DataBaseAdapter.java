package dailyrecord.test.haoxx.dailyrecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DataBaseAdapter {

    private DataBaseHelper dataBaseHelper;
    //Context
    private Context context;
    //SQLiteDatabase;
    private SQLiteDatabase sqLiteDatabase;

    //定义一个参数的构造器
    public DataBaseAdapter(Context context) {
        this.context = context;
    }
    /**
     * 定义一个打开数据库的方法
     */
    public void open() {
        dataBaseHelper = new DataBaseHelper(context);
        try {
            sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        } catch (Exception e) {
            // TODO: handle exception
            sqLiteDatabase = dataBaseHelper.getReadableDatabase();
            Log.i("open-->", e.toString());
        }
    }
    /**
     * 关闭数据库
     */
    public void close() {
        sqLiteDatabase.close();
    }

    /**
     * 插入语句
     *
     * @param costBean
     */
    public void insertCost(CostBean costBean) {
        //获取数据库对象
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        //键值映射
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.COST_TITLE, costBean.getCostTitle());
        contentValues.put(DataBaseHelper.COST_DATE, costBean.getCostDate());
        contentValues.put(DataBaseHelper.COST_MONEY, costBean.getCostMoney());
        sqLiteDatabase.insert(DataBaseHelper.Table_Name, null, contentValues);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public Cursor getAllCursor() {
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        //columns 填 null 意味着获取所有字段
        return sqLiteDatabase.query(DataBaseHelper.Table_Name, null, null, null, null, null, DataBaseHelper.COST_DATE + " DESC");
    }
    public void deleteAllData() {
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(DataBaseHelper.Table_Name, null, null);
    }

    public void deleteData(String cost_title, String cost_date, String cost_money) {
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        String whereClause = DataBaseHelper.COST_TITLE + "= '" + cost_title + "' and " + DataBaseHelper.COST_DATE + "= '" + cost_date + "' and " + DataBaseHelper.COST_MONEY + "= '" + cost_money + "'";
        sqLiteDatabase.delete(DataBaseHelper.Table_Name, whereClause, null);
    }
}
