package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.Components_RecyclerViewAdapter;
import com.example.app.ui.models.ComponentsModel;

import java.util.ArrayList;

public class NewBuildActivity extends AppCompatActivity implements Components_RecyclerViewAdapter.OnComponentListener {

    ArrayList<ComponentsModel> componentsModel = new ArrayList<>();
    String email;
    int[] componentImages = {R.drawable.processor_image, R.drawable.motherboard_image, R.drawable.gpu_image, R.drawable.ram_image,
            R.drawable.hdd_image, R.drawable.ssd_image, R.drawable.cpucooler_image, R.drawable.psu_image, R.drawable.pccase_image};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_build);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("Email")) {
            email = bundle.getString("Email");
        }
        Toast.makeText(NewBuildActivity.this, "Email"+email, Toast.LENGTH_SHORT).show();


        RecyclerView recyclerView = findViewById(R.id.nb_recyclerview_view);

        setUpComponentsModel();

        Components_RecyclerViewAdapter adapter = new Components_RecyclerViewAdapter(this, componentsModel, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpComponentsModel() {
        String[] componentNames = getResources().getStringArray(R.array.new_builds);

        for (int i = 0; i < componentNames.length; i++) {
            componentsModel.add(new ComponentsModel(componentNames[i], componentImages[i]));
        }
    }

    @Override
    public void onComponentClick(int position) {

        switch (position) {
            case 0:
                //Redirects to the Central Processing Unit Activity
                Intent cpu = new Intent(this, CentralProcessingUnitActivity.class);
                cpu.putExtra("email", email);
                cpu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(cpu);
                break;
            case 1:
                //Redirects to the Motherboard Activity
                Intent board = new Intent(this, MotherboardActivity.class);
                board.putExtra("email", email);
                board.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(board);
                break;
            case 2:
                //Redirects to the Graphics Processing Unit Activity
                Intent gpu = new Intent(this, GraphicsProcessingUnitActivity.class);
                gpu.putExtra("email", email);
                gpu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(gpu);
                break;
            case 3:
                //Redirects to the Random Access Memory Activity
                Intent ram = new Intent(this, RandomAccessMemoryActivity.class);
                ram.putExtra("email", email);
                ram.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ram);
                break;
            case 4:
                //Redirects to the Hard Disk Drive Activity
                Intent hdd = new Intent(this, HardDiskDriveActivity.class);
                hdd.putExtra("email", email);
                hdd.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(hdd);
                break;
            case 5:
                //Redirects to the Solid State Drive Activity
                Intent ssd = new Intent(this, SolidStateDriveActivity.class);
                ssd.putExtra("email", email);
                ssd.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ssd);
                break;
            case 6:
                //Redirects to the Cpu Cooler Activity
                Intent cooler = new Intent(this, CpuCoolerActivity.class);
                cooler.putExtra("email", email);
                cooler.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(cooler);
                break;
            case 7:
                //Redirects to the Power Supply Unit Activity
                Intent psu = new Intent(this, PowerSupplyUnitActivity.class);
                psu.putExtra("email", email);
                psu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(psu);
                break;
            case 8:
                //Redirects to the Pc Case Activity
                Intent pc = new Intent(this, PcCaseActivity.class);
                pc.putExtra("email", email);
                pc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pc);
                break;
        }
    }



}