package com.yiwen.uipractice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> msgList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout_left;
        LinearLayout linearLayout_right;
        TextView textView_left;
        TextView textView_right;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout_left = (LinearLayout) itemView.findViewById(R.id.layout_left);
            linearLayout_right = (LinearLayout) itemView.findViewById(R.id.layout_right);
            textView_left = (TextView) itemView.findViewById(R.id.left_msg);
            textView_right = (TextView) itemView.findViewById(R.id.right_msg);
        }
    }

    public MsgAdapter(List<Msg> msgList) {
        this.msgList = msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = msgList.get(position);
        if (msg.getType() == Msg.TYPE_RECEIVED) {
            holder.linearLayout_left.setVisibility(View.VISIBLE);
            holder.linearLayout_right.setVisibility(View.GONE);
            holder.textView_left.setText(msg.getContent());
        } else if (msg.getType() == Msg.TYPE_SEND){
            holder.linearLayout_left.setVisibility(View.GONE);
            holder.linearLayout_right.setVisibility(View.VISIBLE);
            holder.textView_right.setText(msg.getContent());
        }

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
