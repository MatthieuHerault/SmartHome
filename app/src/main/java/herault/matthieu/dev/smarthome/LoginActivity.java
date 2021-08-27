package herault.matthieu.dev.smarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button btn_connexion;
    private EditText txt_mdp;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_connexion = findViewById(R.id.btn_connexion);
        txt_mdp = findViewById(R.id.txt_mdp);
    }

    @Override
    protected void onResume() {
        super.onResume();

        btn_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mdp = txt_mdp.getText().toString();

                if(TextUtils.isEmpty(mdp)) {
                    txt_mdp.setError("Mot de passe requis");
                } else {
                    //Firebase Authentication
                    mAuth = FirebaseAuth.getInstance();
                    signIn("matherault@hotmail.fr", mdp);
                }
            }
        });
    }

    public void signIn(String email, String password) {
        mAuth.signOut();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Connexion firebase : réussie");
                            //user = mAuth.getCurrentUser();
                            //String uid = user.getUid();
                            mToast = Toast.makeText(LoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT);
                            mToast.setGravity(Gravity.BOTTOM, 0,0);
                            mToast.show();

                            //Launch MainActivity
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Connexion firebase : échouée", task.getException());
                            mToast = Toast.makeText(LoginActivity.this, "Connexion échouée", Toast.LENGTH_SHORT);
                            mToast.setGravity(Gravity.BOTTOM, 0,0);
                            mToast.show();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
