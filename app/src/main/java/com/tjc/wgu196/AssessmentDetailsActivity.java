package com.tjc.wgu196;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.tjc.wgu196.utilities.TextFormats;
import com.tjc.wgu196.viewmodels.EditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tjc.wgu196.utilities.Constants.ASSESSMENT_ID_KEY;

public class AssessmentDetailsActivity extends AppCompatActivity {
    @BindView(R.id.ass_detail_date)
    TextView tvAssessmentDate;

    @BindView(R.id.ass_detail_type)
    TextView tvAssessmentType;

    private Toolbar toolbar;
    private int assessmentId;
    private EditorViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initViewModel();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);

        mViewModel.mLiveAssessment.observe(this, assessment -> {
            tvAssessmentDate.setText(TextFormats.fullDateFormat.format(assessment.getDate()));
            tvAssessmentType.setText(assessment.getAssessmentType().toString());
            toolbar.setTitle(assessment.getTitle());
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            assessmentId = extras.getInt(ASSESSMENT_ID_KEY);
            mViewModel.loadAssessmentData(assessmentId);
        } else {
            finish();
        }
    }

    @OnClick(R.id.fab_edit_ass)
    public void openEditActivity() {
        Intent intent = new Intent(this, AssessmentEditActivity.class);
        intent.putExtra(ASSESSMENT_ID_KEY, assessmentId);
        this.startActivity(intent);
        finish();
    }
}
