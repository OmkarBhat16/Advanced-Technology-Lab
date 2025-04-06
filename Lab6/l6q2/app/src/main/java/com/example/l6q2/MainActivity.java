package com.example.l6q2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private MediaPlayer mediaPlayer;
    private int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize song list
        List<Song> songList = new ArrayList<>();
        songList.add(new Song("Song 1", "Artist 1", R.raw.a_random_piece_of_cheese_please));
        songList.add(new Song("Song 2", "Artist 2", R.raw.morax_unlocked_hacker_mode));
        songList.add(new Song("Song 3", "Artist 3", R.raw.random_acoustic_electronic_guitar));

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SongAdapter(songList);
        recyclerView.setAdapter(adapter);
    }

    private class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
        private List<Song> songs;

        public SongAdapter(List<Song> songs) {
            this.songs = songs;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_song, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Song song = songs.get(position);
            holder.tvTitle.setText(song.getTitle());
            holder.btnPlay.setText(position == currentPosition ? "Pause" : "Play");

            holder.btnPlay.setOnClickListener(v -> {
                if (position == currentPosition) {
                    pauseSong();
                } else {
                    playSong(song, position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return songs.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            Button btnPlay;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                btnPlay = itemView.findViewById(R.id.btnPlay);
            }
        }
    }

    private void playSong(Song song, int position) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, song.getRawResId());
        mediaPlayer.setOnPreparedListener(mp -> {
            mediaPlayer.start();
            currentPosition = position;
            adapter.notifyDataSetChanged();
        });

        mediaPlayer.setOnCompletionListener(mp -> {
            currentPosition = -1;
            adapter.notifyDataSetChanged();
        });
    }

    private void pauseSong() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            currentPosition = -1;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private static class Song {
        private String title;
        private String artist;
        private int rawResId;

        public Song(String title, String artist, int rawResId) {
            this.title = title;
            this.artist = artist;
            this.rawResId = rawResId;
        }

        public String getTitle() {
            return title;
        }

        public int getRawResId() {
            return rawResId;
        }
    }
}