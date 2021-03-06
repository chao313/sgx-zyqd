package cn.sgx.zyqd.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Component
public class FreemarkUtil {

    private static Configuration configuration = new Configuration();

    @Value(value = "${socket.xml.path}")
    private String host;

    public StringBuffer generateXmlByTemplate(Map<String, Object> map, String templatePath)
            throws IOException, TemplateException {
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassicCompatible(true);
        File file = new File(host);
        configuration.setDirectoryForTemplateLoading(file);
        Template template =
                configuration.getTemplate(templatePath); //文件名
        java.io.StringWriter
                stringWriter = new StringWriter();
        template.process(map, stringWriter);
        return stringWriter.getBuffer();
    }

}
