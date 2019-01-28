package com.gb.istandwithrefugeesapp;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.SkinManager;
import com.facebook.accountkit.ui.UIManager;
import com.gb.istandwithrefugeesapp.Model.DBHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.File;
import java.net.InetAddress;
import java.util.HashMap;

public class SplashScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private Bitmap bitmap;
    private Bitmap bitmap2;
    private RelativeLayout layout;
    private ProgressDialog progressDialog;

    private static final int APP_REQUEST_CODE = 99;
    private final AccessToken accessToken = AccountKit.getCurrentAccessToken();
    private String phoneNumberString;
    private ProgressDialog pg;
    private InetAddress ipAddr;
    private boolean hasInternet;
    private RelativeLayout relativeLayout;
    private ImageView imageView;
    private DBHelper dbHelper;
    private int nextPermissionsRequestCode = 4000;
    private final HashMap<Integer, OnCompleteListener> permissionsListeners = new HashMap<>();


    /**
     * Checks that the user has download permissions. dialog box prompts the user for permissions
     */
    private void checkDownloadPermissions() {
        OnCompleteListener completeListener = new OnCompleteListener() {
            @Override
            public void onComplete() {
                downloadData();                    }
        };
        final OnCompleteListener writeExternalListener = completeListener;
        completeListener = new OnCompleteListener() {
            @Override
            public void onComplete() {
                requestPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        R.string.permissions_write_external_title,
                        R.string.permissions_write_external_message,
                        writeExternalListener);
            }
        };
        completeListener.onComplete();
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    /**
     * Initializes the AWS mobile client with the set credentials. passes downloadWithTransferUtility() message.
     */
    private void downloadData() {
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {

                downloadWithTransferUtility();
            }

        }).execute();
    }

    /**
     * Method to download small file, to check if the user has a stable internet connection. Progress Dialog is shown to
     * indicate loading.
     * Amazon S3 client is initialised in the TransferUtility builder. Download observer listens for the state of the file
     * download. On Failure, error is displayed on ui as toast message. On complete, progress dialog is closed and a check is
     * performed to see if the user has previously signed in and been granted an access token. If they have, the database helper method
     * dbHelper.getMapMarkersAndLatLongs() is called to load the app data. Else, phone login is called to login through
     * facebook account kit sdk.
     */
    private void downloadWithTransferUtility() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in");
        progressDialog.show();
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                "oy" + ".3gp");
        TransferObserver downloadObserver =
                transferUtility.download(
                        "public/51to52",
                        file);

        // Attach a listener to the observer to get state update and progress notifications
        downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    progressDialog.dismiss();
                    // Handle a completed upload.
                    if (accessToken != null) {
                        dbHelper.getMapMarkersAndLatLongs();
                    } else {
                        phoneLogin();
                    }                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("MainActivity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }
            @Override
            public void onError(int id, Exception ex) {
                progressDialog.dismiss();
                System.out.println(ex + " : " + id);
                toastNetworkMsg();
            }
        });
        Log.d("YourActivity", "Bytes Transferrred: " + downloadObserver.getBytesTransferred());
        Log.d("YourActivity", "Bytes Total: " + downloadObserver.getBytesTotal());
    }

    /**
     * Passes dbHelper collections to Application singleton class, so they can be retrieved on loading the MainActivity.
     * Current activity replaced by the MainActivity, Finish() called so the activity is garbage collected.
     */
    public void loadMainActivity(){
        ((MyAppApplication) this.getApplication()).setLongLatMap(dbHelper.getLongLatMap());
        ((MyAppApplication) this.getApplication()).setMarkersMap(dbHelper.getMarkersMap());
        ((MyAppApplication) this.getApplication()).setLogoUrls(dbHelper.getLogoUrls());
        ((MyAppApplication) this.getApplication()).setOnlineAids(dbHelper.getOnlineAids());
        ((MyAppApplication) this.getApplication()).setLogoUrlsOnline(dbHelper.getLogoUrlsOnline());
        ((MyAppApplication) this.getApplication()).setOverseasMarkersMap(dbHelper.getOverseasMarkersMap());
        ((MyAppApplication) this.getApplication()).setLogoUrlsOverseas(dbHelper.getLogoUrlsOverseas());
        ((MyAppApplication) this.getApplication()).setLongLatMapEU(dbHelper.getLongLatMapEU());
        ((MyAppApplication) this.getApplication()).setCountryAndRegionsArray(dbHelper.getCountryAndRegionsArray());

        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        dbHelper = null;
        finish();
    }

    /**
     * onClick responds to login button onClick listener. If a network connection is available, checkDownloadPermissions()
     * is called. Else, toastNetworkMsg() is called to notify the user that they have no internet connection
     */
    @Override
    public void onClick(View v) {
            if(isNetworkAvailable()){
                checkDownloadPermissions();         }
            else {toastNetworkMsg();}
        }

    /**
     * Toast, notifies the user that they have no internet connection
     */
    private void toastNetworkMsg() {
        Toast.makeText(this, "No Internet Connection Detected", Toast.LENGTH_SHORT).show();
    }

    private interface OnCompleteListener {
        void onComplete();
    }

    @Override
        protected  void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splashscreen);
            dbHelper = new DBHelper(this);
            relativeLayout = (RelativeLayout)findViewById(R.id.contents_frame);

            relativeLayout.setBackground(new BitmapDrawable(getResources(), decodeSampledBitmapFromResource(getResources()
                    ,R.drawable.background, getScreenWidth(), getScreenHeight()))
            );

            imageView = this.findViewById(R.id.imageView1);
            imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources()
                    ,R.drawable.logo,R.dimen._250sdp, R.dimen._250sdp));
            AWSMobileClient.getInstance().initialize(this).execute();
            TextView tx = findViewById(R.id.textviewsplash);
            Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
            tx.setTypeface(custom_font);
            Handler mHandler = new Handler();
            //mHandler.postDelayed(mUpdateTimeTask, 5000);
            Button button = findViewById(R.id.button2);
            button.setOnClickListener(this);
        }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /**
     * Takes a width and height value and downsamples a bitmap until the image is the correct size. Method's purpose is
     * to reduce the memory impact of the bitmap in the cache.
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Clear the Activity's bundle of the subsidiary fragments' bundles.
        outState.clear();
    }

    /**
     * Resource drawable is loaded into bitmap. calculateInSampleSize called to downsample the image to the required size.
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    @Override
    protected  void onResume(){
        super.onResume();
    }

    @Override
    public void onBackPressed() {
       getProgressDialog().setCancelable(false);
       getProgressDialog().dismiss();
    }

    /**
     * Facebook account kit SDK method to log user in
     */
    private void phoneLogin() {
        UIManager uiManager;
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);
        configurationBuilder.setDefaultCountryCode("GB");
        configurationBuilder.setReadPhoneStateEnabled(true);
        configurationBuilder.setReceiveSMS(true);
        uiManager = new SkinManager(
                SkinManager.Skin.CONTEMPORARY,
        getResources().getColor(R.color.toolbarColor),
        R.drawable.background,
                SkinManager.Tint.BLACK,
            55);
        configurationBuilder.setUIManager(uiManager);
        // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        final AccountKitConfiguration configuration = configurationBuilder.build();
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configuration);
        OnCompleteListener completeListener = new OnCompleteListener() {
            @Override
            public void onComplete() {
                startActivityForResult(intent, APP_REQUEST_CODE);
            }
        };
        if (configuration.isReceiveSMSEnabled() && !canReadSmsWithoutPermission()) {
            final OnCompleteListener receiveSMSCompleteListener = completeListener;
            completeListener = new OnCompleteListener() {
                @Override
                public void onComplete() {
                    requestPermissions(
                            Manifest.permission.RECEIVE_SMS,
                            R.string.permissions_receive_sms_title,
                            R.string.permissions_receive_sms_message,
                            receiveSMSCompleteListener);
                }
            };
        }
        if (configuration.isReadPhoneStateEnabled() && !isGooglePlayServicesAvailable()) {
            final OnCompleteListener readPhoneStateCompleteListener = completeListener;
            completeListener = new OnCompleteListener() {
                @Override
                public void onComplete() {
                    requestPermissions(
                            Manifest.permission.READ_PHONE_STATE,
                            R.string.permissions_read_phone_state_title,
                            R.string.permissions_read_phone_state_message,
                            readPhoneStateCompleteListener);
                }
            };
        }
        completeListener.onComplete();
    }

    /**
     * Checks to see if GooglePlayServices Api is available and connection is made.
     */
    private boolean isGooglePlayServicesAvailable() {
        final GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int googlePlayServicesAvailable = apiAvailability.isGooglePlayServicesAvailable(this);
        return googlePlayServicesAvailable == ConnectionResult.SUCCESS;
    }
    private boolean canReadSmsWithoutPermission() {
        final GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int googlePlayServicesAvailable = apiAvailability.isGooglePlayServicesAvailable(this);
        return googlePlayServicesAvailable == ConnectionResult.SUCCESS;
        //TODO we should also check for Android O here t18761104

    }

    /**
     * Checks to see if the requested permission (e.g Manifest.permission.WRITE_EXTERNAL_STORAGE) is granted. If not,
     * dialog box is shown to prompt user for permission.
     */
    private void requestPermissions(
            final String permission,
            final int rationaleTitleResourceId,
            final int rationaleMessageResourceId,
            final OnCompleteListener listener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (listener != null) {
                listener.onComplete();
            }
            return;
        }

        checkRequestPermissions(
                permission,
                rationaleTitleResourceId,
                rationaleMessageResourceId,
                listener);
    }


    @TargetApi(23)
    private void checkRequestPermissions(
            final String permission,
            final int rationaleTitleResourceId,
            final int rationaleMessageResourceId,
            final OnCompleteListener listener) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            if (listener != null) {
                listener.onComplete();
            }
            return;
        }

        final int requestCode = nextPermissionsRequestCode++;
        permissionsListeners.put(requestCode, listener);

        if (shouldShowRequestPermissionRationale(permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(rationaleTitleResourceId)
                    .setMessage(rationaleMessageResourceId)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            requestPermissions(new String[] { permission }, requestCode);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            // ignore and clean up the listener
                            permissionsListeners.remove(requestCode);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            requestPermissions(new String[]{ permission }, requestCode);
        }
    }

    @TargetApi(23)
    @SuppressWarnings("unused")
    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           final @NonNull String permissions[],
                                           final @NonNull int[] grantResults) {
        final OnCompleteListener permissionsListener = permissionsListeners.remove(requestCode);
        if (permissionsListener != null
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionsListener.onComplete();
        }
    }

    /**
     * Checks to see if a network connection is available (e.g data or wifi);
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * onStop (e.g app is sent to the background) the images are nulled
     */
    @Override
    protected void onStop() {
        super.onStop();
        imageView.setImageBitmap(null);
        imageView.setImageDrawable(null);
        relativeLayout.setBackground(null);
    }

    /**
     * restores previously nulled images
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        relativeLayout.setBackground(new BitmapDrawable(getResources(), decodeSampledBitmapFromResource(getResources()
                ,R.drawable.background, getScreenWidth(), getScreenHeight()))
        );
        imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources()
                ,R.drawable.logo,R.dimen._250sdp, R.dimen._250sdp));
    }


    /**
     * dbHelper.getMapMarkersAndLatLongs() is called to load the app data.
     */
    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if(!loginResult.wasCancelled()){
                dbHelper.getMapMarkersAndLatLongs();
            }
            else {
                Toast.makeText(this, "Please login", Toast.LENGTH_LONG).show();
            }
            }
        }
    }

