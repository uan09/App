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
            intent.putExtra("email", email); // Pass the email value to NewBuildActivity
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
        retrieveRAMs();
        retrieveHDDs();
        retrieveSSDs();
        retrieveCoolers();
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
                        String product_name = documentSnapshot.getString("product_name");
                        String product_price = documentSnapshot.getString("product_price");
                        
                        Object productImagesObj = documentSnapshot.get("product_image");
                        List<String> productImages = null;
                        if (productImagesObj instanceof List) {
                            productImages = (List<String>) productImagesObj;
                        }
                        String product_image = null;
                        if (productImages != null && !productImages.isEmpty()) {
                            product_image = productImages.get(0);
                        }
                        final String finalProductImage = product_image;

                        String CPU_Cores = documentSnapshot.getString("CPU_Cores");
                        String CPU_Socket = documentSnapshot.getString("CPU_Socket");
                        CpuModel cpuModel = new CpuModel(product_name, finalProductImage, product_price, CPU_Cores, CPU_Socket);
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
                        String productName = documentSnapshot.getString("product_name");
                        String motherboardSocket = documentSnapshot.getString("Motherboard_Socket");
                        String motherboardFormFactor = documentSnapshot.getString("Motherboard_Form_Factor");
                        String motherboardMemoryType = documentSnapshot.getString("Motherboard_Memory_Type");
                        String productPrice = documentSnapshot.getString("product_price");
                        Object productImagesObj = documentSnapshot.get("product_image");
                        List<String> productImages = null;
                        if (productImagesObj instanceof List) {
                            productImages = (List<String>) productImagesObj;
                        }
                        String productImage = null;
                        if (productImages != null && !productImages.isEmpty()) {
                            productImage = productImages.get(0);
                        }
                        MotherboardModel motherboardModel = new MotherboardModel(productName, motherboardSocket, motherboardFormFactor, motherboardMemoryType, productPrice, productImage);
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

    private void retrieveCoolers() {
        firestore.collection("NewBuild")
                .document("CPU Cooler")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<CoolerModel> coolers = new ArrayList<>();
                        String productName = documentSnapshot.getString("product_name");
                        String coolerSocket = documentSnapshot.getString("Cooler_Socket");
                        String productPrice = documentSnapshot.getString("product_price");
                        Object productImagesObj = documentSnapshot.get("product_image");
                        List<String> productImages = null;
                        if (productImagesObj instanceof List) {
                            productImages = (List<String>) productImagesObj;
                        }
                        String productImage = null;
                        if (productImages != null && !productImages.isEmpty()) {
                            productImage = productImages.get(0);
                        }
                        String coolerRPM = documentSnapshot.getString("Cooler_RPM");
                        CoolerModel coolerModel = new CoolerModel(productName, coolerSocket, productPrice, productImage, coolerRPM);
                        coolers.add(coolerModel);
                        cpuCoolerAdapter.setCPUCoolerModels(coolers);
                        cpuCoolerAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("CheckBuildActivity", "No cooler document found");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("CheckBuildActivity", "Error retrieving cooler document", e);
                });
    }

    private void retrieveGPUs() {
        firestore.collection("NewBuild")
                .document("Graphics Card")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<GpuModel> gpus = new ArrayList<>();
                        String productName = documentSnapshot.getString("product_name");
                        String gpuMemoryType = documentSnapshot.getString("GPU__Memory_Type");
                        String gpuChipset = documentSnapshot.getString("GPU_Chipset");
                        String productPrice = documentSnapshot.getString("product_price");
                        Object productImagesObj = documentSnapshot.get("product_image");
                        List<String> productImages = null;
                        if (productImagesObj instanceof List) {
                            productImages = (List<String>) productImagesObj;
                        }
                        String productImage = null;
                        if (productImages != null && !productImages.isEmpty()) {
                            productImage = productImages.get(0);
                        }
                        GpuModel gpuModel = new GpuModel(productName, gpuMemoryType, gpuChipset, productPrice, productImage);
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

    private void retrieveHDDs() {
        firestore.collection("NewBuild")
                .document("HDD")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<HddModel> hdds = new ArrayList<>();
                        String productName = documentSnapshot.getString("product_name");
                        String hddInterface = documentSnapshot.getString("HDD_Interface");
                        String hddCapacity = documentSnapshot.getString("HDD_Capacity");
                        String productPrice = documentSnapshot.getString("product_price");
                        Object productImagesObj = documentSnapshot.get("product_image");
                        List<String> productImages = null;
                        if (productImagesObj instanceof List) {
                            productImages = (List<String>) productImagesObj;
                        }
                        String productImage = null;
                        if (productImages != null && !productImages.isEmpty()) {
                            productImage = productImages.get(0);
                        }
                        HddModel hddModel = new HddModel(productName, hddInterface, hddCapacity, productPrice, productImage);
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

    private void retrieveCases() {
        firestore.collection("NewBuild")
                .document("Computer Case")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<CaseModel> cases = new ArrayList<>();
                        String productName = documentSnapshot.getString("product_name");
                        String caseType = documentSnapshot.getString("Case_Type");
                        String caseFormFactor = documentSnapshot.getString("Case_Form_Factor");
                        String productPrice = documentSnapshot.getString("product_price");
                        Object productImagesObj = documentSnapshot.get("product_image");
                        List<String> productImages = null;
                        if (productImagesObj instanceof List) {
                            productImages = (List<String>) productImagesObj;
                        }
                        String productImage = null;
                        if (productImages != null && !productImages.isEmpty()) {
                            productImage = productImages.get(0);
                        }
                        CaseModel caseModel = new CaseModel(productName, caseType, caseFormFactor, productPrice, productImage);
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

    private void retrievePSUs() {
        firestore.collection("NewBuild")
                .document("PSU")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<PsuModel> psus = new ArrayList<>();
                        String productName = documentSnapshot.getString("product_name");
                        String psuFormFactor = documentSnapshot.getString("PSU_Form_Factor");
                        String psuWattage = documentSnapshot.getString("PSU_Wattage");
                        String productPrice = documentSnapshot.getString("product_price");
                        Object productImagesObj = documentSnapshot.get("product_image");
                        List<String> productImages = null;
                        if (productImagesObj instanceof List) {
                            productImages = (List<String>) productImagesObj;
                        }
                        String productImage = null;
                        if (productImages != null && !productImages.isEmpty()) {
                            productImage = productImages.get(0);
                        }
                        PsuModel psuModel = new PsuModel(productName, psuFormFactor, psuWattage, productPrice, productImage);
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

    private void retrieveRAMs() {
        firestore.collection("NewBuild")
                .document("RAM")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<RamModel> rams = new ArrayList<>();
                        String memoryName = documentSnapshot.getString("product_name");
                        String memoryType = documentSnapshot.getString("Memory_Type");
                        String memoryCapacity = documentSnapshot.getString("Memory_Capacity");
                        Object productImagesObj = documentSnapshot.get("product_image");
                        List<String> productImages = null;
                        if (productImagesObj instanceof List) {
                            productImages = (List<String>) productImagesObj;
                        }
                        String productImage = null;
                        if (productImages != null && !productImages.isEmpty()) {
                            productImage = productImages.get(0);
                        }
                        String productPrice = documentSnapshot.getString("product_price");
                        RamModel ramModel = new RamModel(memoryName, memoryType, memoryCapacity, productImage, productPrice);
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

    private void retrieveSSDs() {
        firestore.collection("NewBuild")
                .document("SSD")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<SsdModel> ssds = new ArrayList<>();
                        String productName = documentSnapshot.getString("product_name");
                        String ssdInterface = documentSnapshot.getString("SSD_Interface");
                        String ssdCapacity = documentSnapshot.getString("SSD_Capacity");
                        String productPrice = documentSnapshot.getString("product_price");
                        Object productImagesObj = documentSnapshot.get("product_image");
                        List<String> productImages = null;
                        if (productImagesObj instanceof List) {
                            productImages = (List<String>) productImagesObj;
                        }
                        String productImage = null;
                        if (productImages != null && !productImages.isEmpty()) {
                            productImage = productImages.get(0);
                        }
                        SsdModel ssdModel = new SsdModel(productName, ssdInterface, ssdCapacity, productPrice, productImage);
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
}