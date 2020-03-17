package herault.matthieu.dev.smarthome;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //Variables
    Button mButtonSalonSud;
    Button mButtonSalonOuest;
    Button mButtonCuisine;
    Button mButtonChambre1;
    Button mButtonChambre2;
    Button mButtonChambre3;
    Button mButtonToutOuvrir;
    Button mButtonToutFermer;
    Button mButtonScenario;

    DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mDbSalonSudRef = mDatabaseRef.child("volet_salon_sud");
    DatabaseReference mDbSalonOuestRef = mDatabaseRef.child("volet_salon_ouest");
    DatabaseReference mDbCuisineRef = mDatabaseRef.child("volet_cuisine");
    DatabaseReference mDbChambre1Ref = mDatabaseRef.child("volet_chambre_1");
    DatabaseReference mDbChambre2Ref = mDatabaseRef.child("volet_chambre_2");
    DatabaseReference mDbChambre3Ref = mDatabaseRef.child("volet_chambre_3");
    DatabaseReference mDbTousRef = mDatabaseRef.child("volet_tous");

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
}
