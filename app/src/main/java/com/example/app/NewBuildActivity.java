package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.app.ui.adapters.Components_RecyclerViewAdapter;
import com.example.app.ui.models.ComponentsModel;

import java.util.ArrayList;

public class NewBuildActivity extends AppCompatActivity implements Components_RecyclerViewAdapter.OnComponentListener {

    ArrayList<ComponentsModel> componentsModel = new ArrayList<>();

    int[] componentImages = {R.drawable.processor_image, R.drawable.cpucooler_image, R.drawable.gpu_image,
            R.drawable.hdd_image, R.drawable.ram_image, R.drawable.motherboard_image, R.drawable.pccase_image,
            R.drawable.mouse_image, R.drawable.psu_image, R.drawable.ssd_image};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_build);

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
                //Central Processing Unit Activity contains products about the cpu
                Intent cpu = new Intent(this, CentralProcessingUnitActivity.class);
                startActivity(cpu);
                break;
            case 1:
                //Redirects to the CPU Cooler Activity
                //CPU Cooler Activity contains products about the cpu fans / coolers
                Intent cooler = new Intent(this, CpuCoolerActivity.class);
                startActivity(cooler);
                break;
            case 2:
                //Redirects to the Graphics Processing Unit Activity
                //Graphics Processing Unit Activity contains products about the gpu
                Intent gpu = new Intent(this, GraphicsProcessingUnitActivity.class);
                startActivity(gpu);
                break;
            case 3:
                //Redirects to the Hard Disk Drive Activity
                //Hard Disk Drive Activity contains products about the hdd
                Intent hdd = new Intent(this, HardDiskDriveActivity.class);
                startActivity(hdd);
                break;
            case 4:
                //Redirects to the Random Access Memory Activity
                //Random Access Memory Activity contains products about the ram
                Intent ram = new Intent(this, RandomAccessMemoryActivity.class);
                startActivity(ram);
                break;
            case 5:
                //Redirects to the Motherboard Activity
                //Motherboard Activity contains products about the motherboards
                Intent board = new Intent(this, MotherboardActivity.class);
                startActivity(board);
                break;
            case 6:
                //Redirects to the PC Case Activity
                //PC Case Activity contains products about the pc casings
                Intent pc = new Intent(this, PcCaseActivity.class);
                startActivity(pc);
                break;
            case 7:
                //Redirects to the Peripherals Activity
                //Peripherals Activity contains products about the peripherals
                Intent peripherals = new Intent(this, PeripheralsActivity.class);
                startActivity(peripherals);
                break;
            case 8:
                //Redirects to the Power Supply Unit Activity
                //Power Supply Unit Activity contains products about the psu
                Intent psu = new Intent(this, PowerSupplyUnitActivity.class);
                startActivity(psu);
                break;
            case 9:
                //Redirects to the Solid State Drive Activity
                //Solid State Drive Activity contains products about the ssd
                Intent ssd = new Intent(this, SolidStateDriveActivity.class);
                startActivity(ssd);
                break;
        }
    }
}