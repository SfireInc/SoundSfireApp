package fr.cpe.sfireinc;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import fr.cpe.sfireinc.databinding.AudioFileItemBinding;

public class AudioFileListAdapter extends
        RecyclerView.Adapter<AudioFileListAdapter.ViewHolder> {

    static MediaPlayer mediaPlayer;

    List<AudioFile> audioFileList;
    public AudioFileListAdapter(List<AudioFile> fileList) {
        assert fileList != null;
        audioFileList = fileList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AudioFileItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.audio_file_item, parent,false);

        return new ViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AudioFile file = audioFileList.get(position);
        holder.viewModel.setAudioFile(file);
    }
    @Override
    public int getItemCount() {
        return audioFileList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private AudioFileItemBinding binding;
        private AudioFileViewModel viewModel = new AudioFileViewModel();


        
        ViewHolder(AudioFileItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setAudioFileViewModel(viewModel);
            this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopMusic();
                    Toast.makeText(v.getContext(), "Current playing: " + ViewHolder.this.viewModel.getTitle(), Toast.LENGTH_SHORT).show();
                    /*MediaPlayer mediaPlayer = MediaPlayer.create(v.getContext(), Uri.parse(ViewHolder.this.viewModel.getFilePath()));
                    mediaPlayer.start();*/
                    Uri myUri = Uri.parse(ViewHolder.this.viewModel.getFilePath()); // initialize Uri here
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(v.getContext(), myUri);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                       e.getMessage();
                    }


                }

                private void stopMusic() {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            });
        }
    }
}