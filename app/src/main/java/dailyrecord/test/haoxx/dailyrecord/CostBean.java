package dailyrecord.test.haoxx.dailyrecord;

import java.io.Serializable;

/**
 * 存储数据的javaBean
 * Serializable使其可序列化，在活动间传递
 */
public class CostBean implements Serializable {

    private String costTitle;
    private String costDate;
    private String costMoney;

    public String getCostTitle() {
        return costTitle;
    }

    public void setCostTitle(String costTitle) {
        this.costTitle = costTitle;
    }

    public String getCostDate() {
        return costDate;
    }

    public void setCostDate(String costDate) {
        this.costDate = costDate;
    }

    public String getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(String costMoney) {
        this.costMoney = costMoney;
    }
}
