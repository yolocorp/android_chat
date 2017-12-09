package com.example.yoann.chat;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yoann on 27/11/2017.
 */



public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    static final int DEFAULT_CARD_VIEW = 0;
    static final int OWN_CARD_VIEW = 1;

    private List<Message> mData;
    private String currentUserEmail;
    View view;

    public MessageAdapter(List<Message> mData, String currentUserEmail) {
        this.mData = mData;
        this.currentUserEmail = currentUserEmail;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MessageAdapter.DEFAULT_CARD_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message, parent, false);
                break;
            case MessageAdapter.OWN_CARD_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message_right, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setData(mData.get(position));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Log.d("Tag", "je suis dans le long click");
                Log.d("TagId", mData.get(position).toString());

                ChatActivity.removeMessage();
                notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).getUserEmail().equals(this.currentUserEmail)) {
            return OWN_CARD_VIEW;
        }else {
            return DEFAULT_CARD_VIEW;
        }
    }

    public void setDatas(List<Message> message) {
        mData = message;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView1, textView2;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.username);
            textView2 = (TextView) itemView.findViewById(R.id.message);
            image = (ImageView) itemView.findViewById(R.id.avatar);
        }

        public void setData(Message message) {
            textView1.setText(message.userName + " - "+ message.getDate());
            textView2.setText(message.content);
        }


    }
}
