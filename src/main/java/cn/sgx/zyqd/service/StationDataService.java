package cn.sgx.zyqd.service;

import cn.sgx.zyqd.mybatis.dao.StationDataDAO;
import cn.sgx.zyqd.mybatis.vo.StationDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StationDataService {

    @Autowired
    private StationDataDAO dao;

    public StationDataVo queryById(Integer id) {
        return dao.queryById(id);
    }

    public Integer insert(StationDataVo cat) {
        return dao.insert(cat);
    }

    public Integer update(StationDataVo cat) {
        return dao.updateById(cat);
    }

    public Integer delete(Integer id) {
        return dao.deleteById(id);
    }

}
