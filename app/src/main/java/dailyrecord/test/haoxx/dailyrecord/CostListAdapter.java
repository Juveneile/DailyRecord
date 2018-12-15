package dailyrecord.test.haoxx.dailyrecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CostListAdapter extends BaseAdapter {
    private List<CostBean> mList;
    private Context mContext;
    //解析布局时使用
    private LayoutInflater mLayoutInflater;

    public CostListAdapter(Context context, List<CostBean> list) {
        this.mContext = context;
        this.mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //获取viewHolder
        //如果convertView为空就需要初始化一个ViewHolder
        if (convertView == null) {
            viewHolder = new ViewHolder();
            //导入布局
            convertView = mLayoutInflater.inflate(R.layout.list_item, null);
            viewHolder.mTvCostTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.mTvCostDate = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.mTvCostMoney = (TextView) convertView.findViewById(R.id.tv_cost);
            //标志返回一个布局对象
            convertView.setTag(viewHolder);
        } else {
            //如果已经有这个布局就是直接取出来
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //把数据放入viewHolder
        CostBean bean = mList.get(position);
        viewHolder.mTvCostTitle.setText(bean.getCostTitle());
        viewHolder.mTvCostDate.setText(bean.getCostDate());
        viewHolder.mTvCostMoney.setText(bean.getCostMoney());

        return convertView;
    }

    //私有静态内部类
    private static class ViewHolder {
        public TextView mTvCostTitle;
        public TextView mTvCostDate;
        public TextView mTvCostMoney;
    }
}
