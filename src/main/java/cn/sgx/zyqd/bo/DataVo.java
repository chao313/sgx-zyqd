package cn.sgx.zyqd.bo;

import cn.sgx.zyqd.util.DateUtils;
import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.MessageFormat;

@Data
@ToString
public class DataVo {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String roadID; //车道号
    private Timestamp dtPassTime; //经过时间
    private String weightNum; //总重
    private String szVehicleLicense; //车牌
    private String no;//站数据包序号,可转化为整数的8字节文本，可以每次加1，调试用点00000005
    private String stationID;//站点
    private String systemNO;//32111100600120181011000110750000 ***  点编码+2位车道+时间精确到毫秒紧缩格式+"000"
    //  private String systemNO;//321111006002201401031915530000
//  private String systemNO;//32012500400220170714134617767000
//  private String systemNO;//32111100600220140103191553000000
    private String workDate;//"2018-10-11";
    private String passTime;//"2018-10-11 00:01:10.750"
    private DecimalFormat decimalFormat;


    private static int i = 11;

    public DataVo init(String stationID, Integer iLane, Integer iTotalWeight) throws Exception {

        if (null == iLane || null == dtPassTime || null == iTotalWeight || null == szVehicleLicense) {
            String message = MessageFormat.format("DataVo init失败{1}", this.toString());
            throw new Exception(message);
        }

        this.roadID = String.valueOf(iLane).substring(2); //去掉102 中的1
        this.weightNum = String.valueOf(iTotalWeight);
        this.stationID = stationID;
        this.workDate = DateUtils.getFormatDateTime(this.dtPassTime, DateUtils.DATE_FORMAT);
        this.passTime = DateUtils.getFormatDateTime(this.dtPassTime, DateUtils.TIMESTAMP_LONG_FORMAT);
        this.systemNO = this.stationID + this.roadID + DateUtils.getFormatDateTime(this.dtPassTime,
                DateUtils.DATE_TIMESTAMP_LONG_LONG_FORMAT) + "000";
        this.no = this.generateNo();

        return this;
    }

    private String generateNo() {
        if (null == decimalFormat) {
            decimalFormat = new DecimalFormat("00000000");
        }
        return decimalFormat.format(i);
    }
}
