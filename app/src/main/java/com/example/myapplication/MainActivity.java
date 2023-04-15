package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import PokeService.PokeService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends AppCompatActivity {

    private PokeService pokeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listViewPokys = findViewById(R.id.listViewPoky);
        List<String> data = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listViewPokys.setAdapter(arrayAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pokeService = retrofit.create(PokeService.class);

        // Call the API endpoint and update the data
        Call<PokyResponse> call = pokeService.getPokemons();
        ((Call<?>) call).enqueue(new Callback<PokyResponse>() {

            public void onResponse(Call<PokyResponse> call, Response<PokyResponse> response) {
                PokyResponse pokemonList = response.body();
                List<Poky> pokys = pokemonList.results();
                for (Poky poky : pokys) {
                    data.add(poky.Getname());
                }
                arrayAdapter.notifyDataSetChanged();
            }


            public void onFailure(Call<PokyResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error loading pokemons", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
