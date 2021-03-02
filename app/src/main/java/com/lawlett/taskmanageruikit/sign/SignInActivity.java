package com.lawlett.taskmanageruikit.sign;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.lawlett.taskmanageruikit.R;

public class SignInActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 6;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView status_tv, detail;
    Button disconnectButton, signOutButton;
    Group signOutAndDisconnect;
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();
        initClickers();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressBar();
        if (user != null) {
            status_tv.setText("Google User: %s" + user.getEmail());
            detail.setText("Firebase UID: %s" + user.getUid());
            signInButton.setVisibility(View.GONE);
            signOutAndDisconnect.setVisibility(View.VISIBLE);
        } else {
            status_tv.setText("Sign Out");
            detail.setText(null);
            signInButton.setVisibility(View.VISIBLE);
            signOutAndDisconnect.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        signInButton = findViewById(R.id.signInButton);
        progressBar = findViewById(R.id.progress_bar);
        signOutAndDisconnect = findViewById(R.id.signOutAndDisconnect);
        status_tv = findViewById(R.id.status);
        detail = findViewById(R.id.detail);
        mainLayout = findViewById(R.id.main_layout);
        signOutButton = findViewById(R.id.signOutButton);
        disconnectButton=findViewById(R.id.disconnectButton);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initClickers() {
        signInButton.setOnClickListener(v -> signIn());
        disconnectButton.setOnClickListener(v -> revokeAccess());
        signOutButton.setOnClickListener(v -> signOut());
    }

    private void signIn() {
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, RC_SIGN_IN);
        showProgressBar();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        // [START_EXCLUDE silent]
        showProgressBar();
        // [END_EXCLUDE]
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar.make(mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
    }
}