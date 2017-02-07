package adamcooper.supertask;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Class for managing cards
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    //fields
    private Context context;
    ArrayList<Task> items;
    TaskManager manager;




    /**
     * Constructor
     */
    public CardAdapter(Context context) {
        this.context = context;
        this.manager = new TaskManager();
        this.items = manager.orderedItems();
    }




    /**
     * Inflates a card within the view holder
     * @param viewGroup The group in which the card is contained
     * @param position not used
     * @return The view holder with the created inflated card
     */
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        //inflates a card
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_view_task, viewGroup, false);
        return new ViewHolder(view);
    }




    /**
     * Tells how many tasks there are
     * @return items.size
     */
    public int getItemCount() {
        return items.size();
    }


    /**
     * Manages the viewholder
     * @param viewHolder The viewholder to manage
     * @param position just here for override
     */
    public void onBindViewHolder(CardAdapter.ViewHolder viewHolder, int position) {
        viewHolder.setViews(this);
    }


    /**
     * Adds a card to the data set
     * @param task The task card to add
     */
    public void addCard(Task task) {
        manager.add(task);
        items = manager.orderedItems();
        notifyDataSetChanged();
    }




    /**
     * Represents functionality of a single card
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //fields
        CardAdapter adapter;
        TextView name, course, dueDate;
        ImageButton favoriteButton;
        boolean favorite = false;




        /**
         * Constructor, initializes all views and sets up functionality
         * @param view Default
         */
        public ViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.taskNameTextView);
            this.course = (TextView) view.findViewById(R.id.classTextView);
            this.dueDate = (TextView) view.findViewById(R.id.dueDateTextView);

            setUpFavoriteButton(view);
            setUpMenuButton(view);

            view.setOnClickListener(this); //automatically calls this.onClick
        }




        /**
         * Helper method for constructor to set up favorite button
         * @param view Default, passed from constructor
         */
        private void setUpFavoriteButton(View view) {
            favoriteButton = (ImageButton) view.findViewById(R.id.favoriteButton);
            favoriteButton.setFocusable(false);
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageButton button = (ImageButton) view;
                    if (favorite) {
                        button.setImageDrawable(ContextCompat.
                                getDrawable(context, R.drawable.ic_star_gold_24dp));
                    } else {
                        button.setImageDrawable((ContextCompat.
                                getDrawable(context, R.drawable.ic_star_white_24dp)));
                    }
                    items.get(getAdapterPosition()).setFavoriteStatus(favorite);
                    favorite = !favorite;
                }
            });
        }




        /**
         * Helper method for constructor to set up menu button
         * @param view Default, passed from constructor
         */
        private void setUpMenuButton(View view) {
            final ImageButton menuButton = (ImageButton) view.findViewById(R.id.menuButton);
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(context, menuButton);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_task_card, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.editTask:
                                    //TODO edit functionality
                                    break;
                                case R.id.addExtension:
                                    //TODO add extension
                                    break;
                                case R.id.markComplete:
                                    //TODO mark complete
                                    break;
                                case R.id.deleteTask:
                                    //TODO add "are you sure?" dialog
                                    manager.remove(items.remove(getAdapterPosition()));
                                    notifyDataSetChanged();
                                    break;
                                default:
                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                    });
                }
            });
        }


        /**
         * Sets up all the views of this card with the appropriate information
         * @param adapter The adapter to use
         */
        public void setViews(CardAdapter adapter) {
            this.adapter = adapter;
            Task task = items.get(getAdapterPosition());
            name.setText(task.getName());
            course.setText(task.getAssociatedClass().getName());
            dueDate.setText(Task.getDateAsString(task.getDueDate()));
            if (task.isFavorite()) {
                favoriteButton.setImageDrawable(ContextCompat.
                        getDrawable(context, R.drawable.ic_star_gold_24dp));
            }
        }




        /**
         * Tells what to do upon click of the card
         * @param v The view being clicked
         */
        @Override
        public void onClick(View v) {
            //TODO goto view task activity
        }
    }
}
