package com.example.powder.monica.storage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.powder.monica.R;

import java.util.List;

public class StorageArrayAdapter extends ArrayAdapter<FileItem>{
    private LayoutInflater inflater;

    public StorageArrayAdapter(Context context, List<FileItem> fileItems){
        super(context, R.layout.row_layout, R.id.itemTextView, fileItems);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FileItem fileItem = this.getItem(position);
        CheckBox checkBox;
        TextView textView;
        convertView = inflater.inflate(R.layout.row_layout, null);
        textView = convertView.findViewById(R.id.itemTextView);
        checkBox = convertView.findViewById(R.id.itemCheckBox);
        checkBox.setOnClickListener(v -> {
            CheckBox cb = (CheckBox) v;
            FileItem fi = (FileItem) cb.getTag();
            fi.setChecked(cb.isChecked());
        });

        checkBox.setTag(fileItem);
        checkBox.setChecked(fileItem.isChecked());
        textView.setText(fileItem.getName());


        ImageView imageView = convertView.findViewById(R.id.itemIcon);
        if (fileItem.getName().contains(".jpg")) {
            imageView.setImageResource(R.drawable.ic_image_gray_24dp);
        } else {
            imageView.setImageResource(R.drawable.ic_music_note_gray_24dp);
        }


        return convertView;
    }
}
