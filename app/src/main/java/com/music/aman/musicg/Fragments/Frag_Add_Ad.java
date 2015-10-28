package com.music.aman.musicg.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.music.aman.musicg.Activity_Advertisement;
import com.music.aman.musicg.FileUtils;
import com.music.aman.musicg.MainActivity;
import com.music.aman.musicg.Models.APIInterface;
import com.music.aman.musicg.Models.APIModel;
import com.music.aman.musicg.Models.Addvertisment;
import com.music.aman.musicg.R;
import com.music.aman.musicg.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.janmuller.android.simplecropimage.CropImage;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by AmaN on 10/22/2015.
 */
public class Frag_Add_Ad extends Fragment {

    @Bind(R.id.frag_add_ad_image)
    ImageView ivImage;
    @Bind(R.id.frag_add_ad_et_url)
    EditText etUrl;

    private static final int REQUEST_CODE_CROP_IMAGE = 102;
    View view;
    private Uri uri;
    private Activity_Advertisement advertisement;

    private Addvertisment objAddvertisment = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_ad, container, false);
        ButterKnife.bind(this, view);
        advertisement = ((Activity_Advertisement) getActivity());
        advertisement.ivAddAd.setVisibility(View.GONE);
        advertisement.setTvTitle("Add new Advertisement");

        if (getArguments() != null) {
            advertisement.setTvTitle("Edit Advertisement");
            objAddvertisment = (Addvertisment) getArguments().getSerializable("ad");
            Picasso.with(getActivity()).load("http://whistleandfind.com/developer/add_pictures/" + objAddvertisment.getImage_url()).into(ivImage);
            etUrl.setText(objAddvertisment.getUrl());
        }

        return view;
    }

    @OnClick(R.id.frag_add_ad_btn_save)
    public void saveAdd() {

        TypedFile typedFile = null;

        if (objAddvertisment == null) {
            if (uri == null || etUrl.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Please enter url and image.", Toast.LENGTH_LONG).show();
                return;
            }
            typedFile = new TypedFile("image/*", new File(FileUtils.getPath(getActivity(), uri)));

        } else {
            if (etUrl.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Please enter url.", Toast.LENGTH_LONG).show();
                return;
            }
            if (uri == null) {
                typedFile = null;
            }else {
                typedFile = new TypedFile("image/*", new File(FileUtils.getPath(getActivity(), uri)));
            }

        }

        advertisement.showProgess();

        final APIInterface apiInterface = Utils.getAdapterWebService();


        apiInterface.upload(typedFile,objAddvertisment == null?"":objAddvertisment.getId(),advertisement.preferences.getString(MainActivity.USER_ID_KEY, ""), etUrl.getText().toString(), new Callback<APIModel>() {
            @Override
            public void success(APIModel apiModel, Response response) {
                advertisement.hideProgess();
                if (apiModel.getSuccess().equals("1")) {
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getActivity(), apiModel.getReplyMsg(), Toast.LENGTH_LONG).show();
                }
                System.out.println(apiModel);
            }

            @Override
            public void failure(RetrofitError error) {
                advertisement.hideProgess();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
    }

    @OnClick(R.id.frag_add_ad_btn_add_image)
    public void openFile() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // special intent for Samsung file manager
        Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        // if you want any file type, you can skip next line
        sIntent.putExtra("CONTENT_TYPE", "image/*");
        sIntent.addCategory(Intent.CATEGORY_DEFAULT);

        Intent chooserIntent;
        if (getActivity().getPackageManager().resolveActivity(sIntent, 0) != null) {
            // it is device with samsung file manager
            chooserIntent = Intent.createChooser(sIntent, "Open file");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent});
        } else {
            chooserIntent = Intent.createChooser(intent, "Open file");
        }

        try {
            this.startActivityForResult(chooserIntent, 101);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "No suitable File Manager was found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            String path = FileUtils.getPath(getActivity(), uri);
            Log.e("Path", path);
            runCropImage(path);
        }

        if (requestCode == REQUEST_CODE_CROP_IMAGE && resultCode == Activity.RESULT_OK) {

            String path = data.getStringExtra(CropImage.IMAGE_PATH);

            // if nothing received
            if (path == null) {

                return;
            }

            Picasso.with(getActivity()).load(new File(path)).into(ivImage);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void runCropImage(String filePath) {

        // create explicit intent
        Intent intent = new Intent(getActivity(), CropImage.class);

        // tell CropImage activity to look for image to crop 

        intent.putExtra(CropImage.IMAGE_PATH, filePath);

        // allow CropImage activity to rescale image
        intent.putExtra(CropImage.SCALE, true);

        // if the aspect ratio is fixed to ratio 3/2
        intent.putExtra(CropImage.ASPECT_X, 3);
        intent.putExtra(CropImage.ASPECT_Y, 3);

        // start activity CropImage with certain request code and listen
        // for result
        this.startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }
}
