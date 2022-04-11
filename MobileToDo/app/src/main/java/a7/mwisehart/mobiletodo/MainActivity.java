package a7.mwisehart.mobiletodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnItemListener {

    TabLayout tabLists;
    EditText txtToDoItem;
    RecyclerView listItems;
    private String selectedTab;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ArrayList<Item> items = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedTab = getResources().getString(R.string.home);

        listItems = findViewById(R.id.listItems);
        listItems.setAdapter(new RecycleAdapter(this, items, this));
        listItems.setLayoutManager(new LinearLayoutManager(this));

        getItems();
        tabLists = findViewById(R.id.tabLists);
        tabLists.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = Objects.requireNonNull(tab.getText()).toString();
                Log.i("TAB_CHANGED", selectedTab);
                getItems();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        txtToDoItem = findViewById(R.id.txtToDoItem);
        txtToDoItem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Item tmp = new Item(txtToDoItem.getText().toString());
                addItem(tmp);
                txtToDoItem.setText(null);
                return false;
            }
        });
    }

    public void onAddClicked(View view){
        txtToDoItem.onEditorAction(EditorInfo.IME_ACTION_DONE);

    }
    //firebase functions here
    private void addItem(Item item){
        try {
            String path = URLEncoder.encode(item.getDesc(), String.valueOf(StandardCharsets.UTF_8));
            DatabaseReference ref = database.getReference(selectedTab + "/" + path);
            ref.setValue(item);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void getItems(){
        DatabaseReference ref = database.getReference(selectedTab);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                Iterator<DataSnapshot> itr = snapshot.getChildren().iterator();
                while(itr.hasNext()){
                    HashMap val = (HashMap) itr.next().getValue();
                    Item tmp = new Item(val.get("desc").toString());
                    items.add(tmp);
                }
                listItems.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        Log.i("ITEM_CLICKED", items.get(position).getDesc());
        try{
            String path = URLEncoder.encode(items.get(position).getDesc(), String.valueOf(StandardCharsets.UTF_8));
            DatabaseReference ref = database.getReference(selectedTab + "/" + path);
            ref.removeValue();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}