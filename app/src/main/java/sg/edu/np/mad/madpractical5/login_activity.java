package sg.edu.np.mad.madpractical5;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class login_activity extends AppCompatActivity {

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://practical-5-81730-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mDatabase = database.getReference("user");

//        String[][] userData = {
//                {"user1", "password1"},
//                {"user2", "password2"},
//                {"user3", "password3"},
//        };
//
//        // Add each user to the database
//        for (String[] data : userData) {
//            String username = data[0];
//            String password = data[1];
//
//            // Create a new user object with username and password
//            UserLogin user = new UserLogin(username, password);
//
//            // Push the user object to the "users" node in the database
//            myRef.push().setValue(user, (error, ref) -> {
//                if (error == null) {
//                    System.out.println("User added successfully: " + username);
//                } else {
//                    System.err.println("Failed to add user: " + username);
//                }
//            });
//        }

        // Initialize UI components
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        // Set click listener for login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                Log.d("Username", username);
                Log.d("Password", password);

                verifyUser(username, password);
            }
        });
    }

    public void verifyUser(final String username, final String password) {
        Query query = mDatabase.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User exists, check password
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        UserLogin user = userSnapshot.getValue(UserLogin.class);
                        if (user != null && user.getPassword().equals(password)) {
                            // Password matches
                            Log.d(TAG, "User authenticated successfully.");
                            Toast.makeText(getApplicationContext(), "User Authenticated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            // Password does not match
                            Log.d(TAG, "Authentication failed: Incorrect password.");
                            Toast.makeText(getApplicationContext(), "Invalid User!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // User does not exist
                    Log.d(TAG, "Authentication failed: User not found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting user failed, log a message
                Log.w(TAG, "verifyUser:onCancelled", databaseError.toException());
            }
        });
    }
}