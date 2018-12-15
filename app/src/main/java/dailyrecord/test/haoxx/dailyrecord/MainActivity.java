package dailyrecord.test.haoxx.dailyrecord;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView costList;
    private List<CostBean>  mCostBeanList = new ArrayList<>();;
    private CostListAdapter costListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initData();
        initVew();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建一个AlertDialog.Builder对象
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                //加载布局
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View viewDialog = inflater.inflate(R.layout.new_cost_data,null);
                final EditText title = (EditText) viewDialog.findViewById(R.id.et_cost_title);
                final EditText money = (EditText) viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker date = (DatePicker)viewDialog.findViewById(R.id.dp_cost_content);
                //把布局塞进去
                builder.setView(viewDialog);
                builder.setTitle("New Cost");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CostBean costBean = new CostBean();
                        costBean.setCostTitle(title.getText().toString());
                        costBean.setCostMoney(money.getText().toString());
                        costBean.setCostDate(date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDayOfMonth());
                        DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(MainActivity.this);
                        dataBaseAdapter.open();
                        dataBaseAdapter.insertCost(costBean);
                        dataBaseAdapter.close();
                        initData();
                        costListAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.create().show();
            }
        });
        costList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int cur = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("您是否要删除此条记录？");
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String costTitle = mCostBeanList.get(cur).getCostTitle();
                        String costDate = mCostBeanList.get(cur).getCostDate();
                        String costMoney = mCostBeanList.get(cur).getCostMoney();
                        DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(MainActivity.this);
                        dataBaseAdapter.open();
                        dataBaseAdapter.deleteData(costTitle,costDate,costMoney);
                        dataBaseAdapter.close();
                        initData();
                        costListAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();
                return false;
            }
        });
    }

    private void initData() {
        mCostBeanList.clear();
        DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(this);
        dataBaseAdapter.open();
        Cursor cursor = dataBaseAdapter.getAllCursor();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CostBean costBean = new CostBean();
                costBean.setCostTitle(cursor.getString(cursor.getColumnIndex("cost_title")));
                costBean.setCostDate(cursor.getString(cursor.getColumnIndex("cost_date")));
                costBean.setCostMoney(cursor.getString(cursor.getColumnIndex("cost_money")));
                mCostBeanList.add(costBean);
            }
            cursor.close();
        }
        dataBaseAdapter.close();
    }

    private void initVew() {
        costList = (ListView) findViewById(R.id.lv_main);
        costListAdapter = new CostListAdapter(this, mCostBeanList);
        costList.setAdapter(costListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 点击菜单
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            /*//显示list图表
            Intent intent = new Intent(MainActivity.this,ChartsActivity.class);
            // 继承Serializable接口后可以序列化
            intent.putExtra("cost_list",(Serializable) mCostBeanList);
            startActivity(intent);*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
