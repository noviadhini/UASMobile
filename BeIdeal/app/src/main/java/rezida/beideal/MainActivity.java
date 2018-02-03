package rezida.beideal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import rezida.beideal.Model.User;

public class MainActivity extends AppCompatActivity {
    private TextView register;
    private EditText inputUsername, inputPassword;
    private Button btnLogin;

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.Register);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        inputUsername = (EditText) findViewById(R.id.usernameLogin);
        inputPassword = (EditText) findViewById(R.id.passwordLogin);

        register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),DaftarUser.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loginUser(inputUsername.getText().toString(), inputPassword.getText().toString());
            }
        });
    }

    private void loginUser(String username, String password)
    {
        databaseUser = FirebaseDatabase.getInstance().getReference();
        Query query = databaseUser.child("user").orderByChild("username").equalTo(username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        User user = issue.getValue(User.class);

                        inputPassword.setText(user.getPassword());

                        Intent i = new Intent(getApplicationContext(), Home2.class);

                        i.putExtra("username", inputUsername.getText().toString());

                        startActivity(i);
                    }
                }
                else
                {
                    inputUsername.setText(null);
                    inputPassword.setText(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
