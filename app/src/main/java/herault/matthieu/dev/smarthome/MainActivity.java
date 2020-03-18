package herault.matthieu.dev.smarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //Variables
    private Button mButtonSalonSud;
    private Button mButtonSalonOuest;
    private Button mButtonCuisine;
    private Button mButtonChambre1;
    private Button mButtonChambre2;
    private Button mButtonChambre3;
    private Button mButtonToutOuvrir;
    private Button mButtonToutFermer;
    private Button mButtonScenario;

    private FirebaseAuth mAuth;
    FirebaseUser user;
    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mDbSalonSudRef = mDatabaseRef.child("volet_salon_sud");
    private DatabaseReference mDbSalonOuestRef = mDatabaseRef.child("volet_salon_ouest");
    private DatabaseReference mDbCuisineRef = mDatabaseRef.child("volet_cuisine");
    private DatabaseReference mDbChambre1Ref = mDatabaseRef.child("volet_chambre_1");
    private DatabaseReference mDbChambre2Ref = mDatabaseRef.child("volet_chambre_2");
    private DatabaseReference mDbChambre3Ref = mDatabaseRef.child("volet_chambre_3");
    private DatabaseReference mDbTousRef = mDatabaseRef.child("volet_tous");


    String name;
    String email;
    Uri photoUrl;
    boolean emailVerified;
    String uid;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSalonSud = findViewById(R.id.btn_salon_sud);
        mButtonSalonOuest = findViewById(R.id.btn_salon_ouest);
        mButtonCuisine = findViewById(R.id.btn_cuisine);
        mButtonChambre1 = findViewById(R.id.btn_chambre_1);
        mButtonChambre2 = findViewById(R.id.btn_chambre_2);
        mButtonChambre3 = findViewById(R.id.btn_chambre_3);
        mButtonToutOuvrir = findViewById(R.id.btn_tout_ouvrir);
        mButtonToutFermer = findViewById(R.id.btn_tout_fermer);
        mButtonScenario = findViewById(R.id.btn_scenario);

        //Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        //mAuth.signOut();
        signIn("matherault@hotmail.fr", "LaurieMatthieu2015");

        //mDatabaseRef.removeValue();

        /*user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            name = user.getDisplayName();
            email = user.getEmail();
            photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            uid = user.getUid();

            Toast.makeText(MainActivity.this, "Email : " +email+ " UID : " +uid, Toast.LENGTH_LONG).show();

        }*/

    }

    @Override
    protected void onStart() {
        super.onStart();

        mButtonSalonSud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbSalonSudRef.setValue(1);
                Toast.makeText(MainActivity.this, "Fermeture volet salon sud", Toast.LENGTH_LONG).show();
            }
        });

        mButtonSalonOuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbSalonOuestRef.setValue(1);
                Toast.makeText(MainActivity.this, "Fermeture volet salon ouest", Toast.LENGTH_LONG).show();
            }
        });

        mButtonCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbCuisineRef.setValue(1);
                Toast.makeText(MainActivity.this, "Fermeture volet cuisine", Toast.LENGTH_LONG).show();
            }
        });

        mButtonChambre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbChambre1Ref.setValue(1);
                Toast.makeText(MainActivity.this, "Fermeture volet chambre 1", Toast.LENGTH_LONG).show();
            }
        });

        mButtonChambre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbChambre2Ref.setValue(1);
                Toast.makeText(MainActivity.this, "Fermeture volet chambre 2", Toast.LENGTH_LONG).show();
            }
        });

        mButtonChambre3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbChambre3Ref.setValue(1);
                Toast.makeText(MainActivity.this, "Fermeture volet chambre 3", Toast.LENGTH_LONG).show();
            }
        });

        mButtonToutOuvrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbTousRef.setValue(1);
                Toast.makeText(MainActivity.this, "Ouverture tous", Toast.LENGTH_LONG).show();
            }
        });

        mButtonToutFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbTousRef.setValue(0);
                Toast.makeText(MainActivity.this, "Fermeture tous", Toast.LENGTH_LONG).show();
            }
        });

        mButtonScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScenarioActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Log in success.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Log in failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
