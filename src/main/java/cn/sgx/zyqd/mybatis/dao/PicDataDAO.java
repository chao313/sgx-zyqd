package cn.sgx.zyqd.mybatis.dao;

import cn.sgx.zyqd.mybatis.vo.PicDataVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PicDataDAO {

    int insert(PicDataVo vo);

    List<PicDataVo> get();

    List<String> getTodayPicNames();

    int updateStatusByID(@Param(value = "id") Integer id);

    void deleteBeforeyyyyMMddDay(@Param(value = "day") String day);
}
