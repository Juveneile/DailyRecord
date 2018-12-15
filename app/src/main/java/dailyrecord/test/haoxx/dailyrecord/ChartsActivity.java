package dailyrecord.test.haoxx.dailyrecord;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class ChartsActivity extends Activity {
    private LinearLayout linearLayout;
    private LineChartView mChart;
    //数据源，使用TreeMap默认排序
    private Map<String, Integer> table = new TreeMap<>();
    private LineChartData mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);
        mData = new LineChartData();
        linearLayout = (LinearLayout)findViewById(R.id.chartLayout);
        mChart = (LineChartView) findViewById(R.id.chart);
        //获取传递过来的数据
        List<CostBean> allData = (List<CostBean>) getIntent().getSerializableExtra("cost_list");
        generateValues(allData);
        generateData();
        mChart = new LineChartView(this);
        mChart.setLineChartData(mData);
        linearLayout.addView(mChart);
    }

    private void generateData() {
        //lines 存储画的线，一张图可能有多个线，此处先存了一条
        List<Line> lines = new ArrayList<>();
        //处理点
        List<PointValue> values = new ArrayList<>();
        int indexX = 0;
        for (Integer value : table.values()) {
            values.add(new PointValue(indexX,value));
            indexX++;
        }
        //处理线
        //把点连成线
        Line line = new Line(values);
        //设置线的属性
        line.setColor(ChartUtils.COLORS[0]);
        line.setShape(ValueShape.CIRCLE);
        line.setPointColor(ChartUtils.COLORS[3]);
        lines.add(line);
        mData.setLines(lines);
    }

    private void generateValues(List<CostBean> allData) {
        if (allData != null) {
            //遍历处理整个数据源，把重复天的花费累加到一起
            for (int i = 0; i < allData.size(); i++) {
                CostBean costBean = allData.get(i);
                String costDate = costBean.getCostDate();
                int costMoney = Integer.parseInt(costBean.getCostMoney());
                //如果table中还没有这一天就增加一个key,如果有就取出花费与当前的花费相加
                if (!table.containsKey(costDate)) {
                    table.put(costDate, costMoney);
                } else {
                    int originMoney = table.get(costDate);
                    table.put(costDate, costMoney + originMoney);
                }
            }
        }
    }
}
