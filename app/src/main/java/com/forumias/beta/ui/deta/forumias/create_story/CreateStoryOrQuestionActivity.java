package com.forumias.beta.ui.deta.forumias.create_story;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chinalwb.are.AREditor;
import com.forumias.beta.adapter.ColorAdapter;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.DefaultPojo;
import com.forumias.beta.utility.InternetConnection;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.utility.PermissionUtility;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.academy.AcademyActivity;
import com.forumias.beta.ui.deta.forumias.page.MyPagesPostActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CreateStoryOrQuestionActivity extends AppCompatActivity {

    @BindView(R.id.colorRecyclerView)
    RecyclerView colorRecyclerView;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.llBackCover)
    LinearLayoutCompat llBackCover;
    @BindView(R.id.llSelectImageSection)
    LinearLayoutCompat llSelectImageSection;
    @BindView(R.id.checkCover)
    AppCompatCheckBox checkCover;
    @BindView(R.id.radioGroupQuestion)
    RadioGroup radioGroupQuestion;
    @BindView(R.id.editTitle)
    AppCompatEditText editTitle;
    @BindView(R.id.tvPost)
    AppCompatTextView tvPost;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;
    @BindView(R.id.editDescription)
    AppCompatEditText editDescription;
    @BindView(R.id.imageSection)
    LinearLayoutCompat imageSection;
    @BindView(R.id.ivUserImage)
    ImageView ivUserImage;
    @BindView(R.id.tvName)
    AppCompatTextView tvName;
    @BindView(R.id.viewOne)
    View viewImage;
    @BindView(R.id.viewColor)
    View viewColor;
    @BindView(R.id.tvPostDescription)
    AppCompatTextView tvPostDescription;
    @BindView(R.id.llEditorSection)
    LinearLayoutCompat llEditorSection;
    @BindView(R.id.rlCreatePostSection)
    RelativeLayout rlCreatePostSection;
    @BindView(R.id.radioQuestion)
    AppCompatRadioButton radioQuestion;
    @BindView(R.id.radioDiscussion)
    AppCompatRadioButton radioDiscussion;
    @BindView(R.id.radioImage)
    AppCompatRadioButton radioImage;
    @BindView(R.id.radioColor)
    AppCompatRadioButton radioColor;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;




    private int status, loginUserId , type , darkModeStatus;
    private static final int CAMERA_REQUEST = 100;
    private static final int GALLERY_REQUEST = 101;

    public final String APP_TAG = "MyCustomApp";
    public String photoFileName = "photo.jpg";
    File photoFile;
    private AREditor arEditor;
    Activity context;
    private String tagId;
    private int isDiscussion = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_story_or_question);
        ButterKnife.bind(this);
        this.arEditor = findViewById(R.id.postEditor);
        initView();
        colorBox();
        shareImageData();

           Bundle bundle = getIntent().getExtras();

               String imageUrl = bundle.getString(BaseUrl.IMAGE);
               if(imageUrl != null) {
                   Intent intent = new Intent();
                   intent.putExtra("IMAGE", imageUrl);
                   this.arEditor.onActivityResult(100, -1, intent, "image");
               }


        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioImage) {
                colorRecyclerView.setVisibility(View.GONE);
                llSelectImageSection.setVisibility(View.VISIBLE);
                viewColor.setVisibility(View.GONE);
                viewImage.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.radioColor) {
                colorRecyclerView.setVisibility(View.VISIBLE);
                viewColor.setVisibility(View.VISIBLE);
                viewImage.setVisibility(View.GONE);
                llSelectImageSection.setVisibility(View.GONE);
            }

        });

        checkCover.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radioGroup.setVisibility(View.VISIBLE);
                llBackCover.setVisibility(View.VISIBLE);
                colorRecyclerView.setVisibility(View.VISIBLE);
            } else {
                radioGroup.setVisibility(View.GONE);
                colorRecyclerView.setVisibility(View.GONE);

            }
        });

        if(radioQuestion.isChecked()){
            isDiscussion = 0;
        }

        radioQuestion.setOnClickListener(view -> {
            isDiscussion = 0;
        });

        radioDiscussion.setOnClickListener(view -> {
            isDiscussion = 1;
        });


    }

    private void shareImageData() {
        Intent intent = getIntent();
        String type = intent.getType();
        String action = intent.getAction();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendImage(intent);
            }
        }
        if (Intent.ACTION_SEND_MULTIPLE.equals(action) && getIntent().hasExtra(Intent.EXTRA_STREAM)) {
            ArrayList<Parcelable> list = getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);
           /* Intent i = new Intent(this, RequestListingActivity.class);
            i.putExtra(PARAM_MULTIPLE_IMAGE, list);
            startActivity(i);
            finish();*/
           Log.e("multiplae image==> " , list.toString());
        }
    }
    private void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            Log.e("image url====> " , imageUri.toString());
          /*  Intent i = new Intent(this, DataAddActivity.class);
            i.putExtra(PARAM_IMAGE, imageUri.toString());
            startActivity(i);
            finish();*/
        } else {
            Toast.makeText(this, "Error occured, URI is invalid", Toast.LENGTH_LONG).show();
        }

    }


    private void initView() {


        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        String userImage = myPreferenceData.getData(BaseUrl.TAG_IMAGE);
        String userName = myPreferenceData.getData(BaseUrl.FULL_NAME);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        setDarkModeView();

        if (!Utility.isNullOrEmpty(userImage)) {
            Glide.with(this).load(userImage)
                    .into(ivUserImage);
        }
        tvName.setText(userName);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        status = bundle.getInt(BaseUrl.ASK_STATUS, 0);
        tagId = bundle.getString(BaseUrl.TAG_ID);
        switch (status) {
            case 0:
                editTitle.setHint(getString(R.string.question_title_hint));
                radioGroupQuestion.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.GONE);
                tvPost.setText(getString(R.string.ask));
                checkCover.setVisibility(View.GONE);
                tvPostDescription.setText("Do’s &amp; Don'ts#1 Begin your question with What,Why, When, How, If, Can " +
                        "etc.#2 Do not ask Coaching Reviews. This is not a coaching Review Website. Please do not ask or post " +
                        "Coaching Review. #3 Do not promote your website or blog Post. Anything promotion");
                type =0;
                break;
            case 1:
                radioGroupQuestion.setVisibility(View.GONE);
                editTitle.setHint(getString(R.string.story_hint));
                checkCover.setVisibility(View.GONE);
                tvTitle.setVisibility(View.VISIBLE);
                tvPost.setText(getString(R.string.publish));
                tvPostDescription.setText("Describe your story in detail. It would help others to understand you better\n");
                type=1;
                break;
            case 2:
                editTitle.setVisibility(View.VISIBLE);
                checkCover.setVisibility(View.GONE);
                checkCover.setVisibility(View.GONE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText(getString(R.string.link));
                editTitle.setHint(getString(R.string.past_link));
                imageSection.setVisibility(View.GONE);
                radioGroupQuestion.setVisibility(View.GONE);
                editTitle.setVisibility(View.GONE);
                tvPostDescription.setText("Paste a link here");
                tvPost.setText(getString(R.string.sharelink));
                llBackCover.setVisibility(View.GONE);
                break;
            case 4:
            case 3:
                editTitle.setHint("Enter title for your post");
                radioGroupQuestion.setVisibility(View.GONE);
                checkCover.setVisibility(View.GONE);
                tvTitle.setVisibility(View.GONE);
                tvPost.setText(getString(R.string.publish));
                type=1;
                break;
            case 11:
                break;

        }
    }

    private void setDarkModeView() {
        if(darkModeStatus == 1){
            rlCreatePostSection.setBackgroundResource(R.color.darkmode_back_color);
            llBackCover.setBackgroundResource(R.drawable.dark_post_border);
            llEditorSection.setBackgroundResource(R.drawable.dark_post_border);
            tvName.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            tvTitle.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            radioQuestion.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            radioDiscussion.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            checkCover.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            radioImage.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            radioColor.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            editTitle.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            editTitle.setHintTextColor(ContextCompat.getColor(this,R.color.light_white));
            editTitle.setBackgroundResource(R.drawable.dark_post_border);
            tvPostDescription.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key_white));


        }else{
            rlCreatePostSection.setBackgroundResource(R.color.back_color);
            llBackCover.setBackgroundResource(R.drawable.border_shape);
            llEditorSection.setBackgroundResource(R.color.white);
            tvName.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            tvTitle.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            radioQuestion.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            radioDiscussion.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            checkCover.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            radioImage.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            radioColor.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            editTitle.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            editTitle.setHintTextColor(ContextCompat.getColor(this,R.color.black_light));
            editTitle.setBackgroundResource(R.color.white);
            tvPostDescription.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key));

        }
    }


    @OnClick(R.id.ivBack)
    public void onClickBack() {
        callIntent();
    }

    @OnClick(R.id.tvPost)
    public void onClikPost() {

        switch (status) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:

                if (Objects.requireNonNull(editTitle.getText()).toString().isEmpty()) {
                    editTitle.setError(getString(R.string.required_field));
                    editTitle.requestFocus();
                    return;
                }

                String description = this.arEditor.getARE().getHtml();
                if (Utility.isNullOrEmpty(description)) {
                    Toast.makeText(this, "Description field is required..!", Toast.LENGTH_LONG).show();
                    return;
                }

                InternetConnection internetConnection = new InternetConnection();
                boolean onlineStatus  = internetConnection.checkConnection(this);
                if(onlineStatus){
                    createStory(editTitle.getText().toString().trim(), description);
                }else{
                    MyUtility myUtility = new MyUtility();
                    myUtility.checkConnection(this);
                }


                break;
        }

      //  Log.e("data===========> ", this.arEditor.getARE().getHtml());

    }

    private void createStory(String title, String description) {

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait..!");
        dialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("user_id", String.valueOf(loginUserId));
        params.put("title", title);
        params.put("description", description);
        params.put("tag_id", tagId);
        params.put("is_discussion", String.valueOf(isDiscussion)); // 0 = Question  , 1 = Discussion
        params.put("type", String.valueOf(type));// 0 = Question , 1 = Story
        params.put("file_upload", "");

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<CreateStoryModel> call = apiInterface.createStory(params);
        call.enqueue(new Callback<CreateStoryModel>() {
            @Override
            public void onResponse(@NotNull Call<CreateStoryModel> call, @NotNull Response<CreateStoryModel> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        callIntent();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CreateStoryModel> call, @NotNull Throwable t) {
                dialog.dismiss();
            }
        });
    }

    private void callIntent() {
        switch (status){
            case 0:
            case 1:
            case 2:
               /* Intent home = new Intent(this , WelcomeHomeActivity.class);
                startActivity(home);
                finish();*/
               finish();
            break ;

            case 3:
                Intent page = new Intent(this , MyPagesPostActivity.class);
                startActivity(page);
                finish();
                break;
            case 4:
                Intent academy = new Intent(this , AcademyActivity.class);
                startActivity(academy);
                finish();
                break;
        }


    }

    @OnClick(R.id.tvCamera)
    public void onClickCamera() {
        boolean cameraStatus = new PermissionUtility().checkCameraPermissions(this, CreateStoryOrQuestionActivity.this);
        if (cameraStatus) {
            startCamera();
        } else {
            Toast.makeText(getApplicationContext(), "Please Granted Camera Permission..!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tvGallery)
    public void onClickGallery() {
        boolean cameraStatus = new PermissionUtility().checkCameraPermissions(this, CreateStoryOrQuestionActivity.this);
        if (cameraStatus) {
            galleryCamera();
        } else {
            Toast.makeText(getApplicationContext(), "Please Granted Gallery Permission..!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tvFile)
    public void onClickFile() {

    }

    private void galleryCamera() {
        Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_REQUEST);
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);

        Uri fileProvider = FileProvider.getUriForFile(CreateStoryOrQuestionActivity.this, "com.forumias.chat.provider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory");
        }
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callIntent();
       finish();
    }


    private void colorBox() {
        List<DefaultPojo> colorList = new ArrayList<>();
        DefaultPojo model1 = new DefaultPojo(R.color.c1_green);
        DefaultPojo model2 = new DefaultPojo(R.color.c2_blue);
        DefaultPojo model3 = new DefaultPojo(R.color.c3_yellow);
        DefaultPojo model4 = new DefaultPojo(R.color.c4_red);
        DefaultPojo model5 = new DefaultPojo(R.color.c5_dark_yellow);
        DefaultPojo model6 = new DefaultPojo(R.color.c6_parpal);
        DefaultPojo model7 = new DefaultPojo(R.color.c7_normal_black);
        colorList.add(model1);
        colorList.add(model2);
        colorList.add(model3);
        colorList.add(model4);
        colorList.add(model5);
        colorList.add(model6);
        colorList.add(model7);

        ColorAdapter colorAdapter = new ColorAdapter(colorList, this);
        colorRecyclerView.setAdapter(colorAdapter);

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.arEditor.onActivityResult(requestCode, resultCode, data,"image");
    }


    private void createImageFileUrl(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://forumias.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        APIInterface api = retrofit.create(APIInterface.class);
        Call<String> call = api.postImageFile(part);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("response data===> ", response.body().toString());

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }



}
