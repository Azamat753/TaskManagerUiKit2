package com.lawlett.taskmanageruikit.achievement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.achievement.adapter.AchievementAdapter;
import com.lawlett.taskmanageruikit.achievement.models.AchievementModel;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.HomeDoneSizePreference;
import com.lawlett.taskmanageruikit.utils.MeetDoneSizePreference;
import com.lawlett.taskmanageruikit.utils.PersonDoneSizePreference;
import com.lawlett.taskmanageruikit.utils.PrivateDoneSizePreference;
import com.lawlett.taskmanageruikit.utils.WorkDoneSizePreference;

import java.util.Calendar;
import java.util.List;

import static com.lawlett.taskmanageruikit.achievement.models.AchievementModel.*;

public class AchievementActivity extends AppCompatActivity {

    private AchievementViewModel mViewModel;
    private RecyclerView recyclerViewPersonal, recyclerViewWork, recyclerViewMeet, recyclerViewHome, recyclerViewDone;
    private AchievementAdapter personalAdapter, workAdapter, meetAdapter, homeAdapter, doneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        initViewModel();
        initRecyclerView();
        subscribeAchievementData();
        insertedAchievements();
    }

    private void insertedAchievements() {
        insertedAchievementsCategory(PersonDoneSizePreference.getInstance(this).getPersonalSize(), Category.PERSONAL);
        insertedAchievementsCategory(WorkDoneSizePreference.getInstance(this).getDataSize(), Category.WORK);
        insertedAchievementsCategory(MeetDoneSizePreference.getInstance(this).getDataSize(), Category.MEET);
        insertedAchievementsCategory(HomeDoneSizePreference.getInstance(this).getDataSize(), Category.HOME);
        insertedAchievementsCategory(PrivateDoneSizePreference.getInstance(this).getDataSize(), Category.DONE);
    }

    private void insertedAchievementsCategory(int size, Category category) {
        List<AchievementModel> dataCategory = App.getDataBase().achievementDao().getAllByCategory(category);

        for (int i = 0; i < size / 5; i++) {
            if (i + 1 > dataCategory.size()) {
                App.getDataBase().achievementDao().insert(new AchievementModel(Calendar.getInstance().getTime(), (i + 1) * 5, category));
            }
        }
    }

    private void initRecyclerView() {
        initRecyclerViews();
        initAdapters();
        setAdapters();
    }

    private void setAdapters() {
        recyclerViewPersonal.setAdapter(personalAdapter);
        recyclerViewWork.setAdapter(workAdapter);
        recyclerViewMeet.setAdapter(meetAdapter);
        recyclerViewHome.setAdapter(homeAdapter);
        recyclerViewDone.setAdapter(doneAdapter);
    }

    private void initAdapters() {
        personalAdapter = new AchievementAdapter();
        workAdapter = new AchievementAdapter();
        meetAdapter = new AchievementAdapter();
        homeAdapter = new AchievementAdapter();
        doneAdapter = new AchievementAdapter();
    }

    private void initRecyclerViews() {
        recyclerViewPersonal = findViewById(R.id.recycler_view_personal);
        recyclerViewWork = findViewById(R.id.recycler_view_work);
        recyclerViewMeet = findViewById(R.id.recycler_view_meet);
        recyclerViewHome = findViewById(R.id.recycler_view_home);
        recyclerViewDone = findViewById(R.id.recycler_view_done);

    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(App.instance))
                .get(AchievementViewModel.class);
    }

    private void subscribeAchievementData() {
        mViewModel.data.observe(this, achievementModels -> {
            personalAdapter.clearAll();
            workAdapter.clearAll();
            meetAdapter.clearAll();
            homeAdapter.clearAll();
            doneAdapter.clearAll();
            for (AchievementModel achievementModel : achievementModels) {
                switch (achievementModel.getCategory()) {
                    case PERSONAL:
                        personalAdapter.addItem(achievementModel);
                        break;
                    case WORK:
                        workAdapter.addItem(achievementModel);
                        break;
                    case MEET:
                        meetAdapter.addItem(achievementModel);
                        break;
                    case HOME:
                        homeAdapter.addItem(achievementModel);
                        break;
                    case DONE:
                        doneAdapter.addItem(achievementModel);
                }
            }
        });
    }

}
