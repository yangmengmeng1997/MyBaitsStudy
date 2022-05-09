package com.newcoder.community.acurator;

import com.newcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author xiuxiaoran
 * @date 2022/5/8 22:40
 * 自定义监控方式,模拟监控方式，然后就是配置权限只有管理员才可以登录
 */

@Component
@Endpoint(id = "database")
public class DataBaseEndPoint {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseEndPoint.class);

    @Autowired
    private DataSource datasource;  //IDEA报错，运行不报错，麻了

    @ReadOperation
    public String checkConnection() {
        try (
                Connection conn = datasource.getConnection();
        ) {
            return CommunityUtil.getJSONString(0, "获取连接成功!");
        } catch (SQLException e) {
            logger.error("获取连接失败:" + e.getMessage());
            return CommunityUtil.getJSONString(1, "获取连接失败!");
        }
    }
}
