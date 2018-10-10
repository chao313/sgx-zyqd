package cn.sgx.zyqd.mybatis.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import cn.sgx.zyqd.mybatis.vo.StationDataVo;

@Component
public interface StationDataDAO {

    StationDataVo queryById(@Param(value = "catId") Integer id);

    Integer insert(StationDataVo cat);

    Integer updateById(StationDataVo cat);

    Integer deleteById(@Param(value = "catId") Integer id);

}
