package com.example.wallpaperapplication.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wallpaperapplication.MainActivity;
import com.example.wallpaperapplication.R;
import com.example.wallpaperapplication.models.User;
import com.example.wallpaperapplication.session.SharedPreference;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
import com.huawei.hms.support.api.entity.common.CommonConstant;

public class LoginActivity extends AppCompatActivity {

    TextView signUp;
    TextView terms;
    TextView forgot;
    EditText email;
    EditText pass;
    Button btnLogin;

    // AccountAuthService provides a set of APIs, including silentSignIn, getSignInIntent, and signOut.
    private AccountAuthService mAuthService;

    // Set HUAWEI ID sign-in authorization parameters.
    private AccountAuthParams mAuthParam;

    // Define the request code for signInIntent.
    private static final int REQUEST_CODE_SIGN_IN = 1000;

    // Define the log tag.
    private static final String TAG = "Account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUp = findViewById(R.id.sign_up_fragment);
        terms = findViewById(R.id.tv_terms);
        forgot = findViewById(R.id.tv_forgot);
        email = findViewById(R.id.et_email_login);
        pass = findViewById(R.id.et_password_login);
        btnLogin = findViewById(R.id.btn_login);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _email = email.getText().toString();
                String _pass = pass.getText().toString();

                if(email.getText().toString().isEmpty() && pass.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Email and Password can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if(email.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Email can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if(pass.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Password can't be empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //masih dummy
                    SharedPreference sharedPreference = new SharedPreference(LoginActivity.this);
                    sharedPreference.save(new User("User Dummy", _email, _pass));

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        findViewById(R.id.HuaweiIdAuthButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                silentSignInByHwId();
            }
        });

    }

    private void silentSignInByHwId() {
        // 1. Use AccountAuthParams to specify the user information to be obtained, including the user ID (OpenID and UnionID), email address, and profile (nickname and picture).
        // 2. By default, DEFAULT_AUTH_REQUEST_PARAM specifies two items to be obtained, that is, the user ID and profile.
        // 3. If your app needs to obtain the user's email address, call setEmail().
        mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setEmail()
                .createParams();

        // Use AccountAuthParams to build AccountAuthService.
        mAuthService = AccountAuthManager.getService(this, mAuthParam);

        // Use silent sign-in to sign in with a HUAWEI ID.
        Task<AuthAccount> task = mAuthService.silentSignIn();
        task.addOnSuccessListener(new OnSuccessListener<AuthAccount>() {
            @Override
            public void onSuccess(AuthAccount authAccount) {
                // The silent sign-in is successful. Process the returned account object AuthAccount to obtain the HUAWEI ID information.
                dealWithResultOfSignIn(authAccount);
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                // The silent sign-in fails. Use the getSignInIntent() method to show the authorization or sign-in screen.
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    Intent signInIntent = mAuthService.getSignInIntent();
                    // If your app appears in full-screen mode duing user sign-in, that is, with no satus bar at the top of the phone screen, add the following parameter in the intent:
                    // intent.putExtra(CommonConstant.RequestParams.IS_FULL_SCREEN, true);
                    // Check the details in this FAQ.
                    // signInIntent.putExtra(CommonConstant.RequestParams.IS_FULL_SCREEN, true);
                    startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
                }
            }
        });
    }

    /**
     * Process the returned AuthAccount object to obtain the HUAWEI ID information.
     *
     * @param authAccount AuthAccount object, which contains the HUAWEI ID information.
     */
    private void dealWithResultOfSignIn(AuthAccount authAccount) {
        // Obtain the HUAWEI DI information.
        Log.i(TAG, "display name:" + authAccount.getDisplayName());
        Log.i(TAG, "photo uri string:" + authAccount.getAvatarUriString());
        Log.i(TAG, "photo uri:" + authAccount.getAvatarUri());
        Log.i(TAG, "email:" + authAccount.getEmail());
        Log.i(TAG, "openid:" + authAccount.getOpenId());
        Log.i(TAG, "unionid:" + authAccount.getUnionId());
        // TODO: Implement service logic after the HUAWEI ID information is obtained.

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            Log.i(TAG, "onActivitResult of sigInInIntent, request code: " + REQUEST_CODE_SIGN_IN);
            Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
            if (authAccountTask.isSuccessful()) {
                // The sign-in is successful, and the authAccount object that contains the HUAWEI ID information is obtained.
                AuthAccount authAccount = authAccountTask.getResult();
                dealWithResultOfSignIn(authAccount);
                Log.i(TAG, "onActivitResult of sigInInIntent, request code: " + REQUEST_CODE_SIGN_IN);
            } else {
                // The sign-in fails. Find the failure cause from the status code. For more information, please refer to Error Codes.
                Log.e(TAG, "sign in failed : " +((ApiException)authAccountTask.getException()).getStatusCode());
            }
        }
    }
}