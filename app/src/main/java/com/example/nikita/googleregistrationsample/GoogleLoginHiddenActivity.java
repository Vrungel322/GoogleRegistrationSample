package com.example.nikita.googleregistrationsample;
/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import timber.log.Timber;

public class GoogleLoginHiddenActivity extends AppCompatActivity
    implements GoogleApiClient.OnConnectionFailedListener {

  public static final String EXTRA_CLIENT_ID = "EXTRA_CLIENT_ID";
  private static final int RC_SIGN_IN = 1000;

  private GoogleApiClient mGoogleApiClient;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    GoogleSignInOptions gso =
        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestId()
            .requestProfile()
            .requestIdToken(getIntent().getStringExtra(EXTRA_CLIENT_ID))
            .requestEmail()
            .build();

    mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();

    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Throwable throwable = new Throwable(connectionResult.getErrorMessage());
    Timber.e("onConnectionFailed");
    Timber.e(throwable);
    //SocialLoginManager.getInstance(this).onLoginError(throwable);
    //finish();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Timber.e("onActivityResult");

    if (requestCode == RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      handleSignInResult(result);
      Timber.e("requestCode == RC_SIGN_IN");
    }
  }

  private void handleSignInResult(GoogleSignInResult result) {
    Timber.e(result.getStatus().toString());

    if (result.isSuccess()) {
      Timber.e("isSuccess");
      GoogleSignInAccount acct = result.getSignInAccount();
      SocialUser user = new SocialUser();
      user.userId = acct.getId();
      user.accessToken = acct.getIdToken();
      user.photoUrl = acct.getPhotoUrl() != null ? acct.getPhotoUrl().toString() : "";
      SocialUser.Profile profile = new SocialUser.Profile();
      profile.email = acct.getEmail();
      profile.name = acct.getDisplayName();
      profile.firstName = acct.getGivenName();
      profile.lastName = acct.getFamilyName();
      user.profile = profile;
      Auth.GoogleSignInApi.signOut(mGoogleApiClient);
      Timber.e(user.toString());
      //SocialLoginManager.getInstance(this).onLoginSuccess(user);
    } else {
      Throwable throwable = new Throwable(result.getStatus().getStatusMessage());
      Timber.e(throwable);
      //SocialLoginManager.getInstance(this).onLoginError(throwable);
    }

    //finish();
  }
}
