package cn.sgx.zyqd.mybatis.vo;

import lombok.Data;

import java.text.MessageFormat;

@Data
public class PicDataVo {
    private Integer id;
    private String picName;
    private byte[] picBin;
    private Long lastModified;//文件最后修改时间
    private boolean isSended;//1-false为未发送 ，0-true为发送
    private String yyyyMMddTimeFromName;//图片命名中年月日时间20181011
    private String szVehicleLicenseFromName;//图片命名中的车牌号

    public PicDataVo init() throws Exception {
        if (null == this.picName || null == picBin) {
            String message = MessageFormat.format("PicDataVo init失败{1}", this.toString());
            throw new Exception(message);
        } else {
            if (this.picName.contains("-")) {
                String[] split = picName.split("-");
                if (split.length >= 2) {
                    this.yyyyMMddTimeFromName = split[0];//时间
                    this.szVehicleLicenseFromName = split[1];//车牌
                }

            }
        }
        return this;
    }

    @Override
    public String toString() {
        return "PicDataVo{" +
                "id=" + id +
                ", picName='" + picName + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }
}
