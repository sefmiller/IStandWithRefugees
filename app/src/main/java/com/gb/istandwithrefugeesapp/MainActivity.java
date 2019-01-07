       package com.gb.istandwithrefugeesapp;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.androidmapsextensions.ClusterGroup;
import com.androidmapsextensions.ClusterOptions;
import com.androidmapsextensions.ClusterOptionsProvider;
import com.androidmapsextensions.ClusteringSettings;
import com.androidmapsextensions.MarkerOptions;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.gb.istandwithrefugeesapp.Model.BookmarkType;
import com.gb.istandwithrefugeesapp.Model.Charity;
import com.gb.istandwithrefugeesapp.Model.DBHelper;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gb.istandwithrefugeesapp.Model.Fundraiser;
import com.gb.istandwithrefugeesapp.Model.LatLong;
import com.gb.istandwithrefugeesapp.Model.UkMarker;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.PointTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.google.maps.android.clustering.algo.Algorithm;
import com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.mvc.imagepicker.ImagePicker;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;


       public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String JSON_URL = "http://ec2-34-207-107-248.compute-1.amazonaws.com/get_all_latlongmarkers.php";
    private TreeMap<Integer, Object> contributionsMap;
    private int contributionsPosition;
    private boolean isActivityInForeground = true;
           private int in = 0;

           public boolean isActivityInForeground() {
               return isActivityInForeground;
           }

           public void setActivityInForeground(boolean activityInForeground) {
               isActivityInForeground = activityInForeground;
           }

           public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public Bitmap getCharityLogo() {
        return charityLogo;
    }

    public void setCharityLogo(Bitmap charityLogo) {
        this.charityLogo = charityLogo;
    }

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MainActivity anActivity;
    private boolean isHome;
    private int downloadResultType;
    private UkListAdapter ukListAdapter;
    private BookmarksListAdapter bookmarksListAdapter;
    private BookmarkType type;
    private Bitmap charityLogo;
    private SparseArray longLatMap;
    private SelectedOrgFragment selectedOrgFrag;
    private ProgressDialog progressDialog;
    private ArrayList<String> selectedTypeOfAid;
    private ArrayList<String> regions;
    private  String typeOfAid;
    private CustomDialogUkMap CustomDialogUkMap;
           public Account getAccount() {
        return account;
    }

    private void setAccount(Account account) {
        this.account = account;
    }

           public com.gb.istandwithrefugeesapp.CustomDialogUkMap getCustomDialogUkMap() {
               return CustomDialogUkMap;
           }

           public void setCustomDialogUkMap(CustomDialogUkMap cduk) {
               this.CustomDialogUkMap = cduk;
           }


           private Account account;

           public ArrayList<String> getSelectedTypeOfAid() {
               return selectedTypeOfAid;
           }

           public void setSelectedTypeOfAid(ArrayList<String> selectedTypeOfAid) {
               this.selectedTypeOfAid = selectedTypeOfAid;
           }

           public ArrayList<String> getRegions() {
               return regions;
           }

           public void setRegions(ArrayList<String> regions) {
               this.regions = regions;
           }

           public String getPhoneNumberString() {
        return phoneNumberString;
    }

           public String getTypeOfAid() {
               return typeOfAid;
           }

           public void setTypeOfAid(String typeOfAid) {
               this.typeOfAid = typeOfAid;
           }

           MenuItem previousItem;
    private final ActionBarDrawerToggle drawerToggle = null;

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    private DBHelper dbHelper;
    protected GeoDataClient mGeoDataClient;
    private String phoneNumberString;
    private int bookMarksMapPosition;
    private Charity charityToBeAdded;
    private Fundraiser fundraiserToBeAdded;
    private SparseArray<HashMap<String, UkMarker>> ukMap;
    private TreeMap<Integer, Object> bookmarksMap;
    private SparseArray<UkMarker> overseasMap;
    private SparseArray<UkMarker> onlineMap;

    private TreeMap<Integer, Object> getContributionsMap() {
        return contributionsMap;
    }

    public void setContributionsMap(TreeMap<Integer, Object> contributionsMap) {
        this.contributionsMap = contributionsMap;
    }

    public TreeMap<Integer, Object> getBookmarksMap() {
        return bookmarksMap;
    }

    public void setBookmarksMap(TreeMap<Integer, Object> bookmarksMap) {
        this.bookmarksMap = bookmarksMap;
    }
           @Override
           protected void onCreate(Bundle savedInstanceState) {
               super.onCreate(savedInstanceState);
               setContentView(R.layout.main_activity);
               anActivity = this;
               dbHelper = new DBHelper(anActivity);
               dbHelper.setLongLatMap(((MyAppApplication) this.getApplication()).getLongLatMap());
               dbHelper.setMarkersMap(((MyAppApplication) this.getApplication()).getMarkersMap());
               dbHelper.setLogoUrls(((MyAppApplication) this.getApplication()).getLogoUrls());
               ((MyAppApplication) this.getApplication()).setLongLatMap(null);
               ((MyAppApplication) this.getApplication()).setMarkersMap(null);
               ((MyAppApplication) this.getApplication()).setLogoUrls(null);
               if (dbHelper.getMarkersMap().size() == 0){
                   Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
                   startActivity(intent);
                   finish();
               }
               AndroidThreeTen.init(this);
               AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                   @Override
                   public void onSuccess(final Account account) {
                       anActivity.setAccount(account);
                       getUserTable();
                   }
                   @Override
                   public void onError(final AccountKitError error) {
                       Toast.makeText(anActivity, "Error: Please Login", Toast.LENGTH_LONG).show();
                       AccountKit.logOut();
                       Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
                       startActivity(intent);
                       finish();
                   }
               });

               AWSMobileClient.getInstance().initialize(this).execute();

               Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
               Toolbar toolbar = findViewById(R.id.toolbar);
               mTitle = toolbar.findViewById(R.id.toolbar_title);
               mTitle.setTypeface(custom_font);
               setSupportActionBar(toolbar);
               getSupportActionBar().setDisplayShowTitleEnabled(false);
               getSupportActionBar().setDisplayHomeAsUpEnabled(true);
               drawerLayout = findViewById(R.id.drawer_layout);
               ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                       this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
               drawerLayout.setDrawerListener(toggle);
               toggle.syncState();
               getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
               //CurrentPlatform.Init();
               //TodoItem item = new TodoItem { Text = "Awesome item" };
               //await MobileService.GetTable<TodoItem>().InsertAsync(item);

               // Tie DrawerLayout events to the ActionBarToggle
               drawerLayout.addDrawerListener(drawerToggle);

               getSupportActionBar().setDisplayHomeAsUpEnabled(true);
               navigationView = findViewById(R.id.nvView);


               Menu m = navigationView.getMenu();
               for (int i = 0; i < m.size(); i++) {
                   MenuItem mi = m.getItem(i);
                   applyFontToMenuItem(mi);
               }

               android.view.View hView = navigationView.getHeaderView(0);

               TextView nav_user = hView.findViewById(R.id.nav_header_title);
               ImageView nav_image = (ImageView)hView.findViewById(R.id.navheader_userimage);
               Glide.with(this)
                       .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/logo.png")
                       .into(nav_image);
               nav_user.setTypeface(custom_font);
               navigationView.setNavigationItemSelectedListener(this);

           }
    public void getAllbookmarks(){
        bookMarksMapPosition = 1;
        bookmarksMap = new TreeMap<>();
        getUkBookmarks();
    }

    private void getUkBookmarks(){
        if (!anActivity.dbHelper.getBookmarksArray().isEmpty()) {
            for (int i = 0; i < ukMap.size(); i++) {
                if (anActivity.dbHelper.getBookmarksArray().contains(i + 1)) {
                    HashMap currenMarkerMap = ukMap.get(i + 1);
                    TreeMap<Enum, HashMap<String, UkMarker>> ukMarkerMap = new TreeMap();
                    ukMarkerMap.put(BookmarkType.UK, currenMarkerMap);
                    bookmarksMap.put(bookMarksMapPosition, ukMarkerMap);
                    bookMarksMapPosition++;
                }
            }
        }
        getOverseasBookmarks();
    }

    private void getOverseasBookmarks(){
        if (!anActivity.dbHelper.getBookmarksOverseasArray().isEmpty()) {
            for (int i = 0; i < overseasMap.size(); i++) {
                if (anActivity.dbHelper.getBookmarksOverseasArray().contains(i + 1)) {
                    UkMarker overseasItem = overseasMap.get(i + 1);
                    TreeMap<Enum, UkMarker> osMap = new TreeMap();
                    osMap.put(BookmarkType.OVERSEAS, overseasItem);
                    bookmarksMap.put(bookMarksMapPosition, osMap);
                    bookMarksMapPosition++;
                }
            }
        }
        getOnlineBookmarks();
    }
    private void getOnlineBookmarks(){
        if (!anActivity.dbHelper.getBookmarksOnlineArray().isEmpty()) {
            for (int i = 0; i < onlineMap.size(); i++) {
                if (anActivity.dbHelper.getBookmarksOnlineArray().contains(i + 1)) {
                    UkMarker onlineItem = onlineMap.get(i + 1);
                    TreeMap<Enum, UkMarker> onMap = new TreeMap();
                    onMap.put(BookmarkType.OVERSEAS, onlineItem);
                    bookmarksMap.put(bookMarksMapPosition, onMap);
                    bookMarksMapPosition++;
                }
            }
        }
    }

    private void getAllContributions(){
        contributionsPosition = 0;
        contributionsMap = new TreeMap<>();
        getUkContributions();
    }

    private void getUkContributions(){
        for (int i = 0; i < ukMap.size(); i++) {
                HashMap currentMarkerMap = ukMap.get(i + 1);
                if (currentMarkerMap.containsKey("Organisation")){
                    final Charity charity = (Charity) currentMarkerMap.get("Organisation");
                    if (charity.getLoginId() == Integer.parseInt(anActivity.getDbHelper().getLoginId())){
                        TreeMap<Enum, HashMap<String, UkMarker>> ukMarkerMap = new TreeMap();
                        ukMarkerMap.put(BookmarkType.UK, currentMarkerMap);
                        contributionsMap.put(contributionsPosition, ukMarkerMap);
                        contributionsPosition++;
                    }
                }
                if (currentMarkerMap.containsKey("Fundraiser")){
                    final Fundraiser fundraiser = (Fundraiser) currentMarkerMap.get("Fundraiser");
                    if (fundraiser.getLoginId() == Integer.parseInt(anActivity.getDbHelper().getLoginId())){
                        TreeMap<Enum, HashMap<String, UkMarker>> ukMarkerMap = new TreeMap();
                        ukMarkerMap.put(BookmarkType.UK, currentMarkerMap);
                        contributionsMap.put(contributionsPosition, ukMarkerMap);
                        contributionsPosition++;
                    }
                }
            }
        getOverseasContributions();
    }

    private void getOverseasContributions(){
        getOnlineContributions();
    }

    private void getOnlineContributions(){


    }

    public String getSearchString() {
        return searchString;
    }

    public String getArea() {
        return area;
    }

    private boolean isFund() {
        return isFund;
    }

    private boolean isOrg() {
        return isOrg;
    }

    private String searchString;
    private String area;

    private void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    private void setArea(String area) {
        this.area = area;
    }

    private void setFund(boolean fund) {
        isFund = fund;
    }

    private void setOrg(boolean org) {
        isOrg = org;
    }

    private boolean isFund;
    private boolean isOrg;

    private TextView getmTitle() {
        return mTitle;
    }

    public void setmTitle(TextView mTitle) {
        this.mTitle = mTitle;
    }

    private TextView mTitle;

    public static String getJsonUrl() {
        return JSON_URL;
    }

    Toolbar toolbar;




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

    private void checkDownloadPermissions(int i) {
            downloadResultType = i;
        MainActivity.OnCompleteListener completeListener = new MainActivity.OnCompleteListener() {
            @Override
            public void onComplete() {
                dowloadData();                    }
        };
        final MainActivity.OnCompleteListener writeExternalListener = completeListener;
        completeListener = new MainActivity.OnCompleteListener() {
            @Override
            public void onComplete() {
                requestPermissions(
                        writeExternalListener);
            }
        };
        completeListener.onComplete();

    }


    private void dowloadData() {
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {

                downloadWithTransferUtility();
            }

        }).execute();
    }

    private void downloadWithTransferUtility() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
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
                     switch (downloadResultType){
                         case 0:
                             ukListAdapter.downloadResult();
                             break;
                         case 1:
                             bookmarksListAdapter.downloadResult();
                             break;
                         case 2:
                             selectedOrgFrag.downloadResult();
                             break;
                     }
                }
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
                toastNetworkMsg();

            }

        });


        Log.d("YourActivity", "Bytes Transferrred: " + downloadObserver.getBytesTransferred());
        Log.d("YourActivity", "Bytes Total: " + downloadObserver.getBytesTotal());
    }

    private void toastNetworkMsg() {
        Toast.makeText(this, "No Internet Connection Detected", Toast.LENGTH_SHORT).show();
    }

    public void checkDownloadPermissionsBookmarksUk(int i, UkListAdapter ukListAdapter) {
            this.ukListAdapter = ukListAdapter;
            checkDownloadPermissions(0);
    }

    public void checkDownloadPermissionsBookmarks(int i, BookmarksListAdapter bookmarksListAdapter) {
        this.bookmarksListAdapter = bookmarksListAdapter;
        checkDownloadPermissions(1);
    }

    private void checkDownloadPermissionsSelected(int i, MainActivity.SelectedOrgFragment selectedOrgFragment) {
        this.selectedOrgFrag = selectedOrgFragment;
        checkDownloadPermissions(2);
    }


    private void setType(BookmarkType type) {
        this.type = type;
    }

    public BookmarkType getType() {
        return type;
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.WEBP, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        String cursorString = cursor.getString(idx);
        cursor.close();
        return cursorString;
    }

    private void uploadData() {
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                uploadWithTransferUtility();
            }

        }).execute();
    }


    private void uploadWithTransferUtility() {
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Nearly there...");
        progressDialog.show();
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider().getCredentials()))
                                .build();

        Bitmap bitmap = Bitmap.createScaledBitmap(anActivity.charityLogo, 150, 150, true);

        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        Uri tempUri = getImageUri(getApplicationContext(), bitmap);

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        File finalFile = new File(getRealPathFromURI(tempUri));

        final String key = "public/" + charityToBeAdded.getMarkerId() + ".png";
        final String logo = "https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/" + key;
        charityToBeAdded.setImageUrl(logo);
        TransferObserver uploadObserver =
                transferUtility.upload(
                        key,
                        finalFile,
                        CannedAccessControlList.PublicReadWrite);

        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    int dimen = (int) anActivity.getResources().getDimensionPixelSize(R.dimen._50sdp);
                    Glide.with(anActivity)
                            .load(logo).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .signature(new ObjectKey(charityToBeAdded.getLastModified())))
                            .preload(dimen, dimen);
                    progressDialog.dismiss();
                    HomeFragment frag = new HomeFragment();
                    loadFragment(frag);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("MainActivity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                System.out.println(ex);
                progressDialog.dismiss();
                toastNetworkMsg();

            }

        });


        Log.d("YourActivity", "Bytes Transferrred: " + uploadObserver.getBytesTransferred());
        Log.d("YourActivity", "Bytes Total: " + uploadObserver.getBytesTotal());
    }
    public void addLogo() {
            uploadData();
    }

    private interface OnCompleteListener {
        void onComplete();
    }
    private int nextPermissionsRequestCode = 4000;
    private final SparseArray<OnCompleteListener> permissionsListeners = new SparseArray<>();


    private void requestPermissions(
            final OnCompleteListener listener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (listener != null) {
                listener.onComplete();
            }
            return;
        }

        checkRequestPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                R.string.permissions_write_external_title,
                R.string.permissions_write_external_message,
                listener);
    }

    @TargetApi(23)
    private void checkRequestPermissions(
            final String permission,
            final int rationaleTitleResourceId,
            final int rationaleMessageResourceId,
            final MainActivity.OnCompleteListener listener) {
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
        final MainActivity.OnCompleteListener permissionsListener = permissionsListeners.get(requestCode);
        if (permissionsListener != null
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionsListener.onComplete();
        }
    }

    public void loadFragment(final Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null).commit();    }

    /**
     * Asynchronous task.
     * called in onCreate. used to retrieve all user data on login
     * task creates new database object with constructor dbHelper = new DatabaseHelper(username);
     * task forwards to database helper method getTables. passes in url which points to the php script to get the user data
     * from the backend database.
     */
    private void getUserTable() {
         class  GetUserTy extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                System.out.println("here");
                PhoneNumber phoneNumber = account.getPhoneNumber();
                phoneNumberString = phoneNumber.toString();
                System.out.println(phoneNumberString);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                dbHelper.getTables();
            }
        }
        GetUserTy gut = new GetUserTy();
        gut.execute();
    }

    public void init() {
        class Init extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

                HomeFragment frag = new HomeFragment();
                loadFragment(frag);
            }
        }
        Init i = new Init();
        i.execute();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.item_home:
                ListItemClicked(0);
                break;
            case R.id.item_find_uk:
                Fragment fragment = new UkMapFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.item_list_uk:
                ListItemClicked(2);
                break;
            case R.id.item_add_uk:
                ListItemClicked(3);
                break;
            case R.id.item_add_fund_uk:
                ListItemClicked(4);
                break;
            case R.id.item_find_overseas:
              //  ListItemClicked(5);
                break;
            case R.id.item_list_overseas:
                //ListItemClicked(6);
                break;
            case R.id.item_add_overseas:
                //ListItemClicked(7);
                break;
            case R.id.item_other_resources:
                ListItemClicked(8);
                break;
            case R.id.item_list_online:
                //ListItemClicked(9);
                break;
            case R.id.add_online_item:
                //ListItemClicked(10);
                break;
            case R.id.cont_item:
                ListItemClicked(11);
                break;
            case R.id.bookmark_item:
                ListItemClicked(12);
                break;
            case R.id.logout_item:
                logout();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout() {
        class Logout extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                getFragmentManager().popBackStack();
                AccountKit.logOut();
                super.onPostExecute(result);
                Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
                startActivity(intent);
                finish();
            }
        }
        Logout logout = new Logout();
        logout.execute();
    }
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


    private void ListItemClicked(int position) {
        android.support.v4.app.Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 2:
                fragment = new ListMarkersFragment();
                break;
            case 3:
                fragment = new AddTitleUkFragment();
                anActivity.charityLogo = null;
                anActivity.charityToBeAdded = new Charity("", "", "http://www.", "",
                        "", "", "", "", "", "", "", "",
                        0, Integer.parseInt(anActivity.getDbHelper().getLoginId()));
                break;
            case 4:
                fragment = new AddTitleUkFundFragment();
                anActivity.fundraiserToBeAdded = new Fundraiser("", "", "http://www.", "",
                        "", "", "", "", "", "", "","", "",
                        null, 0, Integer.parseInt(anActivity.getDbHelper().getLoginId()));
                break;
            case 8:
                fragment = new OtherResourcesFragment();
                break;
            case 12:
                fragment = new BookmarksFragment();
                break;
            case 11:
                anActivity.charityLogo = null;
                fragment = new ManageContributionsFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

    public boolean isHome() {
        return isHome;
    }

    private void setHome(boolean home) {
        isHome = home;
    }

           public static class HomeFragment extends Fragment implements View.OnClickListener {
               ImageView viewOneImg;
               ImageView viewTwoImg;
               ImageView viewThreeImg;
               ImageView viewFourImg;
               private TextView viewOneTv;
               private TextView viewTwoTv;
               private TextView viewFourTv;
               private TextView viewThreeTv;
               private MainActivity anActivity;
               private SharedPreferences tutorialShowcases;
               private ShowcaseView showcaseView;

               @Override
               public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
                   if (viewGroup != null) {
                       viewGroup.removeAllViews();
                   }
                   //inflates xml layout. sets toolbar title to 'Home'
                   View view = inflater.inflate(R.layout.fragment_home, viewGroup, false);
                   anActivity = (MainActivity) getActivity();
                   assert anActivity != null;
                   anActivity.getmTitle().setText(R.string.home);
                   anActivity.setHome(true);
                   anActivity.getSupportActionBar().show();
                   viewOneImg = view.findViewById(R.id.ukimg1);
                   viewTwoImg = view.findViewById(R.id.ukimg2);
                   viewThreeImg = view.findViewById(R.id.ukimg3);
                   viewFourImg = view.findViewById(R.id.ukimg4);

                   Glide.with(this)
                           .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/ukhomebutton.png")

                           .into(viewOneImg);
                   Glide.with(this)
                           .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/overseashomebutton.png")
                           .into(viewTwoImg);
                   Glide.with(this)
                           .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/onlinehomebutton.png")
                           .into(viewThreeImg);
                   Glide.with(this)
                           .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/homepersonalbutton.png")
                           .into(viewFourImg);

                   viewOneImg.setOnClickListener(this);
                   viewTwoImg.setOnClickListener(this);
                   viewThreeImg.setOnClickListener(this);
                   viewFourImg.setOnClickListener(this);

                   viewOneTv = view.findViewById(R.id.uktext1);
                   viewTwoTv = view.findViewById(R.id.uktext2);
                   viewThreeTv = view.findViewById(R.id.uktext3);
                   viewFourTv = view.findViewById(R.id.uktext4);

                   tutorialShowcases = anActivity.getSharedPreferences("showcaseTutorial", MODE_PRIVATE);

                   //sets font for icon text
                   Typeface titleFont = Typeface.
                           createFromAsset(getContext().getAssets(), "fonts/Pacifico.ttf");
                   viewOneTv.setTypeface(titleFont);
                   viewTwoTv.setTypeface(titleFont);
                   viewThreeTv.setTypeface(titleFont);
                   viewFourTv.setTypeface(titleFont);


                   TextPaint titleFormat = new TextPaint();
                   titleFormat.setColor(0xffffff);
                   if (isShowCaseAllowed()) {
                       initShowCase();
                   }
                   return view;
               }

               boolean isShowCaseAllowed() {
                   boolean isShowCaseAllowed = tutorialShowcases.getBoolean("isShowCaseAllowed", true);
                   boolean test = tutorialShowcases.contains("isShowCaseAllowed");
                   System.out.println(test);
                   //return true;
                   return isShowCaseAllowed;
               }

               void initShowCase() {
                   PointTarget home = new PointTarget(16, 16);  //Design guideline is 32x32
                   showcaseView = new ShowcaseView.Builder(anActivity)
                           .setStyle(R.style.CustomShowcaseTheme2)
                           .setTarget(home)
                           .setContentTitle("Tip: ")
                           .setContentText("Use the Menu Drawer to navigate through your Personal Content, as well as existing Action in the Uk, Online and Overseas")
                           .blockAllTouches()
                           .build();
                   showcaseView.overrideButtonClick(new View.OnClickListener() {
                       int count1 = 0;

                       @Override
                       public void onClick(View v) {
                           count1++;
                           switch (count1) {
                               case 1:
                                   showcaseView.setContentText("You can also navigate through the app using the buttons on the Home screen");
                                   showcaseView.setShowcase(new ViewTarget(viewOneImg), false);
                                   break;
                               case 2:
                                   showcaseView.hide();
                                   SharedPreferences.Editor tutorialShowcasesEdit = tutorialShowcases.edit();
                                   tutorialShowcasesEdit.putBoolean("isShowCaseAllowed", false);
                                   tutorialShowcasesEdit.apply();
                                   break;
                           }
                       }
                   });
               }

               @Override
               public void onDestroyView() {
                   showcaseView = null;
                   viewOneImg.setImageDrawable(null);
                   viewTwoImg.setImageDrawable(null);
                   viewThreeImg.setImageDrawable(null);
                   viewFourImg.setImageDrawable(null);
                   viewOneImg.setImageBitmap(null);
                   viewTwoImg.setImageBitmap(null);
                   viewThreeImg.setImageBitmap(null);
                   viewFourImg.setImageBitmap(null);
                   anActivity = null;
                   super.onDestroyView();
               }


               /**
                * @param v View which is clicked and responded to onClicklistener
                *          Loads the fragment which relates to the icon and text. E.g if user clicks 'Add Refugee' grid option,
                *          AddRefInsFragment() is loaded into the content frame.
                *          OnClick options differ depending on whether the user is a refugee or aidworker.
                */
               @Override
               public void onClick(View v) {
                   MainActivity anActivity = (MainActivity) getActivity();
                   if (v == viewOneImg) {
                       android.support.v4.app.Fragment frag = new UKFragment();
                       assert anActivity != null;
                       anActivity.loadFragment(frag);
                   }
                   if (v == viewTwoImg) {
                       android.support.v4.app.Fragment frag = new OverseasFragment();
                       assert anActivity != null;
                       anActivity.loadFragment(frag);
                   }
                   if (v == viewThreeImg) {
                       android.support.v4.app.Fragment frag = new OnlineFragment();
                       assert anActivity != null;
                       anActivity.loadFragment(frag);
                   }
                   if (v == viewFourImg) {
                       android.support.v4.app.Fragment frag = new PersonalFragment();
                       assert anActivity != null;
                       anActivity.loadFragment(frag);
                   }
               }


           }

    public static class UKFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

        ImageView ukimg_1;
        ImageView ukimg_2;
        ImageView ukimg_3;
        ImageView ukimg_4;

        private MainActivity anActivity;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            View view = inflater.inflate(R.layout.uk_fragment, viewGroup, false);

            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText(R.string.action_uk);
            anActivity.getSupportActionBar().show();
            ukimg_1 = view.findViewById(R.id.ukimg1);
            ukimg_2 = view.findViewById(R.id.ukimg2);
            ukimg_3 = view.findViewById(R.id.ukimg3);
            ukimg_4 = view.findViewById(R.id.ukimg4);
            int dimen = (int)anActivity.getResources().getDimensionPixelSize(R.dimen._100sdp);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/searchbutton.png")
                    .into(ukimg_1);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/list_button.png")
                    .into(ukimg_2);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/add_org_button.png")
                    .into(ukimg_3);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/fundraiserbutton.png")
                    .into(ukimg_4);

            //pickup button
            ukimg_1.setOnClickListener(this);
            ukimg_2.setOnClickListener(this);
            ukimg_3.setOnClickListener(this);
            ukimg_4.setOnClickListener(this);

            TextView viewOneTv = view.findViewById(R.id.uktext1);
            TextView viewTwoTv = view.findViewById(R.id.uktext2);
            TextView viewThreeTv = view.findViewById(R.id.uktext3);
            TextView viewFourTv = view.findViewById(R.id.uktext4);


            //sets font for icon text
            Typeface titleFont = Typeface.
                    createFromAsset(getContext().getAssets(), "fonts/Pacifico.ttf");
            viewOneTv.setTypeface(titleFont);
            viewTwoTv.setTypeface(titleFont);
            viewThreeTv.setTypeface(titleFont);
            viewFourTv.setTypeface(titleFont);

            return view;
        }

        @Override
        public void onClick(View v) {
            if (v == ukimg_4) {
                android.support.v4.app.Fragment frag = new AddTitleUkFundFragment();
                anActivity.fundraiserToBeAdded = new Fundraiser("", "", "http://www.", "",
                        "", "", "", "", "", "", "","", "",
                        null, 0, Integer.parseInt(anActivity.getDbHelper().getLoginId()));
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
            if (v == ukimg_3) {
                android.support.v4.app.Fragment frag = new AddTitleUkFragment();
                anActivity.charityLogo = null;
                anActivity.charityToBeAdded = new Charity("", "", "http://www.", "",
                        "", "", "", "", "", "",
                        "", "", 0, Integer.parseInt(anActivity.getDbHelper().getLoginId()));
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
            if (v == ukimg_2) {
                android.support.v4.app.Fragment frag = new ListMarkersFragment();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
            if (v == ukimg_1) {
                android.support.v4.app.Fragment frag = new UkMapFragment();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag).addToBackStack(null);
                ft.commit();
            }
        }

        @Override
        public void onDestroyView() {
            ukimg_1.setImageDrawable(null);
            ukimg_2.setImageDrawable(null);
            ukimg_3.setImageDrawable(null);
            ukimg_4.setImageDrawable(null);
            ukimg_2.setImageBitmap(null);
            ukimg_1.setImageBitmap(null);
            ukimg_3.setImageBitmap(null);
            ukimg_4.setImageBitmap(null);
            anActivity = null;
            super.onDestroyView();
        }
    }

    public static class OverseasFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
        ImageView overseasimg_1;
        ImageView overseasimg_2;
        ImageView overseasimg_3;
        ImageView overseasimg_4;
        private MainActivity anActivity;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText(R.string.action_overseas);
            anActivity.getSupportActionBar().show();
            View view = inflater.inflate(R.layout.overseas_fragment, viewGroup, false);
            overseasimg_1 = view.findViewById(R.id.overseasimg1);
            overseasimg_2 = view.findViewById(R.id.overseasimg2);
            overseasimg_3 = view.findViewById(R.id.overseasimg3);
            overseasimg_4 = view.findViewById(R.id.overseasimg4);

            overseasimg_4.setOnClickListener(this);

            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/searchbutton.png")
                    .into(overseasimg_1);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/add_org_button.png")
                    .into(overseasimg_2);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/list_button_alt.png")
                    .into(overseasimg_3);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/resources_button.png")
                    .into(overseasimg_4);

            TextView viewOneTv = view.findViewById(R.id.overseastext1);
            TextView viewTwoTv = view.findViewById(R.id.overseastext2);
            TextView viewThreeTv = view.findViewById(R.id.overseastext3);
            TextView viewFourTv = view.findViewById(R.id.overseastext4);

            //sets font for icon text
            Typeface titleFont = Typeface.
                    createFromAsset(getContext().getAssets(), "fonts/Pacifico.ttf");
            viewOneTv.setTypeface(titleFont);
            viewTwoTv.setTypeface(titleFont);
            viewThreeTv.setTypeface(titleFont);
            viewFourTv.setTypeface(titleFont);
            return view;
        }

        @Override
        public void onClick(View v) {
            if (v == overseasimg_4){
                Fragment frag = new OtherResourcesFragment();
                anActivity.loadFragment(frag);
            }
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            overseasimg_1.setImageDrawable(null);
            overseasimg_2.setImageDrawable(null);
            overseasimg_3.setImageDrawable(null);
            overseasimg_4.setImageDrawable(null);
            overseasimg_1.setImageBitmap(null);
            overseasimg_2.setImageBitmap(null);
            overseasimg_3.setImageBitmap(null);
            overseasimg_4.setImageBitmap(null);
            anActivity = null;
        }

    }

    public static class OnlineFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
        ImageView onlineimg_1;
        ImageView onlineimg_2;
        private MainActivity anActivity;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.online_fragment, viewGroup, false);

            onlineimg_1 = view.findViewById(R.id.onlineimg1);
            onlineimg_2 = view.findViewById(R.id.onlineimg2);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/list_button_alt.png")
                    .into(onlineimg_1);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/add_org_button.png")
                    .into(onlineimg_2);

            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText(R.string.online_frag);
            anActivity.getSupportActionBar().show();
            TextView viewOneTv = view.findViewById(R.id.online_text1);
            TextView viewTwoTv = view.findViewById(R.id.online_text2);

            //sets font for icon text
            Typeface titleFont = Typeface.
                    createFromAsset(getContext().getAssets(), "fonts/Pacifico.ttf");
            viewOneTv.setTypeface(titleFont);
            viewTwoTv.setTypeface(titleFont);

            return view;
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onDestroyView() {
            onlineimg_1.setImageDrawable(null);
            onlineimg_2.setImageDrawable(null);
            onlineimg_1.setImageBitmap(null);
            onlineimg_2.setImageBitmap(null);
            anActivity = null;
            super.onStop();
            super.onDestroyView();
        }

        @Override
        public void onResume() {
            anActivity = (MainActivity) getActivity();
            super.onResume();
        }
    }

    public static class AddTitleUkFundFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

        TextView textViewTitle;
        EditText titleBox;
        Button nextButton;
        private MainActivity anActivity;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            View view = inflater.inflate(R.layout.add_uk_title_fund, viewGroup, false);
            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText("Add Title");
            textViewTitle = view.findViewById(R.id.textviewtitle);
            anActivity.getSupportActionBar().show();


            //sets font for icon text
            Typeface titleFont = Typeface.
                    createFromAsset(getContext().getAssets(), "fonts/Lobster_1.3.otf");
            textViewTitle.setTypeface(titleFont);
            textViewTitle.setTypeface(titleFont);

            titleBox = view.findViewById(R.id.title_box);
            titleBox.setText(anActivity.fundraiserToBeAdded.getTitle());


            nextButton = view.findViewById(R.id.next_button);
            nextButton.setOnClickListener(this);

            return view;
        }

        @Override
        public void onClick(View v) {
            if (checkTitle(titleBox.getText().toString())) {
                Fragment frag = new AddDescriptionFundFragment();
                String title = titleBox.getText().toString().trim();
                anActivity.fundraiserToBeAdded.setTitle(title);
                anActivity.loadFragment(frag);
            }
        }

        @Override
        public void onDestroyView() {
            anActivity = null;

            super.onDestroyView();
        }

        private boolean checkTitle(String title){
            boolean result = true;
            if (title.length() <1){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a Title")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }
    }

    public static class AddDescriptionFundFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

        TextView textViewTitle;
        EditText desBox;
        Button nextButton;
        Button previousButton;
        private MainActivity anActivity;
        private TextView charLimitText;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            View view = inflater.inflate(R.layout.add_uk_description, viewGroup, false);

            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText("Add Description");
            textViewTitle = view.findViewById(R.id.textviewtitle);
            anActivity.getSupportActionBar().show();

            //sets font for icon text
            Typeface titleFont = Typeface.
                    createFromAsset(getContext().getAssets(), "fonts/Lobster_1.3.otf");
            textViewTitle.setTypeface(titleFont);
            textViewTitle.setTypeface(titleFont);

            desBox = view.findViewById(R.id.title_box);
            desBox.setText(anActivity.fundraiserToBeAdded.getDescription());
            desBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String des = desBox.getText().toString();
                    int desLength = des.length();
                    int charLimit = 250;
                    int charsRemaining = charLimit - desLength;
                    charLimitText.setText(charsRemaining + " characters remaining");
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String des = desBox.getText().toString();
                    int desLength = des.length();
                    int charLimit = 250;
                    int charsRemaining = charLimit - desLength;
                    charLimitText.setText(charsRemaining + " characters remaining");
                }
            });

            charLimitText = view.findViewById(R.id.char_limit_text);
            String des = desBox.getText().toString();
            int desLength = des.length();
            int charLimit = 250;
            int charsRemaining = charLimit - desLength;
            charLimitText.setText(charsRemaining + " characters remaining");
            nextButton = view.findViewById(R.id.next_button);
            previousButton = view.findViewById(R.id.back_button);
            previousButton.setOnClickListener(this);
            nextButton.setOnClickListener(this);
            return view;
        }

        @Override
        public void onClick(View v) {
            Fragment frag;
            switch (v.getId()) {
                case R.id.back_button:
                    anActivity.onBackPressed();
                    break;
                case R.id.next_button:
                    if (checkDescription(desBox.getText().toString())) {
                        frag = new LinkFundToCharityFragment();
                        String desc = desBox.getText().toString().trim();
                        anActivity.fundraiserToBeAdded.setDescription(desc);
                        anActivity.loadFragment(frag);
                    }
                    break;
            }
        }

        @Override
        public void onDestroyView() {
            anActivity = null;
            super.onDestroyView();
        }

        private boolean checkDescription(String description){
            boolean result = true;
            if (description.length() <1){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a Description of the Fundraiser")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }
    }

    public static class LinkFundToCharityFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

        TextView textViewTitle;
        Button nextButton;
        TextView charityTextView;
        Button previousButton;
        private MainActivity anActivity;
        Charity charityLinkedToFundraiser;
        private String charityName;
        ImageView dropArrow;
        private CharityAdapter charityAdapter;
        private ArrayList<String> charityList;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            View view = inflater.inflate(R.layout.link_charity_fund, viewGroup, false);

            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText("Link to charity");
            textViewTitle = view.findViewById(R.id.textviewtitle);
            anActivity.getSupportActionBar().show();
            dropArrow = view.findViewById(R.id.drop);
            int dimen = (int) getResources().getDimensionPixelSize(R.dimen._40sdp);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/dropdown_arrow.png")
                    .into(dropArrow);
            //sets font for icon text
            Typeface titleFont = Typeface.
                    createFromAsset(getContext().getAssets(), "fonts/Lobster_1.3.otf");
            textViewTitle.setTypeface(titleFont);
            textViewTitle.setTypeface(titleFont);
            nextButton = view.findViewById(R.id.next_button);
            previousButton = view.findViewById(R.id.back_button);
            previousButton.setOnClickListener(this);
            nextButton.setOnClickListener(this);
            charityTextView = view.findViewById(R.id.ref_name);
            if (anActivity.fundraiserToBeAdded.getaCharity() != null){
                charityLinkedToFundraiser = anActivity.fundraiserToBeAdded.getaCharity();
                charityName = anActivity.fundraiserToBeAdded.getaCharity().getTitle();
                charityTextView.setText(charityName);
            }
            charityTextView.setOnClickListener(this);

            return view;
        }

        @Override
        public void onClick(View v) {
            Fragment frag;
            switch (v.getId()) {
                case R.id.back_button:
                    anActivity.onBackPressed();
                    break;
                case R.id.next_button:
                    if (checkCharity(charityName)) {
                    frag = new AddDateFundraiserFragment();
                    anActivity.fundraiserToBeAdded.setaCharity(charityLinkedToFundraiser);
                    anActivity.loadFragment(frag);
                }
                    break;
                case  R.id.ref_name:
                    final CustomDialog cdd = new CustomDialog(getActivity());
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                    Window view = cdd.getWindow();
                    TextView titleView = view.findViewById(R.id.dialog_title);
                    titleView.setText("Charity");
                    charityList = new ArrayList<>();
                    ListView listView = view.findViewById(R.id.dialog_listview);
                    SparseArray <HashMap<String, UkMarker>> markersMap = anActivity.dbHelper.getMarkersMap();
                    final HashMap<String, Charity> charityMap = new HashMap<>();
                    for(int i = 0; i < markersMap.size(); i++){
                        HashMap currentMap = anActivity.dbHelper.getMarkersMap().get(i + 1);
                        if (currentMap.containsKey("Organisation")) {
                            Charity charity = (Charity) currentMap.get("Organisation");
                            String charityName = charity.getTitle();
                            charityList.add(charityName);
                            charityMap.put(charityName, charity);
                        }
                    }
                    charityAdapter = new CharityAdapter(getContext(), charityList);
                    charityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    listView.setAdapter(charityAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
// TODO Auto-generated method stub
                            TextView charityText = getView().findViewById(R.id.ref_name);
                            charityName = parent.getItemAtPosition(position).toString();
                            charityLinkedToFundraiser = charityMap.get(charityName);
                            charityText.setText(charityName);
                            cdd.dismiss();
                        }
                    });
                    SearchView searchView = view.findViewById(R.id.search_list_one);
                    searchView.setIconifiedByDefault(false);
                    searchView.setSubmitButtonEnabled(false);
                    searchView.setQueryHint("Search");
                    View vi = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
                    AutoCompleteTextView search_text = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
                    search_text.setHint(R.string.edittext_hint);
                    vi.setBackgroundColor(Color.TRANSPARENT);
                    ImageView icon = searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
                    ImageView icon2 = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
                    icon.setColorFilter(Color.WHITE);
                    icon2.setColorFilter(Color.RED);
                    searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            charityAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                    break;
            }
        }

        @Override
        public void onDestroyView() {
            anActivity = null;
            dropArrow.setImageDrawable(null);
            dropArrow.setImageBitmap(null);
            if (charityAdapter != null) {
                charityAdapter.clear();
            }
            super.onDestroyView();
        }

        private boolean checkCharity(String charityName){
            boolean result = true;
            if (charityName == null){
                result = false;
                new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please select a charity whom you are fundraising for")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }
    }

    public static class AddDateFundraiserFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

        TextView textViewTitle;
        TextView dateTextView;
        Button nextButton;
        Button previousButton;
        private MainActivity anActivity;
        private Calendar calendar;
        private TextView timeTextView;
        private TextView textViewTimeTitle;
        private String dateString;
        private String timeHour;
        private String hour;
        private String minute;
        private ImageView timeImg;
        private ImageView dateImg;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }

            View view = inflater.inflate(R.layout.add_uk_date_fund, viewGroup, false);
            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText("Fundraiser Date");
            textViewTitle = view.findViewById(R.id.textviewtitle);
            textViewTimeTitle = view.findViewById(R.id.textviewtime_ins);
            anActivity.getSupportActionBar().show();
            timeImg = view.findViewById(R.id.time_img);
            int dimen = (int)getResources().getDimensionPixelSize(R.dimen._40sdp);
            Glide.with(this).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/time.png")
                    .into(timeImg);
            dateImg = view.findViewById(R.id.date_img);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/ic_menu_my_calendar.png")
                    .into(dateImg);

            //sets font for icon text
            Typeface titleFont = Typeface.
                    createFromAsset(getContext().getAssets(), "fonts/Lobster_1.3.otf");
            textViewTitle.setTypeface(titleFont);
            textViewTimeTitle.setTypeface(titleFont);
            nextButton = view.findViewById(R.id.next_button);
            previousButton = view.findViewById(R.id.back_button);
            previousButton.setOnClickListener(this);
            nextButton.setOnClickListener(this);
            calendar = Calendar.getInstance();
            dateTextView = view.findViewById(R.id.title_date);
            dateTextView.setOnClickListener(this);


            if (!anActivity.fundraiserToBeAdded.getDate().equals("")){
                dateTextView.setText(anActivity.fundraiserToBeAdded.getDate());
                dateString = anActivity.fundraiserToBeAdded.getDate();
                SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yy");
                try {
                    calendar.setTime(sd.parse(anActivity.fundraiserToBeAdded.getDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            timeTextView = view.findViewById(R.id.title_time);
            timeTextView.setOnClickListener(this);

            if (!anActivity.fundraiserToBeAdded.getTime().equals("")){
                timeTextView.setText(anActivity.fundraiserToBeAdded.getTime());
                timeHour = anActivity.fundraiserToBeAdded.getTime();
                hour = timeHour.substring(0, 1);
                minute = timeHour.substring(3, 4);

            }

            return view;
        }

        boolean checkInputs(){
            boolean checksSucceed = true;

            if (! checkDate(dateString)){
                checksSucceed = false;
            }
            if (! checkTime(timeHour)){
                checksSucceed = false;
            }

            return checksSucceed;
        }

        private boolean checkTime(String time){
            boolean result = true;
            if (time == null){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a time")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }

        private boolean checkDate(String dateString){
            boolean result = true;
            if (dateString == null){
                result = false;
                new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a date")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return result;
            }
            String todaysDateString = formattedDateString();
            org.threeten.bp.LocalDate todaysDate = convertStringToDate(todaysDateString);
            org.threeten.bp.LocalDate fundDate = convertStringToDate(dateString);
            if (!fundDate.isAfter(todaysDate)){
                result = false;
                new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a date that is in the future")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }

        /**
         * @return String formatted todays date string
         */
        private String formattedDateString() {
            String myFormat = "dd/MM/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
            Calendar c = Calendar.getInstance();
            return sdf.format(c.getTime());
        }

        @Override
        public void onClick(View v) {
            Fragment frag;
            switch (v.getId()) {
                case R.id.back_button:
                    anActivity.onBackPressed();
                    break;
                case R.id.next_button:
                    if (checkInputs()) {
                        frag = new AddAddressFundFragment();
                        anActivity.fundraiserToBeAdded.setDate(dateString);
                        anActivity.fundraiserToBeAdded.setTime(timeHour);
                        anActivity.loadFragment(frag);
                    }
                    break;
                case  R.id.title_date:
                    int year = calendar.get(Calendar.YEAR);
                    final int month = calendar.get(Calendar.MONTH);
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);

                    new DatePickerDialog(getActivity(), R.style.yourCustomStyle, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Date date = new Date(year, monthOfYear, dayOfMonth);
                            SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yy");
                            dateString = sd.format(date);
                            try {
                                calendar.setTime(sd.parse(dateString));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateTextView.setText(dateString);
                        }

                    }, year, month, day).show();
                    break;
               case R.id.title_time:

                    int hours = calendar.get(Calendar.HOUR_OF_DAY);
                    int minutes = calendar.get(Calendar.MINUTE);
                   if (hour != null){
                       hours = Integer.parseInt(hour);
                       minutes = Integer.parseInt(minute);
                   }

                   new TimePickerDialog(getActivity(), R.style.yourCustomStyle, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            timeTextView.setText(String.format("%02d:%02d", hourOfDay, minute));
                            timeHour = String.format("%02d:%02d", hourOfDay, minute);
                        }
                    }, hours, minutes, true).show();
                            break;

            }
        }

        @Override
        public void onDestroyView() {
            anActivity = null;
            Glide.with(this).clear(timeImg);
            timeImg.setImageDrawable(null);
            dateImg.setImageDrawable(null);
            dateImg.setImageBitmap(null);
            timeImg.setImageBitmap(null);
            super.onDestroyView();
        }

        private org.threeten.bp.LocalDate convertStringToDate(String hotDateString) {
            org.threeten.bp.format.DateTimeFormatter formatter = org.threeten.bp.format.DateTimeFormatter.ofPattern("dd/MM/yy");
            return org.threeten.bp.LocalDate.parse(hotDateString, formatter);
        }
    }

    public static class AddAddressFundFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

        private TextView streetNumTitle;
        private TextView streetTitle;
        private TextView addressTitle;
        private TextView cityTitle;
        private TextView postcodeTitle;
        private EditText streetNumEdit;
        private EditText streetEdit;
        private EditText addressEdit;
        private EditText cityEdit;
        private EditText postcodeEdit;
        Button nextButton;
        Button previousButton;
        private MainActivity anActivity;
        private ImageView dropArrow;
        private LinearLayout regionText;
        private  String area;
        private ArrayAdapter<CharSequence> regionAdapter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            View view = inflater.inflate(R.layout.add_uk_address, viewGroup, false);

            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText("Add Address");
            dropArrow = view.findViewById(R.id.drop);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/dropdown_arrow.png")
                    .into(dropArrow);
            regionText = view.findViewById(R.id.region_container);
            TextView regionTextView = (TextView) view.findViewById(R.id.ref_name);
            area = anActivity.fundraiserToBeAdded.getRegion();
            regionTextView.setText(area);
            regionText.setOnClickListener(this);

            streetNumTitle = view.findViewById(R.id.street_num_title);
            streetTitle = view.findViewById(R.id.street_title);
            addressTitle = view.findViewById(R.id.address_title);
            cityTitle = view.findViewById(R.id.city_title);
            postcodeTitle = view.findViewById(R.id.postcode_title);
            anActivity.getSupportActionBar().show();
            streetNumEdit = view.findViewById(R.id.street_num_text);
            streetNumEdit.setText(anActivity.fundraiserToBeAdded.getHouseNoOrBuldingName());
            streetEdit = view.findViewById(R.id.street_text);
            streetEdit.setText(anActivity.fundraiserToBeAdded.getStreet());
            addressEdit = view.findViewById(R.id.address_text);
            addressEdit.setText(anActivity.fundraiserToBeAdded.getOtherAddress());
            cityEdit = view.findViewById(R.id.city_text);
            cityEdit.setText(anActivity.fundraiserToBeAdded.getCityOrTown());
            postcodeEdit = view.findViewById(R.id.postcode_text);
            postcodeEdit.setText(anActivity.fundraiserToBeAdded.getPostcode());



            //sets font for icon text
            Typeface titleFont = Typeface.
                    createFromAsset(getContext().getAssets(), "fonts/Lobster_1.3.otf");
            streetNumTitle.setTypeface(titleFont);
            streetTitle.setTypeface(titleFont);
            addressTitle.setTypeface(titleFont);
            cityTitle.setTypeface(titleFont);
            postcodeTitle.setTypeface(titleFont);

            nextButton = view.findViewById(R.id.next_button);
            nextButton.setText("Submit");
            previousButton = view.findViewById(R.id.back_button);
            previousButton.setOnClickListener(this);
            nextButton.setOnClickListener(this);

            return view;
        }

        @Override
        public void onClick(View v) {
            Fragment frag;
            switch (v.getId()) {
                case R.id.back_button:
                    anActivity.onBackPressed();
                    break;
                case R.id.next_button:
                    if (checkInputs()) {
                        anActivity.fundraiserToBeAdded.setHouseNoOrBuldingName(streetNumEdit.getText().toString());
                        anActivity.fundraiserToBeAdded.setStreet(streetEdit.getText().toString());
                        anActivity.fundraiserToBeAdded.setOtherAddress(addressEdit.getText().toString());
                        anActivity.fundraiserToBeAdded.setCityOrTown(cityEdit.getText().toString());
                        anActivity.fundraiserToBeAdded.setPostcode(postcodeEdit.getText().toString());
                        anActivity.fundraiserToBeAdded.setRegion(area);
                        anActivity.fundraiserToBeAdded.setLastModified("");
                        anActivity.setProgressDialog(new ProgressDialog(anActivity));
                        anActivity.getProgressDialog().setMessage("Adding Address");
                        anActivity.getProgressDialog().show();
                        anActivity.getDbHelper().addAddressFundraiser();
                    }
                    break;
                case  R.id.region_container:
                    final CustomDialogNoSearch cdd = new CustomDialogNoSearch(getActivity());
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                    Window view = cdd.getWindow();
                    TextView titleView = view.findViewById(R.id.dialog_title);
                    titleView.setText("Region");
                    //charityList = new ArrayList<>()
                    ListView listView = view.findViewById(R.id.dialog_listview);
                    regionAdapter = ArrayAdapter.createFromResource(anActivity, R.array.area_array, R.layout.simple_list_item_2);
                    regionAdapter.setDropDownViewResource(R.layout.simple_list_item_1);
                    listView.setAdapter(regionAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
// TODO Auto-generated method stub
                            TextView regionText = getView().findViewById(R.id.ref_name);
                            area = parent.getItemAtPosition(position).toString();
                            regionText.setText(area);
                            cdd.dismiss();
                        }
                    });
                    break;
            }
        }

        boolean checkInputs(){
            boolean checksSucceed = true;
            if (! checkHouseNo(streetNumEdit.getText().toString())){
                checksSucceed = false;
                return checksSucceed;
            }
            if (! checkStreet(streetEdit.getText().toString())){
                checksSucceed = false;
                return checksSucceed;
            }
            if (! checkCity(cityEdit.getText().toString())){
                checksSucceed = false;
                return checksSucceed;
            }
            if (! checkPostcode(postcodeEdit.getText().toString())){
                checksSucceed = false;
                return checksSucceed;
            }
            if (! checkRegion(area.toString())){
                checksSucceed = false;
                return checksSucceed;
            }
            return checksSucceed;
        }

        private boolean checkHouseNo(String houseNo){
            boolean result = true;
            if (houseNo.length() < 1){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a Building Number / Building Name")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }
        private boolean checkRegion(String region){
            boolean result = true;
            if (region.length() < 1){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a Region")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }
        private boolean checkStreet(String street){
            boolean result = true;
            if (street.length() < 1){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a street")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }
        private boolean checkPostcode(String postcode){
            boolean result = true;
            if (postcode.length() < 1){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a postcode")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }

        private boolean checkCity(String city){
            boolean result = true;
            if (city.length() < 1){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a city / town / village")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }

        @Override
        public void onDestroyView() {
            anActivity = null;
            super.onDestroyView();
        }
    }

    public static class AddTitleUkFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

        TextView textViewTitle;
        EditText titleBox;
        Button nextButton;
        private MainActivity anActivity;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            View view = inflater.inflate(R.layout.add_uk_title, viewGroup, false);

            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText("Add Title");
            textViewTitle = view.findViewById(R.id.textviewtitle);
            anActivity.getSupportActionBar().show();
            //sets font for icon text
            Typeface titleFont = Typeface.
                    createFromAsset(getContext().getAssets(), "fonts/Lobster_1.3.otf");
            textViewTitle.setTypeface(titleFont);
            textViewTitle.setTypeface(titleFont);


            titleBox = view.findViewById(R.id.title_box);
            titleBox.setText(anActivity.charityToBeAdded.getTitle());
            nextButton = view.findViewById(R.id.next_button);
            nextButton.setOnClickListener(this);

            return view;
        }

        @Override
        public void onClick(View v) {
            if (checkTitle(titleBox.getText().toString())) {
                Fragment frag = new AddLogoFragment();
                String title = titleBox.getText().toString().trim();
                anActivity.charityToBeAdded.setTitle(title);
                anActivity.loadFragment(frag);
            }
        }

        @Override
        public void onDestroyView() {
            anActivity = null;
            super.onDestroyView();
        }

        private boolean checkTitle(String title){
            boolean result = true;

            if (title.length() <1){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a Title")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }
    }

    public static class AddDescriptionUkFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

        TextView textViewTitle;
        EditText desBox;
        Button nextButton;
        Button previousButton;
        private MainActivity anActivity;
        private TextView charLimitText;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            View view = inflater.inflate(R.layout.add_uk_description, viewGroup, false);

            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText("Add Description");
            textViewTitle = view.findViewById(R.id.textviewtitle);
            anActivity.getSupportActionBar().show();
            //sets font for icon text
            Typeface titleFont = Typeface.
                    createFromAsset(getContext().getAssets(), "fonts/Lobster_1.3.otf");
            textViewTitle.setTypeface(titleFont);
            textViewTitle.setTypeface(titleFont);
            textViewTitle.setText("Please describe your organisation");

            desBox = view.findViewById(R.id.title_box);
            desBox.setText(anActivity.charityToBeAdded.getDescription());
            desBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String des = desBox.getText().toString();
                    int desLength = des.length();
                    int charLimit = 300;
                    int charsRemaining = charLimit - desLength;
                    charLimitText.setText(charsRemaining + " characters remaining");
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String des = desBox.getText().toString();
                    int desLength = des.length();
                    int charLimit = 300;
                    int charsRemaining = charLimit - desLength;
                    charLimitText.setText(charsRemaining + " characters remaining");
                }
            });

            charLimitText = view.findViewById(R.id.char_limit_text);
            String des = desBox.getText().toString();
            int desLength = des.length();
            int charLimit = 300;
            int charsRemaining = charLimit - desLength;
            charLimitText.setText(charsRemaining + " characters remaining");

            nextButton = view.findViewById(R.id.next_button);
            previousButton = view.findViewById(R.id.back_button);
            previousButton.setOnClickListener(this);
            nextButton.setOnClickListener(this);

            return view;
        }

        @Override
        public void onClick(View v) {
            Fragment frag;
            switch (v.getId()) {
                case R.id.back_button:
                anActivity.onBackPressed();
                break;
                case R.id.next_button:
                    if (checkDescription(desBox.getText().toString())) {
                        frag = new AddTypeOfAidFragment();
                        String desc = desBox.getText().toString().trim();
                        anActivity.charityToBeAdded.setDescription(desc);
                        anActivity.loadFragment(frag);
                    }
                    break;
            }
        }

        @Override
        public void onDestroyView() {
            anActivity = null;
            super.onDestroyView();
        }

        private boolean checkDescription(String description){
            boolean result = true;
            if (description.length() <1){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a Description of the Fundraiser")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }
    }

           public static class AddTypeOfAidFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

               TextView textViewTitle;
               Button nextButton;
               TextView typeOfAidTextView;
               Button previousButton;
               private MainActivity anActivity;
               private String typeOfAid;
               ImageView dropArrow;
               private ArrayAdapter<String> typeOfAidAdapter;

               @Override
               public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
                   if (viewGroup != null) {
                       viewGroup.removeAllViews();
                   }
                   View view = inflater.inflate(R.layout.add_uk_typeofaid, viewGroup, false);

                   anActivity = (MainActivity) getActivity();
                   assert anActivity != null;
                   anActivity.getmTitle().setText("Type of Support");
                   textViewTitle = view.findViewById(R.id.textviewtitle);
                   anActivity.getSupportActionBar().show();
                   dropArrow = view.findViewById(R.id.drop);
                   int dimen = (int) getResources().getDimensionPixelSize(R.dimen._40sdp);
                   Glide.with(this)
                           .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/dropdown_arrow.png")
                           .into(dropArrow);
                   //sets font for icon text
                   Typeface titleFont = Typeface.
                           createFromAsset(getContext().getAssets(), "fonts/Lobster_1.3.otf");
                   textViewTitle.setTypeface(titleFont);
                   textViewTitle.setTypeface(titleFont);
                   nextButton = view.findViewById(R.id.next_button);
                   previousButton = view.findViewById(R.id.back_button);
                   previousButton.setOnClickListener(this);
                   nextButton.setOnClickListener(this);
                   typeOfAidTextView = view.findViewById(R.id.ref_name);
                   if (anActivity.charityToBeAdded.getTypeOfAid() != null){
                       typeOfAid = anActivity.charityToBeAdded.getTypeOfAid();
                       typeOfAidTextView.setText(typeOfAid);
                   }
                   typeOfAidTextView.setOnClickListener(this);
                   dropArrow.setOnClickListener(this);
                   return view;
               }

               @Override
               public void onClick(View v) {
                   Fragment frag;
                   switch (v.getId()) {
                       case R.id.back_button:
                           anActivity.onBackPressed();
                           break;
                       case R.id.next_button:
                           if (checkTypeOfAid(typeOfAid)) {
                               frag = new AddWebsiteFragment();
                               anActivity.charityToBeAdded.setTypeOfAid(typeOfAid);
                               anActivity.loadFragment(frag);
                           }
                           break;
                       case  R.id.ref_name :
                           final CustomDialogNoSearch cdd = new CustomDialogNoSearch(getActivity());
                           cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                           cdd.show();
                           Window view = cdd.getWindow();
                           TextView titleView = view.findViewById(R.id.dialog_title);
                           titleView.setText("Type");
                           ListView listView = view.findViewById(R.id.dialog_listview);
                           List<String> typeList = new LinkedList<String>(Arrays.asList(getResources().getStringArray(R.array.type_of_aid_array)));
                           typeOfAidAdapter = new ArrayAdapter<>(anActivity, R.layout.simple_list_item_2, typeList);
                           typeOfAidAdapter.setDropDownViewResource(R.layout.simple_list_item_1);
                           listView.setAdapter(typeOfAidAdapter);
                           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                               @Override
                               public void onItemClick(AdapterView<?> parent, View view,
                                                       int position, long id) {
// TODO Auto-generated method stub
                                   typeOfAid = parent.getItemAtPosition(position).toString();
                                   typeOfAidTextView.setText(typeOfAid);
                                   cdd.dismiss();
                               }
                           });
                           break;
                       case  R.id.drop:
                           final CustomDialogNoSearch cd = new CustomDialogNoSearch(getActivity());
                           cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                           cd.show();
                           view = cd.getWindow();
                           titleView = view.findViewById(R.id.dialog_title);
                           titleView.setText("Type");
                           listView = view.findViewById(R.id.dialog_listview);
                           List<String> types = new LinkedList<String>(Arrays.asList(getResources().getStringArray(R.array.type_of_aid_array)));
                           typeOfAidAdapter = new ArrayAdapter<>(anActivity, R.layout.simple_list_item_2, types);                           typeOfAidAdapter.setDropDownViewResource(R.layout.simple_list_item_1);
                           listView.setAdapter(typeOfAidAdapter);
                           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                               @Override
                               public void onItemClick(AdapterView<?> parent, View view,
                                                       int position, long id) {
// TODO Auto-generated method stub
                                   typeOfAid = parent.getItemAtPosition(position).toString();
                                   typeOfAidTextView.setText(typeOfAid);
                                   cd.dismiss();
                               }
                           });
                           break;
                   }
               }

               @Override
               public void onDestroyView() {
                   anActivity = null;
                   dropArrow.setImageDrawable(null);
                   dropArrow.setImageBitmap(null);
                   if (typeOfAidAdapter != null) {
                       typeOfAidAdapter.clear();
                   }
                   super.onDestroyView();
               }

               private boolean checkTypeOfAid(String typeOfAid){
                   boolean result = true;
                   if (typeOfAid == null || typeOfAid.length() < 1){
                       result = false;
                       new AlertDialog.Builder(anActivity)
                               .setTitle("Error")
                               .setMessage("Please select the type of support the organisation provides")
                               .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(final DialogInterface dialog, final int which) {
                                       dialog.dismiss();
                                   }
                               })
                               .setIcon(android.R.drawable.ic_dialog_alert)
                               .show();
                   }
                   return result;
               }
           }
    public static class AddWebsiteFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

        TextView textViewTitle;
        EditText webBox;
        Button nextButton;
        Button previousButton;
        private MainActivity anActivity;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            View view = inflater.inflate(R.layout.add_uk_website, viewGroup, false);

            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText("Add Website");
            textViewTitle = view.findViewById(R.id.textviewtitle);
            anActivity.getSupportActionBar().show();
            //sets font for icon text
            Typeface titleFont = Typeface.
                    createFromAsset(getContext().getAssets(), "fonts/Lobster_1.3.otf");
            textViewTitle.setTypeface(titleFont);
            textViewTitle.setTypeface(titleFont);


            webBox = view.findViewById(R.id.title_box);
            webBox.setText(anActivity.charityToBeAdded.getWebsite());

            nextButton = view.findViewById(R.id.next_button);
            previousButton = view.findViewById(R.id.back_button);
            previousButton.setOnClickListener(this);
            nextButton.setOnClickListener(this);
            return view;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back_button:
                    anActivity.onBackPressed();
                    break;
                case R.id.next_button:
                    if (checkWebsite(webBox.getText().toString())) {
                        Fragment frag = new AddAddressFragment();
                        anActivity.charityToBeAdded.setWebsite(webBox.getText().toString());

                        anActivity.loadFragment(frag);
                    }
                    break;
            }
        }
        private boolean checkWebsite(String website){
            boolean result = true;
            if (website.length() <15){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a website address / social media link for the organisation")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }

        @Override
        public void onDestroyView() {
            anActivity = null;
            super.onDestroyView();
        }
    }

    public static class AddAddressFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

        private TextView streetNumTitle;
        private TextView streetTitle;
        private TextView addressTitle;
        private TextView cityTitle;
        private TextView postcodeTitle;
        private EditText streetNumEdit;
        private EditText streetEdit;
        private EditText addressEdit;
        private EditText cityEdit;
        private EditText postcodeEdit;
        Button nextButton;
        Button previousButton;
        private ImageView dropArrow;
        private MainActivity anActivity;
        private ArrayAdapter<CharSequence> regionAdapter;
        private  String area;
        private LinearLayout regionText;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            View view = inflater.inflate(R.layout.add_uk_address, viewGroup, false);
            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText("Add Address");
            streetNumTitle = view.findViewById(R.id.street_num_title);
            streetTitle = view.findViewById(R.id.street_title);
            addressTitle = view.findViewById(R.id.address_title);
            cityTitle = view.findViewById(R.id.city_title);
            postcodeTitle = view.findViewById(R.id.postcode_title);
            anActivity.getSupportActionBar().show();
            dropArrow = view.findViewById(R.id.drop);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/dropdown_arrow.png")
                    .into(dropArrow);

            area = anActivity.charityToBeAdded.getRegion();
            Charity charity = (Charity) anActivity.dbHelper.getMarkersMap().get(1).get("Organisation");
            System.out.println(charity.toString());
            regionText = view.findViewById(R.id.region_container);
            regionText.setOnClickListener(this);
            TextView regionTextView = (TextView) view.findViewById(R.id.ref_name);
            regionTextView.setText(area);
            //sets font for icon text
            Typeface titleFont = Typeface.
                    createFromAsset(getContext().getAssets(), "fonts/Lobster_1.3.otf");
            streetNumTitle.setTypeface(titleFont);
            streetTitle.setTypeface(titleFont);
            addressTitle.setTypeface(titleFont);
            cityTitle.setTypeface(titleFont);
            postcodeTitle.setTypeface(titleFont);

            streetNumEdit = view.findViewById(R.id.street_num_text);
            streetNumEdit.setText(anActivity.charityToBeAdded.getHouseNoOrBuldingName());
            streetEdit = view.findViewById(R.id.street_text);
            streetEdit.setText(anActivity.charityToBeAdded.getStreet());
            addressEdit = view.findViewById(R.id.address_text);
            addressEdit.setText(anActivity.charityToBeAdded.getOtherAddress());
            cityEdit = view.findViewById(R.id.city_text);
            cityEdit.setText(anActivity.charityToBeAdded.getCityOrTown());
            postcodeEdit = view.findViewById(R.id.postcode_text);
            postcodeEdit.setText(anActivity.charityToBeAdded.getPostcode());

            nextButton = view.findViewById(R.id.next_button);
            previousButton = view.findViewById(R.id.back_button);
            previousButton.setOnClickListener(this);
            nextButton.setOnClickListener(this);

            return view;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back_button:
                    anActivity.onBackPressed();
                    break;
                case R.id.next_button:
                    if (checkInputs()) {
                        anActivity.charityToBeAdded.setHouseNoOrBuldingName(streetNumEdit.getText().toString());
                        anActivity.charityToBeAdded.setStreet(streetEdit.getText().toString());
                        anActivity.charityToBeAdded.setOtherAddress(addressEdit.getText().toString());
                        anActivity.charityToBeAdded.setCityOrTown(cityEdit.getText().toString());
                        anActivity.charityToBeAdded.setPostcode(postcodeEdit.getText().toString());
                        anActivity.charityToBeAdded.setRegion(area);
                        anActivity.charityToBeAdded.setLastModified(String.valueOf(System.currentTimeMillis()));
                        anActivity.setProgressDialog(new ProgressDialog(anActivity));
                        anActivity.getProgressDialog().setMessage("Adding Address");
                        anActivity.getProgressDialog().show();
                        anActivity.getDbHelper().addAddressOrg();                    }
                    break;
                case  R.id.region_container:
                    final CustomDialogNoSearch cdd = new CustomDialogNoSearch(getActivity());
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                    Window view = cdd.getWindow();
                    TextView titleView = view.findViewById(R.id.dialog_title);
                    titleView.setText("Region");
                    //charityList = new ArrayList<>()
                    ListView listView = view.findViewById(R.id.dialog_listview);
                    regionAdapter = ArrayAdapter.createFromResource(anActivity, R.array.area_array, R.layout.simple_list_item_2);
                    regionAdapter.setDropDownViewResource(R.layout.simple_list_item_1);
                    listView.setAdapter(regionAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
// TODO Auto-generated method stub
                            TextView regionText = getView().findViewById(R.id.ref_name);
                            area = parent.getItemAtPosition(position).toString();
                            regionText.setText(area);
                            cdd.dismiss();
                        }
                    });
                    break;
            }
        }



        boolean checkInputs(){
            boolean checksSucceed = true;

            if (! checkHouseNo(streetNumEdit.getText().toString())){
                checksSucceed = false;
                return checksSucceed;
            }
            if (! checkStreet(streetEdit.getText().toString())){
                checksSucceed = false;
                return checksSucceed;

            }
            if (! checkCity(cityEdit.getText().toString())){
                checksSucceed = false;
                return checksSucceed;

            }
            if (! checkPostcode(postcodeEdit.getText().toString())){
                checksSucceed = false;
                return checksSucceed;

            }
            if (! checkRegion(area.toString())){
                checksSucceed = false;
                return checksSucceed;

            }
            return checksSucceed;
        }

        private boolean checkRegion(String region){
            boolean result = true;
            if (region.length() < 1){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a Region")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }

        private boolean checkHouseNo(String houseNo){
            boolean result = true;
            if (houseNo.length() < 1){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a Building Number / Building Name")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }
        private boolean checkStreet(String street){
            boolean result = true;
            if (street.length() < 1){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a street")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }
        private boolean checkPostcode(String postcode){
            boolean result = true;
            if (postcode.length() < 1){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a postcode")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }

        private boolean checkCity(String city){
            boolean result = true;
            if (city.length() < 1){
                result = false;
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please enter a city / town / village")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            return result;
        }

        @Override
        public void onDestroyView() {
            anActivity = null;
            super.onDestroyView();
        }
    }

    public static class AddLogoFragment extends android.support.v4.app.Fragment implements View.OnClickListener {


        Button nextButton;
        Button previousButton;
        private MainActivity anActivity;
        private Calendar calendar;
        private CircleImageView logoHolder;
        private TextView textViewTitle;
        static final int GALLERY_ONLY_REQ = 1212;


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // width and height will be at least 600px long (optional).
            ImagePicker.setMinQuality(150, 150);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            View view = inflater.inflate(R.layout.add_logo, viewGroup, false);

            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getSupportActionBar().show();
            anActivity.getmTitle().setText("Add Logo");
            logoHolder = view.findViewById(R.id.logo_holder);
            int dimen = (int)getResources().getDimensionPixelSize(R.dimen._150sdp);
            if (anActivity.charityLogo == null && anActivity.charityToBeAdded.getImageUrl().length() > 2){
                Glide.with(this).load(anActivity.charityToBeAdded.getImageUrl()).apply(new RequestOptions()
                        .signature(new ObjectKey(anActivity.charityToBeAdded.getLastModified()))
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        BitmapDrawable b = (BitmapDrawable)resource;
                        anActivity.charityLogo = b.getBitmap();
                        return false;
                    }
                })
                        .apply(new RequestOptions().override(dimen,
                                dimen))
                        .into(logoHolder);
            }
            if (anActivity.charityLogo != null){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                anActivity.charityLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Glide.with(this)
                        .asBitmap()
                        .load(stream.toByteArray())
                        .apply(new RequestOptions().override(dimen,
                                dimen).
                                signature(new ObjectKey(anActivity.charityToBeAdded.getLastModified()))
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                        .into(logoHolder);
            }
            if (anActivity.charityLogo == null && anActivity.charityToBeAdded.getImageUrl().length() < 2) {
                Glide.with(this)
                        .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/ins_log.png")
                        .into(logoHolder);
            }
                textViewTitle = view.findViewById(R.id.textviewtitle);

            //sets font for icon text
            Typeface titleFont = Typeface.
                    createFromAsset(getContext().getAssets(), "fonts/Lobster_1.3.otf");
            textViewTitle.setTypeface(titleFont);
            nextButton = view.findViewById(R.id.next_button);
            previousButton = view.findViewById(R.id.back_button);
            previousButton.setOnClickListener(this);
            nextButton.setOnClickListener(this);
            logoHolder.setOnClickListener(this);
            calendar = Calendar.getInstance();
            return view;
        }

        boolean checkLogo(){
            boolean result = true;
            if (anActivity.charityLogo == null){
                AlertDialog alertDialog =  new AlertDialog.Builder(anActivity)
                        .setTitle("Error")
                        .setMessage("Please add a logo")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                result = false;
            }
            return result;
        }

        @Override
        public void onClick(View v) {
            Fragment frag;
            switch (v.getId()) {
                case R.id.back_button:
                    anActivity.onBackPressed();
                    break;
                case R.id.next_button:
                    if (checkLogo()) {
                        frag = new AddDescriptionUkFragment();
                        anActivity.loadFragment(frag);
                    }
                    break;
                case R.id.logo_holder:
                    ImagePicker.pickImageGalleryOnly(AddLogoFragment.this, GALLERY_ONLY_REQ);
                    break;
            }
        }

        @Override
        public void onDestroyView() {
            anActivity = null;
            logoHolder.setImageDrawable(null);
            logoHolder.setImageBitmap(null);
            super.onDestroyView();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            switch (requestCode) {
                case GALLERY_ONLY_REQ:
                    String pathFromGallery = "file:///";
                    String path = ImagePicker.getImagePathFromResult(getActivity(), requestCode,
                            resultCode, data);
                    String finalpath = pathFromGallery + path;
                    if (path != null) {
                        int dimen = (int)getResources().getDimensionPixelSize(R.dimen._150sdp);
                        Glide.with(getActivity()).load(finalpath).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }
                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        BitmapDrawable b = (BitmapDrawable)resource;
                               anActivity.charityLogo = b.getBitmap();
                                return false;
                            }
                        })
                                .apply(new RequestOptions().override(dimen,
                                        dimen))
                        .into(logoHolder);
                    }
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static class ListMarkersFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

        private  UkListAdapter adapter;
        private RecyclerView.LayoutManager layoutManager;
        private  RecyclerView recyclerView;
        private TextView areaTextView;
        private TextView typeOfAidTextView;
        private RadioButton orgCheckView;
        private RadioButton fundView;
        private MainActivity anActivity;
        private ImageView dropArrow;
        private ImageView dropArrowTwo;
        private ArrayAdapter<CharSequence> areaAdapter;
        private ArrayAdapter<CharSequence> typeOfAidAdapter;


        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            //inflates xml layout. sets toolbar title to 'Home'
            View view = inflater.inflate(R.layout.list_markers_fragment, viewGroup, false);
            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText(R.string.list_orgs);
            recyclerView = view.findViewById(R.id.list_markers_recycler_view);
            anActivity.getSupportActionBar().show();
            dropArrow = view.findViewById(R.id.drop);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/dropdown_arrow.png")
                    .into(dropArrow);
            dropArrowTwo = view.findViewById(R.id.type_of_aid_drop);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/dropdown_arrow.png")
                    .into(dropArrowTwo);
            dropArrow.setOnClickListener(this);
            dropArrowTwo.setOnClickListener(this);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            SparseArray <HashMap<String, UkMarker>> ukList = anActivity.getDbHelper().getMarkersMap();
            SparseArray <LatLong> longLatMap = anActivity.getDbHelper().getLongLatMap();
            adapter = new UkListAdapter(anActivity, ukList, longLatMap, this);
            recyclerView.setHasFixedSize(false);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            TextView results = view.findViewById(R.id.results);
            results.setText(adapter.getmDataset().size() + " Results");

            areaTextView = view.findViewById(R.id.ref_name);
            areaTextView.setOnClickListener(this);

            typeOfAidTextView = view.findViewById(R.id.type_of_aid_list);
            typeOfAidTextView.setOnClickListener(this);

            orgCheckView = view.findViewById(R.id.org_check);
            orgCheckView.setOnClickListener(this);

            fundView = view.findViewById(R.id.fund_check);
            fundView.setOnClickListener(this);
            anActivity.setTypeOfAid("");
            anActivity.setArea("");
            ArrayList<String> list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.type_of_aid_array)));
            anActivity.setSelectedTypeOfAid(list);
            list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.area_array)));
            anActivity.setRegions(list);
            anActivity.setSearchString("");
            anActivity.setFund(true);
            anActivity.setOrg(true);
            fundView.setChecked(true);
            orgCheckView.setChecked(true);
            SearchView searchView = view.findViewById(R.id.search_list);
            searchView.setIconifiedByDefault(false);
            searchView.setSubmitButtonEnabled(false);
            searchView.setQueryHint("Search");
            View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            AutoCompleteTextView search_text = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            search_text.setTextSize(14);
            search_text.setHint(R.string.edittext_hint);
            v.setBackgroundColor(Color.TRANSPARENT);
            ImageView icon = searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
            ImageView icon2 = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
            icon.setColorFilter(Color.WHITE);
            icon2.setColorFilter(Color.RED);
            searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    anActivity.setSearchString(newText);
                    setFilterType();
                    return false;
                }
            });
            return view;
        }

        void getFundFilter() {
            class GetFundFilter extends AsyncTask<Void, Void, Void> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    MainActivity anActivity = (MainActivity) getActivity();
                    anActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            String fundraiser = "Fundraiser";
                            adapter.getFundFilter().filter(fundraiser);
                        }
                    });
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);
                }
            }
            GetFundFilter gff = new GetFundFilter();
            gff.execute();
        }

        void getOrgsFilter() {
            class GetOrgsFilter extends AsyncTask<Void, Void, Void> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Void doInBackground(Void... params) {

                    MainActivity anActivity = (MainActivity) getActivity();
                    anActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            String org = orgCheckView.getText().toString();
                            adapter.getOrgOnlyFilter().filter(org);
                        }
                    });
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);
                }
            }
            GetOrgsFilter gof = new GetOrgsFilter();
            gof.execute();
        }


        void getAllFilter() {
            class GetOrgFilter extends AsyncTask<Void, Void, Void> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    MainActivity anActivity = (MainActivity) getActivity();
                    anActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            adapter.getFilter().filter("");
                        }
                    });
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);
                }
            }
            GetOrgFilter gof = new GetOrgFilter();
            gof.execute();
        }

        public void setResults() {
            TextView results = getView().findViewById(R.id.results);
            results.setText(adapter.getmDataset().size() + " Results");
            System.out.println("here");
            adapter.notifyDataSetChanged();
        }

        void setFilterType() {
            if (anActivity.isFund() && !anActivity.isOrg()) {
                System.out.println("yolo");
                getFundFilter();
            } else if (anActivity.isOrg() && !anActivity.isFund() ) {
                System.out.println("yohoho");
                getOrgsFilter();
            } else {
                getAllFilter();
                System.out.println("yo");
            }
        }

        @Override
        public void onDestroyView() {
            anActivity = null;
            dropArrow.setImageDrawable(null);
            dropArrow.setImageBitmap(null);
            recyclerView.setAdapter(null);
            adapter = null;
            super.onDestroyView();
        }

        @Override
        public void onClick(View v) {
            if (v == fundView) {
                if (anActivity.isFund()){
                    orgCheckView.setChecked(true);
                    fundView.setChecked(false);
                    anActivity.setFund(false);
                    anActivity.setOrg(true);
                    getOrgsFilter();
                }
                else {
                    anActivity.setFund(true);
                    fundView.setChecked(true);
                    if (anActivity.isFund() && !anActivity.isOrg()){
                        getFundFilter();
                    }
                    else
                        {
                            adapter.getFilter().filter("");
                        }
                }
                }
            if (v == orgCheckView) {
                if (anActivity.isOrg()) {
                    fundView.setChecked(true);
                    orgCheckView.setChecked(false);
                    anActivity.setFund(true);
                    anActivity.setOrg(false);
                    getFundFilter();
                }
                else {
                    anActivity.setOrg(true);
                    orgCheckView.setChecked(true);
                    if (anActivity.isOrg() && !anActivity.isFund()){
                        getOrgsFilter();
                    }
                    else
                    {
                        adapter.getFilter().filter("");
                    }
                }
            }
            if (v == areaTextView || v == dropArrow) {
                final CustomDialogNoSearchWithButton cdd = new CustomDialogNoSearchWithButton(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
                cdd.setCancelable(false);
                Window view = cdd.getWindow();
                TextView titleView = view.findViewById(R.id.dialog_title);
                titleView.setText(R.string.area);
                Button button = view.findViewById(R.id.next_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //System.out.print(anActivity.getRegions());
                        TextView regionsText = getView().findViewById(R.id.ref_name);
                        if (anActivity.getRegions().size() > 1){
                            regionsText.setText("Multiple");
                            cdd.dismiss();
                            setFilterType();
                        }
                        else if (anActivity.getRegions().size() == 0){
                            Toast.makeText(anActivity,"Please select at least one Region",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{
                            regionsText.setText(anActivity.anActivity.getRegions().get(0));
                            cdd.dismiss();
                            setFilterType();
                        }
                    }
                });
                final ListView listView = view.findViewById(R.id.dialog_listview);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setItemsCanFocus(false);
                String[] myResArray = getResources().getStringArray(R.array.area_array);
                List<String> myResArrayList = Arrays.asList(myResArray);
                ArrayList<String> arrayList = new ArrayList<>(myResArrayList);
                areaAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_multiple_choice, arrayList);
                areaAdapter.setDropDownViewResource(R.layout.simple_list_item_1);
                listView.setAdapter(areaAdapter);
                for (int i = 0; i < areaAdapter.getCount(); i++){
                    System.out.println(areaAdapter.getItem(i).toString());
                    if (anActivity.getRegions().contains(areaAdapter.getItem(i).toString())){
                        listView.setItemChecked(i, true);
                    }
                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        if (!listView.isItemChecked(position)) {
                            anActivity.getRegions().remove(parent.getItemAtPosition(position).toString());
                        }
                        else {
                            anActivity.setArea(parent.getItemAtPosition(position).toString());
                            if (!anActivity.getRegions().contains(parent.getItemAtPosition(position).toString())) {
                                anActivity.getRegions().add(parent.getItemAtPosition(position).toString());
                            }
                        }
                    }
                });
            }
            if (v == typeOfAidTextView || v == dropArrowTwo) {
                final CustomDialogNoSearchWithButton cdd = new CustomDialogNoSearchWithButton(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

                Window view = cdd.getWindow();
                cdd.setCancelable(false);
                TextView titleView = view.findViewById(R.id.dialog_title);
                titleView.setText("Type Of Support");
                Button button = view.findViewById(R.id.next_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView typeOfAidText = getView().findViewById(R.id.type_of_aid_list);
                        //System.out.print(anActivity.getSelectedTypeOfAid());
                        if (anActivity.getSelectedTypeOfAid().size() > 1){
                            typeOfAidText.setText("Multiple");
                            cdd.dismiss();
                            setFilterType();
                            //System.out.println(anActivity.getSelectedTypeOfAid());
                        }
                        else if (anActivity.getSelectedTypeOfAid().size() == 0){
                            Toast.makeText(anActivity,"Please select at least one category",
                                    Toast.LENGTH_LONG).show();
                            setFilterType();
                            //System.out.println(anActivity.getSelectedTypeOfAid());
                        }
                        else{
                            typeOfAidText.setText(anActivity.anActivity.getSelectedTypeOfAid().get(0));
                            cdd.dismiss();
                            setFilterType();
                        }
                        }
                });
                final ListView listView = view.findViewById(R.id.dialog_listview);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setItemsCanFocus(false);
                String[] myResArray = getResources().getStringArray(R.array.type_of_aid_array);
                List<String> myResArrayList = Arrays.asList(myResArray);
                ArrayList<String> arrayList = new ArrayList<>(myResArrayList);
                typeOfAidAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_list_item_multiple_choice, arrayList);
                typeOfAidAdapter.setDropDownViewResource(R.layout.simple_list_item_1);
                listView.setAdapter(typeOfAidAdapter);
                for (int i = 0; i < typeOfAidAdapter.getCount(); i++){
                    System.out.println(typeOfAidAdapter.getItem(i).toString());
                    if (anActivity.getSelectedTypeOfAid().contains(typeOfAidAdapter.getItem(i).toString())){
                        listView.setItemChecked(i, true);
                    }
                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                     if (!listView.isItemChecked(position)) {
                         anActivity.getSelectedTypeOfAid().remove(parent.getItemAtPosition(position).toString());
                     } else {
                         anActivity.setTypeOfAid(parent.getItemAtPosition(position).toString());
                         if (!anActivity.getSelectedTypeOfAid().contains(parent.getItemAtPosition(position).toString())) {
                             anActivity.getSelectedTypeOfAid().add(parent.getItemAtPosition(position).toString());
                         }
                     }
                 }
                });
            }
        }
    }

    public static class BookmarksFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

        private  BookmarksListAdapter adapter;
        private RecyclerView.LayoutManager layoutManager;
        private  RecyclerView recyclerView;
        private TextView typeTextView;
        private MainActivity anActivity;
        private ImageView dropArrow;
        private ArrayAdapter typeAdapter;



        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            //inflates xml layout. sets toolbar title to 'Home'
            View view = inflater.inflate(R.layout.list_bookmarks_fragment, viewGroup, false);
            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText(R.string.bookmarks);
            anActivity.ukMap = anActivity.getDbHelper().getMarkersMap();
            anActivity.overseasMap = anActivity.getDbHelper().getOverseasMap();
            anActivity.onlineMap = anActivity.getDbHelper().getOnlineMap();
            anActivity.longLatMap = anActivity.getDbHelper().getLongLatMap();
            anActivity.bookmarksMap = new TreeMap<>();
            anActivity.bookMarksMapPosition = 1;
            anActivity.getSupportActionBar().show();
            dropArrow = view.findViewById(R.id.drop);
            int dimen = (int) getResources().getDimensionPixelSize(R.dimen._30sdp);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/dropdown_arrow.png")
                    .apply(new RequestOptions().override(dimen,
                            dimen))
                    .into(dropArrow);
            recyclerView = view.findViewById(R.id.list_markers_recycler_view);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

           
            anActivity.getAllbookmarks();
            adapter = new BookmarksListAdapter(anActivity, anActivity.bookmarksMap, anActivity.longLatMap, this);
            recyclerView.setHasFixedSize(false);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            TextView results = view.findViewById(R.id.results);
            results.setText(adapter.getmDataset().size() + " Results");

            typeTextView = view.findViewById(R.id.ref_name);
            typeTextView.setOnClickListener(this);

            anActivity.setType(null);
            anActivity.setSearchString("");

            SearchView searchView = view.findViewById(R.id.search_list);
            searchView.setIconifiedByDefault(false);
            searchView.setSubmitButtonEnabled(false);
            searchView.setQueryHint("Search");
            View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            AutoCompleteTextView search_text = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            search_text.setTextSize(14);
            search_text.setHint(R.string.edittext_hint);
            v.setBackgroundColor(Color.TRANSPARENT);
            ImageView icon = searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
            ImageView icon2 = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
            icon.setColorFilter(Color.WHITE);
            icon2.setColorFilter(Color.RED);
            searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    anActivity.setSearchString(newText);
                    getAllFilter();
                    return false;
                }
            });
            return view;
        }

        

        void getAllFilter() {
            class GetOrgFilter extends AsyncTask<Void, Void, Void> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    MainActivity anActivity = (MainActivity) getActivity();
                    anActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            adapter.getFilter().filter("");
                        }
                    });
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);
                }
            }
            GetOrgFilter gof = new GetOrgFilter();
            gof.execute();
        }

        public void setResults() {
            TextView results = getView().findViewById(R.id.results);
            results.setText(adapter.getmDataset().size() + " Results");
            adapter.notifyDataSetChanged();
        }



        @Override
        public void onClick(View v) {
                if (v == typeTextView) {
                final CustomDialogNoSearch cdd = new CustomDialogNoSearch(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
                final Window view = cdd.getWindow();
                TextView titleView = view.findViewById(R.id.dialog_title);
                titleView.setText("Uk, Europe or Online");
                ListView listView = view.findViewById(R.id.dialog_listview);
                ArrayList<BookmarkType> arrayList = new ArrayList<>();
                arrayList.add(0, BookmarkType.UK);
                arrayList.add(0, BookmarkType.OVERSEAS);
                arrayList.add(0, BookmarkType.ONLINE);
                //arrayList.
                typeAdapter = new ArrayAdapter(getContext(), R.layout.simple_list_item_1, android.R.id.text1, arrayList);
                typeAdapter.setDropDownViewResource(R.layout.simple_list_item_1);
                listView.setAdapter(typeAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
// TODO Auto-generated method stub
                        anActivity.setType((BookmarkType) parent.getItemAtPosition(position));
                        TextView typeText = getView().findViewById(R.id.ref_name);
                        typeText.setText(anActivity.getType().toString());
                                                getAllFilter();
                        cdd.dismiss();
                    }

                });
            }
        }

        @Override
        public void onDestroyView() {
            anActivity = null;
            dropArrow.setImageDrawable(null);
            dropArrow.setImageBitmap(null);
            if (typeAdapter != null) {
                typeAdapter.clear();
            }
            recyclerView.setAdapter(null);
            adapter = null;
            super.onDestroyView();
        }
    }

    public static class ManageContributionsFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

        private  ContributionsAdapter adapter;
        private RecyclerView.LayoutManager layoutManager;
        private  RecyclerView recyclerView;
        private TextView typeTextView;
        private MainActivity anActivity;
        private ImageView dropArrow;
        private ArrayAdapter typeAdapter;



        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            //inflates xml layout. sets toolbar title to 'Home'
            View view = inflater.inflate(R.layout.manage_contributions_fragment, viewGroup, false);
            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText("Contributions");
            anActivity.ukMap = anActivity.getDbHelper().getMarkersMap();
            anActivity.overseasMap = anActivity.getDbHelper().getOverseasMap();
            anActivity.onlineMap = anActivity.getDbHelper().getOnlineMap();
            anActivity.longLatMap = anActivity.getDbHelper().getLongLatMap();
            anActivity.contributionsMap = new TreeMap<>();
            anActivity.contributionsPosition = 0;
            anActivity.getSupportActionBar().show();
            dropArrow = view.findViewById(R.id.drop);
            int dimen = (int) getResources().getDimensionPixelSize(R.dimen._30sdp);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/dropdown_arrow.png")
                    .apply(new RequestOptions().override(dimen,
                            dimen))
                    .into(dropArrow);
            recyclerView = view.findViewById(R.id.list_markers_recycler_view);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            anActivity.getAllContributions();
            adapter = new ContributionsAdapter(anActivity, anActivity.contributionsMap, this);
            recyclerView.setHasFixedSize(false);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            typeTextView = view.findViewById(R.id.ref_name);
            typeTextView.setOnClickListener(this);

            anActivity.setType(null);
            anActivity.setSearchString("");

            SearchView searchView = view.findViewById(R.id.search_list);
            searchView.setIconifiedByDefault(false);
            searchView.setSubmitButtonEnabled(false);
            searchView.setQueryHint("Search");
            View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            AutoCompleteTextView search_text = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            search_text.setTextSize(14);
            search_text.setHint(R.string.edittext_hint);
            v.setBackgroundColor(Color.TRANSPARENT);
            ImageView icon = searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
            ImageView icon2 = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
            icon.setColorFilter(Color.WHITE);
            icon2.setColorFilter(Color.RED);
            searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    anActivity.setSearchString(newText);
                    getAllFilter();
                    return false;
                }
            });
            return view;
        }



        void getAllFilter() {
            class GetOrgFilter extends AsyncTask<Void, Void, Void> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    MainActivity anActivity = (MainActivity) getActivity();
                    anActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            adapter.getFilter().filter("");
                        }
                    });
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);
                }
            }
            GetOrgFilter gof = new GetOrgFilter();
            gof.execute();
        }


        @Override
        public void onClick(View v) {
            if (v == typeTextView) {
                final CustomDialogNoSearch cdd = new CustomDialogNoSearch(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
                final Window view = cdd.getWindow();
                TextView titleView = view.findViewById(R.id.dialog_title);
                titleView.setText("Uk, Europe or Online");
                ListView listView = view.findViewById(R.id.dialog_listview);
                ArrayList<BookmarkType> arrayList = new ArrayList<>();
                arrayList.add(0, BookmarkType.UK);
                arrayList.add(0, BookmarkType.OVERSEAS);
                arrayList.add(0, BookmarkType.ONLINE);

                //arrayList.
                typeAdapter = new ArrayAdapter(getContext(), R.layout.simple_list_item_1, android.R.id.text1, arrayList);
                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listView.setAdapter(typeAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
// TODO Auto-generated method stub
                        anActivity.setType((BookmarkType) parent.getItemAtPosition(position));
                        TextView typeText = getView().findViewById(R.id.ref_name);
                        typeText.setText(anActivity.getType().toString());
                        getAllFilter();
                        cdd.dismiss();
                    }

                });
            }
        }

        public void updateRecylerView() {
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onDestroyView() {
            anActivity = null;
            dropArrow.setImageDrawable(null);
            dropArrow.setImageBitmap(null);
            if (typeAdapter!= null) {
                typeAdapter.clear();
            }
            recyclerView.setAdapter(null);
            adapter = null;
            super.onDestroyView();
        }

    }

    public static class PersonalFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
        private ImageView bookmarksButton;
        private ImageView contributionsButton;
        MainActivity anActivity;

        public @Override
        View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            View view = inflater.inflate(R.layout.personal_fragment, viewGroup, false);

            TextView viewOneTv = view.findViewById(R.id.contributions_text);
            TextView viewTwoTv = view.findViewById(R.id.bookmarks_text);

            bookmarksButton = view.findViewById(R.id.bookmarks_image);
            contributionsButton = view.findViewById(R.id.contributions_image);
            bookmarksButton.setOnClickListener(this);
            contributionsButton.setOnClickListener(this);
            int dimen = (int) getResources().getDimensionPixelSize(R.dimen._120sdp);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button_alt.png")
                    .apply(new RequestOptions().override(dimen,
                            dimen))
                    .into(bookmarksButton);
            Glide.with(this)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/contributions_button.png")
                    .apply(new RequestOptions().override(dimen,
                            dimen))
                    .into(contributionsButton);


            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            anActivity.getmTitle().setText(R.string.personal);
            anActivity.getSupportActionBar().show();
            //sets font for icon text
            Typeface titleFont = Typeface.
                    createFromAsset(getContext().getAssets(), "fonts/Pacifico.ttf");
            viewOneTv.setTypeface(titleFont);
            viewTwoTv.setTypeface(titleFont);
            return view;
        }

        @Override
        public void onClick(View v) {
            if (v == bookmarksButton) {
                android.support.v4.app.Fragment frag = new BookmarksFragment();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
            if (v == contributionsButton) {
                anActivity.charityLogo = null;
                android.support.v4.app.Fragment frag = new ManageContributionsFragment();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft.replace(R.id.content_frame, frag);
                ft.addToBackStack(null).commit();
            }
        }

        @Override
        public void onDestroyView() {
            contributionsButton.setImageDrawable(null);
            Glide.with(this).clear(bookmarksButton);
            bookmarksButton.setImageDrawable(null);
            bookmarksButton.setImageBitmap(null);
            contributionsButton.setImageBitmap(null);
            anActivity = null;
            super.onDestroyView();
        }
    }

           public static class OtherResourcesFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
               private Button button;
               private Button button2;
               MainActivity anActivity;

               public @Override
               View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
                   if (viewGroup != null) {
                       viewGroup.removeAllViews();
                   }
                   View view = inflater.inflate(R.layout.other_resources, viewGroup, false);

                   TextView viewOneTv = view.findViewById(R.id.refugee_resource_title);
                   TextView viewTwoTv = view.findViewById(R.id.refugee_map_title);

                   button = view.findViewById(R.id.button);
                   button2 = view.findViewById(R.id.button2);
                   button.setOnClickListener(this);
                   button2.setOnClickListener(this);
                   int dimen = (int) getResources().getDimensionPixelSize(R.dimen._120sdp);

                   anActivity = (MainActivity) getActivity();
                   assert anActivity != null;
                   anActivity.getmTitle().setText("Other Resources");
                   anActivity.getSupportActionBar().show();
                   //sets font for icon text
                   Typeface titleFont = Typeface.
                           createFromAsset(getContext().getAssets(), "fonts/Pacifico.ttf");
                   viewOneTv.setTypeface(titleFont);
                   viewTwoTv.setTypeface(titleFont);
                   return view;
               }

               @Override
               public void onClick(View v) {
                   if (v == button) {
                       Uri uri = Uri.parse("https://twitter.com/refugeemaps?lang=en");
                       Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                       anActivity.startActivity(intent);
                   }
                   if (v == button2) {
                       Uri uri = Uri.parse("https://www.google.com/maps/d/u/0/viewer?mid=101pLsNs0xugexponJYhMLLlhyFk&ll=55.03118241523351%2C-3.9436459999999443&z=5");
                       Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                       anActivity.startActivity(intent);
                   }
               }

               @Override
               public void onDestroyView() {
                   anActivity = null;
                   super.onDestroyView();
               }
           }

    public static class UkMapFragment extends android.support.v4.app.Fragment implements View.OnClickListener, com.androidmapsextensions.OnMapReadyCallback, GoogleMap.OnMyLocationClickListener, com.androidmapsextensions.GoogleMap.OnMyLocationButtonClickListener {
        private static final String TAG = "";
        private static final String REQUEST_LOCATION = "Allow the Map to access your current Location";
        private View rootView;
        private com.androidmapsextensions.GoogleMap googleMap;
        private SparseArray<LatLong> latLongMap;
        private ClusterManager mClusterManager;
        private MainActivity anActivity;
        private float selectedLon = 0;
        private float selectedLat = 0;
        private Algorithm<LatLong> clusterManagerAlgorithm;
        private OverlappingMarkerSpiderfier oms;
        private Button button;
        private ClusteringSettings clusterSettings;

        public SparseArray<LatLong> getLatLongMap() {
            return latLongMap;
        }

        public void setLatLongMap(SparseArray<LatLong> latLongMap) {
            this.latLongMap = latLongMap;
        }

        @Override
        public void onDetach() {
            super.onDetach();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            if (container != null) {
                container.removeAllViews();
            }
            rootView = inflater.inflate(R.layout.map_fragment, container, false);

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                selectedLat = bundle.getFloat("selectedLat");
                selectedLon = bundle.getFloat("selectedLon");
            }
            anActivity = (MainActivity) getActivity();
           // latLongMap = anActivity.getDbHelper().getLongLatMap().clone();
            latLongMap = anActivity.getDbHelper().getLongLatMap();

            anActivity.getSupportActionBar().hide();
            button = rootView.findViewById(R.id.filter_map_button);
            button.setOnClickListener(this);

            ArrayList<String> list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.type_of_aid_array)));
            anActivity.setSelectedTypeOfAid(list);
            list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.area_array)));
            anActivity.setRegions(list);

            com.androidmapsextensions.SupportMapFragment mapFragment = (com.androidmapsextensions.SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getExtendedMapAsync(this);
            SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment)
                    getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
            EditText etPlace = autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input);
            etPlace.setHintTextColor(anActivity.getResources().getColor(R.color.toolbarColor));
            etPlace.setHint(R.string.edittext_hint);
            etPlace.setTextSize(18);
            etPlace.setTextColor(anActivity.getResources().getColor(R.color.toolbarColor));
            ImageButton imageButton = autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_button);
            imageButton.setColorFilter(R.color.toolbarColor);
            autocompleteFragment.setHint("Address");
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    Log.i(TAG, "Place: " + place.getName());
                    LatLng searchedLatLng = place.getLatLng();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(searchedLatLng, 17));
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }

            });
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setCountry("GB")
                    .build();

            autocompleteFragment.setFilter(typeFilter);


            return rootView;
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            if (requestCode == 1) {
                if (ContextCompat.checkSelfPermission(anActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                    googleMap.setOnMyLocationButtonClickListener(this);
                } else {
                    Toast.makeText(anActivity, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
        void checkMapPermissions() {
            if (ContextCompat.checkSelfPermission(anActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                googleMap.setMyLocationEnabled(true);
                googleMap.setOnMyLocationButtonClickListener(this);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }

        void loadFragment(final Fragment fragment, String markerId) {
            Bundle b = new Bundle();
            b.putString("markerId", markerId);
            fragment.setArguments(b);
            anActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .disallowAddToBackStack().
                    commit();
        }

        @Override
        public void onViewCreated(View v, Bundle savedInstanceState) {

        }



        @Override
        public void onClick(View v) {
            if (v == button){
                anActivity.setCustomDialogUkMap(new CustomDialogUkMap(getActivity(), this));
                anActivity.getCustomDialogUkMap().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                anActivity.getCustomDialogUkMap().show();
                anActivity.getCustomDialogUkMap().setCancelable(false);
            }

        }



        @Override
        public void onDestroyView() {
            googleMap.clear();
            clusterSettings = null;
            oms = null;
            mClusterManager = null;
            super.onDestroyView();
        }

        public com.androidmapsextensions.GoogleMap getGoogleMap() {
            return googleMap;
        }

        @Override
        public void onMapReady(final com.androidmapsextensions.GoogleMap map) {
            final HashMap <String, String> mMarkers = new HashMap<>();
            googleMap = map;
            map.clear();

            updateClusteringRadius(); // <= Assuming clustering is activated
            oms = new OverlappingMarkerSpiderfier(googleMap);
            checkMapPermissions();
            LatLng startingZoom = new LatLng(56.7004, -4.3536);
            float zoomLevel = 5;
            if (selectedLat != 0 && selectedLon != 0 ){
                startingZoom = new LatLng(selectedLat, selectedLon);
                zoomLevel = 17;
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingZoom, zoomLevel));
                //mClusterManager = new ClusterManager(anActivity, googleMap);
            // Instantiate the cluster manager algorithm as is done in the ClusterManager
            clusterManagerAlgorithm = new NonHierarchicalDistanceBasedAlgorithm();

            // Set this local algorithm in clusterManager
           //      mClusterManager.setAlgorithm(clusterManagerAlgorithm);
            float zoom = map.getCameraPosition().zoom;
            float maxZoom = googleMap.getMaxZoomLevel();

                googleMap.setOnMarkerClickListener(new com.androidmapsextensions.GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(com.androidmapsextensions.Marker marker) {

                        // We need to figure out if it was a seperate marker or a cluster marker
                        if (marker.isCluster()) {
                            if (googleMap.getCameraPosition().zoom >= 15) //Play around with this. We assume the SPIDERFICATION_ZOOM_THRSH is constant and never changes.
                                oms.spiderListener(marker); // That's where the magic happens
                            else {
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        marker.getPosition(),
                                        googleMap.getCameraPosition().zoom + dynamicZoomLevel()));
                                updateClusteringRadius();
                            }
                        }
                        else {
                            // Calculate required horizontal shift for current screen density
                            final int dX = getResources().getDimensionPixelSize(R.dimen._1sdp);
                            // Calculate required vertical shift for current screen density
                            final int dY = getResources().getDimensionPixelSize(R.dimen._150sdp);
                            final Projection projection = googleMap.getProjection();
                            final Point markerPoint = projection.toScreenLocation(
                                    marker.getPosition()
                            );
                            // Shift the point we will use to center the map
                            markerPoint.offset(dX, -dY);
                            final LatLng newLatLng = projection.fromScreenLocation(markerPoint);
                            // Buttery smooth camera swoop :)
                            googleMap.animateCamera(CameraUpdateFactory.newLatLng(newLatLng));
                            // Show the info window (as the overloaded method would)
                            System.out.println(googleMap.getCameraPosition().zoom);
                            marker.showInfoWindow();}
                        return true;
                    }});

                for(int i=1; i<= latLongMap.size(); i++) {
                    MarkerOptions mo = new MarkerOptions()
                            .position(new LatLng((double) latLongMap.get(i).getLat(), (double) latLongMap.get(i).getLon()))
                            .flat(true)
                            .snippet(latLongMap.get(i).getSnippet())
                            .rotation(0)
                            .title(latLongMap.get(i).getTitle());
                    com.androidmapsextensions.Marker marker = map.addMarker(mo);
                    mMarkers.put(marker.getId(), latLongMap.get(i).getMarkerId());
                }

            googleMap.setInfoWindowAdapter(new CustomInfoViewAdapter(LayoutInflater.from(anActivity), mMarkers, anActivity));
                    googleMap.setOnInfoWindowClickListener(new com.androidmapsextensions.GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(com.androidmapsextensions.Marker marker) {
                            Fragment fragment = new MainActivity.SelectedOrgFragment();
                            String markerId =   mMarkers.get(marker.getId());
                            loadFragment(fragment, markerId);

                        }
                    });
                }

        private int clusterRadiusCalculation() {
            final int minRad = 0, maxRad = 150;
            final float minRadZoom = 10F, maxRadZoom = 7.333F;

            if (googleMap.getCameraPosition().zoom >= minRadZoom) {

                return minRad;

            } else if (googleMap.getCameraPosition().zoom <= maxRadZoom)
                return maxRad;
            else
                // simple interpolation:
                return (int) (maxRad - (maxRadZoom - googleMap.getCameraPosition().zoom) *
                        (maxRad - minRad) / (maxRadZoom - minRadZoom));
        }

        private void updateClusteringRadius() {
            if (clusterSettings == null) {
                clusterSettings = new ClusteringSettings();
                clusterSettings.addMarkersDynamically(true);
                clusterSettings.clusterSize(clusterRadiusCalculation());
                /** Based on pl.mg6.android.maps.extensions.demo.ClusterGroupsFragment */
                ClusterOptionsProvider provider = new ClusterOptionsProvider() {
                    @Override public ClusterOptions getClusterOptions(List<com.androidmapsextensions.Marker> markers) {
                        float hue;
                        switch (markers.get(0).getClusterGroup()) {
                            case ClusterGroup.FIRST_USER:
                                hue = BitmapDescriptorFactory.HUE_ORANGE;
                                break;
                            case ClusterGroup.DEFAULT: // The color of "spiderfied at least once" clusters
                                hue = BitmapDescriptorFactory.HUE_GREEN;
                                break;
                            default: // ClusterGroup.NOT_CLUSTERED:
                                hue = BitmapDescriptorFactory.HUE_ROSE;
                                break;
                        }
                        BitmapDescriptor defaultIcon = BitmapDescriptorFactory.defaultMarker(hue);
                        return new ClusterOptions().icon(defaultIcon);                }
                };
                googleMap.setClustering(clusterSettings.clusterOptionsProvider(provider));
            }
            else {
                clusterSettings.clusterSize(clusterRadiusCalculation());
            }
        }

        private float dynamicZoomLevel() {
            float currZoomLvl = googleMap.getCameraPosition().zoom;
            final float minZoomStepAtZoom = 17.3F, minZoomStep = 1.8F;
            final float maxZoomStepAtZoom = 7F, maxZoomStep = 2.8F;

            if (currZoomLvl >= minZoomStepAtZoom)
                return minZoomStep;
            else if (currZoomLvl <= maxZoomStepAtZoom)
                return maxZoomStep;
            else
                // simple interpolation:
                return (currZoomLvl - maxZoomStepAtZoom)
                        * (maxZoomStep - minZoomStep)
                        / (maxZoomStepAtZoom - minZoomStepAtZoom) + maxZoomStep;
        }
        @Override
        public void onMyLocationClick(@NonNull Location location) {
                    }

        @Override
        public boolean onMyLocationButtonClick() {
            // Return false so that we don't consume the event and the default behavior still occurs
            // (the camera animates to the user's current position).
            return false;        }
    }

    public static class SelectedOrgFragment extends android.support.v4.app.Fragment implements s3DownloaderInterface {

        private CircleImageView logo;
        private String title;
        private SelectedOrgFragment selectedOrgFragment;
        private MainActivity anActivity;
        private String markerId;
        private HashMap<String, UkMarker> currentMap;
        private TextView titleText;
        private TextView desc;
        private TextView date;
        private TextView address;
        private ImageView bookmarkImg;
        private ImageView webImg;
        private ImageView mapImg;
        private ImageView locationImg;
        private ImageView dateImg;
        private ImageView timeImg;
        private String logoUrl;


        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            //inflates xml layout. sets toolbar title to 'Home'
            View view = inflater.inflate(R.layout.selected_org_view, viewGroup, false);
            selectedOrgFragment = this;
            anActivity = (MainActivity) getActivity();
            assert anActivity != null;
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                markerId = bundle.getString("markerId");
            }
            anActivity.getSupportActionBar().show();
            titleText = view.findViewById(R.id.ref_name);
            Typeface titleFont = Typeface.
                    createFromAsset(anActivity.getAssets(), "fonts/Lobster_1.3.otf");
            titleText.setTypeface(titleFont);
            desc = view.findViewById(R.id.card_desc);
            date = view.findViewById(R.id.date_text);
            address = view.findViewById(R.id.address);

            bookmarkImg = view.findViewById(R.id.bookmarks_button);
            webImg = view.findViewById(R.id.web_button);
            mapImg = view.findViewById(R.id.map_button);
            logo = view.findViewById(R.id.nat_circle_image_view);
            locationImg = view.findViewById(R.id.location_img);
            dateImg = view.findViewById(R.id.date_img);
            timeImg = view.findViewById(R.id.time_img);
            Glide.with(this).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/map_google_icon.png")
                    .into(locationImg);
            Glide.with(this).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/ic_menu_my_calendar.png")
                    .into(dateImg);
            Glide.with(this).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/time.png")
                    .into(timeImg);
            Glide.with(this).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/hyperlink_icon.png")
                    .into(webImg);
            Glide.with(this).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/map_button.png")
                    .into(mapImg);


            Integer id = Integer.parseInt(markerId);
            currentMap = anActivity.getDbHelper().getMarkersMap().get(id);
            if (currentMap.containsKey("Organisation")) {
                RelativeLayout rel = view.findViewById(R.id.date_time_container);
                rel.setVisibility(View.INVISIBLE);
                final Charity charity = (Charity) currentMap.get("Organisation");
                titleText.setText(charity.getTitle());
                logoUrl = charity.getImageUrl();

                Glide.with(anActivity).load(logoUrl).apply(new RequestOptions()
                        .signature(new ObjectKey(charity.getLastModified()))
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                        .into(logo);
                anActivity.getmTitle().setText(R.string.details);
                String houseNoOrBuldingName = charity.getHouseNoOrBuldingName();
                String street = charity.getStreet();
                String otherAddress = charity.getOtherAddress();
                String cityOrTown = charity.getCityOrTown();
                if (street.length() != 0) {
                    street = houseNoOrBuldingName + " " + street + ", ";
                }

                if (otherAddress.length() != 0) {
                    otherAddress = otherAddress + ", ";
                }

                if (cityOrTown.length() != 0 && charity.getPostcode().length() != 0) {
                    cityOrTown = cityOrTown + ", ";
                }

                address.setText(street + otherAddress + cityOrTown + charity.getPostcode());
                desc.setText(charity.getDescription());
                webImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(charity.getWebsite());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        anActivity.startActivity(intent);
                    }
                });

            } else {
                final Fundraiser fundraiser = (Fundraiser) currentMap.get("Fundraiser");
                titleText.setTextSize(18);
                RelativeLayout rel = view.findViewById(R.id.date_time_container);
                rel.setVisibility(View.VISIBLE);
                logoUrl = fundraiser.getaCharity().getImageUrl();
                Glide.with(anActivity).load(logoUrl).apply(new RequestOptions()
                        .signature(new ObjectKey(fundraiser.getLastModified()))
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                        .into(logo);
                date.setText(fundraiser.getDate());
                TextView timeTextView = view.findViewById(R.id.time_text);
                timeTextView.setText(fundraiser.getTime());
                titleText.setText(fundraiser.getTitle() + "\n" + "\n" + "For: " + fundraiser.getaCharity().getTitle());
                String houseNoOrBuldingName = fundraiser.getHouseNoOrBuldingName();
                String street = fundraiser.getStreet();
                String otherAddress = fundraiser.getOtherAddress();
                String cityOrTown = fundraiser.getCityOrTown();
                if (street.length() != 0) {
                    street = houseNoOrBuldingName + " " + street + ", ";
                }

                if (otherAddress.length() != 0) {
                    otherAddress = otherAddress + ", ";
                }

                if (cityOrTown.length() != 0 && fundraiser.getPostcode().length() != 0) {
                    cityOrTown = cityOrTown + ", ";
                }

                address.setText(street + otherAddress + cityOrTown + fundraiser.getPostcode());
                desc.setText(fundraiser.getDescription());
                webImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(fundraiser.getWebsite());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        anActivity.startActivity(intent);
                    }
                });
            }
            if (anActivity.getDbHelper().getBookmarksArray().contains(Integer.parseInt(markerId))) {
                Glide.with(this).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button_alt.png")
                        .into(bookmarkImg);        }
            else{
                Glide.with(this).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button.png")
                        .into(bookmarkImg);          }
            mapImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LatLong l = anActivity.getDbHelper().getLongLatMap().get(Integer.parseInt(markerId));
                    float lat = l.getLat();
                    float lon = l.getLon();
                    Fragment frag = new MainActivity.UkMapFragment();
                    loadMapFragment(frag, lat, lon);
                }
            });
            bookmarkImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentMap.containsKey("Organisation")) {
                        final Charity charity = (Charity) currentMap.get("Organisation");
                        title = charity.getTitle();
                    } else {
                        final Fundraiser fundraiser = (Fundraiser) currentMap.get("Fundraiser");
                        title = fundraiser.getTitle();
                    }
                    anActivity.checkDownloadPermissionsSelected(2, selectedOrgFragment);

                }
            });
            return view;
        }

        @Override
        public void downloadResult() {
            if (anActivity.getDbHelper().getBookmarksArray().contains(Integer.valueOf(markerId))) {
                removeBookmark();
            } else {
                addBookmark();
            }
        }

        private void removeBookmark() {
            String toastMsg = title + " bookmark removed.";
            Toast.makeText(anActivity, toastMsg, Toast.LENGTH_SHORT).show();
            anActivity.getDbHelper().getBookmarksArray().remove(Integer.valueOf(markerId));
            Glide.with(this).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button.png")
                    .into(bookmarkImg);
            anActivity.getDbHelper().removeBookmark(Integer.valueOf(markerId));
        }

        private void addBookmark() {
            String toastMsg = title + " bookmarked.";
            Toast.makeText(anActivity, toastMsg, Toast.LENGTH_SHORT).show();
            anActivity.getDbHelper().getBookmarksArray().add(Integer.valueOf(markerId));
            Glide.with(this).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button_alt.png")
                    .into(bookmarkImg);
            anActivity.getDbHelper().addBookmark(Integer.valueOf(markerId));
        }

        void loadMapFragment(final Fragment fragment, float lat, float lon) {
            Bundle b = new Bundle();
            b.putFloat("selectedLat", lat);
            b.putFloat("selectedLon", lon);
            fragment.setArguments(b);
            anActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .addToBackStack(null)
                    .commit();
        }



        @Override
        public void onDestroyView() {
            selectedOrgFragment = null;
            Glide.with(this).clear(logo);
            Glide.with(this).clear(locationImg);
            Glide.with(this).clear(dateImg);
            Glide.with(this).clear(timeImg);
            Glide.with(this).clear(webImg);
            Glide.with(this).clear(bookmarkImg);
            Glide.with(this).clear(mapImg);
            logo.setImageBitmap(null);
            logo.setImageDrawable(null);
            locationImg.setImageBitmap(null);
            locationImg.setImageDrawable(null);
            dateImg.setImageBitmap(null);
            dateImg.setImageDrawable(null);
            timeImg.setImageBitmap(null);
            timeImg.setImageDrawable(null);
            webImg.setImageBitmap(null);
            webImg.setImageDrawable(null);
            bookmarkImg.setImageBitmap(null);
            bookmarkImg.setImageDrawable(null);
            mapImg.setImageBitmap(null);
            mapImg.setImageDrawable(null);
            super.onDestroyView();
        }
    }

    public Charity getCharityToBeAdded() {
        return charityToBeAdded;
    }

    public void setCharityToBeAdded(Charity charityToBeAdded) {
        this.charityToBeAdded = charityToBeAdded;
    }

    public Fundraiser getFundraiserToBeAdded() {
        return fundraiserToBeAdded;
    }

    public void setFundraiserToBeAdded(Fundraiser fundraiserToBeAdded) {
        this.fundraiserToBeAdded = fundraiserToBeAdded;
    }
}
