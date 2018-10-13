package cn.sgx.zyqd.service;

import cn.sgx.zyqd.mybatis.dao.PicAndStationDAO;
import cn.sgx.zyqd.mybatis.dao.StationDataDAO;
import cn.sgx.zyqd.mybatis.vo.PicAndStationVo;
import cn.sgx.zyqd.mybatis.vo.StationDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PicAndStationService {

    @Autowired
    private PicAndStationDAO dao;

    public List<PicAndStationVo> queryByITotalWeight(Integer limitWeight) {
        return dao.queryByITotalWeight(limitWeight);
    }


}
