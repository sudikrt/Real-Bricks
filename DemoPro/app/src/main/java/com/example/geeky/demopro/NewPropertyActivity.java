package com.example.geeky.demopro;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NewPropertyActivity extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    private static String TAG = "NewProp";
    private static final String TAG_MESSAGE = "message";

    private static int RESULT_LOAD_IMAGE = 1;

    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    EditText inputContact;
    EditText inputLocation;
    //EditText inputStatus;
    EditText city;
    EditText inputPrice;
    EditText inputDesc;

    Button btnAdd;
    Button btnCancel;
    Button btnLoad;

    String Contact;
    String Name;
    String Location;
    String user;
    //String Status;
    String Price;
    String City;
    String Description;
    String path_url;

    ImageView imageView;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;


    // url to create new product
    private static String url_new_property = Constants.URL + "new_prop.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_property);

        user = getIntent().getExtras().getString("userName");
        // Edit Text
        inputName = (EditText) findViewById(R.id.name);
        inputLocation = (EditText) findViewById(R.id.location);
        city = (EditText) findViewById(R.id.city);
        //inputStatus = (EditText) findViewById(R.id.status);
        inputPrice = (EditText) findViewById(R.id.price);
        inputDesc = (EditText) findViewById(R.id.desc);
        inputContact = (EditText) findViewById(R.id.cont);
        imageView = (ImageView) findViewById(R.id.imgView);

        // Create button
        btnAdd = (Button) findViewById(R.id.btnNew);
        btnCancel = (Button) findViewById(R.id.btnKanso);
        btnLoad = (Button) findViewById(R.id.browse);


        // button click event
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Contact = inputContact.getText().toString();
                Name = inputName.getText().toString();
                Location = inputLocation.getText().toString();
                //Status = inputStatus.getText().toString();
                City = city.getText().toString();
                Price = inputPrice.getText().toString();
                Description = inputDesc.getText().toString();

                uploadMultipart();
                // creating new product in background thread
                new CreateNewProduct().execute();
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                showFileChooser();
               /* Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);*/
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Screen_Activity.class);
                startActivity(i);
            }
        });
    }

    /**
     * Background Async Task to Create new product
     * */
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewPropertyActivity.this);
            pDialog.setMessage("Adding Property Record..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userName", user));
            params.add(new BasicNameValuePair("contact", Contact));
            params.add(new BasicNameValuePair("name", Name));
            params.add(new BasicNameValuePair("location", Location));
            params.add(new BasicNameValuePair("city", City));
            //params.add(new BasicNameValuePair("status", Status));
            params.add(new BasicNameValuePair("price", Price));
            params.add(new BasicNameValuePair("description", Description));
            params.add(new BasicNameValuePair("path", path_url));


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_new_property,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    finish();
                    return json.getString(TAG_MESSAGE);
                } else {
                    // failed to create product
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(NewPropertyActivity.this, file_url, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(NewPropertyActivity.this, "Server Not Found try after some time!!",
                        Toast.LENGTH_LONG).show();
            }
        }

    }
    //method to show file chooser
    private void showFileChooser() {
        if (isStoragePermissionGranted()) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        } else {
            requestStoragePermission();
        }
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.destroyDrawingCache();
                imageView.setMaxHeight(100);
                imageView.setMaxWidth(100);
                imageView.setPadding(2,3,2,3);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadMultipart() {
        //getting name for the image
        String name = user + new Date().toString();

        name = name.replace('+', '_');
        name = name.replace(':', '_');
        name = name.replace(' ', '_');

        //getting the actual path of the image
        String path = getPath(filePath);
        String extension = path.substring(path.lastIndexOf("."));
        path_url = name + extension;
        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Constants.UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
}
