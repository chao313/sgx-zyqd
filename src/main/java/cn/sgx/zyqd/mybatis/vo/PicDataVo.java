package cn.sgx.zyqd.mybatis.vo;

import lombok.Data;

@Data
public class PicDataVo {
    private Integer id;
    private String picName;
    private byte[] picBin;
    private Long lastModified;//文件最后修改时间

    @Override
    public String toString() {
        return "PicDataVo{" +
                "id=" + id +
                ", picName='" + picName + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }
}
