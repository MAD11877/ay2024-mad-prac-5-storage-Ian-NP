package sg.edu.np.mad.madpractical5;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{

    Context context;
    List<User> userList;

    public UserAdapter(Context context, List<User> userList){
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview, parent, false);

        return new UserAdapter.MyViewHolder(view, userList);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        // assigning values to views (rows) created in the recycler_view_row layout file
        // based on the position of the recycler view

        User user = userList.get(position);

        holder.userName.setText(user.getName());
        holder.description.setText(user.getDescription());
    }

    @Override
    public int getItemCount() {
        // the recyclerview just wants to know the number of items you want displayed
        return userList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        // grabbing the views from our recycler_view_row layout file
        // kinda like the onCreate method

        ImageView profileBtn;
        TextView userName;
        TextView description;


        public MyViewHolder(@NonNull View itemView, List<User> userList){
            super(itemView);

            profileBtn = itemView.findViewById(R.id.profileBtn);
            userName = itemView.findViewById(R.id.userName);
            description = itemView.findViewById(R.id.description);

            profileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    User clickedUser = userList.get(position);
                    Intent viewAccActivity = new Intent(profileBtn.getContext(), ViewAccountActivity.class);
                    int userId = clickedUser.getID();
                    String userName = clickedUser.getName();
                    String description = clickedUser.getDescription();
                    boolean followed = clickedUser.getFollowed();
                    Log.i("followed", String.valueOf(followed));
                    viewAccActivity.putExtra("userId", userId);
                    viewAccActivity.putExtra("userName", userName);
                    viewAccActivity.putExtra("userDesc", description);
                    viewAccActivity.putExtra("userFollowed", followed);
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Profile");
                    builder.setMessage(userName);
                    builder.setCancelable(false);
                    builder.setPositiveButton("VIEW", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(itemView.getContext(), viewAccActivity, null);
                                }
                            });

                    builder.setNegativeButton("CLOSE", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }
    }
}
