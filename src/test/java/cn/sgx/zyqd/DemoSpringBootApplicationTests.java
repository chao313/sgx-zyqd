package cn.sgx.zyqd;

import cn.sgx.zyqd.mybatis.vo.PicAndStationVo;
import cn.sgx.zyqd.service.HttpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoSpringBootApplicationTests {

    private Logger logger = LoggerFactory.getLogger(getClass());
//	http:
//	limit: 5
//	host: localhost:65500/chao

    @Value(value = "${http.limit}")
    private Integer limit;

    @Value(value = "${http.limitWeigeht}")
    private Integer limitWeigeht;
    @Value(value = "${http.host}")
    private String host;

    @Autowired
    HttpService httpService;


    @Test
    public void testHttpService() {
        try {
            List<PicAndStationVo> picAndStationVos =
                    httpService.getPicAndStationVoLimit(host, limitWeigeht, limit);
            logger.error("[ SUCCESS testHttpService getPicAndStationVoLimit ] vos: {} ",picAndStationVos);
        } catch (IOException e) {
            logger.error("[ ERROR testHttpService getPicAndStationVoLimit ] msg: {} ",e.getMessage(),e);
        }
    }

}
