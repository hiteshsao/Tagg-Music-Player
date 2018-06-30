package ca.bragg.tagg;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.turingtechnologies.materialscrollbar.INameableAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> implements INameableAdapter {

    private ArrayList<SongInfo> songs;
    private Context context;
    private MediaController mediaController;

    private OnItemClickListener onItemClickListener;

    @Override
    public Character getCharacterForElement(int element) {
        return songs.get(element).getSongName().charAt(0);
    }

    public SongAdapter(Context context, ArrayList<SongInfo> songs) {
        this.context = context;
        this.mediaController = MediaController.getSelf();
        this.songs = songs;
    }

    public interface OnItemClickListener {
        void onItemClick(Button b, View v, SongInfo si, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(context).inflate(R.layout.row_song, parent, false);
        return new SongHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int i) {
        final SongInfo c = songs.get(i);
        holder.songName.setText(c.getSongName());
        holder.artistName.setText(c.getArtistName());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mediaController.playSongFromUser(c); }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        TextView songName, artistName;
        View view;

        public SongHolder(View itemView) {
            super(itemView);
            view = itemView;
            songName = itemView.findViewById(R.id.songNameTextView);
            artistName = itemView.findViewById(R.id.artistNameTextView);
        }

        public View getView() {
            return view;
        }
    }
}