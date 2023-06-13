package com.example.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ui.adapters.Components_RecyclerViewAdapter;
import com.example.app.ui.models.ComponentsModel;

import java.util.ArrayList;
import java.util.List;

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


        // Prepare your product data as a list of documents
        List<String> documents = prepareProductData();

        // Calculate TF-IDF scores for the product data
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

    private List<String> prepareProductData() {
        List<String> documents = new ArrayList<>();

        // Add your product descriptions or relevant text data here
        documents.add("Product A description");
        documents.add("Product B description");
        // ...

        return documents;
    }
    @Override
    public void onComponentClick(int position) {

        switch (position) {
            case 0:
                //Redirects to the Central Processing Unit Activity
                //Central Processing Unit Activity contains products about the cpu
                Intent cpu = new Intent(this, CentralProcessingUnitActivity.class);

                /*ArrayList<String> recommendedCPUs = getRecommendedItems("CPU");

                // Pass the recommended CPU IDs to the redirected activity
                cpu.putStringArrayListExtra("recommendedItems", recommendedCPUs);
                */

                startActivity(cpu);
                break;
            case 1:
                //Redirects to the CPU Cooler Activity
                //CPU Cooler Activity contains products about the cpu fans / coolers
                Intent board = new Intent(this, MotherboardActivity.class);

                /*ArrayList<String> recommendedCPUs = getRecommendedItems("CPU");

                // Pass the recommended CPU IDs to the redirected activity
                cpu.putStringArrayListExtra("recommendedItems", recommendedCPUs);
                */

                startActivity(board);
                break;
            case 2:
                //Redirects to the Graphics Processing Unit Activity
                //Graphics Processing Unit Activity contains products about the gpu
                Intent gpu = new Intent(this, GraphicsProcessingUnitActivity.class);

                /*ArrayList<String> recommendedCPUs = getRecommendedItems("CPU");

                // Pass the recommended CPU IDs to the redirected activity
                cpu.putStringArrayListExtra("recommendedItems", recommendedCPUs);
                */

                startActivity(gpu);
                break;
            case 3:
                //Redirects to the Hard Disk Drive Activity
                //Hard Disk Drive Activity contains products about the hdd
                Intent ram = new Intent(this, RandomAccessMemoryActivity.class);

                /*ArrayList<String> recommendedCPUs = getRecommendedItems("CPU");

                // Pass the recommended CPU IDs to the redirected activity
                cpu.putStringArrayListExtra("recommendedItems", recommendedCPUs);
                */

                startActivity(ram);
                break;
            case 4:
                //Redirects to the Random Access Memory Activity
                //Random Access Memory Activity contains products about the ram
                Intent hdd = new Intent(this, HardDiskDriveActivity.class);

                /*ArrayList<String> recommendedCPUs = getRecommendedItems("CPU");

                // Pass the recommended CPU IDs to the redirected activity
                cpu.putStringArrayListExtra("recommendedItems", recommendedCPUs);
                */

                startActivity(hdd);
                break;
            case 5:
                //Redirects to the Motherboard Activity
                //Motherboard Activity contains products about the motherboards
                Intent ssd = new Intent(this, SolidStateDriveActivity.class);

                /*ArrayList<String> recommendedCPUs = getRecommendedItems("CPU");

                // Pass the recommended CPU IDs to the redirected activity
                cpu.putStringArrayListExtra("recommendedItems", recommendedCPUs);
                */

                startActivity(ssd);
                break;
            case 6:
                //Redirects to the PC Case Activity
                //PC Case Activity contains products about the pc casings
                Intent cooler = new Intent(this, CpuCoolerActivity.class);

                /*ArrayList<String> recommendedCPUs = getRecommendedItems("CPU");

                // Pass the recommended CPU IDs to the redirected activity
                cpu.putStringArrayListExtra("recommendedItems", recommendedCPUs);
                */

                startActivity(cooler);
                break;
            case 7:
                //Redirects to the Peripherals Activity
                //Peripherals Activity contains products about the peripherals
                Intent psu = new Intent(this, PowerSupplyUnitActivity.class);

                /*ArrayList<String> recommendedCPUs = getRecommendedItems("CPU");

                // Pass the recommended CPU IDs to the redirected activity
                cpu.putStringArrayListExtra("recommendedItems", recommendedCPUs);
                */

                startActivity(psu);
                break;
            case 8:
                //Redirects to the Power Supply Unit Activity
                //Power Supply Unit Activity contains products about the psu
                Intent pc = new Intent(this, PcCaseActivity.class);

                /*ArrayList<String> recommendedCPUs = getRecommendedItems("CPU");

                // Pass the recommended CPU IDs to the redirected activity
                cpu.putStringArrayListExtra("recommendedItems", recommendedCPUs);
                */

                startActivity(pc);
                break;
            case 9:
                //Redirects to the Solid State Drive Activity
                //Solid State Drive Activity contains products about the ssd
                Intent peripherals = new Intent(this, PeripheralsActivity.class);

                /*ArrayList<String> recommendedCPUs = getRecommendedItems("CPU");

                // Pass the recommended CPU IDs to the redirected activity
                cpu.putStringArrayListExtra("recommendedItems", recommendedCPUs);
                */

                startActivity(peripherals);
                break;
        }
    }

   /* private ArrayList<String> getRecommendedItems(String component) {
        // Retrieve the user's preferences (e.g., usage, budget, performance)
        String usage = getUserUsagePreference();
        float budget = getUserBudgetPreference();
        float performance = getUserPerformancePreference();

        // Retrieve the compatibility information for the selected component
        List<String> compatibleItems = getCompatibleItems(component);

        // Prepare the query for retrieving TF-IDF scores
        String query = usage + " " + component;

        // Retrieve the TF-IDF scores for the query
        Map<String, Float> tfidfScores = tfidfHelper.getTFIDFScores(query);

        // Filter and sort the items based on compatibility and TF-IDF scores
        ArrayList<String> recommendedItems = new ArrayList<>();
        for (String item : compatibleItems) {
            if (tfidfScores.containsKey(item)) {
                float compatibilityScore = getCompatibilityScore(item);
                float tfidfScore = tfidfScores.get(item);

                // Calculate the overall score based on compatibility, TF-IDF score, budget, and performance
                float overallScore = compatibilityScore * tfidfScore * (budget / getItemPrice(item)) * performance;

                // Add the item to the recommended list
                recommendedItems.add(item);
            }
        }

        // Sort the recommended items based on the overall score (you can implement a custom comparator for sorting)

        return recommendedItems;
    }

    // Retrieve the user's usage preference
    private String getUserUsagePreference() {
        // Assuming you have a variable to store the user's preference called userUsagePreference
        // Return the stored user's usage preference
        return userUsagePreference;
    }

    // Retrieve the user's budget preference
    private float getUserBudgetPreference() {
        // Assuming you have a variable to store the user's preference called userBudgetPreference
        // Return the stored user's budget preference
        return userBudgetPreference;
    }

    // Retrieve the user's performance preference
    private float getUserPerformancePreference() {
        // Assuming you have a variable to store the user's preference called userPerformancePreference
        // Return the stored user's performance preference
        return userPerformancePreference;
    }

    // Retrieve the compatible items for the selected component
    private List<String> getCompatibleItems(String component) {
        // Assuming you have a method to retrieve compatible items based on the selected component called getCompatibleItemsFromDatabase()
        // Call the method and return the list of compatible items
        return getCompatibleItemsFromDatabase(component);
    }

    // Retrieve the compatibility score for an item
    private float getCompatibilityScore(String item) {
        // Assuming you have a method to retrieve the compatibility score for an item called getCompatibilityScoreFromDatabase()
        // Call the method and return the compatibility score for the item
        return getCompatibilityScoreFromDatabase(item);
    }

    // Retrieve the price of an item
    private float getItemPrice(String item) {
        // Assuming you have a method to retrieve the price of an item called getItemPriceFromDatabase()
        // Call the method and return the price of the item
        return getItemPriceFromDatabase(item);
    }
     */
}