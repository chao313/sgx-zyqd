package cn.sgx.zyqd.mybatis.vo;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
public class PicAndStationVo {
    private Integer id;//picData的编号
    private Integer iLane; //车道号
    private Timestamp dtPassTime; //经过时间
    private Integer iTotalWeight; //总重
    private String szVehicleLicense; //车牌
    private String picBin;//图片数据

    @Override
    public String toString() {
        return "PicAndStationVo{" +
                "iLane=" + iLane +
                ", dtPassTime=" + dtPassTime +
                ", iTotalWeight=" + iTotalWeight +
                ", szVehicleLicense='" + szVehicleLicense + '\'' +
                '}';
    }
}
