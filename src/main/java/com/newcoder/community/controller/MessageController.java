package com.newcoder.community.controller;

import com.newcoder.community.entity.Message;
import com.newcoder.community.entity.Page;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.MessageService;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CommunityUtil;
import com.newcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author xiuxiaoran
 * @date 2022/4/28 21:22
 * 处理私信相关的类
 */
@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;  //需要求的是当前用户的私信
    //访问私信列表，还有验证这个功能是否正确
    @RequestMapping(path = "/letter/list",method = RequestMethod.GET)
    public String getLetterList(Model model, Page page){

        User user = hostHolder.getUser();
        //分页信息
        page.setLimit(5);
        page.setPath("/letter/list");
        page.setRows(messageService.findConversationCount(user.getId()));
        //会话列表
        List<Message> conversationsList = messageService.findConversations(user.getId(), page.getOffset(), page.getLimit());
        //遍历每一个会话，每一个会话里面有多个信息，就和评论里面有的信息一样一个评论有多个回复
        List<Map<String,Object>> conversations = new ArrayList<>();
        for(Message message:conversationsList){
            Map<String,Object> map = new HashMap<>();
            //存储会话信息,是每一个会话的最新的消息
            map.put("conversation",message);
            //单会话存储未读数量
            map.put("unreadCount",messageService.findLetterUnreadCount(user.getId(),message.getConversationId()));
            //包含几条消息
            map.put("letterCount",messageService.findLetterCount(message.getConversationId()));
            //获取目标Id，也就是与当前user通信的userId，需要显示头像等信息,
            //如果这里是当前用户的Id是信息的发送者，那么显示对应的接受者信息等
            int targetId = user.getId()==message.getFromId() ? message.getToId():message.getFromId();
            map.put("target",userService.findUserById(targetId));
            //得到的信息集合放到List列表中，一个map表示一行会话信息，里面有多个子信息
            conversations.add(map);
        }
        model.addAttribute("conversations",conversations);

        //查询未读消息的数量,是查询所有的未读消息
        int letterUnderReaderCount = messageService.findLetterUnreadCount(user.getId(),null);
        model.addAttribute("letterUnderReaderCount",letterUnderReaderCount);
        return "/site/letter";  //返回外面的会话列表
    }


    @RequestMapping(path = "/letter/detail/{conversationId}",method =RequestMethod.GET)
    public String getLetterDetail(@PathVariable("conversationId") String conversationId,Page page,Model model){
        User user = hostHolder.getUser();
        //分页信息
        page.setLimit(5);
        page.setPath("/letter/detail/"+conversationId);
        page.setRows(messageService.findLetterCount(conversationId));

        //里面封装的是和一个用户会话的多条消息，私信列表
        List<Message> lettersList = messageService.findLetters(conversationId, page.getOffset(), page.getLimit());
        //将会话信息都要封装起来存储好
        List<Map<String,Object>> letters = new ArrayList<>();

        if(lettersList!=null){
            for(Message message:lettersList){
                Map<String,Object> map = new HashMap<>();
                map.put("letter",message);  // 存储私信消息
                map.put("fromUser",userService.findUserById(message.getFromId()));  //存储私信我的用户
                letters.add(map);
            }
        }
        model.addAttribute("letters",letters);

        //我们将私信的未读消息设置为已读，一次性更新多条记录状态
        List<Integer> unreadIds= getUnreadLettersId(lettersList);
        if(!unreadIds.isEmpty()){
            messageService.readMessage(unreadIds);   //将维度消息状态设置为已读
        }

        //查询私信的用户
        User targetUser = getLetterTarget(conversationId);
        model.addAttribute("target",targetUser);
        return "/site/letter-detail";
    }
    //找到发送消息的用户，对应于当前用户的对立面
    private User getLetterTarget(String conversationId){
        String[] ids = conversationId.split("_");
        int d0 = Integer.parseInt(ids[0]);
        int d1 = Integer.parseInt(ids[1]);
        if(hostHolder.getUser().getId()==d0) return userService.findUserById(d1);
        else return userService.findUserById(d0);
    }

    //获取未读消息的ID
    private List<Integer> getUnreadLettersId(List<Message> letterList){
        List<Integer> ids = new ArrayList<>();

        if(letterList!=null){
            for(Message message:letterList){
                if(hostHolder.getUser().getId()==message.getToId() && message.getStatus()==0){
                    //如果当前用户是信息的接收者，那么将消息设置为已读,且消息处于0未读状态
                    ids.add(message.getId());  //获得未读消息的ID
                }
            }
        }
        return ids;
    }

    @RequestMapping(value = "/letter/send",method = RequestMethod.POST)
    @ResponseBody
    public String sendLetter(String toName,String content){

        User target = userService.findUserByName(toName);  //得到对话目标
        if(target==null){
            return CommunityUtil.getJSONString(1,"发送私信的用户不存在，发送失败！");
        }
        Message message = new Message();
        message.setFromId(hostHolder.getUser().getId());
        message.setToId(target.getId());
        if(message.getFromId()<message.getToId()){
            message.setConversationId(message.getFromId()+"_"+message.getToId());
        }else{
            message.setConversationId(message.getToId()+"_"+message.getFromId());
        } //拼接会话
        message.setContent(content);
        message.setCreateTime(new Date());
        messageService.addMessage(message);  //插入会话
        return CommunityUtil.getJSONString(0);
    }
}
