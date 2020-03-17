package herault.matthieu.dev.smarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //Variables
    TextView mConditionTextView;
    Button mButtonSalonSud;
    Button mButtonSalonOuest;
    Button mButtonCuisine;
    Button mButtonChambre1;
    Button mButtonChambre2;
    Button mButtonChambre3;
    Button mButtonScenario;

    DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mDatabaseRef.child("led_value");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConditionTextView = findViewById(R.id.textViewCondition);
        mButtonSalonSud = findViewById(R.id.btn_salon_sud);
        mButtonSalonOuest = findViewById(R.id.btn_salon_ouest);
        mButtonCuisine = findViewById(R.id.btn_cuisine);
        mButtonChambre1 = findViewById(R.id.btn_chambre_1);
        mButtonChambre2 = findViewById(R.id.btn_chambre_2);
        mButtonChambre3 = findViewById(R.id.btn_chambre_3);
        mButtonScenario = findViewById(R.id.btn_scenario);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mConditionTextView.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mButtonSalonSud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConditionRef.setValue("mButtonSalonSud");
            }
        });

        mButtonSalonOuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConditionRef.setValue("mButtonSalonOuest");
            }
        });

        mButtonCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConditionRef.setValue("mButtonCuisine");
            }
        });

        mButtonChambre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConditionRef.setValue("mButtonChambre1");
            }
        });

        mButtonChambre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConditionRef.setValue("mButtonChambre2");
            }
        });

        mButtonChambre3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConditionRef.setValue("mButtonChambre3");
            }
        });

        mButtonScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
    }
}
