package sg.edu.np.mad.madpractical5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        Intent receivingEnd = getIntent();
        int id = receivingEnd.getIntExtra("userId", -1);
        String name = receivingEnd.getStringExtra("userName");
        String description = receivingEnd.getStringExtra("userDesc");
        boolean followed = receivingEnd.getBooleanExtra("userFollowed", false);

        User clickedUser = new User(id, name, description, followed);

        TextView tvName = findViewById(R.id.tvName);
        TextView tvDescription = findViewById(R.id.tvDescription);
        Button btnfollow = findViewById(R.id.followbtn);
        Button btnMessage = findViewById(R.id.msgbtn);

        // Set the TextViews with the User's name, description and default button message
        tvName.setText(name);
        tvDescription.setText(description);
        if (followed){
            btnfollow.setText("Unfollow");
        }
        else{
            btnfollow.setText("Follow");
        }

        btnfollow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (clickedUser.getFollowed()){
                    btnfollow.setText("Follow");
                    clickedUser.setFollowed(false);
                    Toast.makeText(getApplicationContext(), "Unfollowed", Toast.LENGTH_SHORT).show();
                    dbHandler.updateUser(clickedUser);
                }
                else if (!clickedUser.getFollowed()){
                    btnfollow.setText("Unfollow");
                    clickedUser.setFollowed(true);
                    Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_SHORT).show();
                    dbHandler.updateUser(clickedUser);
                }
            }
        });
    }
}