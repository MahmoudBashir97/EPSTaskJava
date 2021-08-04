package com.mahmoudbashir.taskepsj.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mahmoudbashir.taskepsj.locationTracker.GpsTracker_Location;
import com.mahmoudbashir.taskepsj.utils.Constants;
import com.mahmoudbashir.taskepsj.R;
import com.mahmoudbashir.taskepsj.databinding.FragmentMainScreenBinding;
import com.mahmoudbashir.taskepsj.pojo.DataModel;
import com.mahmoudbashir.taskepsj.viewModel.StViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

public class MainScreen_Fragment extends Fragment {
    private FragmentMainScreenBinding mainBinding;
    String photoUri;
    private StViewModel viewModel;

    boolean GpsStatus = false;
    FusedLocationProviderClient fusedLocationClient;
    LocationCallback locationCallback;
    double lat = 0.0;
    double lng = 0.0;

    GpsTracker_Location tracker;
    String addressDetails;

    public MainScreen_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_screen_, container, false);


        //initializing inside viewModel
        doInitialization();
        //getting last cached location with Fusedlocation And LocationTracker after checking GPS Enabled
        //getLocationFunction();
        getLocationWithFused();


        //click action to capture a new pic
        mainBinding.linPicPhoto.setOnClickListener(v -> {
            openCam();
        });

        //click action to save data into local(Room) database
        mainBinding.btnSave.setOnClickListener(v -> {
            insertingDataToRoom();
        });

        mainBinding.txtLocation.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(addressDetails)) {
                getLocationWithFused();
                mainBinding.txtLocation.setText(addressDetails);
            } else {
                mainBinding.txtLocation.setError("please enable your Gps and click to get your address details");
                mainBinding.txtLocation.requestFocus();
                tracker = new GpsTracker_Location(requireContext());
                getLocationFunction();
                getLocationWithFused();
            }
        });

        return mainBinding.getRoot();
    }

    private void getLocationWithFused() {

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }else fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Double latitude = location.getLatitude();
                            Double longitude = location.getLongitude();
                            try {
                                if (latitude > 0.0)
                                    addressDetails = getAddress(latitude, longitude);
                                mainBinding.txtLocation.setText(addressDetails);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            tracker.showSettingsAlert();
                        }
                    }
                });
    }
    private void getLocationFunction() {

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location :locationResult.getLocations() ){
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                } }
        };


        /*if (tracker.canGetLocation()) {
            Double latitude  = tracker.getLatitude();
            Double longitude = tracker.getLongitude();
            try {
                if (latitude>0.0)
                    addressDetails =  getAddress(latitude,longitude);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            tracker.showSettingsAlert();
        }*/
    }

    private void stopLocationUpdates() {
        //fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private String getAddress(double lat,double lng) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        addresses = geocoder.getFromLocation(lat, lng, 1);

        String address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

        Log.d("addressName: ","city name :" +address );

       return address;
    }

    private void doInitialization(){
        viewModel = ViewModelProviders.of(this).get(StViewModel.class);
        tracker = new GpsTracker_Location(requireContext());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }
    private void insertingDataToRoom(){
        if (isValidate()){
            Log.d("successMessage : ", "successfuly!");
            DataModel model = new DataModel(photoUri,
                    mainBinding.otpView.getText().toString(),
                    mainBinding.edtTitle.getText().toString(),
                    addressDetails);
            viewModel.insertMessage(model).observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if (s.equals("completed")){
                        photoUri="";
                        mainBinding.edtTitle.getText().clear();
                        mainBinding.otpView.getText().clear();
                        mainBinding.imgCaptured.setImageResource(R.drawable.ic_baseline_camera_alt_24);
                        displayMessage(getContext(),"Data has been added successfully");
                    }else {
                        displayMessage(getContext(),"error occured !!");
                    }
                }
            });
        }
    }

    //do check validation of inputs that we need to add
    private boolean isValidate(){
        if (TextUtils.isEmpty(photoUri)){
            displayMessage(getContext(),"please capture a new Image!");
            return false;
        }else if (TextUtils.isEmpty(mainBinding.otpView.getText().toString())){
            mainBinding.otpView.setError("Please Enter right Code");
            mainBinding.otpView.requestFocus();
            return false;
        }else if (TextUtils.isEmpty(mainBinding.txtLocation.getText())){
            mainBinding.txtLocation.setError("please enable your Gps and click to get your address details");
            mainBinding.txtLocation.requestFocus();
            return false;
        }else if (TextUtils.isEmpty(mainBinding.edtTitle.getText())){
            mainBinding.edtTitle.setError("please enter a valid input!");
            mainBinding.edtTitle.requestFocus();
            return false;
        } else return true;
    }
    //open camera with its permission and intent
    private void openCam(){
        if (ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0
            );
        } else {
            Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(photoIntent, Constants.CAPTURE_IMAGE_REQUEST);
        }
    }

    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    //here to get a Uri of captured image
    private Uri getImageUri(Context appContext, Bitmap photo){
        OutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), photo, "EpsImage", null);
        return Uri.parse(path);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CAPTURE_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(getContext(),photo);
                photoUri = ""+tempUri;
                mainBinding.imgCaptured.setImageBitmap(photo);
            }else if (resultCode == Activity.RESULT_CANCELED){
                displayMessage(getContext(),"CANCELED");
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }
}