package com.example.edwinmperazaduran.instagramphotoviewer;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    private static class ViewHolder{
        TextView tvUsername;
        TextView tvTimeStamp;
        TextView tvNumlikes;
        TextView tvCaptionUsername;
        TextView tvCaption;
        TextView tvComments;
        TextView tvMoreComments;
        ImageView ivPhoto;
        ImageView ivPhotoProfile;
    }

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1 , objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo,
                    parent, false);
            viewHolder.tvUsername  = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
            viewHolder.tvNumlikes  = (TextView) convertView.findViewById(R.id.tvNumlikes);
            viewHolder.tvCaptionUsername = (TextView) convertView.findViewById(R.id.tvCaptionUsername);
            viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.tvComments = (TextView) convertView.findViewById(R.id.tvComments);
            viewHolder.tvMoreComments = (TextView) convertView.findViewById(R.id.tvMoreComments);
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.ivPhotoProfile = (ImageView) convertView.findViewById(R.id.ivPhotoProfile);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String strTimestamp = DateUtils.getRelativeTimeSpanString(photo.getCreatedTime() * 1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE).toString();

        viewHolder.tvUsername.setText(photo.getUsername());
        viewHolder.tvTimeStamp.setText(strTimestamp);
        viewHolder.tvNumlikes.setText("\u2764 " + String.valueOf(photo.getLikesCount()));
        viewHolder.tvCaptionUsername.setText(photo.getUsername());
        viewHolder.tvCaption.setText(photo.getCaption());
        if (photo.getCommentCount() > 2){
            String htmlTextCountComments = "View all " + photo.getCommentCount() +  " comments";
            viewHolder.tvMoreComments.setText(Html.fromHtml(htmlTextCountComments));
        }
        switch (photo.getCommentCount()){
            case 0:
            case 1:
                viewHolder.tvMoreComments.setVisibility(View.GONE);
                break;
            default: {
                String htmlTextComments = "";
                for (int i = 0; i < photo.getComments().size() && i < 2; i++) {
                    InstagramComment comment = (InstagramComment) photo.comments.get(i);
                    htmlTextComments += "<b><font color=\"#084B8A\">" + comment.commentUserName
                            + " </font></b><font color=\"#000000\">" + comment.text + "</font><br>";
                }
                viewHolder.tvComments.setText(Html.fromHtml(htmlTextComments));
                break;
            }
        }
        viewHolder.ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.getImageUrl()).placeholder(R.drawable.placeholder).into(viewHolder.ivPhoto);
        viewHolder.ivPhotoProfile.setImageResource(0);
        Picasso.with(getContext()).load(photo.getProfileUrl()).transform(new CircleTransform())
                .into(viewHolder.ivPhotoProfile);
        return convertView;
    }
}
