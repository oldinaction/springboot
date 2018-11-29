package cn.aezo.springboot.exe4jaccessdb;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "myexe")
@Data
public class Config {
    private String dbpath;
}
