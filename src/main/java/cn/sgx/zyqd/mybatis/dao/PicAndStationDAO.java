package cn.sgx.zyqd.mybatis.dao;

import cn.sgx.zyqd.mybatis.vo.StationDataVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PicAndStationDAO {

    List<StationDataVo> queryByITotalWeight(@Param(value = "limitWeight") Integer limitWeight);

}
