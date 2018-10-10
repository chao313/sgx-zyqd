package cn.sgx.zyqd.mybatis.vo;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class StationDataVo {
    Integer iLane; //车道号
    Timestamp dtPassTime; //经过时间
    Integer iTotalWeight; //总重
    String szVehicleLicense; //车牌
}


