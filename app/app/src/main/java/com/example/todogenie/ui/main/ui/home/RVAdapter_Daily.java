package com.example.todogenie.ui.main.ui.home;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todogenie.R;

import java.util.ArrayList;
import java.util.List;


public class RVAdapter_Daily extends RecyclerView.Adapter<MyViewHolder> implements Filterable {
    Context mContext;
    List<TodoVO> mData;
    List<TodoVO> unFilData;
    Dialog mDialog;

    public RVAdapter_Daily(Context mContext, List<TodoVO> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.unFilData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v ;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_todo, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        final Animation animFadeIn = AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in);
        final Animation animFadeOut = AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out);
        final Animation animFadeInHalf = AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in_half);
        final Animation animFadeOutHalf = AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out_half);

        //Dialog init
//        mDialog = new Dialog(mContext);
//        mDialog.setContentView(R.layout.dialog_add_todo);
//        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

/*  Below can be used to todo edit dialog */
//        vHolder.item_contact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(mContext, "Text Click"+ String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
//
//                TextView dialog_name_tv = mDialog.findViewById(R.id.dialog_name);
//                TextView dialog_phone_tv = mDialog.findViewById(R.id.dialog_phone);
//                TextView dialog_company_tv = mDialog.findViewById(R.id.dialog_company);
//                TextView dialog_position_tv = mDialog.findViewById(R.id.dialog_position);
//                TextView dialog_email_tv = mDialog.findViewById(R.id.dialog_email);
//                ImageView dialog_img = mDialog.findViewById(R.id.dialog_img);
//
//                dialog_name_tv.setText(mData.get(vHolder.getAdapterPosition()).getName());
//                dialog_phone_tv.setText(mData.get(vHolder.getAdapterPosition()).getPhone());
//                dialog_company_tv.setText(mData.get(vHolder.getAdapterPosition()).getCompany());
//                dialog_position_tv.setText(mData.get(vHolder.getAdapterPosition()).getPosition());
//                dialog_email_tv.setText(mData.get(vHolder.getAdapterPosition()).getEmail());
//
//                dialog_img.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v){
//                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                        ((Activity) mContext).startActivityForResult(intent, GALLERY_REQUEST_CODE);
//                    }
//                });
//
//                mDialog.show();
//
//                Button call_btn = (Button) mDialog.findViewById(R.id.dialog_btn_call);
//                Button msg_btn = (Button) mDialog.findViewById(R.id.dialog_btn_message);
//                Button share_btn = (Button) mDialog.findViewById(R.id.dialog_btn_share);
//
//                call_btn.setOnClickListener(new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //Toast.makeText(mContext, "Call button click", Toast.LENGTH_SHORT).show();
//
//                        try {
//                            Intent callIntent = new Intent(Intent.ACTION_CALL);
//                            callIntent.setData(Uri.parse("tel:" + vHolder.tv_phone.getText().toString()));
//                            v.getContext().startActivity(callIntent);
//                        }
//                        catch (ActivityNotFoundException e) {
//                            Log.e("Call", "Fail to Calling", e);
//                        }
//                    }
//                });
//
//                msg_btn.setOnClickListener(new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //Toast.makeText(mContext, "Msg button click", Toast.LENGTH_SHORT).show();
//
//                        try {
//                            Intent msgIntent = new Intent(Intent.ACTION_VIEW);
//                            msgIntent.setData(Uri.parse("sms:" + vHolder.tv_phone.getText().toString()));
//                            msgIntent.putExtra("sms_body", "Hello world!");
//                            v.getContext().startActivity(msgIntent);
//                        }
//                        catch (ActivityNotFoundException e) {
//                            Log.e("Msg", "Fail to Messaging", e);
//                        }
//                    }
//                });
//
//                share_btn.setOnClickListener(new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //Toast.makeText(mContext, "Share button click", Toast.LENGTH_SHORT).show();
//
//                        try {
//                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                            shareIntent.setType("text/plain");
//                            String Text_Message = "이름 : " + vHolder.tv_name.getText().toString() + "\n" + "연락처 : " + vHolder.tv_phone.getText().toString();
//                            shareIntent.putExtra(Intent.EXTRA_TEXT, Text_Message);
//                            Intent Sharing = Intent.createChooser(shareIntent, "공유하기");
//                            v.getContext().startActivity(Sharing);
//                        }
//                        catch (ActivityNotFoundException e) {
//                            Log.e("Msg", "Fail to Messaging", e);
//                        }
//                    }
//                });
//
//            }
//        });

        vHolder.btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vHolder.menus_todo.setVisibility(View.VISIBLE);
                if(vHolder.is_menu_opened == false) {
                    vHolder.is_menu_opened = true;
                    vHolder.btn_menu.startAnimation(animFadeOutHalf);
                    vHolder.menus_todo.startAnimation(animFadeIn);
                }
                else {
                    vHolder.is_menu_opened = false;
                    vHolder.btn_menu.startAnimation(animFadeInHalf);
                    vHolder.menus_todo.startAnimation(animFadeOut);
                }
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TodoVO todoVO = mData.get(position);
        holder.tv_title.setText(todoVO.getTitle());
        holder.tv_content.setText(todoVO.getContent());
        holder.checkBox.setChecked(todoVO.getChecked());
        holder.tv_start.setText(todoVO.getStartStr());
        holder.tv_end.setText(todoVO.getEndStr());
        holder.menus_todo.setVisibility(View.INVISIBLE);
    }

    //3) getItemCount
    @Override
    public int getItemCount() {
        if (mData == null){
            return 0;
        }
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString();
                if(str.isEmpty()) {
                    mData = unFilData;
                } else {
                    List<TodoVO> filteringList = new ArrayList<>();
                    for(TodoVO item : unFilData) {
                        if(item.getTitle().toLowerCase().contains(str) || item.getTitle().contains(str) || item.getContent().contains(str))
                            filteringList.add(item);
                    }
                    mData = filteringList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mData;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mData = (List<TodoVO>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
