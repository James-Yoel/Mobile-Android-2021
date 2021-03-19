package umn.ac.id.jamesyoel_00000028895_if633_fl_uts;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class ListLaguAdapter extends RecyclerView.Adapter<ListLaguAdapter.ListLaguHolder>{
    private final ArrayList<File> mListLagu;
    private LayoutInflater mInflater;
    public boolean empty = true;

    ListLaguAdapter(Context context, ArrayList<File> listLagu){
        mInflater = LayoutInflater.from(context);
        mListLagu = listLagu;
    }

    @NonNull
    @Override
    public ListLaguAdapter.ListLaguHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mLaguView = mInflater.inflate(R.layout.daftar_lagu, parent, false);
        return new ListLaguHolder(mLaguView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListLaguAdapter.ListLaguHolder holder, int position) {
        Log.i("SIZE", ""+mListLagu.size());
        if(mListLagu.size() == 0){
            holder.judulLagu.setText("No Music Found.");
            empty = true;
        }
        else{
            empty = false;
            File mCurrent = mListLagu.get(position);
            String songName = mCurrent.getName().replace(".mp3", "").replace(".wav", "");
            Log.i("SONG",  songName);
            holder.judulLagu.setText(songName);
        }
    }

    @Override
    public int getItemCount() {
        return mListLagu.size();
    }

    class ListLaguHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView judulLagu;
        final ListLaguAdapter mAdapter;

        public ListLaguHolder(@NonNull View laguView, ListLaguAdapter adapter){
            super(laguView);
            judulLagu = laguView.findViewById(R.id.lagu);
            this.mAdapter = adapter;
            laguView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            if(!empty){
                int mPosition = getLayoutPosition();
                Log.i("POS", ""+mPosition);
                String songName = mListLagu.get(mPosition).getName();
                Log.i("PLAY", songName);
                Intent playMusic = new Intent(v.getContext(), PlayMusicActivity.class);
                playMusic.putExtra("Music", mListLagu).putExtra("Title", songName).putExtra("Position", mPosition);
                v.getContext().startActivity(playMusic);
            }
        }
    }
}
