package cn.sgx.zyqd.service;

import cn.sgx.zyqd.framework.Code;
import cn.sgx.zyqd.framework.Response;
import cn.sgx.zyqd.mybatis.vo.PicAndStationVo;
import cn.sgx.zyqd.util.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HttpService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String getPicAndStationVoLimitURL = "/picAndStation/queryByITotalWeightLimit";

    private String updatePicDataByIdURL = "/picAndStation/updateStatusByID";

    public List<PicAndStationVo> getPicAndStationVoLimit(String host, Integer limitWeight, Integer limit, String dataSource) throws IOException {
        String jsonStr = HttpClientUtils.doGet(host + getPicAndStationVoLimitURL + "/" + limitWeight + "/" + limit + "/" + dataSource, null);
        List<PicAndStationVo> picAndStationVos = new ArrayList<>();
        Response<List<PicAndStationVo>> response =
                JSON.parseObject(jsonStr, new TypeReference<Response<List<PicAndStationVo>>>() {
                });

        if (response.getCode().equals(Code.System.OK)) {
            logger.info("HttpService:getPicAndStationVoLimit -> SUCCCESS; vos:{}", response.getContent());
            picAndStationVos = response.getContent();
        } else if (response.getCode().equals(Code.System.FAIL)) {
            logger.error("HttpService:getPicAndStationVoLimit -> FAIL; message:{}", response.getMsg());
        }

        return picAndStationVos;
    }

    public Boolean updatePicDataById(String host, Integer id, String dataSource) throws IOException {
        String jsonStr = HttpClientUtils.doGet(host + updatePicDataByIdURL + "/" + id + "/" + dataSource, null);
        Boolean bool = false;
        Response<Boolean> response =
                JSON.parseObject(jsonStr, new TypeReference<Response<Boolean>>() {
                });
        if (Code.System.OK.equals(response.getCode()) ) {
            logger.info("HttpService:updatePicDataById -> SUCCCESS; bool:{}", response.getContent());
            bool = response.getContent();
        } else if ( Code.System.FAIL.equals(response.getCode())) {
            logger.error("HttpService:updatePicDataById -> FAIL; message:{}", response.getMsg());

        }
        return bool;
    }


}
