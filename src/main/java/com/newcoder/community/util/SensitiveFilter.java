package com.newcoder.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiuxiaoran
 * @date 2022/4/27 10:10
 * 敏感词过滤器
 */
@Component
public class SensitiveFilter {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    //替换符
    private static final String REPLACEMENT = "***";

    //初始化根节点
    private TireNode root = new TireNode();

    @PostConstruct   //容器实例化这个对象时被自动调用
    public void init(){
        try {
            //字符流
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
            //缓冲流
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
            String keywords;
            while ((keywords = reader.readLine())!=null){
                //把敏感词添加到前缀树中
                this.addKeyWords(keywords);
            }
        }catch (Exception e){
            logger.error("加载敏感词文件失败"+e);
        }finally {

        }
    }

    /*
           text : 带过滤的文本
           返回的是未曾过滤的文本
     */
    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }
        //指针1
        TireNode tempNode = root;
        //指针2
        int begin =0 ;
        int position = 0;
        //存储结果
        StringBuilder sb = new StringBuilder();
        while(position<text.length()){
            char c = text.charAt(position);
            //处理特殊符号
            if(isSymbol(c)){
                //如果指针1处于根节点，直接加
                if(tempNode == root){
                    sb.append(c);
                    begin++;
                }
                position++;
                continue;  // 直接进入下一轮循环
            }
            //检查这个字符是否是可能的敏感词开头，如果不是可疑的敏感词汇tempNode为Null
            tempNode = tempNode.getSubNode(c);
            //如果为 null，说明c不是敏感词，可以加入处理后的字符串
            if(tempNode==null){
                sb.append(text.charAt(begin)); // 进入下一个位置
                position = ++begin;   //加入后两者同时后移，并且将其归位为root
                tempNode = root;
                //发现敏感词
            }else if(tempNode.isKeywordEnd){
                //将敏感词屏蔽掉
                sb.append(REPLACEMENT);
                //进入下一个位置
                begin = ++position;
                //重新指向根节点
                tempNode = root;
            }else{
                position++;
            }
        }
        //将最后一批字符记录下来
        sb.append(text.substring(begin));
        return sb.toString();
    }

    //判断是否为特殊符号
    private boolean isSymbol(Character c){
        //判断是否是大写字母，小写字母，数字，其他的过滤掉
        //0x2E80-0x9FFF 是东亚文字，不可以被过滤
        return !CharUtils.isAsciiAlphanumeric(c) &&(c<0x2E80 || c>0x9FFF);
    }

    //将敏感词添加到前缀树中
    private void addKeyWords(String keywords){
        TireNode tempNode = root;
        for(int i=0;i<keywords.length();i++){
            char c = keywords.charAt(i);
            TireNode subNode = tempNode.getSubNode(c);
            if(subNode==null){
                //初始化子节点
                subNode = new TireNode();
                tempNode.addSubNode(c,subNode);
            }
            //指针指向子节点继续
            tempNode = subNode;
            //设置结束标记
            if(i==keywords.length()-1){
                tempNode.setKeywordEnd(true);
            }
        }
    }

    private class TireNode{
        //构造前缀树

        //敏感词结尾标识
        private boolean isKeywordEnd = false;  //标识结尾

        //当前节点的子节点，可能有多个，是多叉树
        //存储的是子节点的字符和子节点
        private Map<Character,TireNode> subNode = new HashMap<>();


        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        //添加子节点
        public void addSubNode(Character c,TireNode node){
            subNode.put(c,node);
        }

        //获取子节点
        public TireNode getSubNode(Character c){
            return subNode.get(c);
        }
    }
}
