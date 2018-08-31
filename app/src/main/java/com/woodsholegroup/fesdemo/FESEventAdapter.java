package com.woodsholegroup.fesdemo;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ltossou on 8/15/2018.
 */

public class FESEventAdapter extends RecyclerView.Adapter<FESEventAdapter.ViewHolder> {

    private List<FESEvent> events;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fes_event, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events != null ? events.size() : 0;
    }

    public void addEvent(FESEvent event) {
        if (events == null) {
            events = new ArrayList<>();
        }
        events.add(event);
        notifyItemInserted(events.size() - 1);
    }

    public void setEvents(List<FESEvent> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    public List<FESEvent> getEvents() {
        return events;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventDescriptionText;
        TextView eventTimeText;
        ImageView eventIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            eventDescriptionText = itemView.findViewById(R.id.text_event_description);
            eventTimeText = itemView.findViewById(R.id.text_event_time);
            eventIcon = itemView.findViewById(R.id.image_event_icon);
        }

        public void bind(FESEvent fesEvent) {
            eventDescriptionText.setText(fesEvent.getDescription());
            ((GradientDrawable) eventDescriptionText.getBackground()).setColor(getColorByEvent(fesEvent.getEventType()));

            eventTimeText.setText(fesEvent.getFormattedTime());

            // Set icon
            eventIcon.setImageResource(getDrawableByEvent(fesEvent));
        }

        private int getColorByEvent(@FESEvent.EventType int eventType) {
            switch (eventType) {
                case FESEvent.ERROR:
                    return Color.RED;
                case FESEvent.FILE_RECEPTION:
                    return Color.BLUE;
                case FESEvent.FILE_SENDING:
                    return Color.parseColor("#c8e6c9");
                case FESEvent.OTHER:
                    return Color.parseColor("#DEE8EE");
                case FESEvent.IRIDIUM_DATA:
                    return Color.parseColor("#fff59d");
            }
            return 0;
        }

        @DrawableRes
        private int getDrawableByEvent(FESEvent event) {
            int eventType = event.getEventType();
            switch (eventType) {
                case FESEvent.ERROR:
                    return R.drawable.ic_error;
                case FESEvent.FILE_RECEPTION:
                    return R.drawable.inbox;
                case FESEvent.FILE_SENDING:
                    return R.drawable.ic_outbox;
                case FESEvent.OTHER:
                    return R.drawable.ic_info;
                case FESEvent.IRIDIUM_DATA:
                    return R.drawable.ic_satellite;
            }
            return 0;
        }
    }
}
