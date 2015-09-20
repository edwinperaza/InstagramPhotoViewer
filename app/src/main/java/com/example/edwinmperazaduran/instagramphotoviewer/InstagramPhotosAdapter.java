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
    //

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1 , objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        if (convertView == null){
            //create a new view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo,
                    parent, false);
        }
        TextView tvUsername  = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
        TextView tvNumlikes  = (TextView) convertView.findViewById(R.id.tvNumlikes);
        TextView tvCaptionUsername = (TextView) convertView.findViewById(R.id.tvCaptionUsername);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvComments = (TextView) convertView.findViewById(R.id.tvComments);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivPhotoProfile = (ImageView) convertView.findViewById(R.id.ivPhotoProfile);

        String strTimestamp = DateUtils.getRelativeTimeSpanString(photo.getCreatedTime() * 1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE).toString();

        tvUsername.setText(photo.getUsername());
        tvTimeStamp.setText(strTimestamp);
        tvNumlikes.setText(String.valueOf(photo.getLikesCount()));
        tvCaptionUsername.setText(photo.getUsername());
        tvCaption.setText(photo.getCaption());
        //Insert Comments
        String htmlText = "";
        for(int i=0; i < 2 ;i++) {
            InstagramComment comment = (InstagramComment) photo.comments.get(i);
            htmlText += "<b><font color=\"#287AA9\">" + comment.commentUserName + "</font></b> "
                     + comment.text + "<br>";
        }
        tvComments.setText(Html.fromHtml(htmlText));
        //Insert de image using picasso
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.getImageUrl()).into(ivPhoto);
        ivPhotoProfile.setImageResource(0);
        Picasso.with(getContext()).load(photo.getProfileUrl()).transform(new CircleTransform())
                .into(ivPhotoProfile);
        return convertView;
    }
}
