package cn.sgx.zyqd.mybatis.dao;

import cn.sgx.zyqd.mybatis.vo.PicDataVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PicDataDAO {

    boolean insert(PicDataVo vo);

    List<PicDataVo> get();

    List<String> getTodayPicNames();
}
