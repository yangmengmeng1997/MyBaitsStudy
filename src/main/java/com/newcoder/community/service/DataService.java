package com.newcoder.community.service;

import com.newcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author xiuxiaoran
 * @date 2022/5/7 15:06
 * 数据处理Service
 */
@Service
public class DataService {
    @Autowired
    private RedisTemplate redisTemplate;

    private SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    //记录请求
    //记录UV
    public void recordUV(String ip){
        String redisKey = RedisKeyUtil.getUVKey(format.format(new Date()));
        redisTemplate.opsForHyperLogLog().add(redisKey,ip);
    }

    //统计区间范围内的UV
    public long calculateUV(Date start,Date end){
        if(start==null || end==null) throw new IllegalArgumentException("区间不能为空");
        //获取区间范围内的key
        List<String> keyList = new ArrayList<>();
        //使用Calendar 来比较日期大小
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while(!calendar.getTime().after(end)){
            String key =  RedisKeyUtil.getUVKey(format.format(calendar.getTime()));
            keyList.add(key);
            calendar.add(Calendar.DATE,1);
        }
        //何并这些数据的key
        String redisKey = RedisKeyUtil.getUVKey(format.format(start),format.format(end));
        redisTemplate.opsForHyperLogLog().union(redisKey,keyList.toArray());

        //返回结果就好了
        return redisTemplate.opsForHyperLogLog().size(redisKey);
    }

    //记录DAU,根据用户的ID来查询
    public void recordDAU(int userId){
        String redisKey = RedisKeyUtil.getDAUKey(format.format(new Date()));
        redisTemplate.opsForValue().setBit(redisKey,userId,true);  //userId位置处设立为false
    }
    //统计区间范围内的DAU
    public long calculateDAU(Date start,Date end){
        if(start==null || end==null) throw new IllegalArgumentException("区间不能为空");
        //获取区间范围内的key
        List<byte[]> keyList = new ArrayList<>();
        //使用Calendar 来比较日期大小
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while(!calendar.getTime().after(end)){
            String key = RedisKeyUtil.getDAUKey(format.format(calendar.getTime()));
            keyList.add(key.getBytes());  //转换为字节数组
            calendar.add(Calendar.DATE,1);
        }

        //进行or运算
        return (long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                String redisKey = RedisKeyUtil.getDauKey(format.format(start),format.format(end));
                redisConnection.bitOp(RedisStringCommands.BitOperation.OR,
                        redisKey.getBytes(),keyList.toArray(new byte[0][0]));
                return redisConnection.bitCount(redisKey.getBytes());
            }
        });
    }
}
