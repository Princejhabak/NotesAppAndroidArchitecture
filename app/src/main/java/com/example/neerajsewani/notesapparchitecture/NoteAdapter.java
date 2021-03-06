package com.example.neerajsewani.notesapparchitecture;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {
    OnItemClickListener listener;

    protected NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return
                    oldItem.getDescription() == newItem.getDescription() &&
                            oldItem.getTitle() == newItem.getTitle() &&
                            oldItem.getPriority() == newItem.getPriority()
                    ;
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.note_item, viewGroup, false);

        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int position) {
        Note currentNote = getItem(position);

        //  setting the values in the TextViews
        noteHolder.priorityTv.setText(String.valueOf(currentNote.getPriority()));
        noteHolder.descriptionTv.setText(currentNote.getDescription());
        noteHolder.titleTv.setText(currentNote.getTitle());


    }

    //  to delete a specific note
    Note deleteNoteAt(int position){
        return getItem(position);
    }

    class NoteHolder extends ViewHolder {

        private TextView titleTv;
        private TextView descriptionTv;
        private TextView priorityTv;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.text_view_title);
            descriptionTv = itemView.findViewById(R.id.text_view_description);
            priorityTv = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
