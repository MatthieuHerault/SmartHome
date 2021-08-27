package herault.matthieu.dev.smarthome;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

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
    private Button mButtonCamera;

    private Dialog mDialogSalonSud;
    private Dialog mDialogSalonOuest;
    private Dialog mDialogCuisine;
    private Dialog mDialogChambre1;
    private Dialog mDialogChambre2;
    private Dialog mDialogChambre3;
    private Dialog mDialogToutOuvrir;
    private Dialog mDialogToutFermer;

    private Toast mToast;

    public DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mDbSalonSudRef = mDatabaseRef.child("volet_salon_sud");
    private DatabaseReference mDbSalonOuestRef = mDatabaseRef.child("volet_salon_ouest");
    private DatabaseReference mDbCuisineRef = mDatabaseRef.child("volet_cuisine");
    private DatabaseReference mDbChambre1Ref = mDatabaseRef.child("volet_chambre_1");
    private DatabaseReference mDbChambre2Ref = mDatabaseRef.child("volet_chambre_2");
    private DatabaseReference mDbChambre3Ref = mDatabaseRef.child("volet_chambre_3");
    private DatabaseReference mDbTousRef = mDatabaseRef.child("volet_tous");

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
        mButtonCamera = findViewById(R.id.btn_camera);

        mDialogSalonSud = new Dialog(this);
        Objects.requireNonNull(mDialogSalonSud.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogSalonOuest = new Dialog(this);
        Objects.requireNonNull(mDialogSalonOuest.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogCuisine = new Dialog(this);
        Objects.requireNonNull(mDialogCuisine.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogChambre1 = new Dialog(this);
        Objects.requireNonNull(mDialogChambre1.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogChambre2 = new Dialog(this);
        Objects.requireNonNull(mDialogChambre2.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogChambre3 = new Dialog(this);
        Objects.requireNonNull(mDialogChambre3.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogToutOuvrir = new Dialog(this);
        Objects.requireNonNull(mDialogToutOuvrir.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogToutFermer = new Dialog(this);
        Objects.requireNonNull(mDialogToutFermer.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    protected void onStart() {
        super.onStart();

        mButtonSalonSud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(mDialogSalonSud, mDbSalonSudRef, "Salon Sud");
            }
        });

        mButtonSalonOuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(mDialogSalonOuest, mDbSalonOuestRef, "Salon Ouest");
            }
        });

        mButtonCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(mDialogCuisine, mDbCuisineRef, "Cuisine");
            }
        });

        mButtonChambre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(mDialogChambre1, mDbChambre1Ref, "Chambre 1");
            }
        });

        mButtonChambre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(mDialogChambre2, mDbChambre2Ref, "Chambre 2");
            }
        });

        mButtonChambre3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(mDialogChambre3, mDbChambre3Ref, "Chambre 3");
            }
        });

        mButtonToutOuvrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpAll(mDialogToutOuvrir, mDbTousRef, "Tout Ouvrir ?", "Ouverture tous", "Ouvrir");
            }
        });

        mButtonToutFermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpAll(mDialogToutFermer, mDbTousRef, "Tout Fermer ?", "Fermeture tous", "Fermer");

            }
        });

        mButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Launch CameraActivity
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
    }

    public void showPopUp(Dialog mDialog, DatabaseReference mDbRef, String mPiece) {
        TextView dialog_name;
        Button btn_ouvrir;
        Button btn_stop;
        Button btn_fermer;
        final DatabaseReference DbRef = mDbRef;
        final String piece_name = mPiece;
        final Dialog dialog = mDialog;

        dialog.setContentView(R.layout.pop_up);
        dialog_name = dialog.findViewById(R.id.dialog_name);
        btn_ouvrir = dialog.findViewById(R.id.btn_ouvrir);
        btn_stop = dialog.findViewById(R.id.btn_stop);
        btn_fermer = dialog.findViewById(R.id.btn_fermer);

        Objects.requireNonNull(dialog.getWindow()).setWindowAnimations(R.style.AnimZoom);

        dialog_name.setText(piece_name);

        btn_ouvrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbRef.setValue("Ouvrir");
                mToast = Toast.makeText(MainActivity.this, "Ouverture Volet " +piece_name, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.BOTTOM, 0,0);
                mToast.show();
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbRef.setValue("Stop");
                mToast = Toast.makeText(MainActivity.this, "Arrêt Volet " +piece_name, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.BOTTOM, 0,0);
                mToast.show();
            }
        });

        btn_fermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbRef.setValue("Fermer");
                mToast = Toast.makeText(MainActivity.this, "Fermeture Volet " +piece_name, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.BOTTOM, 0,0);
                mToast.show();
            }
        });

        dialog.show();
    }

    public void showPopUpAll(Dialog mDialog, DatabaseReference mDbRef, String mAction, String mToastText, String mValueText) {
        TextView dialog_name;
        Button btn_action_oui;
        Button btn_action_non;
        final DatabaseReference DbRef = mDbRef;
        final String action_name = mAction;
        final Dialog dialog = mDialog;
        final String toastText = mToastText;
        final String valueText = mValueText;


        dialog.setContentView(R.layout.pop_up_all);
        dialog_name = dialog.findViewById(R.id.dialog_name);
        btn_action_oui = dialog.findViewById(R.id.btn_action_oui);
        btn_action_non = dialog.findViewById(R.id.btn_action_non);

        Objects.requireNonNull(dialog.getWindow()).setWindowAnimations(R.style.AnimZoom);

        dialog_name.setText(action_name);

        btn_action_oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbTousRef.setValue(valueText);
                mToast = Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.BOTTOM, 0,0);
                mToast.show();
                dialog.cancel();
            }
        });

        btn_action_non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAuth.signOut();
        finish();
    }
}
