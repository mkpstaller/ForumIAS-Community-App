package com.forumias.beta.ui.deta.forumias;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.pojo.ReportModel;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportDailogFragment extends DialogFragment {
    @BindView(R.id.editOtherReport)
    AppCompatEditText editOtherReport;
    @BindView(R.id.rdOne)
    AppCompatRadioButton rdOne;



    private int postId , loginUserId;
    private int selectReportOpt;
    private String reportDescription = "";


    public ReportDailogFragment(int postId, int loginUserId) {

        this.loginUserId = loginUserId;
        this.postId = postId;
    }
   /* public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_report_dailog, container, false);
        ButterKnife.bind(this , view);

        if(rdOne.isChecked()){
            selectReportOpt = 1;
        }

        return view;
    }

    @OnClick(R.id.ivClose)
    public void onClickClose(){
        dismiss();
    }

    @OnClick(R.id.rdOne)
    public void OnClickRdOne(){
        selectReportOpt = 1;
        editOtherReport.setVisibility(View.GONE);
        editOtherReport.setText("");

    }
    @OnClick(R.id.rdTwo)
    public void OnClickRdTwo(){
        selectReportOpt = 2;
        editOtherReport.setVisibility(View.GONE);
        editOtherReport.setText("");
    }
    @OnClick(R.id.rdThree)
    public void OnClickRdThree(){
        selectReportOpt = 3;
        editOtherReport.setVisibility(View.GONE);
        editOtherReport.setText("");
    }
    @OnClick(R.id.rdFour)
    public void OnClickRdFour(){
        selectReportOpt = 4;
        editOtherReport.setVisibility(View.GONE);
        editOtherReport.setText("");
    }
    @OnClick(R.id.rdFive)
    public void OnClickRdFive(){
        selectReportOpt = 5;
        editOtherReport.setVisibility(View.VISIBLE);

    }


    @OnClick(R.id.tvSubmit)
    public void onClickSubmit(){
        if(selectReportOpt == 5){
            if(TextUtils.isEmpty(editOtherReport.getText().toString())){
                editOtherReport.setError(getString(R.string.required_field));
                editOtherReport.requestFocus();
                return;
            }
            reportDescription = editOtherReport.getText().toString();
        }
        postReportData();
    }

    private void postReportData() {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Report Submit..! ");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ReportModel> call = apiInterface.postReport(postId ,loginUserId , selectReportOpt  , reportDescription);
        call.enqueue(new Callback<ReportModel>() {
            @Override
            public void onResponse(Call<ReportModel> call, Response<ReportModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        Toast.makeText(getContext() , response.body().getMessage() , Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getContext() , response.body().getMessage() , Toast.LENGTH_LONG).show();
                    }
                }
                dismiss();
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<ReportModel> call, Throwable t) {
                pd.dismiss();
            }
        });
    }

}
