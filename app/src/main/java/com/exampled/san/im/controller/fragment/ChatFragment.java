
package com.exampled.san.im.controller.fragment;


import android.content.Intent;

import com.exampled.san.im.controller.activity.ChatActivity;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.List;

/**
 * Created by San on 2016/11/9.
 */
public class ChatFragment extends EaseConversationListFragment {
    @Override
    protected void initView() {
        super.initView();
        //跳转到会话页面
        setConversationListItemClickListener(new EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                //传递参数
                intent.putExtra(EaseConstant.EXTRA_USER_ID,conversation.conversationId());
                //是否是群聊
                if(conversation.getType()==EMConversation.EMConversationType.GroupChat){
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_GROUP);
                }
                startActivity(intent);
            }
        });
        conversationList.clear();
         //监听绘画消息
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
    }
    private EMMessageListener emMessageListener=new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            //设置数据
            EaseUI.getInstance(). getNotifier().onNewMesg(list);
            //更新数据
            refresh();
        }
        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {
        }
        @Override
        public void onMessageReadAckReceived(List<EMMessage> list) {
        }
        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> list) {
        }
        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {

        }
    };




}
