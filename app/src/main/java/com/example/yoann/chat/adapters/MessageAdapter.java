package com.example.yoann.chat.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yoann.chat.R;
import com.example.yoann.chat.utils.CryptUtil;
import com.example.yoann.chat.activities.ChatActivity;
import com.example.yoann.chat.models.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private static final int DEFAULT_CARD_VIEW = 0;
    private static final int OWN_CARD_VIEW = 1;

    private List<Message> mData;
    private String currentUserEmail;
    private View view;

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

                String messageKey = mData.get(position).getKey();
                ChatActivity.showAlertDialog(messageKey);
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

        ViewHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.username);
            textView2 = (TextView) itemView.findViewById(R.id.message);
            image = (ImageView) itemView.findViewById(R.id.avatar);

        }

        public void setData(Message message) {
            textView1.setText(message.getUserName() + " - "+ message.getDate());
            textView2.setText(message.getContent());

            String lienImage = "https://www.gravatar.com/avatar/" +  CryptUtil.md5(message.getUserEmail());

            Glide
                    .with(image.getContext())
                    .load(lienImage)
                    .apply(RequestOptions.circleCropTransform())
                    .into(image);
                
        }


    }


}
