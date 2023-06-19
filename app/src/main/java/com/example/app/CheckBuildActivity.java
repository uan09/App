package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.CaseAdapter;
import com.example.app.ui.adapters.CoolerAdapter;
import com.example.app.ui.adapters.CpuAdapter;
import com.example.app.ui.adapters.GpuAdapter;
import com.example.app.ui.adapters.HddAdapter;
import com.example.app.ui.adapters.MotherboardAdapter;
import com.example.app.ui.adapters.PsuAdapter;
import com.example.app.ui.adapters.RamAdapter;
import com.example.app.ui.adapters.SsdAdapter;
import com.example.app.ui.models.CaseModel;
import com.example.app.ui.models.CoolerModel;
import com.example.app.ui.models.CpuModel;
import com.example.app.ui.models.GpuModel;
import com.example.app.ui.models.HddModel;
import com.example.app.ui.models.MotherboardModel;
import com.example.app.ui.models.PsuModel;
import com.example.app.ui.models.RamModel;
import com.example.app.ui.models.SsdModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CheckBuildActivity extends AppCompatActivity {
    private RecyclerView processorRecyclerView, motherboardRecyclerView, gpuRecyclerView, ramRecyclerView, hddRecyclerView;
    private RecyclerView ssdRecyclerView, cpuCoolerRecyclerView, psuRecyclerView, computerCaseRecyclerView;

    private CpuAdapter processorAdapter;
    private MotherboardAdapter motherboardAdapter;
    private GpuAdapter gpuAdapter;
    private RamAdapter ramAdapter;
    private HddAdapter hddAdapter;
    private SsdAdapter ssdAdapter;
    private CoolerAdapter cpuCoolerAdapter;
    private PsuAdapter psuAdapter;
    private CaseAdapter computerCaseAdapter;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_build);

        String email = getIntent().getStringExtra("email");
        if (email != null) {
            Toast.makeText(CheckBuildActivity.this, "mail" + email, Toast.LENGTH_SHORT).show();
        }

        ImageView back = findViewById(R.id.menu_back);
        back.setOnClickListener(view -> {

            Intent intent = new Intent(this, NewBuildActivity.class);
            intent.putExtra("Email", email); // Pass the email value to NewBuildActivity
            startActivity(intent);

        });

        processorRecyclerView = findViewById(R.id.processor);
        motherboardRecyclerView = findViewById(R.id.motherboard);
        gpuRecyclerView = findViewById(R.id.gpu);
        ramRecyclerView = findViewById(R.id.ram);
        hddRecyclerView = findViewById(R.id.hdd);
        ssdRecyclerView = findViewById(R.id.ssd);
        cpuCoolerRecyclerView = findViewById(R.id.cooler);
        psuRecyclerView = findViewById(R.id.psu);
        computerCaseRecyclerView = findViewById(R.id.pccase);

        processorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        motherboardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        gpuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ramRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hddRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ssdRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cpuCoolerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        psuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        computerCaseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        processorAdapter = new CpuAdapter(this, new ArrayList<>());
        motherboardAdapter = new MotherboardAdapter(this, new ArrayList<>());
        gpuAdapter = new GpuAdapter(this, new ArrayList<>());
        ramAdapter = new RamAdapter(this, new ArrayList<>());
        hddAdapter = new HddAdapter(this, new ArrayList<>());
        ssdAdapter = new SsdAdapter(this, new ArrayList<>());
        cpuCoolerAdapter = new CoolerAdapter(this, new ArrayList<>());
        psuAdapter = new PsuAdapter(this, new ArrayList<>());
        computerCaseAdapter = new CaseAdapter(this, new ArrayList<>());

        processorRecyclerView.setAdapter(processorAdapter);
        motherboardRecyclerView.setAdapter(motherboardAdapter);
        gpuRecyclerView.setAdapter(gpuAdapter);
        ramRecyclerView.setAdapter(ramAdapter);
        hddRecyclerView.setAdapter(hddAdapter);
        ssdRecyclerView.setAdapter(ssdAdapter);
        cpuCoolerRecyclerView.setAdapter(cpuCoolerAdapter);
        psuRecyclerView.setAdapter(psuAdapter);
        computerCaseRecyclerView.setAdapter(computerCaseAdapter);

        firestore = FirebaseFirestore.getInstance();

        retrieveProcessors();
        retrieveMotherboards();
        retrieveGPUs();
        retrieveRAM();
        retrieveHDDs();
        retrieveSSDs();
        retrieveCPUCoolers();
        retrievePSUs();
        retrieveCases();
    }

    private void retrieveProcessors() {
        firestore.collection("NewBuild")
                .document("Processor")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<CpuModel> processors = new ArrayList<>();
                        CpuModel cpuModel = documentSnapshot.toObject(CpuModel.class);
                        processors.add(cpuModel);
                        processorAdapter.setProcessorModels(processors);
                        processorAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("CheckBuildActivity", "No processor document found");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("CheckBuildActivity", "Error retrieving processor document", e);
                });
    }


    private void retrieveMotherboards() {
        firestore.collection("NewBuild")
                .document("Motherboard")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<MotherboardModel> motherboards = new ArrayList<>();
                        MotherboardModel motherboardModel = documentSnapshot.toObject(MotherboardModel.class);
                        motherboards.add(motherboardModel);
                        motherboardAdapter.setMotherboardModels(motherboards);
                        motherboardAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("CheckBuildActivity", "No motherboard document found");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("CheckBuildActivity", "Error retrieving motherboard document", e);
                });
    }

// Similar modifications for other categories (GPUs, RAM, HDDs, SSDs, CPU Coolers, PSUs, Computer Cases)...

    private void retrieveGPUs() {
        firestore.collection("NewBuild")
                .document("Graphics Card")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<GpuModel> gpus = new ArrayList<>();
                        GpuModel gpuModel = documentSnapshot.toObject(GpuModel.class);
                        gpus.add(gpuModel);
                        gpuAdapter.setGPUModels(gpus);
                        gpuAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("CheckBuildActivity", "No GPU document found");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("CheckBuildActivity", "Error retrieving GPU document", e);
                });
    }

    private void retrieveRAM() {
        firestore.collection("NewBuild")
                .document("RAM")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<RamModel> rams = new ArrayList<>();
                        RamModel ramModel = documentSnapshot.toObject(RamModel.class);
                        rams.add(ramModel);
                        ramAdapter.setRAMModels(rams);
                        ramAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("CheckBuildActivity", "No RAM document found");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("CheckBuildActivity", "Error retrieving RAM document", e);
                });
    }

    private void retrieveHDDs() {
        firestore.collection("NewBuild")
                .document("HDD")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<HddModel> hdds = new ArrayList<>();
                        HddModel hddModel = documentSnapshot.toObject(HddModel.class);
                        hdds.add(hddModel);
                        hddAdapter.setHDDModels(hdds);
                        hddAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("CheckBuildActivity", "No HDD document found");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("CheckBuildActivity", "Error retrieving HDD document", e);
                });
    }

    private void retrieveSSDs() {
        firestore.collection("NewBuild")
                .document("SSD")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<SsdModel> ssds = new ArrayList<>();
                        SsdModel ssdModel = documentSnapshot.toObject(SsdModel.class);
                        ssds.add(ssdModel);
                        ssdAdapter.setSSDModels(ssds);
                        ssdAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("CheckBuildActivity", "No SSD document found");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("CheckBuildActivity", "Error retrieving SSD document", e);
                });
    }

    private void retrieveCPUCoolers() {
        firestore.collection("NewBuild")
                .document("CPU Cooler")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<CoolerModel> cpuCoolers = new ArrayList<>();
                        CoolerModel cpuCoolerModel = documentSnapshot.toObject(CoolerModel.class);
                        cpuCoolers.add(cpuCoolerModel);
                        cpuCoolerAdapter.setCPUCoolerModels(cpuCoolers);
                        cpuCoolerAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("CheckBuildActivity", "No CPU Cooler document found");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("CheckBuildActivity", "Error retrieving CPU Cooler document", e);
                });
    }

    private void retrievePSUs() {
        firestore.collection("NewBuild")
                .document("PSU")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<PsuModel> psus = new ArrayList<>();
                        PsuModel psuModel = documentSnapshot.toObject(PsuModel.class);
                        psus.add(psuModel);
                        psuAdapter.setPSUModels(psus);
                        psuAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("CheckBuildActivity", "No PSU document found");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("CheckBuildActivity", "Error retrieving PSU document", e);
                });
    }

    private void retrieveCases() {
        firestore.collection("NewBuild")
                .document("Computer Case")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<CaseModel> cases = new ArrayList<>();
                        CaseModel caseModel = documentSnapshot.toObject(CaseModel.class);
                        cases.add(caseModel);
                        computerCaseAdapter.setCaseModels(cases);
                        computerCaseAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("CheckBuildActivity", "No case document found");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("CheckBuildActivity", "Error retrieving case document", e);
                });
    }
}