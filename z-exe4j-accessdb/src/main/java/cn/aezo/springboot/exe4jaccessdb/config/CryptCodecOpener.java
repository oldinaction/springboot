package cn.aezo.springboot.exe4jaccessdb.config;

import com.healthmarketscience.jackcess.CryptCodecProvider;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import net.ucanaccess.jdbc.JackcessOpenerInterface;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * 连接加密access数据库时需要
 */
@Configuration
public class CryptCodecOpener implements JackcessOpenerInterface {
    public Database open(File fl, String pwd) throws IOException {
        DatabaseBuilder dbd = new DatabaseBuilder(fl);
        dbd.setAutoSync(false);
        dbd.setCodecProvider(new CryptCodecProvider(pwd));
        dbd.setReadOnly(false);
        return dbd.open();
    }
    // Notice that the parameter setting AutoSync=false is recommended with UCanAccess for performance reasons.
    // UCanAccess flushes the updates to disk at transaction end.
    // For more details about autosync parameter (and related tradeoff), see the Jackcess documentation.
}
