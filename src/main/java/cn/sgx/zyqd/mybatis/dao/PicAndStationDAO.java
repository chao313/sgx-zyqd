package cn.sgx.zyqd.mybatis.dao;

import cn.sgx.zyqd.mybatis.vo.PicAndStationVo;
import cn.sgx.zyqd.mybatis.vo.StationDataVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PicAndStationDAO {

    /**
     * 查询当天的数据
     * 查询未发送的数据
     *
     * @param limitWeight
     * @return
     */
    List<PicAndStationVo> queryByITotalWeight(@Param(value = "limitWeight") Integer limitWeight);


    /**
     * 查询当天的数据
     * 查询未发送的数据
     * 每次只查询limit数量
     *
     * @param limitWeight
     * @return
     */
    List<PicAndStationVo> queryByITotalWeightLimit(@Param(value = "limitWeight") Integer limitWeight,
                                                   @Param(value = "limit") Integer limit);


    /**
     * 查询当天的数据
     * 查询未发送的数据
     * 每次只查询limit数量
     * 查询黄牌的车牌
     * @return
     */
    List<PicAndStationVo> queryAllLimit(@Param(value = "limit") Integer limit);


}
