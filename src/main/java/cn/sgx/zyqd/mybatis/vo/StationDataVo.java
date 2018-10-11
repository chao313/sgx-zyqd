package cn.sgx.zyqd.mybatis.vo;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class StationDataVo {
    private Integer iLane; //车道号
    private Timestamp dtPassTime; //经过时间
    private Integer iTotalWeight; //总重
    private String szVehicleLicense; //车牌
}


