package com.newcoder.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author xiuxiaoran
 * @date 2022/4/30 22:19
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisTest {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testStrings(){
        String redisKey = "test:count";
        redisTemplate.opsForValue().set(redisKey,1);
//        System.out.println(redisTemplate.opsForValue().get(redisKey));
//        System.out.println(redisTemplate.opsForValue().increment(redisKey));

        //访问hash
//        String redisKey1 = "test:user";
//        redisTemplate.opsForHash().put("xiu","id",1);
//        redisTemplate.opsForHash().put("xiu","username","xiuxiu");
//
//        System.out.println(redisTemplate.opsForHash().get("xiu","id"));
//        System.out.println(redisTemplate.opsForHash().get("xiu","username"));

        //测试列表
//        String rediskey2 = "test:ids";
//        redisTemplate.opsForList().leftPush(rediskey2,101);
//        redisTemplate.opsForList().leftPush(rediskey2,102);
//        redisTemplate.opsForList().leftPush(rediskey2,103);
//
//        System.out.println(redisTemplate.opsForList().size(rediskey2));
//        System.out.println(redisTemplate.opsForList().index(rediskey2,0));


        //测试集合
        String redisKey3 = "test:teachers";
        redisTemplate.opsForSet().add(redisKey3,1,2,3,4,5);

        System.out.println(redisTemplate.opsForSet().size(redisKey3));
        System.out.println(redisTemplate.opsForSet().members(redisKey3));
    }

    @Test
    public void testSortedSets(){
        String redisKey = "test:Students";
        redisTemplate.opsForZSet().add(redisKey,"s1",30);
        redisTemplate.opsForZSet().add(redisKey,"s2",50);
        redisTemplate.opsForZSet().add(redisKey,"s3",80);
        redisTemplate.opsForZSet().add(redisKey,"s4",40);
        System.out.println(redisTemplate.opsForZSet().zCard(redisKey));
    }

    @Test   //多次访问同一个key ,将对象绑定到一个key上面
    public void testBoundKey(){
        String key = "test:count";
        BoundValueOperations operations = redisTemplate.boundValueOps(key);
        System.out.println(operations.increment());
    }

    //演示编程式事务
    @Test
    public void TestTrans(){
        Object object = redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations operations) throws DataAccessException {
                  String key = "test:tx";
                  operations.multi();  //事务开启
                  operations.opsForSet().add(key,"111");
                  operations.opsForSet().add(key,"222");
                  System.out.println(operations.opsForSet().members(key));  //中间查询结果无效
                  return operations.exec();   //事务结束
            }
        });
        //返回的对象是[1,1,,[111,222]] 1表示每次操作影响的行数，[111,222]表示最后整体的数值
        System.out.println(object);
    }

    //KyperLoglog
    @Test
    public void testHyperLogLog(){
        String rediskey = "test:hll:01";
        for (int i = 1; i <= 1000 ; i++) {
            redisTemplate.opsForHyperLogLog().add(rediskey,i);
        }

        //重复
        for (int i = 1; i <= 1000 ; i++) {
            int r = (int) (Math.random()*1000+1);
            redisTemplate.opsForHyperLogLog().add(rediskey,r);
        }
        System.out.println(redisTemplate.opsForHyperLogLog().size(rediskey));
    }

    @Test
    public void testHyperLogUnion(){
        String redisKey2 = "test:hll:02";
        for (int i = 1; i <= 1000 ; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey2,i);
        }

        String redisKey3 = "test:hll:03";
        for (int i = 501; i <= 1500 ; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey3,i);
        }

        String redisKey4 =  "test:hll:04";
        for (int i = 1001; i <= 2000 ; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey4,i);
        }

        String unioinKey ="test:hll:union";
        redisTemplate.opsForHyperLogLog().union(unioinKey,redisKey2,redisKey3,redisKey4);

        long size = redisTemplate.opsForHyperLogLog().size(unioinKey);
        System.out.println(size);
    }


    //统计一组数据的布尔值
    @Test
    public void testBitMap(){
        String redisKey = "test:bm:01";
        redisTemplate.opsForValue().setBit(redisKey,1,true);
        redisTemplate.opsForValue().setBit(redisKey,4,true);
        redisTemplate.opsForValue().setBit(redisKey,7,true);
        System.out.println(redisTemplate.opsForValue().getBit(redisKey,0));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey,1));

        //统计
        Object obj = redisTemplate.execute(new RedisCallback() {

            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.bitCount(redisKey.getBytes());
            }
        });
        System.out.println(obj);
    }
}
