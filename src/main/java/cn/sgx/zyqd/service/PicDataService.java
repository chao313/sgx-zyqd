package cn.sgx.zyqd.service;

import cn.sgx.zyqd.mybatis.dao.PicDataDAO;
import cn.sgx.zyqd.mybatis.vo.PicDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PicDataService {

    @Autowired
    private PicDataDAO dao;

    public boolean savePicDataVos(List<PicDataVo> vos) {
        for (int i = 0; i < vos.size(); i++) {
            dao.insert(vos.get(i));
        }
        return true;
    }

    public List<PicDataVo> get() {
        return dao.get();
    }


}
