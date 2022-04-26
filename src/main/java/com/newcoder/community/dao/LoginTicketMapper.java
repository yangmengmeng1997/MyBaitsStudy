package com.newcoder.community.dao;

import com.newcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @author xiuxiaoran
 * @date 2022/4/25 12:22
 * 不再使用mapper的配置文件来进行sql书写，而是采用注解的方式生成
 * 感觉不如xml
 */
@Mapper
public interface LoginTicketMapper {

    @Insert(
            {
                    "insert into login_ticket(user_id,ticket,status,expired) " ,
                    "values(#{userId},#{ticket},#{status},#{expired}) "
            }
    )
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertLogin(LoginTicket loginTicket);


    //只按照ticket查询即可--类似于cookie
    @Select(
            {
                    "select id,user_id,ticket,status,expired ",
                    "from `login_ticket` ",
                    "where ticket=#{ticket} "
            }
    )
    LoginTicket selectByTicket(String ticket);

    //修改凭证状态 退出时凭证失效
    //先根据ticket查找，再更新数据
    @Update({
            "update login_ticket set status=#{status} ",
            "where ticket=#{ticket}"
    })
    int updateStatus(String ticket,int status);
}
