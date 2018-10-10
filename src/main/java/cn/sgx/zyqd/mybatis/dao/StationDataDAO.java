package cn.sgx.zyqd.mybatis.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import cn.sgx.zyqd.mybatis.vo.StationDataVo;

import java.util.List;

@Component
public interface StationDataDAO {

    List<StationDataVo> queryByITotalWeight(@Param(value = "limitWeight") Integer limitWeight);

}
