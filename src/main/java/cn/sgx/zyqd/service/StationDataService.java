package cn.sgx.zyqd.service;

import cn.sgx.zyqd.mybatis.dao.StationDataDAO;
import cn.sgx.zyqd.mybatis.vo.StationDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StationDataService {

    @Autowired
    private StationDataDAO dao;

    public List<StationDataVo> queryByITotalWeight(Integer limitWeight) {
        return dao.queryByITotalWeight(limitWeight);
    }


}
