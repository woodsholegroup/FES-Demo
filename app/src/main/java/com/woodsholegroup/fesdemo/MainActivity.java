package com.woodsholegroup.fesdemo;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.woodsholegroup.fesdemo.api.Broadcasts;
import com.woodsholegroup.fesdemo.api.ErrorCode;
import com.woodsholegroup.fesdemo.api.FESContract;
import com.woodsholegroup.fesdemo.api.Intents;
import com.woodsholegroup.fesdemo.api.model.IridiumStatus;
import com.woodsholegroup.fesdemo.util.FileManager;
import com.woodsholegroup.fesdemo.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        CreateFileDialog.CreateFileListener, FESEventMonitor.FESMonitoringListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String KEY_SELECTED_TABLE = "KEY_SELECTED_TABLE";

    private FESEventAdapter eventAdapter;
    private RecyclerView recyclerView;
    private FESEventMonitor fesEventMonitor;
    private FESDatabase fesDatabase;
    private RadioGroup dbRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide keyboard at opening
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setupViewModel();

        final EditText statusYearText = findViewById(R.id.text_status_date_year);
        final EditText statusDayText = findViewById(R.id.text_status_date_day);
        final EditText statusHourText = findViewById(R.id.text_status_date_hour);
        final EditText statusMinText = findViewById(R.id.text_status_date_min);
        final Spinner statusMonthSpinner = findViewById(R.id.text_status_date_month);

        verifyStoragePermission();

        statusMonthSpinner.setAdapter(new MonthSpinnerAdapter(this));

        Calendar calendar = Calendar.getInstance();
        statusYearText.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        statusDayText.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        statusHourText.setText(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
        statusMinText.setText(String.valueOf(calendar.get(Calendar.MINUTE)));
        statusMonthSpinner.setSelection(calendar.get(Calendar.MONTH));

        Button startScanButton = findViewById(R.id.button_start_scan);
        startScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestFileScanStart();
            }
        });

        Button createFileButton = findViewById(R.id.button_create_file);
        createFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateFileClicked();
            }
        });

        Button getStatusButton = findViewById(R.id.button_get_status);
        getStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLastConnectionStatus();
            }
        });

        Button getStatusHistoryButton = findViewById(R.id.button_get_status_history);
        getStatusHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.parseInt(statusYearText.getText().toString()),
                        statusMonthSpinner.getSelectedItemPosition(),
                        Integer.parseInt(statusDayText.getText().toString()),
                        Integer.parseInt(statusHourText.getText().toString()),
                        Integer.parseInt(statusMinText.getText().toString()));
                showStatusAtDate(cal.getTime());
            }
        });

        dbRadioGroup = findViewById(R.id.radioGroup_db_selection);
        dbRadioGroup.setOnCheckedChangeListener(this);
        if (savedInstanceState != null) {
            ((RadioButton) dbRadioGroup.getChildAt(savedInstanceState.getInt(KEY_SELECTED_TABLE, 0)))
                    .setChecked(true);
        }

        setRecyclerView();

        fesEventMonitor = new FESEventMonitor();
        fesDatabase = new FESDatabase(getContentResolver());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED_TABLE, getSelectedRadioButtonIndex());
    }

    private void setupViewModel() {
        AddFESEventViewModel eventViewModel = ViewModelProviders.of(this).get(AddFESEventViewModel.class);
        eventViewModel.getEvents().observe(this, new Observer<List<FESEvent>>() {
            @Override
            public void onChanged(@Nullable List<FESEvent> fesEvents) {
                // Update UI
                if (fesEvents != null) {
                    eventAdapter.addEvent(fesEvents.get(fesEvents.size() - 1));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fesEventMonitor.register(this, this);

        showDatabaseContent(FESContract.Outbox.CONTENT_URI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        fesEventMonitor.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fesEventMonitor.clear();
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new FESEventAdapter();
        recyclerView.setAdapter(eventAdapter);

        // Set fixed height
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = 300;
        recyclerView.setLayoutParams(params);
    }

    private void requestFileScanStart() {
        Intent intent = new Intent(Intents.ACTION_START_SCAN);
        intent.setPackage(FESEventMonitor.getFESInstalledApp(this));
        sendBroadcast(intent);
    }

    private void requestLastConnectionStatus() {
        Intent intent = new Intent(Intents.ACTION_BROADCAST_IRIDIUM_STATUS);
        intent.setPackage(FESEventMonitor.getFESInstalledApp(this));
        sendBroadcast(intent);
    }

    private void requestFileDeletion(@NonNull String uuid) {
        Intent intent = new Intent(Intents.ACTION_DELETE_FILE_REF);
        intent.putExtra(Broadcasts.EXTRA_UUID, uuid);
        intent.setPackage(FESEventMonitor.getFESInstalledApp(this));
        sendBroadcast(intent);
    }

    @Override
    public void onFileScanStarted() {
        addEvent("File scan started", FESEvent.OTHER);
    }

    @Override
    public void onFileScanFinished(@NonNull ArrayList<String> processedFiles) {
        addEvent("File scan finished. New files: " + processedFiles.toString(), FESEvent.OTHER);
    }

    @Override
    public void onFileReceived(@NonNull String filename, @NonNull String uuid) {
        beep();
        addEvent("File received: " + filename + ". UUID: " + uuid, FESEvent.FILE_RECEPTION);
        Toast.makeText(this, "NEW file received", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFileStatusChanged(@NonNull String filename, @NonNull String changedStatus,
                                    @NonNull String uuid) {
        beep();
        addEvent("Status update: " + filename + "," + uuid + "," + changedStatus, FESEvent.FILE_SENDING);
    }

    @Override
    public void onIridiumStatusReceived(@NonNull IridiumStatus status) {
        beep();
        if (IridiumStatus.STATE_CONNECTED == status.getIridiumStatus()) {
            addEvent("Iridium position received: " + status.getLatitude()
                    + ", " + status.getLongitude()
                    + ", " + status.getIridiumStatus()
                    + ", " + status.getRssi()
                    + ", " + status.getInboundData()
                    + ", " + status.getOutboundData()
                    + ", " + new Date(status.getCreationDate()), FESEvent.IRIDIUM_DATA);
        } else {
            addEvent("Iridium position received. Status: " + status, FESEvent.IRIDIUM_DATA);
        }
    }

    @Override
    public void onErrorReceived(int errorCode, String errorMessage) {
        beep();
        addEvent("Error " + errorCode + " " + errorMessage, FESEvent.ERROR);
        ErrorCode error = ErrorCode.fromInt(errorCode);
        switch (error) {
            case BAD_INPUT_PARAMETER:
                // Invalid parameters were supplied to FES
                break;
            case FILE_TOO_LARGE:
                // File exceeds the FES limit (10 kB)
                break;
            case NO_READ_ACCESS:
                // Read permission is missing
                break;
            case FILE_IS_DIRECTORY:
                // The file is a directory and therefore cannot be sent
                break;
            case FILE_EMPTY:
                // The file is empty
                break;
            case INTERNAL_ERROR:
                // The FES service encountered an error
                break;
        }
    }

    private void onCreateFileClicked() {
        Util.showDialogFragment(getSupportFragmentManager(), CreateFileDialog.newInstance(),
                CreateFileDialog.DIALOG_TAG);
    }

    @Override
    public void onFileContentCreated(@NonNull String content) {
        try {
            File createdFile = FileManager.createFile(FileManager.generateFileName(), content);
            Toast.makeText(this, "File created in FES/Outbox: " + createdFile.getName(),
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating the file", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Adds an event in the list of FES events
     *
     * @param description Description of the event
     * @param eventType   Type of the event. See {@link com.woodsholegroup.fesdemo.FESEvent.EventType}
     */
    private void addEvent(@NonNull String description, @FESEvent.EventType int eventType) {
        addEvent(new FESEvent(description, eventType));
    }

    /**
     * Adds an event in the list of FES events
     *
     * @param event Event to add
     */
    private void addEvent(@NonNull FESEvent event) {
        Log.d(TAG, event.getDescription());
        eventAdapter.addEvent(event);
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(eventAdapter.getItemCount() - 1);
        }
    }

    private void showStatusAtDate(Date date) {
        IridiumStatus status = fesDatabase.getStatusAtDate(date);
        beep();
        if (status != null) {
            addEvent(status.toString(), FESEvent.IRIDIUM_DATA);
        } else {
            addEvent("Not found", FESEvent.IRIDIUM_DATA);
        }
    }

    /**
     * Checks if the app has been granted the {@link Manifest.permission#WRITE_EXTERNAL_STORAGE}
     * permission and if not, requests it
     */
    private void verifyStoragePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    /**
     * Displays the content of the specified database in a table view
     *
     * @param contentUri The URI, using the content:// scheme, for the content to retrieve.
     */
    private void showDatabaseContent(@NonNull Uri contentUri) {
        TableLayout tableLayout = findViewById(R.id.table_database);
        tableLayout.removeAllViews();

        DatabaseListAdapter adapter = new DatabaseListAdapter(contentUri);
        List<TableRow> rows = adapter.getTableRows(this);
        for (TableRow row : rows) {
            tableLayout.addView(row);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_outbox:
                showDatabaseContent(FESContract.Outbox.CONTENT_URI);
                break;
            case R.id.radio_inbox:
                showDatabaseContent(FESContract.Inbox.CONTENT_URI);
                break;
            case R.id.radio_positions:
                showDatabaseContent(FESContract.Status.CONTENT_URI);
                break;
            case R.id.radio_logs:
                showDatabaseContent(FESContract.BroadcastLogs.CONTENT_URI);
                break;
        }
    }

    /**
     * Plays a short "bip" sound
     */
    private void beep() {
        ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP2, 150);
    }

    /**
     * @return The index of the selected radio button
     */
    private int getSelectedRadioButtonIndex() {
        return dbRadioGroup.indexOfChild(findViewById(dbRadioGroup.getCheckedRadioButtonId()));
    }
}
